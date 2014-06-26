package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import models.*;
import repositories.*;
import views.html.*;
import views.html.defaultpages.badRequest;
import views.html.defaultpages.error;
import views.html.main.*;
import views.html.book.*;
import interceptors.WithProvider;
import securesocial.core.Identity;
import securesocial.core.java.SecureSocial;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utils.Util;


public class BookStore extends Controller{
    //@SecureSocial.SecuredAction(authorization = WithProvider.class, params = {})
    public static Result index(){
       //Identity user = (Identity) ctx().args.get(SecureSocial.USER_KEY);
       //return ok(index.render(user));
        return ok(index.render());
   }

    /**
     * Load more products when a user
     * scrolls to the bottom of the page
     * @return JSON
     */
   public static Result loadProducts(int count){
       List<Product> products = Util.iterableToList(ProductRepository.findBySkip(count));
       return ok(Json.toJson(products));
   }

    /**
     * Search products from MongoDB using the given words
     * @param name
     * @return JSON
     */
    public static Result searchProducts(String name){
        List<Product> products;
        //  User inputs empty value
        if(name.equals("")){
            products = Util.iterableToList(ProductRepository.findBySkip(0));
        }
        else{
            products = Util.iterableToList(ProductRepository.findByName(name));
        }
        return ok(Json.toJson(products));
    }

    /**
     * Find image data in the database
     * @param name
     * @return Image binary
     */
    public static Result getImage(String name){
        return ok(ProductRepository.findImage(name));
    }
    @SecureSocial.SecuredAction
    public static Result setting(){
    	Form<User> userForm = Form.form(User.class);
    	Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        User currentUser = null;
        User dbUser = null;
        if(userIdentity != null){
            currentUser = Util.transformIdentityToUser(userIdentity);
            dbUser = UserRepository.findByEmail(currentUser.getEmail());
            userForm = userForm.fill(dbUser);
        }
    	return ok(setting.render(userForm));
    }
    public static Result settingRegister(){
        Form<User> userForm = Form.form(User.class).bindFromRequest();
        if(userForm.hasErrors()){
            return badRequest(setting.render(userForm));
        }
        else{
            UserRepository.update(userForm.get());
            return ok(setting.render(userForm));
            //controllers.BookStore.index
        }
    }



    public static Result description(String id){
        Book product = (Book)ProductRepository.findOneById(id);
        String[] words = product.getName().split(" ");
        List<Book> similarProducts = new ArrayList<Book>();
        System.out.println("Before Add to list ");
        for (int i = 0; i < words.length; i++) {
            List <Product> products = Util.iterableToList(ProductRepository.findByName(words[i]));
            System.out.println("Add to list "+words[i]+products.size());
            for (int j=0; j<products.size();j++){
                System.out.println("Add to list "+products.get(j));
                similarProducts.add((Book)products.get(j));
            }
        }
        return ok(description.render(product,similarProducts));
    }

    /**
     * Load addresses of a current user
     * @return JSON
     */
    @SecureSocial.SecuredAction
    public static Result loadAddresses(){
        Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        User currentUser = Util.transformIdentityToUser(userIdentity);
        User dbUser = UserRepository.findByEmail(currentUser.getEmail()); 
        return ok(Json.toJson(dbUser.getAddresses()));
    }

    /**
     * Add a new address
     * @return HTML status
     */
    @SecureSocial.SecuredAction
    public static Result addAddress(){
        // Get a current user
        Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        User currentUser = Util.transformIdentityToUser(userIdentity);
        User dbUser = UserRepository.findByEmail(currentUser.getEmail());

        // Get a new address, JSON
        Http.RequestBody body = request().body();
        JsonNode data = request().body().asJson();
        String street = data.path("street").textValue();
        String city = data.path("city").textValue();
        String country = data.path("country").textValue();
        String zipcode = data.path("zipcode").textValue();

        // Create a new address
        Address newAddress = new Address();
        newAddress.setStreet(street);
        newAddress.setCity(city);
        newAddress.setCountry(country);
        newAddress.setZipcode(zipcode);
        dbUser.getAddresses().add(newAddress);
        // Update to MongoDB
        UserRepository.update(dbUser);
        return ok();
    }

