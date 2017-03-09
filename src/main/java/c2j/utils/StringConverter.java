package c2j.utils;

import org.apache.commons.lang3.StringUtils;

public class StringConverter {

	public static String cobolToClassName(String cobolName) {
		return StringUtils.join(cobolName.split("-"), "_").toUpperCase();
//		return cobolName;
	}
	
	public static String packageToDirectory(String packageName) {
		return packageName.replaceAll("\\.", "/");
	}
}
