import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class Market extends Thread {

    private LocalTime localTime;
    private boolean open;

    private ArrayList<Product> products;
    private Cashier[] cashiers;
    private ArrayList<Customer> customers;

    private int income;
    private int randomAvgWaitingTime;
    private int shortAvgWaitingTime;

    public Market() {

        //It wasn't mentioned how many cashiers there are so i chose 3.
        cashiers = new Cashier[3];

        cashiers[0] = new Cashier(this, 1);
        cashiers[1] = new Cashier(this, 2);
        cashiers[2] = new Cashier(this, 3);

        customers = new ArrayList<>();

        randomAvgWaitingTime = 0;
        shortAvgWaitingTime = 0;

        income = 0;

        open = true;

        //Set time.
        localTime = LocalTime.of(9,0,0);

        //Getting dynamic location of the file using class location.
        InputStream bufferedInputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("product_prices.txt");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

        products = new ArrayList<>();

        String line;
        try {

            //readline once so i skip the "ProductID Price" line.
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null){

                //I saw that aren't spaces but tabs between numbers so i splitted them using 2 tabs and added the
                //dollar sign too. Had to add escape characters for $ since it has his own uses for regex.
                String[] numbers = line.split("\t\t\\$");
                products.add(new Product(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Cashier cashier : cashiers) {
            cashier.start();
        }

    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public Cashier[] getCashiers() {
        return cashiers;
    }

    public Boolean getOpen() {
        return open;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    //Find both average waiting times by using this method.
    public void calculateAverageWaitingTimes() {

        int randomCount = 0;
        int shortestCount = 0;
        int randomWaitingTime = 0;
        int shortestWaitingTime = 0;

        //Calculate avg times by checking if customer was on random or shortest and taking avg of the them.

        for (Customer customer : customers) {

            if (customer.getPickedQueue().equals("Random")) {

                randomCount++;
                randomWaitingTime += customer.getTimeSpentWaiting();

            } else if (customer.getPickedQueue().equals("Shortest")) {

                shortestCount++;
                shortestWaitingTime += customer.getTimeSpentWaiting();

            }
        }

        int randomAvgWaitingTime = randomWaitingTime / randomCount;
        int shortAvgWaitingTime = shortestWaitingTime / shortestCount;

        this.randomAvgWaitingTime = randomAvgWaitingTime;
        this.shortAvgWaitingTime = shortAvgWaitingTime;

    }

    @Override
    public void run() {

        System.out.println("Market is open!");
        LocalTime closeUp = LocalTime.of(17,0,0);
        //LocalTime test = LocalTime.of(9,5,0);

        while (open) {

            localTime = localTime.plusSeconds(1);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*if (localTime.isAfter(test)) {

                calculateAverageWaitingTimes();

                System.out.println("Average waiting time of the customers who select\n" +
                        "the shortest queue: " + shortAvgWaitingTime + " seconds.");

                System.out.println("Average waiting time of the customers\n" +
                        "who select their queue randomly: " + randomAvgWaitingTime + " seconds.");

            }*/

            //When it's 17:00:00, change the open to false so we get out of loop.
            if (localTime.equals(closeUp)) {

                open = false;

            }
        }

        //Time goes on until there are no customers left.
        while (cashiers[0].getcustomersInQueue().size() > 0 && cashiers[1].getcustomersInQueue().size() > 0
                && cashiers[2].getcustomersInQueue().size() > 0) {

            localTime = localTime.plusSeconds(1);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        System.out.println("Market closed.");

        //Calculating and printing average time waited in queue.
        calculateAverageWaitingTimes();

        System.out.println("Average waiting time of the customers who select\n" +
                "the shortest queue: " + shortAvgWaitingTime + " seconds.");

        System.out.println("Average waiting time of the customers\n" +
                "who select their queue randomly " + randomAvgWaitingTime + " seconds.");

    }
}
