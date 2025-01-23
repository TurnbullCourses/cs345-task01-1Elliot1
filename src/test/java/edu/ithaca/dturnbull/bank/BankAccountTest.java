package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        //Invalid Test Cases
        assertFalse(BankAccount.isEmailValid(""), "Emails must be invalid if they are blank");         // empty string
        assertFalse(BankAccount.isEmailValid("@gmail.com"), "Emails must be invalid if they do not contain a username before the @ symbol");
        assertFalse(BankAccount.isEmailValid("em@il@gmail.com"), "Emails must be invalid if they contain more than one @ symbol");
        assertFalse(BankAccount.isEmailValid("hello"), "Emails must be invalid if they do not contain an @ or a domain and it's suffix");
        assertFalse(BankAccount.isEmailValid("*hello#%()@gmail.com"), "Emails must be invalid if containing illegal characters");
        assertFalse(BankAccount.isEmailValid("elliot@ithaca"), "Emails must be invalid if there is no domain suffix (or period) present");
        assertFalse(BankAccount.isEmailValid("hello@.com"), "Emails must be invalid if there is no domain between the @ and suffix");
        assertFalse(BankAccount.isEmailValid("hello@gmail.superLongDomainSuffix"), "Emails must not be valid with domain suffixes longer than 3 characters");
        assertFalse(BankAccount.isEmailValid("elliot@ithaca.i.o"), "Emails must only be valid if there is one period present after the @ symbol");
        assertFalse(BankAccount.isEmailValid("el-@ithaca.edu"), "Emails must be invalid if the username ends in a special character");
        assertFalse(BankAccount.isEmailValid("hello@ithac#.edu"), "Emails must be invalid if the domain contains an invalid special character");

        //Valid Test Cases
        assertTrue(BankAccount.isEmailValid("email@itha-ca.edu"), "Emails should be valid if domain contains a dash");
        assertTrue(BankAccount.isEmailValid("user_name@gmail.com"), "Emails should be valid if the username contains an underscore.");
        assertTrue(BankAccount.isEmailValid("elliot@duck.io"), "Emails must be valid with 1-3 character long domain suffixes");
        assertTrue(BankAccount.isEmailValid("elliot.oconnor@ithaca.edu"), "Emails must be valid if username contains periods (they should be ignored)");
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address

    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}