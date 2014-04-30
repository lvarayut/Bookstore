package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import models.*;
import views.html.introduction.*;

import java.util.Map;

public class Introduction extends Controller {


    public static Result introduction() {
        return ok(introduction.render());
    }

    public static Result signUp(){
        Map<String,String[]> form = request().body().asFormUrlEncoded();
        String email = form.get("email")[0];
        String username = form.get("username")[0];
        String password = form.get("password")[0];
        User user = new User(email,username,password);
        user.signUp();
        return ok(introduction.render());
    }

    public static Result oAuthDenied(final String providerKey) {
        flash("FLASH_ERROR_KEY","You need to accept the OAuth connection in order to use this website!");
        return ok(introduction.render());
    }

}
