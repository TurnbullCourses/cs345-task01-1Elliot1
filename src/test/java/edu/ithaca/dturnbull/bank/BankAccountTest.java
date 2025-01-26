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
        //Partition: Empty string
        assertFalse(BankAccount.isEmailValid(""), "Emails must be invalid if they are blank");         // empty string
        //Boundary: too many characters in user name (255+)
        assertFalse(BankAccount.isEmailValid("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@gmail.com"));
        //Boundary: less than 1 characters for username
        assertFalse(BankAccount.isEmailValid("@gmail.com"), "Emails must be invalid if they do not contain a username before the @ symbol");
        //Boundary: more than one @ symbol
        assertFalse(BankAccount.isEmailValid("em@il@gmail.com"), "Emails must be invalid if they contain more than one @ symbol");
        //Boundary: less than one @ symbol
        assertFalse(BankAccount.isEmailValid("hello"), "Emails must be invalid if they do not contain an @ or a domain and it's suffix");
        //Partition: Invalid characters present
        assertFalse(BankAccount.isEmailValid("*hello#%()@gmail.com"), "Emails must be invalid if containing illegal characters");
        //Boundary: Less than one period (no domain suffix)
        assertFalse(BankAccount.isEmailValid("elliot@ithaca"), "Emails must be invalid if there is no domain suffix (or period) present");
        //Boundary: less than 1 character domain name
        assertFalse(BankAccount.isEmailValid("hello@.com"), "Emails must be invalid if there is no domain between the @ and suffix");
        //Boundary: more than 3 characters in domain suffix
        assertFalse(BankAccount.isEmailValid("hello@gmail.comx"), "Emails must not be valid with domain suffixes longer than 3 characters");
        //Boundary: more than 1 period in domain suffix
        assertFalse(BankAccount.isEmailValid("elliot@ithaca.i.o"), "Emails must only be valid if there is one period present after the @ symbol");
        //Boundary: Less than 1 character between a dash and the @ symbol
        assertFalse(BankAccount.isEmailValid("el-@ithaca.edu"), "Emails must be invalid if the username ends in a special character");
        //Partition: Invalid characters in domain
        assertFalse(BankAccount.isEmailValid("hello@ithac#.edu"), "Emails must be invalid if the domain contains an invalid special character");

        //Valid Test Cases
        //Partition: legal dash in domain
        assertTrue(BankAccount.isEmailValid("email@itha-ca.edu"), "Emails should be valid if domain contains a dash");
        //Partition: Legal non-alphanumeric character in username
        assertTrue(BankAccount.isEmailValid("user_name@gmail.com"), "Emails should be valid if the username contains an underscore.");
        //Boundary: 1 character domain suffix
        assertTrue(BankAccount.isEmailValid("elliot@duck.i"), "Emails must be valid with 1-3 character long domain suffixes");
        //Partition: legal period in username
        assertTrue(BankAccount.isEmailValid("elliot.oconnor@ithaca.edu"), "Emails must be valid if username contains periods (they should be ignored)");
        //Boundary: shortest legal email
        assertTrue(BankAccount.isEmailValid( "a@b.c"));   // valid email address

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