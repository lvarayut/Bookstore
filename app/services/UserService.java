package services;

import models.SecureSocial.Password;
import models.User;
import play.Application;
import play.Logger;
import scala.Option;
import securesocial.core.*;
import securesocial.core.java.BaseUserService;
import securesocial.core.java.Token;
import java.util.*;

/**
 * UserService is a services that acts as a controller
 * gluing between SecureSocial plugin and the User class
 */
public class UserService extends BaseUserService {
    public Logger.ALogger logger = play.Logger.of("app.services.UserService");
    private HashMap<String, Token> tokens = new HashMap<String, Token>();

    /**
     * This constructor is required for the SecureSocial Plugin
     * @param application
     */
    public UserService(Application application) {
        super(application);
    }

    /**
     * Saves the user.  This method gets called when a user logs in.
     * @param identity
     */
    @Override
    public Identity doSave(Identity identity) {
        User found = null;
        // Verify an existing user
        for (User u : User.findAllUsers()) {
            if (identity.email().isDefined() && u.getEmail().equals(identity.email().get())) {
                found = u;
                break;
            }
        }
        // Create a new user
        if (found == null) {
            // Create a new user
            User user = new User();
            Password password = new Password();
            user.setUserid(identity.identityId().userId());
            user.setProvider(identity.identityId().providerId());
            user.setFirstname(identity.firstName());
            user.setLastname(identity.lastName());
            user.setEmail(identity.email().get());
            user.setAuthmethod(identity.authMethod().method());
            // In case of using third party provider, they don't have a password.
            if (identity.passwordInfo().nonEmpty()) {
                password.setPasswordHasher(identity.passwordInfo().get().hasher());
                password.setPassword(identity.passwordInfo().get().password());
                if(identity.passwordInfo().get().salt().isDefined()){
                    password.setSalt(identity.passwordInfo().get().salt().get());
                }
            }
            user.setPwd(password);
            user.setUsername(identity.firstName());

            // Persist the user to DB
            user.insert();
        }

        return identity;
    }

    public void doLink(Identity current, Identity to) {
//        User target = null;
//
//        for (User u : User.findAllUsers()) {
//            if (u.getIdentities().contains(current)) {
//                target = u;
//                break;
//            }
//        }
//
//        if (target == null) {
//            // this should not happen
//            throw new RuntimeException("Can't find a user for identity: " + current.identityId());
//        }
//        if (!target.getIdentities().contains(to)) target.getIdentities().add((MySocialUser)to);
    }

    /**
     * Find a requested user for the login system
     * @param userId
     * @return
     */
    @Override
    public Identity doFind(IdentityId userId) {
        if (logger.isDebugEnabled()) {
            logger.debug("Finding user " + userId);
        }
        Identity found = null;
        for (User u : User.findAllUsers()) {
                if (u.getUserid().equals(userId.userId()) && u.getProvider().equals(userId.providerId())) {
                    found = this.createUser(u);
                    break;
                }

        }
        return found;
    }

    /**
     * Find a given token
     * @param tokenId
     * @return
     */
    @Override
    public Token doFindToken(String tokenId) {
        return tokens.get(tokenId);
    }


    /**
     * Verify an exist user before signing up
     * by using UserPasswordProvider
     * @param email
     * @param providerId
     * @return
     */
    @Override
    public Identity doFindByEmailAndProvider(String email, String providerId) {
        if (logger.isDebugEnabled()) {
            logger.debug("Finding user by mail " + email);
        }
        Identity result = null;
        // If a user already signed up using the social providers,
        // he/she shouldn't be able to sign up again.
        for (User user : User.findAllUsers()) {
            if(user.getEmail().equals(email)){
                result = this.createUser(user);
                break;
            }
        }
        return result;
    }

    /**
     * Save a new token
     * @param token
     */
    @Override
    public void doSave(Token token) {
        tokens.put(token.uuid, token);
    }

    /**
     * Delete a given token
     * @param uuid
     */
    @Override
    public void doDeleteToken(String uuid) {
        tokens.remove(uuid);
    }

    /**
     * Delete all expired tokens
     */
    @Override
    public void doDeleteExpiredTokens() {
        Iterator<Map.Entry<String, Token>> iterator = tokens.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Token> entry = iterator.next();
            if (entry.getValue().isExpired()) {
                iterator.remove();
            }
        }
    }

    /**
     * A helper method not part of the UserService interface.
     */

//    public User userForIdentity(Identity identity) {
//        User result = null;
//
//        for (User u : User.findAllUsers()) {
//            if ( u.getIdentities().contains(identity)) {
//                result = u;
//                break;
//            }
//        }
//
//        return result;
//    }

    /**
     * Create a SocailUser object from the given User
     * @param newUser User object
     * @return SocialUser object
     */
    public SocialUser createUser(User newUser){
        Password password = newUser.getPwd();
        Option<String> emailOption = Option.apply(newUser.getEmail());
        Option<String> saltOption = Option.apply(password.getSalt());
        IdentityId identityId = new IdentityId(newUser.getUserid(),newUser.getProvider());
        AuthenticationMethod authenticationMethod = new AuthenticationMethod(newUser.getAuthmethod());
        PasswordInfo passwordInfo = new PasswordInfo(password.getPasswordHasher(),
                password.getPassword(), saltOption);
        Option<PasswordInfo> passwordInfoOption = Option.apply(passwordInfo);

        SocialUser socialUser = new SocialUser(
                identityId,
                newUser.getFirstname(),
                newUser.getLastname(),
                newUser.getFirstname()+" "+newUser.getLastname(),
                emailOption,
                null,
                authenticationMethod,
                null,
                null,
                passwordInfoOption);
        return socialUser;
    }
}