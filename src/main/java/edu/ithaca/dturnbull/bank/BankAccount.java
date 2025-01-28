package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws InsufficientFundsException if amount is negative or greater than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
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