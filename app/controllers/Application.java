package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;
import java.util.Date;

public class Application extends Controller {

    public static Result indexDefault() {
        return ok(indexDefault.render("Your new application is ready."));
    }

    public static Result index(){
        // Initialize books
        init();
        return ok(index.render(""));
    }

    public static Result bookList(){
        Iterable<Book> books = Book.findAll();
        return ok(bookList.render(books));
    }

    private static void init(){
        Book mongoDB = new Book("MongoDB", "Kristina Chodorow", new Date());
        Book play = new Book("Play for Java: Covers Play 2"," Nicolas Leroux", new Date());
        Book bootstrap = new Book("Bootstrap Site Blueprints", "David Cochran", new Date());
        mongoDB.insert();
        play.insert();
        bootstrap.insert();
    }

}
