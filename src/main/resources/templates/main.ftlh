package ${packageName};

import c2j.api.*;
import c2j.api.dataStructures.*;

import ${dataStructurePackageName}.*;

public class ${className} extends CobolProgram {

	// Data structures
	<#list dataStructureContainer.topLevelDataStructures as dataStructure>
		<#if dataStructure.picture??>
			<#if dataStructure.value??>
				private final ${dataStructure.type} ${dataStructure.name} = new ${dataStructure.type}("${dataStructure.picture}", ${dataStructure.value});
			<#elseif dataStructure.redefineReference??>
				private final ${dataStructure.type} ${dataStructure.name} = new ${dataStructure.type}("${dataStructure.picture}", ${dataStructure.redefineReference});
			<#else>
				private final ${dataStructure.type} ${dataStructure.name} = new ${dataStructure.type}("${dataStructure.picture}");
			</#if>
		<#else>
			private final ${dataStructure.type} ${dataStructure.name} = new ${dataStructure.type}();		
		</#if>
	</#list> 

	public static void main(String[] args) {
		${className} program = new ${className}();
		program.execute();
	}
	
	public void execute() {
	
	}

}
