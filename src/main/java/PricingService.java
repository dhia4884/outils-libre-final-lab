import java.util.List;

public class PricingService {

    private final DiscountCalculator discountCalculator;
    private final TaxCalculator taxCalculator;

    public PricingService() {
        this.discountCalculator = new DiscountCalculator();
        this.taxCalculator      = new TaxCalculator();
    }

    public PricingResult calculate(Order order) {
        double subtotal       = calculateSubtotal(order.getItems());
        double discount       = discountCalculator.calculate(subtotal, order.getCustomerType(), order.getDiscountCode());
        double afterDiscount  = subtotal - discount;
        double tax            = taxCalculator.calculate(afterDiscount);
        double finalPrice     = afterDiscount + tax;

        return new PricingResult(subtotal, discount, tax, finalPrice);
    }

    private double calculateSubtotal(List<LineItem> items) {
        double subtotal = 0;
        for (LineItem item : items) {
            subtotal += item.getPrice() * item.getQuantity();
        }
        return subtotal;
    }
}