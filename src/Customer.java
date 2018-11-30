import java.time.LocalTime;
import java.util.*;

import static java.lang.Thread.sleep;

public class Customer implements Runnable{

    private Market market;
    private String name;
    private int choosingTime;
    private LocalTime queueEntranceTime;
    private int timeSpentWaiting;
    private Random rng;
    private Cashier cashier;
    private String pickedQueue;
    //I'm using HashMap so that same product won't be chosen and i can assign values as amount of products.
    private HashMap<Product, Integer> chosenProducts = new HashMap<>();

    public Customer(Market market, String name){

        this.name = name;
        this.market = market;
        rng = new Random();
        //choosingTime = rng.nextInt(28*60*1000) + 2*60*1000;
        choosingTime = rng.nextInt(13*1000) + 2*1000;
        queueEntranceTime = market.getLocalTime();
        timeSpentWaiting = 0;

    }

    public HashMap<Product, Integer> getChosenProducts() {
        return chosenProducts;
    }

    public String getName() {
        return name;
    }

    public String getPickedQueue() {
        return pickedQueue;
    }

    public LocalTime getQueueEntranceTime() {
        return queueEntranceTime;
    }

    public void pickCashier() {

        //Check if there is empty cashier.
        for (Cashier cashier: market.getCashiers()) {
            if (cashier.getEmpty()) {
                this.cashier = cashier;
                cashier.setEmpty(false);
                pickedQueue = "Empty";
            }
        }

        //All cashiers working.
        int random = rng.nextInt(2);//Getting 0 or 1 (50% chance)
        if(random == 1) { //50% chance to pick randomly.
            cashier = market.getCashiers()[rng.nextInt(3)];
            pickedQueue = "Shortest";
        } else {//50% chance to pick shortest queue.
            pickedQueue = "Random";
            int cashier1 = market.getCashiers()[0].getCustomers().size();
            int cashier2 = market.getCashiers()[1].getCustomers().size();
            int cashier3 = market.getCashiers()[2].getCustomers().size();
            if (cashier1 > cashier2 && cashier1 > cashier3)
                cashier = market.getCashiers()[0];
            else if (cashier2 > cashier1 && cashier2 > cashier3)
                cashier = market.getCashiers()[1];
            else if (cashier3 > cashier2 && cashier3 > cashier1)
                cashier = market.getCashiers()[2];
            else //All have same amount of customers so i choose randomly.
                cashier = market.getCashiers()[rng.nextInt(3)];

        }

    }

    @Override
    public void run() {

        System.out.println(name + " entered at " + market.getLocalTime().toString());
        try {
            sleep(choosingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Choose 1 to 5 products.
        while (chosenProducts.size() < rng.nextInt(5) + 1)
            chosenProducts.put(market.getProducts().get(rng.nextInt(1000) + 1), rng.nextInt(20) + 1);

        pickCashier();
        cashier.getCustomers().add(this);
        System.out.println(name + " entered queue" + cashier.getQueueNumber() + " at " + market.getLocalTime().toString());


    }
}
