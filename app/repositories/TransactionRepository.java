package repositories;


import models.Order;
import models.User;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;

public class TransactionRepository {


    public static boolean buyProduct(User buyer, User seller, int accountIndex, float productPrice){
        boolean isSuccess = false;
        //PlayJongo.jongo().runCommand("beginTransaction");
        try{
            // Deposit money to seller
            seller.getAccounts().get(0).deposit(productPrice);
            UserRepository.update(seller);
            // Withdraw money from buyer
            buyer.getAccounts().get(accountIndex).withdraw(productPrice);
            UserRepository.update(buyer);
            //PlayJongo.jongo().runCommand("commitTransaction");
            isSuccess = true;
        }
        catch (Exception e){
            //PlayJongo.jongo().runCommand("rollbackTransaction");
            isSuccess = false;
        }

        return isSuccess;
    }



}
