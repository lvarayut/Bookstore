package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;

import java.util.ArrayList;
import java.util.Date;

public class Application extends Controller {


//    public static Result index() {
//        // Initialize books
//        init();
//        User varayut = User.findByName("varayut");
//        return ok(index.render(varayut));
//    }
//
//    public static Result login(){
//        return ok(signUp.render());
//    }
//
//    public static Result bookList() {
//        Iterable<Book> books = Stock.findAllInStock("stock1");
//        User varayut = User.findByName("varayut");
//        return ok(bookList.render(books, varayut));
//    }
//
//    public static Result buy(String bookName, float bookPrice) {
//        Stock bookStock = Stock.findByName("stock1");
//        User varayut = User.findByName("varayut");
//        User bookStore = User.findByName("bookstore");
//        if (varayut.isHaveCredit(bookPrice)) {
//            // Remove money from buyer
//            if(!varayut.buy(bookPrice))return ok(bookList.render(bookStock.getBooks(), varayut)) ;
//            // Add money to seller
//            bookStore.sell(bookPrice);
//            // Remove a book from a stock
//            bookStock.removeBook(bookStock, bookName);
//        }
//        return ok(bookList.render(bookStock.getBooks(), varayut));
//    }

    private static void init() {
        ArrayList<Book> books = new ArrayList<Book>();
        Stock bookStock = new Stock("stock1", books);
        books.add(new Book(
                "MongoDB",
                "Kristina Chodorow",
                new Date(),
                "Manage the huMONGOus amount of data collected through your web application with MongoDB.",
                "http://www.xaprb.com/media/2010/12/mongodb-definitive-guide.gif",
                30
        ));
        books.add(new Book(
                "Play for Java: Covers Play 2",
                "Nicolas Leroux",
                new Date(),
                "Play for Java shows you how to build Java-based web applications using the Play 2 framework.",
                "http://www.playframework.com/assets/images/docs/play-for-java-cover.jpg",
                35
        ));
        books.add(new Book(
                "Bootstrap Site Blueprints",
                "David Cochran",
                new Date(),
                "Learn the inner workings of Bootstrap 3 and create web applications.",
                "http://www.packtpub.com/sites/default/files/4524OS_Bootstrap%20Site%20Blueprints_Frontcover.jpg",
                40
        ));
        if (Stock.findByName("stock1") == null) {
            bookStock.insert();
        }
        if (User.findByName("varayut") == null) {
            Account varayutAccount = new Account(100);
            User varayut = new User("varayut", varayutAccount);
            Account bookStoreAccount = new Account(0);
            User bookStore = new User("bookstore", bookStoreAccount);
            varayut.insert();
            bookStore.insert();
        }
    }

}
