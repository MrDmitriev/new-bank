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
         int todayDay = LocalDate.now().getDayOfMonth();

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
                            //System.out.println(directDebit.toString());
                            int paymentDay = directDebit.getPaymentDayOfMonth();

                            // if the current day of the month = the scheduled payment day
                            // of the month, make the payment
                            if(todayDay == paymentDay && LocalDate.now().isBefore(directDebit.getEndDate())){
                                double amount = directDebit.getRegularPaymentAmount();
                                Account toAccount = directDebit.getToAccount();
                                Customer toCustomer = directDebit.getToCustomer();

                                account.makeReceivePayment(-amount,toCustomer);
                                toAccount.makeReceivePayment(amount, customer);

                                //remove the direct debit if the end date has passed
                            } else if(LocalDate.now().isAfter(directDebit.getEndDate())) {
                                account.cancelDirectDebit(directDebit.getID());
                            }
                        }

                        // for each micro loan
                        for(MicroLoan microLoan: account.getMicroLoans()){
                            //System.out.println(directDebit.toString());
                            int paymentDay = microLoan.getPaymentDayOfMonth();

                            // if the current day of the month = the scheduled payment day
                            // of the month, make the payment (this schedule only runs once per day)
                            if(todayDay == paymentDay && LocalDate.now().isBefore(microLoan.getEndDate())){
                                double amount = microLoan.getRegularPaymentAmount();
                                Account toAccount = microLoan.getToAccount();
                                Customer toCustomer = microLoan.getToCustomer();

                                account.makeReceivePayment(-amount,toCustomer);
                                toAccount.makeReceivePayment(amount, customer);

                                //remove the micro loan if the end date has passed
                            } else if(LocalDate.now().isAfter(microLoan.getEndDate())) {
                                account.cancelMicroLoan(microLoan.getID());
                            }
                        }
                    }
                }
         }
     }
}
