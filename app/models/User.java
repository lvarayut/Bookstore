package models;

import akka.io.Inet;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.avaje.ebean.ExpressionList;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import models.SecureSocial.Password;
import org.jongo.MongoCollection;
import securesocial.core.Identity;
import uk.co.panaxiom.playjongo.PlayJongo;
import org.jongo.marshall.jackson.oid.*;

import java.util.ArrayList;
import java.util.List;

/*
* User is an account for persons
* in the application
*/

public class User {

    @Id @ObjectId
    private String id;
    @JsonProperty("userid")
    private String userid;
    @JsonProperty("provider")
    private String provider;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("email")
    private String email;
    @JsonProperty("authmethod")
    private String authmethod;
    // Shouldn't be the same name with the exist class; Password
    @JsonProperty("pwd")
    private Password pwd;
    @JsonProperty("username")
    private String username;
    @JsonProperty("phoneno")
    private String phoneno;
    @JsonProperty("age")
    private int age;
    @JsonProperty("accounts")
    private List<Account> accounts;
    @JsonProperty("addresses")
    private List<Address> addresses;
    @JsonProperty("roles")
    private List<Role> roles;


    public User(){
        accounts = new ArrayList<Account>();
        roles = new ArrayList<Role>();
        addresses = new ArrayList<Address>();
    }

    // Get users collection
    public static MongoCollection users() {
        return PlayJongo.getCollection("users");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthmethod() {
        return authmethod;
    }

    public void setAuthmethod(String authmethod) {
        this.authmethod = authmethod;
    }

    public Password getPwd() {
        return pwd;
    }

    public void setPwd(Password pwd) {
        this.pwd = pwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


//
//    public Account getAccount() {
//        return account;
//    }
//
//    public void setAccount(Account account) {
//        this.account = account;
//    }




//    public void updateName(String name) {
//        users().update("{name: #}", this.getName()).with("{name:#}", name);
//    }
//
//    public void updateAccount(User user, float balance) {
//        users().update("{name: #}", this.getName()).with("{$set: {account.balance: #}}", user.getAccount().getBalance());
//    }




    //    public ArrayList<String> getTransactionStatus() {
//        return transactionStatus;
//    }
//
//    public void addTransactionStatus(String transaction) {
//        this.getTransactionStatus().add(transaction);
//    }
//
//    public void removeTransactionStatus(String transaction) {
//        this.getTransactionStatus().remove(transaction);
//    }


//    public void updateTransactionStatus(String userName, String transactionId, String command) {
//        if (command.equals("push")) {
//            this.addTransactionStatus(transactionId);
//            users().update("{$and: [{name: #},{transactionStatus:{$ne:#}}]}", userName, transactionId).with("{$#: {transactionStatus:#}}", command, transactionId);
//        } else {
//            users().update("{name: #}", userName).with("{$#: {transactionStatus:#}}", command, transactionId);
//            this.removeTransactionStatus(transactionId);
//        }
//    }



//    public boolean buy(float bookPrice) {
        // Create a transaction
//        Transaction transaction = new Transaction(this.getName(), "bookstore", bookPrice, "initial");
//        transaction.insert();
//        transaction.printOut();
//        if (isHaveCredit(bookPrice)) {
//            transaction.updateStatus("pending");
//            transaction.printOut();
//            this.getAccount().withdraw(bookPrice);
//            // Update Account
//            updateAccount(this, bookPrice);
//            updateTransactionStatus(this.getName(), transaction.getId(), "push");
//            updateTransactionStatus("bookstore", transaction.getId(), "push");
//            printOut();
            // In case of accidence
//            try{
//                throw new RuntimeException();
//            }catch (Exception e){
//                //Roll back
//                // Change status
//                transaction.updateStatus("cancelling");
//                transaction.printOut();
//                this.getAccount().deposit(bookPrice);
//                updateAccount(this, bookPrice);
//                // Remove transactions in the list
//                updateTransactionStatus(this.getName(),transaction.getId(), "pull");
//                updateTransactionStatus("bookstore",transaction.getId(), "pull");
//                printOut();
//                transaction.updateStatus("cancelled");
//                transaction.printOut();
//                if(true) return false;
//            }
            // Change status
//            transaction.updateStatus("committed");
//            transaction.printOut();
//            updateTransactionStatus(this.getName(), transaction.getId(), "pull");
//            updateTransactionStatus("bookstore", transaction.getId(), "pull");
//            printOut();
//            transaction.updateStatus("done");
//            transaction.printOut();
//        }
//         return true;
//    }

//    public void sell(float bookPrice) {
//        this.getAccount().deposit(bookPrice);
//        updateAccount(this, bookPrice);
//    }

//    public boolean isHaveCredit(float bookPrice) {
//        return this.getAccount().getBalance() >= bookPrice;
//    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userid='" + userid + '\'' +
                ", provider='" + provider + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", authmethod='" + authmethod + '\'' +
                ", pwd=" + pwd +
                ", username='" + username + '\'' +
                ", phoneno='" + phoneno + '\'' +
                ", age=" + age +
                ", accounts=" + accounts +
                ", roles=" + roles +
                '}';
    }

}
