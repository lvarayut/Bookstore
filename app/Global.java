import models.SecureSocial.Password;
import play.*;
import play.libs.*;
import java.util.*;
import models.*;
import repositories.ProductRepository;
import repositories.UserRepository;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        InitialData.insert(app);
    }

    static class InitialData {

        public static void insert(Application app) {
            // Insert products
            if(!ProductRepository.findAll().iterator().hasNext()) {
                // Create a default user
                User user = new User();
                Password pwd = new Password();
                Account account = new Account();

                pwd.setPasswordHasher("bcrypt");
                pwd.setPassword("$2a$10$VvRC8QsIYAlx5JpKTsa9Q.PFy6KB.GWIJJGy4HJwo0yNBFQ.gc30m"); // 123456789

                account.setAccountId("GB82 WEST 1234 5698 7654 32");
                account.setType("Saving account");
                account.setBalance(0);

                user.setAuthmethod("userPassword");
                user.setEmail("seller@example.com");
                user.setFirstname("SellerName");
                user.setLastname("SellerLastname");
                user.setProvider("userpass");
                user.setPwd(pwd);
                user.setUserid("seller@example.com");
                user.getAccounts().add(account);
                // Add an account

                UserRepository.insert(user);

                // Get user id
                String userId = UserRepository.findByEmail("seller@example.com").getId();

                // Create products
                Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data.yml");
                for(Object eachProduct: all.get("books")) {
                    Product product = (Product)eachProduct;
                    product.setUserId(userId);
                    ProductRepository.insert((Product)product,null);
                }
            }
        }

    }

}