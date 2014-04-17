package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.introduction.*;

import java.util.ArrayList;
import java.util.Date;

public class Introduction extends Controller {


    public static Result introduction() {
        return ok(introduction.render());
    }


}
