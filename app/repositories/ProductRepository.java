package repositories;


import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import models.Product;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Pattern;

public class ProductRepository {

    private static int FETCHEDNUMBER = 8;

    public static MongoCollection products() {
        return PlayJongo.jongo().getCollection("products");
    }

    public static void insert(Product product){
        String puncPattern = "[^a-zA-Z]";
        String duplicateDashPattern = "-+";
        String trimPunc = "(^-|-$)";
        String imageName = product.getName().replaceAll(puncPattern,"-").replaceAll(duplicateDashPattern,"-").replaceAll(trimPunc,"").toLowerCase();
        product.setImageName(imageName);
        GridFS gfs = PlayJongo.gridfs();
        File imagePath = new File(product.getImagePath());
        try {
            GridFSInputFile gfsFile = gfs.createFile(imagePath);
            gfsFile.setFilename(imageName);
            gfsFile.save();
        } catch (IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
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
        return products().find().skip(count*FETCHEDNUMBER).limit(FETCHEDNUMBER).as(Product.class);
    }

    public static Iterable<Product> findByName(String name){
        return products().find("{ name : # }", Pattern.compile(name, Pattern.CASE_INSENSITIVE)).limit(FETCHEDNUMBER).as(Product.class);
    }

    public static InputStream findImage(String name){
        GridFS gfs = PlayJongo.gridfs();
        GridFSDBFile gfsDBFile = gfs.findOne(name);
        return gfsDBFile.getInputStream();
    }

}
