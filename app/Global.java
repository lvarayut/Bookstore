import play.*;
import play.libs.*;
import java.util.*;
import models.*;
import repositories.ProductRepository;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        InitialData.insert(app);
    }

    static class InitialData {

        public static void insert(Application app) {
            // Insert products
            if(!ProductRepository.findAll().iterator().hasNext()) {
                Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data.yml");
                for(Object product: all.get("books")) {
                    ProductRepository.insert((Product)product);
                }
            }
        }

    }

}