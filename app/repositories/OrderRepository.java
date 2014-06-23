package repositories;


import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import models.Order;
import models.Product;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class OrderRepository {


    public static MongoCollection orders() {
        return PlayJongo.jongo().getCollection("orders");
    }

    public static void insert(Order order){
        orders().save(order);
    }


    public static void update(Order order){
        orders().update("{_id: #}",order.getId()).with(order);
    }


    public static void removeById(Order order){
        orders().remove(order.getId());
    }

    public static Iterable<Order> findAll(){
        return orders().find().as(Order.class);
    }

    public static Order findOneById(String id){
        return orders().findOne("{_id:#}", id).as(Order.class);
    }

    public static Order findOneByUserId(String userId){
        return orders().findOne("{userId:#}", userId).as(Order.class);
    }


}
