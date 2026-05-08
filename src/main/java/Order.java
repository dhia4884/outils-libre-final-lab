import java.util.List;

public class Order {
    private List<LineItem> items;
    private CustomerType customerType;
    private String discountCode;

    public Order(List<LineItem> items, CustomerType customerType, String discountCode) {
        this.items = items;
        this.customerType = customerType;
        this.discountCode = discountCode;
    }

    public List<LineItem> getItems() { return items; }
    public CustomerType getCustomerType() { return customerType; }
    public String getDiscountCode() { return discountCode; }
}