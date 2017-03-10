package c2j.utils;

import org.apache.commons.lang3.StringUtils;

public class StringConverter {

	public static String cobolToClassName(String cobolName) {
		String[] parts = cobolName.split("-");
		for (int i = 0; i < parts.length; i++) {
			parts[i] = StringUtils.capitalize(parts[i].toLowerCase());
		}
		return StringUtils.join(parts, "_");
	}
	
	public static String packageToDirectory(String packageName) {
		return packageName.replaceAll("\\.", "/");
	}
}
