public class PricingResult {
    private final double subtotal;
    private final double discount;
    private final double tax;
    private final double finalPrice;

    public PricingResult(double subtotal, double discount, double tax, double finalPrice) {
        this.subtotal   = subtotal;
        this.discount   = discount;
        this.tax        = tax;
        this.finalPrice = finalPrice;
    }

    public double getSubtotal()   { return subtotal; }
    public double getDiscount()   { return discount; }
    public double getTax()        { return tax; }
    public double getFinalPrice() { return finalPrice; }

    @Override
    public String toString() {
        return String.format(
                "Subtotal: $%.2f%nDiscount: $%.2f%nTax: $%.2f%nFinal Price: $%.2f",
                subtotal, discount, tax, finalPrice
        );
    }
}