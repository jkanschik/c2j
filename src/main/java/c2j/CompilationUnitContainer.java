package c2j;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class CompilationUnitContainer {

	private Map<String, CompilationUnit> compilationUnits;
	private Map<String, ClassOrInterfaceDeclaration> classes;
	
	private String basePackage;
	private String cobolProgramm;
	
	public CompilationUnitContainer(String basePackage, String cobolProgramm) {
		this.compilationUnits = new HashMap<String, CompilationUnit>();
		this.classes = new HashMap<String, ClassOrInterfaceDeclaration>();
		this.basePackage = basePackage;
		this.cobolProgramm = cobolProgramm;
		
        this.createMainClass();
	}
	
	public ClassOrInterfaceDeclaration createMainClass() {
		String className = cobolProgramm;
        CompilationUnit main = new CompilationUnit();
        main.setPackageDeclaration(basePackage);
        ClassOrInterfaceDeclaration mainClass = main.addClass(className);
        compilationUnits.put(basePackage + "." + className, main);
        classes.put(basePackage + "." + className, mainClass);
        return mainClass;
	}
	
	public ClassOrInterfaceDeclaration createDataStructureClass(String cobolName) {
		String className = cobolName;
        com.github.javaparser.ast.CompilationUnit main = new com.github.javaparser.ast.CompilationUnit();
        main.setPackageDeclaration(basePackage + "." + cobolProgramm);
        ClassOrInterfaceDeclaration mainClass = main.addClass(className);
        compilationUnits.put(basePackage + "." + className, main);
        classes.put(cobolName, mainClass);
        return mainClass;
	}

	public ClassOrInterfaceDeclaration getMainClass() {
		String className = cobolProgramm;
        return classes.get(basePackage + "." + className);
	}
	
	public ClassOrInterfaceDeclaration getDataStructureClass(String cobolName) {
		return classes.get(cobolName);
	}
	
	public Collection<CompilationUnit> getCompilationUnits() {
		return compilationUnits.values();
	}
	
}
