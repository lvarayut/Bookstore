package repositories;


import models.Order;
import models.User;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

public class TransactionRepository {


    public static boolean buyProduct(User buyer, User seller, int accountIndex, float productPrice){
        boolean isSuccess = false;
        PlayJongo.jongo().runCommand("{beginTransaction : 1}");
        try{
            // Deposit money to seller
            seller.getAccounts().get(0).deposit(productPrice);
            UserRepository.update(seller);
            // Withdraw money from buyer
            buyer.getAccounts().get(accountIndex).withdraw(productPrice);
            UserRepository.update(buyer);
            //throw new Exception();
            //PlayJongo.jongo().runCommand("{commitTransaction : 1}");
            //isSuccess = true;
        }
        catch (Exception e){
            PlayJongo.jongo().runCommand("{rollbackTransaction : 1 }");
            isSuccess = false;
        }

        return isSuccess;
    }



}
