package org.hire.auth_service.service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordHasher {
    
    private static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 32, 64);

    public static String hashPassword(String password){
        return argon2.hash(2, 12 * 1024, 1, password.toCharArray());
    }

    public static boolean verifyPassword(String password, String hash) {
        return argon2.verify(hash, password.toCharArray());
    }
}
