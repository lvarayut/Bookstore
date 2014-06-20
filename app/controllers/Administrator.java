package controllers;

import models.Book;
import models.Product;
import play.data.Form;
import play.mvc.*;
import repositories.ProductRepository;
import utils.Util;
import views.html.administrator.*;
import views.html.book.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Administrator extends Controller {

    public static Result index(){
        return redirect("/administrator/listbook");
    }

    /**
     * Add a new book (Get request)
     * @return Adding book form
     */
    public static Result addBook(){
        return ok(upsertBook.render(Form.form(Book.class)));
    }

    /**
     * Handle adding a book (Post request)
     * @return redirect to the book list
     */
    public static Result handleAddBook(){
        Form<Book> bookForm = Form.form(Book.class).bindFromRequest();
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart picture = body.getFile("imagePath");
        File pictureFile = picture.getFile();
        if(bookForm.hasErrors()){
            return badRequest(upsertBook.render(bookForm));
        }
        else{
            Book book = bookForm.get();
            book.setCategory("Book");
            ProductRepository.insert(book, pictureFile);
        }
        return redirect("/administrator/listbook");
    }

    /**
     * List all books
     * @return
     */
    public static Result listBook(){
        List<Product> products = Util.iterableToList(ProductRepository.findAll());
        List<Book> books = new ArrayList<Book>();
        for(Product product : products){
            books.add((Book)product);
        }
        return ok(listBook.render(books));
    }

    /**
     * Update a given book (Get request)
     * @param id
     * @return Updating book form
     */
    public static Result updateBook(String id){
        Book book = (Book) ProductRepository.findOneById(id);
        Form bookForm = Form.form(Book.class);
        bookForm = bookForm.fill(book);
        return ok(upsertBook.render(bookForm));
    }

    /**
     * Handle updating a book (Post request)
     * @return Adding book form
     */
    public static Result handleUpdateBook(){
        Form<Book> bookForm = Form.form(Book.class).bindFromRequest();
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart picture = body.getFile("imagePath");
        File pictureFile = null;
        if(bookForm.hasErrors()){
            return badRequest(upsertBook.render(bookForm));
        }
        else {
            Book book = (Book) bookForm.get();
            // Take data from the database
            Book dbBook = (Book) ProductRepository.findOneById(book.getId());
            book.setComments(dbBook.getComments());
            if(picture != null){
                pictureFile = picture.getFile();
            }
            ProductRepository.updateWithPicture(book, pictureFile);
        }
        return redirect("/administrator/listbook");
    }

    public static Result deleteBook(String id){
        ProductRepository.removeById(id);
        return redirect("/administrator/listbook");
    }

}