     /**
     * Edit a requested address
     * @return HTML status
     */
    @SecureSocial.SecuredAction
    public static Result editAddress(){
        // Get a current user
        Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        User currentUser = Util.transformIdentityToUser(userIdentity);
        User dbUser = UserRepository.findByEmail(currentUser.getEmail());

        // Get a new address, JSON
        JsonNode data = request().body().asJson();

        // Create an old address
        Address oldAddress = new Address();
        oldAddress.setStreet(data.path("street").textValue());
        oldAddress.setCity(data.path("city").textValue());
        oldAddress.setCountry(data.path("country").textValue());
        oldAddress.setZipcode(data.path("zipcode").textValue());

        // Create an address
        Address newAddress = new Address();
        newAddress.setStreet(data.path("newstreet").textValue());
        newAddress.setCity(data.path("newcity").textValue());
        newAddress.setCountry(data.path("newcountry").textValue());
        newAddress.setZipcode(data.path("newzipcode").textValue());

        List<Address> addresses = new ArrayList<Address>(dbUser.getAddresses());
        for(int i = 0; i<addresses.size(); i++){
            if(addresses.get(i).getStreet().equals(oldAddress.getStreet()) &&
                    addresses.get(i).getCity().equals(oldAddress.getCity()) &&
                    addresses.get(i).getCountry().equals(oldAddress.getCountry()) &&
                    addresses.get(i).getZipcode().equals(oldAddress.getZipcode())){
                dbUser.getAddresses().remove(i);
                dbUser.getAddresses().add(i, newAddress);
                break;
            }
        }

        // Update to MongoDB
        UserRepository.update(dbUser);
        return ok();
    }

    /** 
     * Remove a requested address
     * @return HTML status
     */
    @SecureSocial.SecuredAction
    public static Result removeAddress(){
        // Get a current user
        Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        User currentUser = Util.transformIdentityToUser(userIdentity);
        User dbUser = UserRepository.findByEmail(currentUser.getEmail());

        // Get a new address, JSON
        JsonNode data = request().body().asJson();
        String street = data.path("street").textValue();
        String city = data.path("city").textValue();
        String country = data.path("country").textValue();
        String zipcode = data.path("zipcode").textValue();

        // Create a new address
        Address newAddress = new Address();
        newAddress.setStreet(street);
        newAddress.setCity(city);
        newAddress.setCountry(country);
        newAddress.setZipcode(zipcode);
        List<Address> addresses = new ArrayList<Address>(dbUser.getAddresses());
        // Remove the requested address.
        for(Address address : addresses){
            if(address.getStreet().equals(newAddress.getStreet()) &&
               address.getCity().equals(newAddress.getCity()) &&
               address.getCountry().equals(newAddress.getCountry()) &&
               address.getZipcode().equals(newAddress.getZipcode())){
                dbUser.getAddresses().remove(address);
                break;
            }
        }

        // Update to MongoDB
        UserRepository.update(dbUser);

        return ok();
    }


    /**
     * Load accounts of a current user
     * @return JSON
     */
    @SecureSocial.SecuredAction
    public static Result loadAccounts(){
        Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        User currentUser = Util.transformIdentityToUser(userIdentity);
        User dbUser = UserRepository.findByEmail(currentUser.getEmail());
        return ok(Json.toJson(dbUser.getAccounts()));
    }

    /**
     * Add a new account
     * @return HTML status
     */
    @SecureSocial.SecuredAction
    public static Result addAccount(){
        // Get a current user
        Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        User currentUser = Util.transformIdentityToUser(userIdentity);
        User dbUser = UserRepository.findByEmail(currentUser.getEmail());

        // Get a new account, JSON
        Http.RequestBody body = request().body();
        JsonNode data = request().body().asJson();
        String accountId = data.path("accountId").textValue();
        String type = data.path("type").textValue();
        float balance = (float)data.path("balance").doubleValue();

        // Create a new account
        Account newAccount = new Account();
        newAccount.setAccountId(accountId);
        newAccount.setType(type);
        newAccount.setBalance(balance);

        dbUser.getAccounts().add(newAccount);
        // Update to MongoDB
        UserRepository.update(dbUser);
        return ok();
    }

