import java.util.List;

public class PricingEngine {

    public double calculate(List<double[]> items, String customerType, String discountCode) {

        // Calculate subtotal
        double subtotal = 0;
        for (double[] item : items) {
            double price = item[0];
            double quantity = item[1];
            subtotal += price * quantity;
        }

        // Apply discount
        double discountAmount = 0;
        if (discountCode != null) {
            if (discountCode.equals("SAVE10")) {
                discountAmount = subtotal * 0.10;
            } else if (discountCode.equals("SAVE20")) {
                discountAmount = subtotal * 0.20;
            } else if (discountCode.equals("SAVE5")) {
                discountAmount = subtotal * 0.05;
            }
        }

        // VIP gets extra 15% discount
        if (customerType != null && customerType.equals("VIP")) {
            discountAmount += subtotal * 0.15;
        }

        // Make sure discount doesn't exceed subtotal
        if (discountAmount > subtotal) {
            discountAmount = subtotal;
        }

        double afterDiscount = subtotal - discountAmount;

        // Calculate tax (8%)
        double tax = afterDiscount * 0.08;

        // Final price
        double finalPrice = afterDiscount + tax;

        System.out.println("Subtotal: $" + subtotal);
        System.out.println("Discount: $" + discountAmount);
        System.out.println("Tax: $" + tax);
        System.out.println("Final Price: $" + finalPrice);

        return finalPrice;
    }
}