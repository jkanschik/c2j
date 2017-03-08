package c2j.visitors;

import c2j.CompilationUnitContainer;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithMembers;
import com.github.javaparser.ast.stmt.BlockStmt;
import io.proleap.cobol.Cobol85Parser;
import io.proleap.cobol.asg.metamodel.Program;
import io.proleap.cobol.asg.metamodel.procedure.move.MoveStatement;
import io.proleap.cobol.asg.metamodel.procedure.move.MoveTo;
import io.proleap.cobol.asg.visitor.impl.CobolCompilationUnitVisitorImpl;
import io.proleap.cobol.asg.visitor.impl.CobolProcedureStatementVisitorImpl;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;

public class CompilationUnitVisitor extends CobolProcedureStatementVisitorImpl {
	private CompilationUnitContainer container;

	public CompilationUnitVisitor(CompilationUnitContainer container) {
		this.container = container;
	}

	public Boolean visitMoveStatement(@NotNull Cobol85Parser.MoveStatementContext ctx) {
		String move = ctx.depth() +  " MOVE " + ctx.moveToStatement().moveToSendingArea().getText() +
			" TO " + ctx.moveToStatement().identifier().get(0).getText();
		System.out.println("Processing move statement: " + move);

		MethodDeclaration mainMethod = container.getMainMethod();
		BlockStmt block = mainMethod.getBody().get();
		MethodCallExpr call = new MethodCallExpr(null, "move");

		// Left hand side:
		Cobol85Parser.MoveToSendingAreaContext sendingAreaContext = ctx.moveToStatement().moveToSendingArea();
		if (sendingAreaContext.literal() != null) {
			// numerical values:
			if (sendingAreaContext.literal().numericLiteral() != null) {
				call.addArgument(sendingAreaContext.getText());
			}
			// COBOL has no booleans??
			if (sendingAreaContext.literal().booleanLiteral() != null) {
				call.addArgument(sendingAreaContext.getText());
			}
			// Strings
			if (sendingAreaContext.literal().NONNUMERICLITERAL() != null) {
				call.addArgument(sendingAreaContext.getText());
			}
		}
		if (sendingAreaContext.identifier() != null) {
			// Identifier, i.e. variable reference
			call.addArgument(sendingAreaContext.getText());
		}

		// Right hand side:
		call.addArgument(ctx.moveToStatement().identifier().get(0).getText());

		// Add statement:
		block.addStatement(call);

		return true;
	}

	@Override
	public Boolean visitDisplayStatement(@NotNull Cobol85Parser.DisplayStatementContext ctx) {
		System.out.println("Processing Display Statement: DISPLAY " + ctx.children.get(1).getText());
		MethodDeclaration mainMethod = container.getMainMethod();
		BlockStmt block = mainMethod.getBody().get();

//		// add a statement do the method body
		MethodCallExpr call = new MethodCallExpr(null, "display");
		call.addArgument(ctx.getChild(1).getText());
		// Shouldn't be necessary, but we can't add a line of code twice.
		call.addArgument(ctx.getSourceInterval().toString());
		block.addStatement(call);

		return true;
	}
}
