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

}
