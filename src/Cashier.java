public class Cashier extends Thread {

    private Market market;
    private Boolean isEmpty;
    private Customer currentCustomer;

    public Cashier(Market market) {

        this.market = market;
        isEmpty = true;

    }

    synchronized public void addIncome(int money) {

        market.setIncome(market.getIncome() + money);

    }

    @Override
    public void run() {
        addIncome();
    }

    public Boolean getEmpty() {
        return isEmpty;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }
/*public void stuff() {

        int money = 0;


        for (Product product : chosenProducts) {
            money += product.getPrice() * rng.nextInt();
        }

        market.addIncome(money);

    }*/

}
