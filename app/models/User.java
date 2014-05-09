package models;

import akka.io.Inet;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.avaje.ebean.ExpressionList;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jongo.MongoCollection;
import securesocial.core.Identity;
import uk.co.panaxiom.playjongo.PlayJongo;
import securesocial.core.SocialUser;

import java.util.ArrayList;
import java.util.List;

/*
* User is an account for persons
* in the application
*/

public class User {

    @JsonProperty("_id")
    private String id;
    private String name;
    private String email;
    private String password;
    private String username;
    private String country;
    private int age;
    private Account account;
    private Identity identity;
//    private ArrayList<String> transactionStatus;
    @JsonCreator
    public User(){

    }

    @JsonCreator
    public User(@JsonProperty("email") String email, @JsonProperty("password") String password, @JsonProperty("username") String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    // Get users collection
    public static MongoCollection users() {
        return PlayJongo.getCollection("users");
    }

    public static User findByName(String name) {
        return users().findOne("{name: #}", name).as(User.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public void signUp(){
        insert();
    }

    public void insert() {
        users().save(this);
    }

    public void remove() {
        users().remove(this.getId());
    }

    public void updateName(String name) {
        users().update("{name: #}", this.getName()).with("{name:#}", name);
    }

    public void updateAccount(User user, float balance) {
        users().update("{name: #}", this.getName()).with("{$set: {account.balance: #}}", user.getAccount().getBalance());
    }

    public static Iterable<User> findAllUsers(){
        return users().find().as(User.class);
    }


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



    public boolean buy(float bookPrice) {
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
         return true;
    }

    public void sell(float bookPrice) {
        this.getAccount().deposit(bookPrice);
        updateAccount(this, bookPrice);
    }

    public boolean isHaveCredit(float bookPrice) {
        return this.getAccount().getBalance() >= bookPrice;
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", account=" + account +
                '}';
    }

    public static void printAllUsers() {
        Iterable<User> users = findAllUsers();
        for (User user : users) {
            System.out.println(user.toString());
        }

    }



}