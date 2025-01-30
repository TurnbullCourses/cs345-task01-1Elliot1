package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance) {
        if (!isEmailValid(email)) {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
        if (!isAmountValid(startingBalance)) {
            throw new IllegalArgumentException("Starting balance is invalid. Must be > 0 and up to two decimals.");
        }
        this.email = email;
        this.balance = startingBalance;
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     *Checks if
     *The amount is positive
     *The amount has at most two decimal places.
     *
     * @param amount the monetary amount to check
     * @return true if the amount is positive and has up to two decimal places,
     * false otherwise.
     */
    public static boolean isAmountValid(double amount) {
    if (amount <= 0) {
        return false;
    }

    double scaled = amount * 100;
    if (Math.floor(scaled) != scaled) {
        return false;
    }

    return true;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws InsufficientFundsException if amount is greater than balance
     * @throws IllegalArgumentException if amount is negative
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException(
                "Withdrawal amount " + amount + " is invalid. Must be > 0 and up to two decimals."
            );
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Not enough money");
        }
        balance -= amount;
    }


    public static boolean isEmailValid(String email){
        // Check if the email is empty + test if git push is working
        if (email == null || email.isEmpty()) {
        return false;
        }

        // Split into prefix and domain part
        String[] parts = email.split("@");
        if (parts.length != 2) { // Must contain exactly one '@'
            return false;
        }

        String prefix = parts[0];
        String domain = parts[1];

        // Check prefix
        if (prefix.isEmpty() || prefix.length() > 254 || prefix.endsWith("-") || prefix.contains("..")) {
            return false;
        }
        if (!prefix.matches("[a-zA-Z0-9._-]+")) { // Only alphanumeric, ., _, -
            return false;
        }

        // Check domain 
        if (domain.isEmpty() || domain.contains("..") || !domain.contains(".")) {
            return false;
        }

        String[] domainParts = domain.split("\\.");
        if (domainParts.length < 2) { // Must have at least one period
            return false;
        }

        if (domainParts.length > 2) {
            return false;
        }

        String domainName = domainParts[0];
        String tld = domainParts[domainParts.length - 1];

        // Check domain name
        if (domainName.isEmpty() || !domainName.matches("[a-zA-Z0-9-]+") || domainName.startsWith("-") || domainName.endsWith("-")) {
            return false;
        }

        // Check top level domain
        if (tld.length() < 1 || tld.length() > 3 || !tld.matches("[a-zA-Z]+")) {
            return false;
        }

        return true; // If all checks pass, the email is valid
    



    }
}