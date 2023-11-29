package alberto.cano.a08_sql.models;

public class Order {
    private String menu;
    private boolean large;
    private int quantity;
    private float price;
    private float total;

    public Order() {
    }

    public Order(String menu, boolean large, int quantity, float price) {
        this.menu = menu;
        this.large = large;
        this.quantity = quantity;
        this.price = price;
        calculateTotal();
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public boolean isLarge() {
        return large;
    }

    public void setLarge(boolean large) {
        this.large = large;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotal();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
        calculateTotal();
    }

    public float getTotal() {
        return total;
    }

    private void calculateTotal() {
        total = quantity * price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "menu='" + menu + '\'' +
                ", large=" + large +
                ", quantity=" + quantity +
                ", price=" + price +
                ", total=" + total +
                '}';
    }
}
