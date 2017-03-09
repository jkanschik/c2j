package c2j.dataStructures;

import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.data.datadescription.DataDescriptionEntry;
import io.proleap.cobol.asg.metamodel.data.datadescription.DataDescriptionEntryGroup;
import io.proleap.cobol.asg.metamodel.data.workingstorage.WorkingStorageSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.misc.Interval;

import c2j.utils.StringConverter;

public class DataStructureContainer {

	private Map<Interval, DataStructure> dataStructures = new HashMap<Interval, DataStructure>();
	private List<DataStructure> topLevelDataStructures = new ArrayList<DataStructure>();

	public void collectDatastructures(CompilationUnit cu) {		
		WorkingStorageSection ws = cu.getProgramUnit().getDataDivision().getWorkingStorageSection();
		for (DataDescriptionEntry dde : ws.getDataDescriptionEntries()) {
			switch (dde.getType()) {
			case GROUP:
				DataDescriptionEntryGroup group = (DataDescriptionEntryGroup) dde;
//				System.out.println("Processing " + group.getName());
				
				DataStructure ds = new DataStructure();
				ds.setCobolName(dde.getName());
				ds.setName(StringConverter.cobolToClassName(dde.getName()));
				if (group.getDataDescriptionEntries().size() == 0) {
					ds.setPicture(group.getPictureClause().getPictureString());
					ds.setType(determinePictureType(ds.getPicture()));
					// VALUE
					if (group.getValueClause() != null) {
						String value = group.getValueClause().getValueIntervals().get(0).getFromValueStmt().getValue().toString();
						if (value.equals("SPACE") || value.equals("SPACES")) {
							value = "\"\"";
						} else if (value.equals("ZERO")) {
							value = "0";
						}
						ds.setValue(value);
					}
					// REDEFINES
					if (group.getRedefinesClause() != null) {
						ds.setRedefineReference(StringConverter.cobolToClassName(group.getRedefinesClause().getRedefinesCall().getName()));
					}
				} else {
					ds.setType(StringConverter.cobolToClassName(group.getName()));
				}
				
				dataStructures.put(dde.getCtx().getSourceInterval(), ds);

				DataStructure parentDataStructure = null;
				DataDescriptionEntryGroup parent = group.getParentDataDescriptionEntryGroup();
				if (parent != null) {
					Interval parentInterval = parent.getCtx().getSourceInterval();
					parentDataStructure = dataStructures.get(parentInterval);					
				}
				
				if (parentDataStructure != null) {
					parentDataStructure.getChildren().add(ds);
				} else {
					topLevelDataStructures.add(ds);
				}
				break;
			default:
				System.out.println("DataDescriptionEntry.Type " + dde.getType() + " is not supported!");
				break;
			}
		}
	}

	private String determinePictureType(String picture) {
		return picture.startsWith("X") ? "AlphaNumericCobolRecordImpl" : "NumericCobolRecord";
	}
	
	public List<DataStructure> getTopLevelDataStructures() {
		return topLevelDataStructures;
	}
}
