# TDD-String-Calculator
Its a Test Driven Development for the String Calculator 

## Task

1. Create a simple String calculator with a method signature:

    -------------------------------------------

    int add(String numbers)

    -------------------------------------------

    The method can take up to two numbers, separated by commas, and will return their sum. 
    for example “” or “1” or “1,2” as inputs.
    
    (for an empty string it will return 0) 
    
    **Hints:**
    - Start with the simplest test case of an empty string and move to one and two numbers
    - Remember to solve things as simply as possible so that you force yourself to write tests you did not think about
    - Remember to refactor after each passing test

    Created a add function which splits input to String array and pass to another function sum to calculate the sum.

    ```java
    private static int add(String numbers) {

		if (numbers.equals("")) {
			return 0;
		} else {
			// Regex to split the string with delimiter ","
			String delimiter = ",";
			String numList[] = numbers.split(delimiter+"|\n");
			return sum(numList);
		}
	}

	private static int sum(String[] numList) {

		int numbersSum = 0;

		for (String number : numList) {
			numbersSum += Integer.parseInt(number);
		}
		return numbersSum;
	}
    ```

    Test Case
    ```Java
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
    ```


2. Allow the Add method to handle an unknown amount of numbers

    ```java
    @Test
    public void testThreeNumbers(){
		//Allow the Add method to handle an unknown amount of numbers
    	assertEquals(6, App.add("1,2,3"));
    }
    ```

3. Allow the Add method to handle new lines between numbers (instead of commas).
    1. the following input is ok: “1\n2,3” (will equal 6)
    2. the following input is NOT ok: “1,\n” (not need to prove it - just clarifying)

	For this case split is used 
	```java
	String numList[] = numbers.split(delimiter+"|\n");
	```
	Test case 
	```java
	@Test
    public void testNewLine(){
		// new lines between numbers
    	assertEquals(6, App.add("1\n2,3"));
    }
	```

4. Support different delimiters
	1. to change a delimiter, the beginning of the string will contain a separate line that looks like this: “//[delimiter]\n[numbers…]” for example “//;\n1;2” should return three where the default delimiter is ‘;’ .
	2. the first line is optional. all existing scenarios should still be supported

	Added a delimeter filter
	```java
	// The if looks for the extra delimeters
	// The condition is after // if there is anything and if after that there is a next line
	// Skip the characters and select the numbers
	if(numbers.matches("//(.*)\n(.*)")){
		delimiter = Character.toString(numbers.charAt(2));
		numbers = numbers.substring(4);
	}
	```

	Testcase

	```java
	@Test
    public void testDifferentDelimeter(){
		// new lines between numbers
    	assertEquals(3, App.add("//;\n1;2"));
    }
	```