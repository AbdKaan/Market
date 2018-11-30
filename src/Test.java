import java.util.ArrayList;
import java.util.Random;

public class Test implements Runnable{

    private static Market market;
    private Random rng = new Random();
    private ArrayList<Customer> customers = new ArrayList<>();

    private void createCustomers(int number) {

        //Check if market is open. If it's open create a customer and add it to array list to start running it.
        if (market.getOpen()) {

            customers.add(new Customer(market, "Customer" + number));
            new Thread(customers.get(number - 1)).start();


        }

    }

    public static void main(String[] args){
        market = new Market();
        Thread t = new Thread(new Test());
        market.start();
        t.start();
    }

    @Override
    public void run() {

        int number = 1;

        while(market.getOpen()) {

            try {
                //Sleep between 2 and 15 minutes.
                Thread.sleep(rng.nextInt(13*60*1000) + 2*60*1000);
                //Thread.sleep(rng.nextInt(13*1000) + 2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            createCustomers(number);

            number++;
        }

    }
}
