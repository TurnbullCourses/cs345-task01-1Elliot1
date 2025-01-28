package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);

        //Boundary: Balance is 0 (minimum valid balance)
        BankAccount newAccount = new BankAccount("z@x.y", 0);
        assertEquals(0, newAccount.getBalance(), 0.001);
        //Boundary: Balance is 0.01 (float case) (minimum valid balance)
        BankAccount thirdAccount = new BankAccount("x@y.z", 0.01);
        assertEquals(0.01, thirdAccount.getBalance(), 0.001);
        //Boundary: Balance is 1 (int case) (just above minimum)
        BankAccount fourthAccount = new BankAccount("y@z.x", 1);
        assertEquals(1, fourthAccount.getBalance(), 0.001);
        //Partition: Balance is nominal value
        BankAccount fifthAccount = new BankAccount("a@b.c", 1105);
        assertEquals(1105, fifthAccount.getBalance(), 0.001);
        //Partition: Balance is nominal float value
        BankAccount sixthAccount = new BankAccount("b@c.d", 60.50);
        assertEquals(60.50, sixthAccount.getBalance(), 0.001);
        //Boundary: Balance is 999999999 (int case) (just below maximum valid balance)
        BankAccount seventhAccount = new BankAccount("c@d.e", 999999999);
        assertEquals(999999999, seventhAccount.getBalance(), 0.001);
        //Boundary: Balance is 999999999.99 (float case) (just below maximum valid balance)
        BankAccount eighthAccount = new BankAccount("d@e.f", 999999999.99);
        assertEquals(999999999.99, eighthAccount.getBalance(), 0.001);
        //Boundary: Balance is 1000000000 (int case) (maximum valid balance)
        BankAccount ninthAccount = new BankAccount("e@f.g", 1000000000);
        assertEquals(1000000000, ninthAccount.getBalance(), 0.001);
        //Boundary: Balance is 999999999.99 (float case) (maximum valid balance)
        BankAccount tenthAccount = new BankAccount("f@g.h", 999999999.99);
        assertEquals(999999999.99, tenthAccount.getBalance(), 0.001);

        //(Above and below maximum and minimum valid balances will be tested on the constructor tests)


    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
        
        //Boundary: Withdraw amount exceeds balance by .01 (float case) Tests just above maximum valid Withdrawal
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(100.01));
        //Boundary: Withdraw amount exceeds balance by 1 (int case) Tests just above maximum valid Withdrawal
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(101));
        //Boundary: Withdraw amount is .01 less than 0 (float case) Tests Minimum invalid negative Withdrawal
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(-0.01));
        //Boundary: Withdraw amount is 1 less than 0 (int case) Tests Minimum invalid negative Withdrawal
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(-1));


        //Boundary: Withdraw amount is 1 less than balance (int case) //Tests just below the maximum valid Withdrawal
        bankAccount.withdraw(99);
        assertEquals(1, bankAccount.getBalance(), 0.001);
        //Boundary: Withdraw amount is .01 less than balance (float case) //Tests just below the maximum valid Withdrawal
        BankAccount newAccount = new BankAccount("z@x.y", 100);
        newAccount.withdraw(99.99);
        assertEquals(0.01, newAccount.getBalance(), 0.001);
        //Partition: Withdraw amount is 0 //Tests 0 Withdrawal
        bankAccount.withdraw(0);
        assertEquals(1, bankAccount.getBalance(), 0.001);
        //Boundary: Withdraw amount is .01 (float case) //Tests just above the minimum valid Withdrawal
        bankAccount.withdraw(0.01);
        assertEquals(0.99, bankAccount.getBalance(), 0.001);
        //Boundary: Withdraw amount is 1 (int case) //Tests just above the minimum valid Withdrawal
        BankAccount thirdAccount = new BankAccount("x@y.z", 1000);
        thirdAccount.withdraw(1);
        assertEquals(999, thirdAccount.getBalance(), 0.001);
        //Partition: Withdraw amount is nominal value within range of balance
        thirdAccount.withdraw(300);
        assertEquals(699, thirdAccount.getBalance(), 0.001);
        //Partition: Withdraw amount is float value within range of balance
        thirdAccount.withdraw(30.50);
        assertEquals(668.50, thirdAccount.getBalance(), 0.001);
    }

    @Test
    void isEmailValidTest(){

        // hello testing git 

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