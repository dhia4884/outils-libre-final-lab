public class DiscountCalculator {

    private static final double SAVE5_RATE  = 0.05;
    private static final double SAVE10_RATE = 0.10;
    private static final double SAVE20_RATE = 0.20;
    private static final double VIP_RATE    = 0.15;

    public double calculate(double subtotal, CustomerType customerType, String discountCode) {
        double discount = getCodeDiscount(subtotal, discountCode);
        discount += getVipDiscount(subtotal, customerType);

        if (discount > subtotal) {
            discount = subtotal;
        }
        return discount;
    }

    private double getCodeDiscount(double subtotal, String discountCode) {
        if (discountCode == null) return 0;
        switch (discountCode) {
            case "SAVE5":  return subtotal * SAVE5_RATE;
            case "SAVE10": return subtotal * SAVE10_RATE;
            case "SAVE20": return subtotal * SAVE20_RATE;
            default:       return 0;
        }
    }

    private double getVipDiscount(double subtotal, CustomerType customerType) {
        if (customerType == CustomerType.VIP) {
            return subtotal * VIP_RATE;
        }
        return 0;
    }
}