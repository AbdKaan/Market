public class Product {

    private int productID;
    private int price;

    public Product(int productID, int price) {

        this.productID = productID;
        this.price = price;

    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", price=" + price +
                '}';
    }
}
