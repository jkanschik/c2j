package ${dataStructurePackageName};

import c2j.api.dataStructures.*;

<#macro dataStructureClass ds>
public class ${ds.name} {

	<#list ds.children as child>
	private ${child.type} ${child.name};
	</#list>
	
	<#list ds.children as child>
	public void set${child.name}( ${child.type} ${child.name}) {
		this.${child.name} = ${child.name};
	}

	public ${child.type} get${child.name}() {
		return ${child.name};
	}

	</#list>
		
	<#list ds.children as child>
		<#if child.hasChildren()>
			<@dataStructureClass child />
		</#if>
	</#list>	
}
</#macro>

<@dataStructureClass dataStructure />
