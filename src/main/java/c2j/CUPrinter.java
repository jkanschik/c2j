package c2j;

import com.github.javaparser.ast.CompilationUnit;

public class CUPrinter {

	public void print(CompilationUnitContainer container) {
		for (CompilationUnit cu : container.getCompilationUnits()) {
			System.out.println(cu);
		}
	}
}
