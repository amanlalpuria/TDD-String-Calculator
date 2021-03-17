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
		//Test for Empty String
		assertEquals(0, App.add(""));
	}
	
	@Test
	public void testOneString() {
		//Test for One String
		assertEquals(1, App.add("1"));
	}
	
	@Test
	public void testTwoString() {
		//Test for Two String
		assertEquals(3, App.add("1,2"));
	}
	
	@Test
    public void testThreeNumbers(){
		//Allow the Add method to handle an unknown amount of numbers
    	assertEquals(6, App.add("1,2,3"));
    }
	
	@Test
    public void testNewLine(){
		// new lines between numbers
    	assertEquals(6, App.add("1\n2,3"));
    }
	
	@Test
    public void testDifferentDelimeter(){
		// new lines between numbers
    	assertEquals(3, App.add("//;\n1;2"));
    }
	
	 
}