    /**
     * Edit a requested account
     * @return HTML status
     */
    @SecureSocial.SecuredAction
    public static Result editAccount(){
        // Get a current user
        Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        User currentUser = Util.transformIdentityToUser(userIdentity);
        User dbUser = UserRepository.findByEmail(currentUser.getEmail());

        // Get a new account, JSON
        JsonNode data = request().body().asJson();

        // Create  an old account
        Account oldAccount = new Account();
        oldAccount.setAccountId(data.path("accountId").textValue());
        oldAccount.setType(data.path("type").textValue());
        oldAccount.setBalance((float)data.path("balance").doubleValue());

        // Create  n new account
        Account newAccount = new Account();
        newAccount.setAccountId(data.path("newaccountId").textValue());
        newAccount.setType(data.path("newtype").textValue());
        newAccount.setBalance((float)data.path("newbalance").doubleValue());

        List<Account> accounts = new ArrayList<Account>(dbUser.getAccounts());
        for(int i = 0; i<accounts.size(); i++){
            if(accounts.get(i).getAccountId().equals(oldAccount.getAccountId()) &&
                    accounts.get(i).getType().equals(oldAccount.getType()) &&
                    accounts.get(i).getBalance() == oldAccount.getBalance()){
                dbUser.getAccounts().remove(i);
                dbUser.getAccounts().add(i, newAccount);
                break;
            }
        }

        // Update to MongoDB
        UserRepository.update(dbUser);
        return ok();
    }

    /**
     * Remove a requested account
     * @return HTML status
     */
    @SecureSocial.SecuredAction
    public static Result removeAccount(){
        // Get a current user
        Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        User currentUser = Util.transformIdentityToUser(userIdentity);
        User dbUser = UserRepository.findByEmail(currentUser.getEmail());

        // Get a new account, JSON
        JsonNode data = request().body().asJson();

        // Create  an  account
        Account removedAccount = new Account();
        removedAccount.setAccountId(data.path("accountId").textValue());
        removedAccount.setType(data.path("type").textValue());
        removedAccount.setBalance((float) data.path("balance").doubleValue());

        List<Account> accounts = new ArrayList<Account>(dbUser.getAccounts());
        // Remove the requested account.
        for(Account account : accounts){
            if(account.getAccountId().equals(removedAccount.getAccountId()) &&
                    account.getType().equals(removedAccount.getType()) &&
                    account.getBalance() == removedAccount.getBalance()){
                dbUser.getAccounts().remove(account);
                break;
            }
        }

        // Update to MongoDB
        UserRepository.update(dbUser);

        return ok();
    }

    @SecureSocial.SecuredAction
    public static Result addReview(){
        String productId;
        // Get a current user
        Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        User currentUser = Util.transformIdentityToUser(userIdentity);
        User dbUser = UserRepository.findByEmail(currentUser.getEmail());
        JsonNode data = request().body().asJson();

        // Create a comment object
        Comment comment = new Comment();
        if(dbUser.getUsername() == null){
            comment.setUser(dbUser.getFirstname());
        }
        else{
            comment.setUser(dbUser.getUsername());
        }
        comment.setPublicationDate(new Date());
        comment.setDescription(data.path("description").textValue());
        comment.setRating(data.path("rating").intValue());

        // Find a current product
        productId = data.path("productId").textValue();
        Product product = ProductRepository.findOneById(productId);
        product.getComments().add(comment);
        // Update product
        ProductRepository.update(product);

        return ok(Json.toJson(comment));
    }

    /**
     * Load comment of a current book
     * @return JSON
     */
    public static Result loadComments(){
        JsonNode data = request().body().asJson();
        String productId = data.path("id").textValue();
        Product product = ProductRepository.findOneById(productId);
        return ok(Json.toJson(product.getComments()));
    }

