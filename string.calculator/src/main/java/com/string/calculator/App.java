package com.string.calculator;

public class App {
	public static void main(String[] args) {
		int result = add("6,1,2");
		System.out.println(result);
	}

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

				numbers = numbers.substring(4);
			}
			String numList[] = numbers.split(delimiter + "|\n");
			return sum(numList);
		}
	}

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

	private static boolean negativeCheck(String number) {
		if (Integer.parseInt(number) > 0)
			return false;
		return true;
	}

}
