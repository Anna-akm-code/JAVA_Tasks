import org.testng.annotations.Test;
import  com.javarush.creditCardValidation;

import static org.testng.Assert.assertEquals;
import java.util.List;



public class TestMain {
    @Test
    public void testConcatenate() {
        Main main = new Main();
        String cardNumber = "5457623898234113";  //Card is valid. Payment system Mastercard.
//       String cardNumber = "1234567891023453"; //Card is valid. Payment system: can't be determined.
//       String cardNumber = "1234567891yy3453"; //One or more entered symbols are not digits.Length must be 16 digits. Card number is invalid.
//       String cardNumber = "1234567891"; // Length must be 16 digits. Card number is invalid.

        List<Integer> cardNumberDigits = Main.cardNumberStringToDigits(cardNumber);
        assertEquals("[5, 4, 5, 7, 6, 2, 3, 8, 9, 8, 2, 3, 4, 1, 1, 3]", cardNumberDigits);

//        Main.sizeCheck(cardNumberDigits);
//        assertEquals("", cardNumberDigits);

        Main.cardValidation(cardNumberDigits);
        assertEquals("Card is valid. Payment system Mastercard.", cardNumberDigits);



    }
}
