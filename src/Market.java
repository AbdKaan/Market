import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Market extends Thread {

    private LocalTime localTime;
    private DateTimeFormatter dtf;
    private boolean open;
    private ArrayList<Product> products;

    public Market() {

        open = true;

        //Set time.
        localTime = LocalTime.of(9,0,0);

        //Set date formatter.
        dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

        //TODO Get all products here.

        //Getting dynamic location of the file using class location.
        InputStream bufferedInputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("product_prices.txt");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

        products = new ArrayList<>();

        String line;
        try {

            //Readline once so i skip the "ProductID Price" line.
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null){

                //I saw that aren't spaces but tabs between numbers so i splitted them using 2 tabs and added the
                //dollar sign too. Had to add escape characters for $ since it has his own uses for regex.
                String[] numbers = line.split("\t\t\\$");
                products.add(new Product(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])));

            }

            System.out.println(products.get(1000).toString());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Boolean getOpen() {

        return open;

    }

    public LocalTime getLocalTime() {

        return localTime;

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
            //TODO Change open to false after 17:00:00
        }
    }
}
