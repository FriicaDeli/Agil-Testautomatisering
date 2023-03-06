package com.example.AgilTestautomatisering;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.AgilTestautomatisering.Validator.isEmailValid;
import static com.example.AgilTestautomatisering.Validator.isPhoneNumberValid;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class ValidatorTests {


    @Test
    @DisplayName("Valid phone number should return true")
    void givenValidPhoneNumber_ShouldReturnTrue() {
        String phoneNumber = "010123123";
        boolean result = isPhoneNumberValid(phoneNumber);

        assertTrue(result);
    }
    @Test
    @DisplayName("Ten digit phone number only zeros should return true")
    void givenTenDigitZerosPhoneNumber_ShouldReturnTrue() {
        String phoneNumber = "0000000000";
        boolean result = isPhoneNumberValid(phoneNumber);

        assertTrue(result);
    }
    @Test
    @DisplayName("Too long phone number should return false")
    void givenTooLongPhoneNumber_ShouldReturnFalse() {
        String phoneNumber = "010123123123";
        boolean result = isPhoneNumberValid(phoneNumber);

        assertFalse(result);
    }
    @Test
    @DisplayName("Phone number not beginning with zero should return false")
    void givenNonZeroBeginningPhoneNumber_ShouldReturnFalse() {
        String phoneNumber = "123123";
        boolean result = isPhoneNumberValid(phoneNumber);

        assertFalse(result);
    }
    @Test
    @DisplayName("Text phone number should return false")
    void givenTextPhoneNumber_ShouldReturnFalse() {
        String phoneNumber = "phoneNumber";
        boolean result = isPhoneNumberValid(phoneNumber);

        assertFalse(result);
    }
    @Test
    @DisplayName("Too short phone number should return false")
    void givenTooShortPhoneNumber_ShouldReturnFalse() {
        String phoneNumber = "01045";
        boolean result = isPhoneNumberValid(phoneNumber);

        assertFalse(result);
    }
    @Test
    @DisplayName("Empty phone number should return false")
    void givenEmptyPhoneNumber_ShouldReturnFalse() {
        String phoneNumber = "";
        boolean result = isPhoneNumberValid(phoneNumber);

        assertFalse(result);
    }
    @Test
    @DisplayName("Phone number with blanks should return false")
    void givenPhoneNumberWithBlanks_ShouldReturnFalse() {
        String phoneNumber = "010 123123";
        boolean result = isPhoneNumberValid(phoneNumber);

        assertFalse(result);
    }

    //Nedan är tester för e-post
    @Test
    @DisplayName("Valid email should return true")
    void givenValidEmail_ShouldReturnTrue() {
        String email = "mail.valid@mailmonkey.com";
        boolean result = isEmailValid(email);

        assertTrue(result);
    }
    @Test
    @DisplayName("Missing @ in email should return false")
    void givenMissingAtInEmail_ShouldReturnFalse() {
        String email = "mail.validmailmonkey.com";
        boolean result = isEmailValid(email);

        assertFalse(result);
    }
    @Test
    @DisplayName("Missing dot after at sign in email should throw exception")
    void givenMissingDotAfterAtSignInEmail_ShouldThrowException() {
        String email = "mail.valid@mailmonkeycom";

        assertThrows(IndexOutOfBoundsException.class, () -> isEmailValid(email));
    }
    @Test
    @DisplayName("No dots in email should return false")
    void givenNoDotsInEmail_ShouldReturnFalse() {
        String email = "mailvalid@mailmonkeycom";
        boolean result = isEmailValid(email);

        assertFalse(result);
    }
    @Test
    @DisplayName("Missing name in email should return false")
    void givenMissingNameInEmail_ShouldReturnFalse() {
        String email = "@mailmonkey.com";
        boolean result = isEmailValid(email);

        assertFalse(result);
    }
    @Test
    @DisplayName("Missing domainName in email should return false")
    void givenMissingDomainNameInEmail_ShouldReturnFalse() {
        String email = "mail.valid@.com";
        boolean result = isEmailValid(email);

        assertFalse(result);
    }
    @Test
    @DisplayName("Missing topLevelDomainName in email should return false")
    void givenMissingTopLevelDomainNameInEmail_ShouldReturnFalse() {
        String email = "mail.valid@mailmonkey.";
        boolean result = isEmailValid(email);

        assertFalse(result);
    }


}
