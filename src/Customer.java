import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Customer extends Thread{

    private Market market;
    private String name;
    private int choosingTime;
    private int spentMoney;
    private Random rng;
    //I'm using HashSet so that same product won't be chosen.
    private HashMap<Integer, Product> chosenProducts = new HashMap<>();

    public Customer(Market market, String name){

        this.name = name;
        this.market = market;
        spentMoney = 0;
        rng = new Random();
        //choosingTime = rng.nextInt(28*60*1000) + 2*60*1000;
        choosingTime = rng.nextInt(13*1000) + 2*1000;

    }

    public Cashier pickCashier() {

        for (Cashier cashier: market.getCashiers()) {
            if (cashier.getEmpty())
                return cashier;
        }
        return market.getCashiers()[1];

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
            chosenProducts.add(market.getProducts().get(rng.nextInt(1000) + 1));

        //TODO Buy 1-20 from each of the products.

        for (Product product : chosenProducts) {
            spentMoney += product.getPrice() * rng.nextInt();
        }

        pickCashier().setCurrentCustomer(this);

    }
}
