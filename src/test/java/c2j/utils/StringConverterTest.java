package c2j.utils;

import static org.junit.Assert.*;

import static org.hamcrest.core.Is.*;
import org.junit.Test;

public class StringConverterTest {

	@Test
	public void packageToDirectory() {
		assertThat(StringConverter.packageToDirectory("a"), is("a"));
		assertThat(StringConverter.packageToDirectory("a.b"), is("a/b"));
		assertThat(StringConverter.packageToDirectory("a.b.c"), is("a/b/c"));
	}

	@Test
	public void cobolToClassName() {
		assertThat(StringConverter.cobolToClassName("a"), is("A"));
		assertThat(StringConverter.cobolToClassName("Feld-X"), is("Feld_X"));
		assertThat(StringConverter.cobolToClassName("FELD-1"), is("Feld_1"));
		assertThat(StringConverter.cobolToClassName("FELD-1-2"), is("Feld_1_2"));
	}

}
