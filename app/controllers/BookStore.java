package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import models.*;
import repositories.*;
import views.html.*;
import views.html.defaultpages.badRequest;
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
     * Add a new book (Get request)
     * @return Adding book form
     */
    public static Result addBook(){
        return ok(upsertBook.render(Form.form(Book.class)));
    }

    /**
     * Handle adding a book (Post request)
     * @return redirect to the book list
     */
    public static Result handleAddBook(){
        Form<Book> bookForm = Form.form(Book.class).bindFromRequest();
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart picture = body.getFile("imagePath");
        File pictureFile = picture.getFile();
        if(bookForm.hasErrors()){
            return badRequest(upsertBook.render(bookForm));
        }
        else{
            Book book = bookForm.get();
            book.setCategory("Book");
            ProductRepository.insert(book,pictureFile);
        }
        return redirect("/listbook");
    }

    /**
     * List all books
     * @return
     */
    public static Result listBook(){
        List<Product> products = Util.iterableToList(ProductRepository.findAll());
        List<Book> books = new ArrayList<Book>();
        for(Product product : products){
            books.add((Book)product);
        }
        return ok(listBook.render(books));
    }

    /**
     * Update a given book (Get request)
     * @param id
     * @return Updating book form
     */
    public static Result updateBook(String id){
        Book book = (Book) ProductRepository.findOneById(id);
        Form bookForm = Form.form(Book.class);
        bookForm = bookForm.fill(book);
        return ok(upsertBook.render(bookForm));
    }

    /**
     * Handle updating a book (Post request)
     * @return Adding book form
     */
    public static Result handleUpdateBook(){
        Form<Book> bookForm = Form.form(Book.class).bindFromRequest();
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart picture = body.getFile("imagePath");
        File pictureFile = picture.getFile();
        if(bookForm.hasErrors()){
            return badRequest(upsertBook.render(bookForm));
        }
        else {
            Book book = (Book) bookForm.get();
            ProductRepository.updateWithPicture(book, pictureFile);
        }
        return redirect("/listbook");
    }

    public static Result deleteBook(String id){
        ProductRepository.removeById(id);
        return redirect("/listbook");
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

        // Create an address
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
        if(dbUser.getUsername().equals("")){
            comment.setUser(dbUser.getUsername());
        }
        else{
            comment.setUser(dbUser.getFirstname());
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

    public static Result loadComments(){
        JsonNode data = request().body().asJson();
        String productId = data.path("id").textValue();
        Product product = ProductRepository.findOneById(productId);
        return ok(Json.toJson(product.getComments()));
    }
}
