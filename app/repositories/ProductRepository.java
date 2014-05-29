package repositories;


import models.Product;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

public class ProductRepository {

    public static MongoCollection products() {
        return PlayJongo.jongo().getCollection("products");
    }

    public static void insert(Product product){
        products().save(product);
    }

    public static void updateName(Product product, String name){
        products().update("{name: #}",product.getName()).with("{name:#}",name);
    }

    public static void remove(Product product){
        products().remove(product.getId());
    }

    public static Iterable<Product> findAll(){
        return products().find().as(Product.class);
    }

    public static Iterable<Product> findBySkip(int count){
        // Fetch only 8 products once
        int fetchedNum = 8;
        return products().find().skip(count*fetchedNum).limit(fetchedNum).as(Product.class);
    }

}
