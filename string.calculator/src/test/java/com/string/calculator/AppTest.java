package com.string.calculator;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.string.calculator.App;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void testEmptyString() {
		// Test for Empty String
		assertEquals(0, App.add(""));
	}

	@Test
	public void testOneString() {
		// Test for One String
		assertEquals(1, App.add("1"));
	}

	@Test
	public void testTwoString() {
		// Test for Two String
		assertEquals(3, App.add("1,2"));
	}

	@Test
	public void testThreeNumbers() {
		// Allow the Add method to handle an unknown amount of numbers
		assertEquals(6, App.add("1,2,3"));
	}

	@Test
	public void testNewLine() {
		// new lines between numbers
		assertEquals(6, App.add("1\n2,3"));
	}

	@Test
	public void testDifferentDelimeter() {
		// new lines between numbers
		assertEquals(3, App.add("//;\n1;2"));
	}

	@Test
	public void testNegativeNumber() {
		try {
			App.add("-1,2");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Negatives not allowed: -1");
		}
	}

	@Test
	public void testMultipleNegativeNumber() {
		try {
			App.add("-1,2,-3,4,-5");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Negatives not allowed: -1,-3,-5");
		}
	}
	
	@Test
	public void testNumberGreaterThousand() {
		// Numbers bigger than 1000 should be ignored, so adding 2 + 1001 = 2
		assertEquals(2, App.add("1000,2"));
	}
	
	@Test
	public void testLengthDelimiter() {
		// Delimiters can be of any length with the following format: “//[delimiter]\n” for example: “//[***]\n1***2***3” should return 6
		assertEquals(6, App.add("//[---]\n1---2---3"));
	}
	
	@Test
	public void testLengthDelimiter2() {
		// Delimiters can be of any length with the following format: “//[delimiter]\n” for example: “//[***]\n1***2***3” should return 6
		assertEquals(6, App.add("//[@@@@@]\n1@@@@@2@@@@@3"));
	}
	
	@Test
	public void testMultipleDelimiter() {
		// Allow multiple delimiters like this: “//[delim1][delim2]\n” for example “//[*][%]\n1*2%3” should return 6.
		assertEquals(6, App.add("//[*][%]\n1*2%3"));
	}
	
	@Test
	public void testMultipleLengthDelimiter() {
		//make sure you can also handle multiple delimiters with length longer than one char
		assertEquals(6, App.add("//[---][%%%]\n1---2%%%3"));
	}
}
