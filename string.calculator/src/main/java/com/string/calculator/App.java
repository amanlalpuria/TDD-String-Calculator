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

	private static int sum(String[] numList) {

		int numbersSum = 0;
		String negativeNumbers = "";

		for (String number : numList) {
			if (number.equals("")) {
			} else if (negativeCheck(number)) {
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
