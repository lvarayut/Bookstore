package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;

import java.util.ArrayList;
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
        ArrayList<Book> books= new ArrayList<Book>();
        books.add(new Book(
                "MongoDB",
                "Kristina Chodorow",
                new Date(),
                "Manage the huMONGOus amount of data collected through your web application with MongoDB.",
                "http://www.xaprb.com/media/2010/12/mongodb-definitive-guide.gif"
        ));
        books.add(new Book(
                "Play for Java: Covers Play 2",
                "Nicolas Leroux",
                new Date(),
                "Play for Java shows you how to build Java-based web applications using the Play 2 framework.",
                "http://www.playframework.com/assets/images/docs/play-for-java-cover.jpg"
        ));
        books.add(new Book(
                "Bootstrap Site Blueprints",
                "David Cochran",
                new Date(),
                "Learn the inner workings of Bootstrap 3 and create web applications.",
                "http://www.packtpub.com/sites/default/files/4524OS_Bootstrap%20Site%20Blueprints_Frontcover.jpg"
        ));
        for(int i = 0; i<books.size();i++){
            if(Book.findByName(books.get(i).getName()) == null){
                books.get(i).insert();
            }

        }
    }

}
