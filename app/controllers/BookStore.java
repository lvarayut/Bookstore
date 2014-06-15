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
        User currentUser = null;
        User dbUser = null;
        if(userIdentity != null){
            currentUser = Util.transformIdentityToUser(userIdentity);
            dbUser = UserRepository.findByEmail(currentUser.getEmail());
            userForm = userForm.fill(dbUser);
        }
    	return ok(setting.render(userForm,dbUser));
    }

    public static Result settingRegister(){
        Identity userIdentity =(Identity) ctx().args.get(SecureSocial.USER_KEY);
        User currentUser = Util.transformIdentityToUser(userIdentity);
        User dbUser = UserRepository.findByEmail(currentUser.getEmail());
        Form<User> userForm = Form.form(User.class).bindFromRequest();
        if(userForm.hasErrors()){
            return badRequest(setting.render(userForm,dbUser));
        }
        else{
            UserRepository.update(userForm.get());
            return ok(setting.render(userForm,dbUser));
            //controllers.BookStore.index
        }
    }

    public static Result description(String id){
        Book product = (Book)ProductRepository.findOneById(id);
        return ok(description.render(product));
    }

    public static Result addBook(){
        return ok(upsertBook.render(Form.form(Book.class)));
    }

    public static Result handleAddBook(){
        Form<Book> bookForm = Form.form(Book.class).bindFromRequest();
        if(bookForm.hasErrors()){
            return badRequest(upsertBook.render(bookForm));
        }
        else{
            Book book = bookForm.get();
            ProductRepository.insert(book);
        }
        return redirect("/listbook");
    }

    public static Result listBook(){
        List<Product> products = Util.iterableToList(ProductRepository.findAll());
        List<Book> books = new ArrayList<Book>();
        for(Product product : products){
            books.add((Book)product);
        }
        return ok(listBook.render(books));
    }

    public static Result updateBook(String id){
        Book book = (Book) ProductRepository.findOneById(id);
        Form bookForm = Form.form(Book.class);
        bookForm = bookForm.fill(book);
        return ok(upsertBook.render(bookForm));
    }

    public static Result handleUpdateBook(){
        Form<Book> bookForm = Form.form(Book.class).bindFromRequest();
        if(bookForm.hasErrors()){
            return badRequest(upsertBook.render(bookForm));
        }
        else {
            Book book = (Book) bookForm.get();
            System.out.println(book);
            ProductRepository.update(book);
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

    public static Result addAddress(){
        return TODO;
    }


}
