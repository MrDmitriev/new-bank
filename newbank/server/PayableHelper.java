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
         int today = LocalDate.now().getDayOfMonth();

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
                    Customer customer = (Customer)user;

                    for(Account account: customer.getAccounts()){
                        //System.out.println(account.toString());

                        // for each direct debit
                        for(DirectDebit directDebit: account.getDirectDebits()){
                            System.out.println(directDebit.toString());
                            int paymentDay = directDebit.getPaymentDayOfMonth();

                            // if the current day of the month = the scheduled payment day
                            // of the month, make the payment
                            if(today == paymentDay){
                                double amount = directDebit.getRegularPaymentAmount();
                                Account toAccount = directDebit.getToAccount();
                                Customer toCustomer = directDebit.getToCustomer();

                                account.makeReceivePayment(-amount,toCustomer);
                                toAccount.makeReceivePayment(amount, customer);
                            }
                        }
                    }
                }
         }
     }
}
