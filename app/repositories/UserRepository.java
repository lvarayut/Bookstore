package repositories;


import models.Product;
import models.User;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

public class UserRepository {

    public static MongoCollection users() {
        return PlayJongo.jongo().getCollection("users");
    }

    public static void insert(User user){
        users().save(user);
    }

    public static void remove(User user){
        users().remove(user.getId());
    }

    public static void update(User user){
        users().update("{_id: #}",user.getId()).with(user);
    }

    public static Iterable<User> findAll(){
        return users().find().as(User.class);
    }

    public static User findByEmail(String email){
        return users().findOne("{email: #}", email).as(User.class);
    }

    public static User findById(String userId){
        return users().findOne("{_id: #}", userId).as(User.class);
    }
}
