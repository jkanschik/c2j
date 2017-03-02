package c2j;

import io.proleap.cobol.asg.applicationcontext.CobolParserContext;
import io.proleap.cobol.asg.applicationcontext.CobolParserContextFactory;
import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.Program;
import io.proleap.cobol.preprocessor.CobolPreprocessor.CobolSourceFormatEnum;

import java.io.File;
import java.io.IOException;

import c2j.visitors.TopLevelDataStructureVisitor;

public class C2J {

	public static void main(String[] args) throws IOException {

		CobolParserContextFactory.configureDefaultApplicationContext();

		// generate ASG from plain COBOL code
		java.io.File inputFile = new File("src/test/resources/cobol/test1.cbl");
		CobolSourceFormatEnum format = CobolSourceFormatEnum.VARIABLE;
		Program program = CobolParserContext.getInstance().getParserRunner().analyzeFile(inputFile, format);


		for (CompilationUnit compilationUnit2 : program.getCompilationUnits()) {
			CompilationUnitContainer container = new CompilationUnitContainer("c2j.example", compilationUnit2.getName());
			new TopLevelDataStructureVisitor(container).visit(compilationUnit2.getCtx());
			new CUPrinter().print(container);
		}
	
	}

}
