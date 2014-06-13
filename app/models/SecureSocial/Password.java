package models.SecureSocial;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Password is used to save a type of
 * encryption, encoded password,
 * and salt respectively
 */
public class Password {
    @JsonProperty("passwordHasher")
    private String passwordHasher;
    @JsonProperty("password")
    private String password;
    @JsonProperty("salt")
    private String salt;

    public Password() {
    }

    public Password(String passwordHasher, String password, String salt) {
        this.passwordHasher = passwordHasher;
        this.password = password;
        this.salt = salt;
    }

    public String getPasswordHasher() {
        return passwordHasher;
    }

    public void setPasswordHasher(String passwordHasher) {
        this.passwordHasher = passwordHasher;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
