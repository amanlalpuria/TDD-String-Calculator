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