package com.example.cyclingapp.data;
import org.mindrot.jbcrypt.BCrypt;

public class Passwords {

    // Hash a plain text password
    public static String hashPassword(String plainTextPassword) {
        // Generate a salt for the password hash
        String salt = BCrypt.gensalt(12); // The higher the number, the more secure (but slower) the hash

        // Hash the password using the salt
        String hashedPassword = BCrypt.hashpw(plainTextPassword, salt);

        return hashedPassword;
    }

    // Verify a plain text password against a hashed password
    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        // Use BCrypt to check if the plain text password matches the hashed password
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    public static void main(String[] args) {
        // Example usage:
        String plainTextPassword = "mySecurePassword";

        // Hash the password before storing it in your database
        String hashedPassword = hashPassword(plainTextPassword);
        System.out.println("Hashed Password: " + hashedPassword);

        // Verify a login attempt by checking the entered password against the stored hashed password
        boolean isPasswordCorrect = verifyPassword("mySecurePassword", hashedPassword);
        System.out.println("Password Correct: " + isPasswordCorrect);
    }
}
