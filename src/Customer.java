public class Customer extends Thread{

    private Market market;
    private String name;

    public Customer(Market market, String name){

        this.name = name;
        this.market = market;

    }

    @Override
    public void run() {

        //TODO Choosing products 2-30 minutes
        System.out.println(name + " entered at " + market.getLocalTime().toString());

        //TODO Select 1-20 products, buy 1-20 from each of them.

    }
}
