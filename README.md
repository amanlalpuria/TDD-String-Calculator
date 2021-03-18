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

5. Calling Add with a negative number will throw an exception “negatives not allowed” - and the negative that was passed. 
if there are multiple negatives, show all of them in the exception message.

	Negative check added in add function

	```java
	private static int sum(String[] numList) {

		int numbersSum = 0;
		String negativeNumbers = "";

		for (String number : numList) {
			if (negativeCheck(number)) {
				if(negativeNumbers.equals(""))
					negativeNumbers = number;
        		else
        			negativeNumbers += ("," + number);
			} else {
				numbersSum += Integer.parseInt(number);
			}
		}
		if (!negativeNumbers.equals("")) {
			throw new IllegalArgumentException("Negatives not allowed: " + negativeNumbers);
		}

		return numbersSum;
	}

	private static boolean negativeCheck(String number) {
		if (Integer.parseInt(number) > 0)
			return false;
		return true;
	}
	```

	Test case for single and multiple negative number
	```java
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
	```

6. Numbers bigger than 1000 should be ignored, so adding 2 + 1001 = 2

	Updated sum function for number greater than 1000
	```java
	private static int sum(String[] numList) {

		int numbersSum = 0;
		String negativeNumbers = "";

		for (String number : numList) {
			if (negativeCheck(number)) {
				if (negativeNumbers.equals(""))
					negativeNumbers = number;
				else
					negativeNumbers += ("," + number);
			} else if (Integer.parseInt(number) < 1000) {
				numbersSum += Integer.parseInt(number);
			}
		}
		if (!negativeNumbers.equals(""))

		{
			throw new IllegalArgumentException("Negatives not allowed: " + negativeNumbers);
		}

		return numbersSum;
	}
	```

	Test case for the same
	```java
	@Test
	public void testNumberGreaterThousand() {
		// Numbers bigger than 1000 should be ignored, so adding 2 + 1001 = 2
		assertEquals(2, App.add("1000,2"));
	}
	```

7. Delimiters can be of any length with the following format: “//[delimiter]\n” for example: “//[***]\n1***2***3” should return 6

	Updated add function
	```java
	static int add(String numbers) {

		if (numbers.equals("")) {
			return 0;
		} else {
			// Regex to split the string with delimiter ","
			String delimiter = ",";

			// The if looks for the extra delimeters
			// The condition is after // if there is anything and if after that
			// there is a next line
			// Skip the characters and select the numbers
			if (numbers.matches("//(.*)\n(.*)")) {
				delimiter = Character.toString(numbers.charAt(2));
				if (delimiter.equals("[")) {
					int closingBracketIndex = numbers.indexOf("]");
					delimiter = numbers.substring(3, closingBracketIndex);
				}
				int nextLineIndex = numbers.indexOf("\n");
				numbers = numbers.substring(nextLineIndex);
			}
			String numList[] = numbers.split(delimiter + "|\n");
			return sum(numList);
		}
	}
	```
	Instead of '*' I have used '-' and '@' to test
	```java
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
	```

8. Allow multiple delimiters like this: “//[delim1][delim2]\n” for example “//[*][%]\n1*2%3” should return 6.

	Updated add function for multiple delimiter 
	```java
	static int add(String numbers) {

		if (numbers.equals("")) {
			return 0;
		} else {
			// Regex to split the string with delimiter ","
			String delimiter = ",";
			String delimiter2 = ",";

			if (numbers.matches("//(.*)\n(.*)")) {
				delimiter = Character.toString(numbers.charAt(2));
				if (delimiter.equals("[")) {
					int closingBracketIndex = numbers.indexOf("]");
					delimiter = numbers.substring(3, closingBracketIndex);
				}// //[*][%]\n1*2%3
				if (numbers.matches("//\\[(.*)\\]\\[(.*)\\]\n(.*)")){
					int firstClosingBracketIndex = numbers.indexOf("]")+1;
					int secondClosingBracketIndex = numbers.indexOf("\n")-1;
					delimiter2 = numbers.substring(firstClosingBracketIndex+1, secondClosingBracketIndex);
					numbers = numbers.replaceAll("\\"+delimiter, delimiter2);
					delimiter = delimiter2;
				}
				int nextLineIndex = numbers.indexOf("\n");
				numbers = numbers.substring(nextLineIndex);
			}
			String numList[] = numbers.split("\\"+delimiter + "|\n");
			return sum(numList);
		}
	}
	```

	Test case for the same
	```java
	@Test
	public void testMultipleDelimiter() {
		// Allow multiple delimiters like this: “//[delim1][delim2]\n” for example “//[*][%]\n1*2%3” should return 6.
		assertEquals(6, App.add("//[*][%]\n1*2%3"));
	}
	```

9. make sure you can also handle multiple delimiters with length longer than one char

	Handled the same in requirement 8

	Test case
	```java
	@Test
	public void testMultipleLengthDelimiter() {
		//make sure you can also handle multiple delimiters with length longer than one char
		assertEquals(6, App.add("//[---][%%%]\n1---2%%%3"));
	}
	```