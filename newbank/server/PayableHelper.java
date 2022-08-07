package newbank.server;
import java.util.*;
import java.time.LocalDate;


class PayableHelper extends TimerTask{


    PayableHelper(){

    }
    public static int i=1;
     public void run(){
         NewBank bank = NewBank.getBank();
         HashMap<String, User> allUsers;
         ArrayList<Customer> allCustomers = new ArrayList<Customer>();

         allUsers = bank.getAllUsers();

         // Creates an iterator so we can loop through the hashmap
         Iterator<Map.Entry<String,User>> entrySet = allUsers.entrySet().iterator();

         while(entrySet.hasNext()){
             Map.Entry<String, User> entry = entrySet.next();
                //System.out.println("Key : " + entry.getKey() + "  Value : "+entry.getValue().toString());
                User user = entry.getValue();

                // for each standard account (these are the ones that contain direct debits)
                // only look for accounts in standard users (these are the only sources of direct debits or micro loans
                if (user.getUserType() == UserType.STANDARD){
                    for(Account account: user.getAccounts()){
                        //System.out.println(account.toString());

                        // for each
                        for(DirectDebit directDebit: account.getDirectDebits()){
                            System.out.println(directDebit.toString());
                        }
                    }
                }
         }


         /*
         Get all customer objects (this equals the whole bank)

            If the customer isn't staff:
                For Each account:
                    For Each Payable:
                        If today is a payment date, make the payment (using toCustomer and toAccount)

         //get accounts

         //for each account check
         */



         //System.out.println("This is called " + i++ + " time");
         UserID serviceUser = new UserID("svc");
         //String result = bank.processRequest(serviceUser,"LISTUSERS");
         //System.out.println(result);
     }
}
