package controllers;

import play.mvc.*;
import models.*;
import views.html.*;
public class BookStore extends Controller{

   public static Result index(){
        return ok(index.render());
   }
}
