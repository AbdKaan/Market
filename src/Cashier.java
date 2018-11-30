import java.util.ArrayList;
import java.util.Map;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;

public class Cashier extends Thread {

    private Market market;
    private int queueNumber;
    private Boolean isEmpty;
    private ArrayList<Customer> customers;
    //TODO customers array list to customersInQueue array list and use customers array list for avg time calculations.

    public Cashier(Market market, int queueNumber) {

        this.market = market;
        isEmpty = true;
        this.queueNumber = queueNumber;
        customers = new ArrayList<>();

    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    synchronized public void addIncome(int money) {

        market.setIncome(market.getIncome() + money);

    }

    public void averageWaitingTime() {

        int randomCount = 0;
        int shortestCount = 0;
        int randomWaitingTime = 0;
        int shortestWaitingTime = 0;

        for (Customer customer : customers) {
            //TODO Do the calculations using timeSpentWaiting
            if (customer.getPickedQueue().equals("Random")) {

                randomCount++;
                randomWaitingTime += 1;

            } else if (customer.getPickedQueue().equals("Shortest")) {

                shortestCount++;
                shortestWaitingTime += 1;

            }
        }

        int randomAvgWaitingTime = randomWaitingTime / randomCount;
        int shortAvgWaitingTime = shortestWaitingTime / shortestCount;

        market.setRandomAvgWaitingTime(randomAvgWaitingTime);
        market.setShortAvgWaitingTime(shortAvgWaitingTime);


    }

    @Override
    public void run() {
        while (market.getOpen()) {
            try{
                sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }

            if (customers.size() != 0) {
                long minutesWaited = customers.get(0).getQueueEntranceTime().until(market.getLocalTime(), MINUTES);
                long secondsWaited = customers.get(0).getQueueEntranceTime().until(market.getLocalTime(), SECONDS) % 60;
                //TODO calculate time waited and add it using customers arrayList.
                System.out.println(customers.get(0).getName() + " waited for " + minutesWaited + " minutes and " +
                        secondsWaited + " seconds in queue" + queueNumber);

                int money = 0;
                int sleep = 0;

                for (Map.Entry<Product, Integer> entry : customers.get(0).getChosenProducts().entrySet()) {
                    money += entry.getValue() * entry.getKey().getPrice();
                    sleep += entry.getValue();
                }

                System.out.println(customers.get(0).getName() + " items are processed in " + sleep / 60
                        + " minutes and " + sleep % 60 + " seconds");
                try {
                    //Thread.sleep(1000*sleep);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(customers.get(0).getName() + " paid $" + money + " and left at " + market.getLocalTime().toString());
                customers.remove(0);
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
