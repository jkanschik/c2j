package c2j.visitors;

import io.proleap.cobol.Cobol85BaseVisitor;
import io.proleap.cobol.Cobol85Parser.DataDescriptionEntryFormat1Context;
import io.proleap.cobol.asg.applicationcontext.CobolParserContext;
import io.proleap.cobol.asg.metamodel.data.datadescription.DataDescriptionEntry;
import io.proleap.cobol.asg.metamodel.data.datadescription.DataDescriptionEntryGroup;
import c2j.CompilationUnitContainer;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;

public class TopLevelDataStructureVisitor extends Cobol85BaseVisitor<Boolean> {
	private long fillerCnt = 1;
	private CompilationUnitContainer container;

	public TopLevelDataStructureVisitor(CompilationUnitContainer container) {
		this.container = container;
	}
	
	@Override
	public Boolean visitDataDescriptionEntryFormat1(DataDescriptionEntryFormat1Context ctx) {
		DataDescriptionEntry entry = (DataDescriptionEntry) CobolParserContext.getInstance().getASGElementRegistry()
				.getASGElement(ctx);

		if (entry.getType() == DataDescriptionEntry.Type.GROUP) {
			DataDescriptionEntryGroup group = (DataDescriptionEntryGroup) entry;
			DataDescriptionEntryGroup parent = group.getParentDataDescriptionEntryGroup();
			// If the group is not used, we can ignore it:
			// BUGGY: if the group is used only on the left hand side of a move, getCalls is zero, but it shouldn't be skipped!
//			if (group.getCalls().size() == 0) {
//				System.out.println("Skipping unused variable " + group.getName());
//				return true;
//			}
			if (parent == null) {
				// Top level elements
				fillerCnt = 1;
				ClassOrInterfaceDeclaration mainClass = container.getMainClass();
				if (group.getDataDescriptionEntries().size() == 0) {
					// has no children, we can simply add it:
					if (group.getRedefinesClause() == null) {
						FieldDeclaration field = mainClass.addField("Object", entry.getName(), Modifier.PRIVATE);
						field.createGetter();
						field.createSetter();
					} else {
						MethodDeclaration getter = mainClass.addMethod("get" + entry.getName(), Modifier.PUBLIC);
				        BlockStmt blockStmt = new BlockStmt();
				        getter.setBody(blockStmt);
				        blockStmt.addStatement(new ReturnStmt(group.getRedefinesClause().getRedefinesCall().getName()));
					}
					
				} else {
					// it has lower level entries, create an own class
					ClassOrInterfaceDeclaration datastructureClass = container
							.createDataStructureClass(group.getName());
					mainClass.addField(datastructureClass.getName().toString(), entry.getName(), Modifier.PRIVATE);
				}
			} else {
				// nested element
				ClassOrInterfaceDeclaration parentDatastructure = container.getDataStructureClass(parent.getName());
				String name = group.getName();
				if (name == null) {
					parentDatastructure.addField("Object", "filler_" + fillerCnt, Modifier.PRIVATE);
					fillerCnt++;
				} else {
					if (group.getDataDescriptionEntries().size() == 0) {
						if (group.getRedefinesClause() == null) {
							FieldDeclaration field = parentDatastructure.addField("Object", name, Modifier.PRIVATE);
							field.createGetter();
							field.createSetter();
						} else {
							MethodDeclaration getter = parentDatastructure.addMethod("get" + name, Modifier.PUBLIC);
					        BlockStmt blockStmt = new BlockStmt();
					        getter.setBody(blockStmt);
					        blockStmt.addStatement(new ReturnStmt(group.getRedefinesClause().getRedefinesCall().getName()));
						}
					} else {
						ClassOrInterfaceDeclaration datastructureClass = container.createDataStructureClass(name);
						parentDatastructure.addField(datastructureClass.getName().toString(), name, Modifier.PRIVATE);
					}
				}
			}
		}
		return visitChildren(ctx);
	}

}
