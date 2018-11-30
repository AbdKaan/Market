import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Market extends Thread {

    private LocalTime localTime;
    private DateTimeFormatter dtf;
    private boolean open;
    private ArrayList<Product> products;
    private int income;
    private int randomAvgWaitingTime;
    private int shortAvgWaitingTime;
    private Cashier[] cashiers;

    public Market() {

        //It wasn't mentioned how many cashiers there are so i chose 3.
        cashiers = new Cashier[3];

        cashiers[0] = new Cashier(this, 1);
        cashiers[1] = new Cashier(this, 2);
        cashiers[2] = new Cashier(this, 3);

        randomAvgWaitingTime = 0;
        shortAvgWaitingTime = 0;

        income = 0;

        open = true;

        //Set time.
        localTime = LocalTime.of(9,0,0);

        //Set date formatter.
        dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

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

    public void setRandomAvgWaitingTime(int randomAvgWaitingTime) {
        this.randomAvgWaitingTime = randomAvgWaitingTime;
    }

    public void setShortAvgWaitingTime(int shortAvgWaitingTime) {
        this.shortAvgWaitingTime = shortAvgWaitingTime;
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

    @Override
    public void run() {

        System.out.println("Market is open!");

        while (open) {

            //System.out.println(localTime.format(dtf));
            localTime = localTime.plusSeconds(1);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //TODO Change open to false after 17:00:00 and print average waiting times.
        }
    }
}
