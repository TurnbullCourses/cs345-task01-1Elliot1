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
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""), "Emails must be invalid if they are blank");         // empty string
        assertFalse(BankAccount.isEmailValid("@gmail.com"), "Emails must be invalid if they do not contain a username before the @ symbol");
        assertFalse(BankAccount.isEmailValid("em@il@gmail.com"), "Emails must be invalid if they contain more than one @ symbol");
        assertFalse(BankAccount.isEmailValid("hello"), "Emails must be invalid if they do not contain an @ or a domain and it's suffix");
        assertFalse(BankAccount.isEmailValid("*hello#%()@gmail.com"), "Emails must be invalid if containing illegal characters");
        assertTrue(BankAccount.isEmailValid("elliot.oconnor@ithaca.edu"), "Emails must be valid if username contains periods (they should be ignored)");
        assertFalse(BankAccount.isEmailValid("elliot@ithaca"), "Emails must be invalid if there is no domain suffix (or period) present");
        assertFalse(BankAccount.isEmailValid("hello@.com"), "Emails must be invalid if there is no domain between the @ and suffix");
        assertFalse(BankAccount.isEmailValid("hello@gmail.superLongDomainSuffix"), "Emails must not be valid with domain suffixes longer than 3 characters");
        assertTrue(BankAccount.isEmailValid("elliot@duck.io"), "Emails must be valid with 1-3 character long domain suffixes");
        assertFalse(BankAccount.isEmailValid("elliot@ithaca.i.o"), "Emails must only be valid if there is one period present after the @ symbol");

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