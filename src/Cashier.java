import java.util.ArrayList;
import java.util.Map;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;

public class Cashier extends Thread {

    private Market market;
    private int queueNumber;
    private Boolean isEmpty;
    //Arraylist is good here for a queue. I can just use the first index and remove the first index when the customer
    //is done since Arraylist automatically will bring the second index to first one after the first one is deleted.
    private ArrayList<Customer> customersInQueue;

    public Cashier(Market market, int queueNumber) {

        this.market = market;
        isEmpty = true;
        this.queueNumber = queueNumber;
        customersInQueue = new ArrayList<>();

    }

    public ArrayList<Customer> getcustomersInQueue() {
        return customersInQueue;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    //Synchronized so that it's thread-safe
    synchronized public void addIncome(int money) {

        market.setIncome(market.getIncome() + money);

    }

    @Override
    public void run() {
        //Run while market is open.
        while (market.getOpen()) {
            try{
                sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            //If there are customers in queue:
            if (customersInQueue.size() != 0) {
                //Calculate waited time by using queue entrance time and current time.
                long minutesWaited = customersInQueue.get(0).getQueueEntranceTime()
                        .until(market.getLocalTime(), MINUTES);
                long secondsWaited = customersInQueue.get(0).getQueueEntranceTime()
                        .until(market.getLocalTime(), SECONDS) % 60;
                //Setting time waited in queue for finding the average time waited.
                customersInQueue.get(0).setTimeSpentWaiting(minutesWaited * 60 + secondsWaited);
                System.out.println(customersInQueue.get(0).getName() + " waited for " + minutesWaited + " minutes and "
                        + secondsWaited + " seconds in queue" + queueNumber);

                int money = 0;
                int sleep = 0;

                //Calculate the money and time for Thread to sleep(Items processing by cashier) by using Map.Entry to
                //loop through HashMap.
                for (Map.Entry<Product, Integer> entry : customersInQueue.get(0).getChosenProducts().entrySet()) {
                    money += entry.getValue() * entry.getKey().getPrice();
                    sleep += entry.getValue();
                }

                try {
                    Thread.sleep(1000*sleep);
                    //Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(customersInQueue.get(0).getName() + " items are processed in " + sleep / 60
                        + " minutes and " + sleep % 60 + " seconds");

                System.out.println(customersInQueue.get(0).getName() + " paid $" + money + " and left at "
                        + market.getLocalTime().toString());

                customersInQueue.remove(0);
                addIncome(money);
                System.out.println("Total income = $" + market.getIncome());
            } else {
                this.isEmpty = true;
            }
        }
    }

    public Boolean getEmpty() {
        return isEmpty;
    }

    public void setEmpty(Boolean empty) {
        isEmpty = empty;
    }

}
