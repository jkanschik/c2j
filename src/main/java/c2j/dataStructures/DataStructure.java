package c2j.dataStructures;

import java.util.ArrayList;
import java.util.List;

public class DataStructure {

	private String cobolName;
	private String name;
	private String type;
	private String picture;
	private String value;
	private String redefineReference;
	
	private List<DataStructure> children = new ArrayList<DataStructure>();

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public String getCobolName() {
		return cobolName;
	}

	public void setCobolName(String cobolName) {
		this.cobolName = cobolName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<DataStructure> getChildren() {
		return children;
	}

	public void setChildren(List<DataStructure> children) {
		this.children = children;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRedefineReference() {
		return redefineReference;
	}

	public void setRedefineReference(String redefineReference) {
		this.redefineReference = redefineReference;
	}
	
	
}