    /**
     * Add an item to cart
     * @return
     */
    @SecureSocial.SecuredAction
    public static Result addToCart(){
        // Get a current user
        Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        User currentUser = Util.transformIdentityToUser(userIdentity);
        User dbUser = UserRepository.findByEmail(currentUser.getEmail());

        JsonNode data = request().body().asJson();
        String productId = data.path("id").textValue();

        Product product = ProductRepository.findOneById(productId);
        Order order = OrderRepository.findOneByUserId(dbUser.getId());

        if(order == null){
            order = new Order();
            order.setUserId(dbUser.getId());
            order.getProductIds().add(product.getId());
            order.setQuantity(1);
            order.setTotal(product.getPrice());
            OrderRepository.insert(order);
        }
        else{
            order.getProductIds().add(product.getId());
            order.setQuantity(order.getQuantity() + 1);
            order.setTotal(order.getTotal() + product.getPrice());
            OrderRepository.update(order);
        }

        return ok(Json.toJson(product));
    }

    /**
     * Load all items for a current user
     * @return
     */
    @SecureSocial.SecuredAction
    public static Result loadCart(){
        // Get a current user
        Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        User currentUser = Util.transformIdentityToUser(userIdentity);
        User dbUser = UserRepository.findByEmail(currentUser.getEmail());

        // Get products in the cart
        Order order = OrderRepository.findOneByUserId(dbUser.getId());
        List<Product> product = null;
        if(order != null){
            List<String> productIds = order.getProductIds();
            product = new ArrayList<Product>();

            for(String id : productIds){
                product.add(ProductRepository.findOneById(id));
            }
            return ok(Json.toJson(product));
        }
        return ok();

    }

    @SecureSocial.SecuredAction
    public static Result payment(){
        return ok(payment.render());
    }

    @SecureSocial.SecuredAction
    public static Result handlePayment(){
        // Get a current user
        Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        User currentUser = Util.transformIdentityToUser(userIdentity);
        User buyer = UserRepository.findByEmail(currentUser.getEmail());

        // Get post data
        JsonNode data = request().body().asJson();
        int addressIndex = data.path("address").asInt();
        int accountIndex = data.path("account").asInt();

        // Get products in the cart
        Order order = OrderRepository.findOneByUserId(buyer.getId());
        List<String> productIds = new ArrayList<String>(order.getProductIds());

        // Create History
        History history = new History();
        history.setBuyer(buyer);
        history.setQuantity(order.getQuantity());
        history.setTotal(order.getTotal());
        history.setPaymentMethod("Credit card");

        // Manage transactions
        for(String id : productIds){
            Product product = ProductRepository.findOneById(id);
            User seller = UserRepository.findById(product.getUserId());

            // Deposit money to seller and Withdraw money from buyer
            boolean isSuccess = TransactionRepository.buyProduct(buyer, seller, accountIndex, product.getPrice());
            if(isSuccess){
                // Add to history
                history.getProducts().add(product);

                // Remove the Order
                order.getProductIds().remove(id);

                // Remove the product
                ProductRepository.removeById(id);
            }
            else{
                // Return Status: 500
                // Update in DB
                OrderRepository.update(order);
                HistoryRepository.insert(history);
                return internalServerError();
            }
        }

        // Update in DB
        OrderRepository.removeById(order);
        HistoryRepository.insert(history);
        return ok();
    }

    @SecureSocial.SecuredAction
    public static Result history(){
        return ok(history.render());
    }

    /**
     * Load history for a current user
     * @return
     */
    @SecureSocial.SecuredAction
    public static Result loadHistory(){
        // Get a current user
        Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        User currentUser = Util.transformIdentityToUser(userIdentity);
        User dbUser = UserRepository.findByEmail(currentUser.getEmail());

        // Get products in the cart
        List<History> histories = Util.iterableToList(HistoryRepository.findByUserId(dbUser.getId()));
        return ok(Json.toJson(histories));
    }
}
