package com.javarush.creditCardValidation;

import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter card number for validation without spaces: ");
        String cardNumber = scanner.nextLine();

        // String cardNumber = "5457623898234113";  //Card is valid. Payment system Mastercard.
        //String cardNumber = "1234567891023453"; //Card is valid. Payment system: can't be determined.
        // String cardNumber = "1234567891yy3453"; //One or more entered symbols are not digits.Length must be 16 digits. Card number is invalid.
        //String cardNumber = "1234567891"; // Length must be 16 digits. Card number is invalid.
        List<Integer> cardNumberDigits = cardNumberStringToDigits(cardNumber);

        sizeCheck(cardNumberDigits);

        if (cardValidation(cardNumberDigits)) {
            System.out.print("Card is valid. ");
            String paymentSystem = determinePaymentSystem(cardNumberDigits);
            System.out.print("Payment system: " + paymentSystem + ".");
        }
    }

    public static  List<Integer> cardNumberStringToDigits (String cardNumber) {
        List<String> cardNumberStrings = List.of(cardNumber.split(""));
        List<Integer> cardNumberDigits = new ArrayList<Integer>();
        try {
            for (String element : cardNumberStrings){
                Integer digit = Integer.parseInt(element);     // int or Integer???
                cardNumberDigits.add(digit);
            }
        } catch (NumberFormatException ex) {
            System.out.println("One or more entered symbols are not digits.");
        }
        return cardNumberDigits;
    }

    public static void sizeCheck(List<Integer> cardNumberDigits) {
        Integer cardNumberSize = cardNumberDigits.size();
        if (!cardNumberSize.equals(16)) {
            System.out.println("Error:");
            System.out.println("Length must be 16 digits"); // throw error ??
        }
    }

    public static boolean cardValidation(List<Integer> cardNumberDigits) {
        Integer lastNum = cardNumberDigits.get(cardNumberDigits.size() - 1);

        List<Integer> cardNumberDigitsWithoutLast = new ArrayList<Integer>(cardNumberDigits);
        cardNumberDigitsWithoutLast.remove(cardNumberDigitsWithoutLast.size() - 1);

        Collections.reverse(cardNumberDigitsWithoutLast);

        List<Integer> evens = new ArrayList<Integer>();
        List<Integer> odds = new ArrayList<Integer>();
        for (int i = 0; i < cardNumberDigitsWithoutLast.size(); i++) { // i < cardNumberDigits.size()-1   ?
            int x = cardNumberDigitsWithoutLast.get(i);
            var b = i % 2 == 0 ? evens.add(x) : odds.add(x);
        }
        List<Integer> evensByTwo = new ArrayList<Integer>();
        List<Integer> oddsByOne = new ArrayList<Integer>();
        for (int ev : evens) {
            Integer evTwo = ev * 2;
            evensByTwo.add(evTwo);
        }
        for (int od : odds) {
            Integer odTwo = od * 1;
            oddsByOne.add(odTwo);
        }

        int evensSUM = addUpEvensOrOdds(evensByTwo);
        int oddsSUM = addUpEvensOrOdds(oddsByOne);

        int totalSum = evensSUM + oddsSUM;
        int calculatedLastNum = 10 - (totalSum % 10); // this must equal the actual entered last digit of credit card number

        if (Objects.equals(calculatedLastNum, lastNum)) {
            return true;
        }
        return false;
    }


    public static int sumDigits(int number) {

        int tensDigit = (number % 100 - number % 10) / 10;
        int onesDigit = number % 10 - number % 1;
        int sum = tensDigit + onesDigit;
        return sum;
    }

    public static int addUpEvensOrOdds(List<Integer> numList) {
        int sum = 0;
        for (int e : numList ) {

            if (e <= 9) {
                sum +=e;
            } else {
                int tensPlusOnes  = sumDigits(e);
                sum +=tensPlusOnes;
            }
        }
        return sum;
    }


    public static String determinePaymentSystem(List<Integer> cardNumberDigits) {
        Integer firstInt = cardNumberDigits.get(0);
        Integer secondInt = cardNumberDigits.get(1);
        Integer thirdInt = cardNumberDigits.get(2);
        Integer fourthInt = cardNumberDigits.get(3);

        if (firstInt.equals(4)) {
            return "Visa";
        } else if (firstInt.equals(5) && (secondInt.equals(1) || secondInt.equals(2)|| secondInt.equals(3)|| secondInt.equals(4)|| secondInt.equals(5)) ) {
            return "Mastercard";
        } else if (firstInt.equals(3) && (secondInt.equals(6) || secondInt.equals(8))) {
            return "Diners Club";
        } else if (firstInt.equals(6) && ( (secondInt.equals(0) &&  (thirdInt.equals(1) &&  fourthInt.equals(1))) || secondInt.equals(5))) {
            return "Discover";
        }else if (firstInt.equals(3) && secondInt.equals(4)) {
            return "JCB";
        }else if (firstInt.equals(3) && (secondInt.equals(4) || secondInt.equals(7))) {
            return "American Express";
        }
        return "can't be determined";
    }
}
