package services;

import rest.models.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
/**
 * Authorization service interface.
 *
 * @author Lukasz Gozdziewski
 */
public interface Authorization {
    /**
     * This method is used to generate stron password with PBKDF2.
     * @param password based password to calculate strong PBKDF2.
     * @return String hashed and encrypted password.
     */
    String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException;

    /**
     * This method is used to generate token for user.
     * @param login which is used to authorization.
     * @return String This returns JJWT token.
     */
    String issueToken(String login);

    /**
     * This method is used to authenticate user.
     * @param login This is the user's login.
     * @param password  This is the user's password.
     * @return User This returns user if authorization successss or SecurityException if credentials are rejected.
     */
    User authenticate(String login, String password) throws Exception;

    /**
     * This method is used to validate user's password.
     * @param originalPassword This is the user's password from JSON object.
     * @param storedPassword  This is the user's password stored in database.
     * @return boolean This returns true if password is valid or false if is not valid.
     */
    boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
