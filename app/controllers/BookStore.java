package controllers;

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

import java.util.ArrayList;
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

    public static Result getImage(String name){
        return ok(ProductRepository.findImage(name));
    }
    @SecureSocial.SecuredAction
    public static Result setting(){
    	Form<User> userForm = Form.form(User.class);
    	Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        if(userIdentity != null){
            User user = Util.transformIdentityToUser(userIdentity);
            userForm = userForm.fill(user);
        }
    	return ok(setting.render(userForm));
    }

    public static Result settingRegister(){
        Form<User> userForm = Form.form(User.class).bindFromRequest();
        if(userForm.hasErrors()){
            return badRequest(setting.render(userForm));
        }
        else{
            //System.out.println("firstname 1 :" + userForm.get().getFirstname());
            //System.out.println("username 1 :" + userForm.get().getUsername());
            UserRepository.update(userForm.get());
            //System.out.println("firstname 2 :" + userForm.get().getFirstname());
            //System.out.println(userForm.get().getLastname());
            //System.out.println("username 2 :" + userForm.get().getUsername());
            //System.out.println(userForm.get().getId());
            return ok(setting.render(userForm));
            //controllers.BookStore.index
        }
    }

    public static Result bookpanel(){
        return ok(bookpanel.render());
    }

    public static Result addBook(){
        return ok(upsertBook.render(Form.form(Book.class)));
    }

    public static Result listBook(){
        List<Product> products = Util.iterableToList(ProductRepository.findAll());
        List<Book> books = new ArrayList<Book>();
        for(Product product : products){
            books.add((Book)product);
        }
        return ok(listBook.render(books));
    }

    public static Result updateBook(String name){
        Book book = (Book) ProductRepository.findOneByName(name);
        Form bookForm = Form.form(Book.class);
        bookForm = bookForm.fill(book);
        return ok(upsertBook.render(bookForm));
    }

    public static Result handleUpdateBook(){
        Form bookForm = Form.form(Book.class).bindFromRequest();
        if(bookForm.hasErrors()){
            return badRequest(upsertBook.render(bookForm));
        }
        else {
            Product product = (Product) bookForm.get();
            System.out.println(product.getRating());
            ProductRepository.update(product);
        }
        return redirect("/listbook");
    }


}
