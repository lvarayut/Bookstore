package repositories;


import models.History;
import models.Order;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

public class HistoryRepository {


    public static MongoCollection histories() {
        return PlayJongo.jongo().getCollection("histories");
    }

    public static void insert(History history){
        histories().save(history);
    }


    public static void update(History history){
        histories().update("{_id: #}",history.getId()).with(history);
    }


    public static void removeById(History history){
        histories().remove(history.getId());
    }

    public static Iterable<History> findAll(){
        return histories().find().as(History.class);
    }

    public static Iterable<History> findByUserId(String id){
        return histories().find("{buyer._id:#}", id).as(History.class);
    }

    public static History findOneById(String id){
        return histories().findOne("{_id:#}", id).as(History.class);
    }

    public static History findOneByUserId(String userId){
        return histories().findOne("{userId:#}", userId).as(History.class);
    }


}
