public class Product {

    private int productID;
    private int price;

    public Product(int productID, int price) {

        this.productID = productID;
        this.price = price;

    }

    @Override
    public String toString() {
        return productID + " " + price;
    }
}
