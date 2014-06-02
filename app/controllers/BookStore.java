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
        List<Product> products = Util.iterableToList(ProductRepository.findByName(name));
        return ok(Json.toJson(products));
    }

    public static Result getImage(String name){
        return ok(ProductRepository.findImage(name));
    }


}
