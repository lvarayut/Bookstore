package controllers;

import play.libs.Json;
import play.mvc.*;
import models.*;
import repositories.*;
import views.html.*;
import views.html.main.*;
import interceptors.WithProvider;
import securesocial.core.Identity;
import securesocial.core.java.SecureSocial;
import java.util.List;
import utils.Util;

public class BookStore extends Controller{
    //@SecureSocial.SecuredAction(authorization = WithProvider.class, params = {})
    public static Result index(){
       //Identity user = (Identity) ctx().args.get(SecureSocial.USER_KEY);
       //return ok(index.render(user));
    	List<Product> products = Util.iterableToList(ProductRepository.findAll());
        return ok(index.render(products));
   }

    /**
     * Load more products when a user
     * scrolls to the bottom of the page
     * @return JSON
     */
   public static Result loadBooks(int count){
        List<Product> products = Util.iterableToList(ProductRepository.findBySkip(count));
       return ok(Json.toJson(products));
   }
}
