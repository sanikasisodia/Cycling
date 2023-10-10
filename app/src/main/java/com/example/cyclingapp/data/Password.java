package com.example.cyclingapp.data;
import org.mindrot.jbcrypt.BCrypt;

public class Password {
    String passwordSalt;
    String passwordHash;

    public Password(String password) {
        this.passwordSalt = BCrypt.gensalt(12);
        this.passwordHash = BCrypt.hashpw(password, this.passwordSalt);
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.passwordHash);
    }
}
