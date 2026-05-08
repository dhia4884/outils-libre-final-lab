import java.util.List;

public class Main {
    public static void main(String[] args) {

        PricingService pricingService = new PricingService();

        System.out.println("===========================================");
        System.out.println("        PRICING & DISCOUNT ENGINE         ");
        System.out.println("===========================================");

        // ─── Test 1: Regular customer, no discount ───
        System.out.println("\n📦 Order 1 — REGULAR, no discount code");
        Order order1 = new Order(
                List.of(
                        new LineItem("Laptop", 800.0, 1),
                        new LineItem("Mouse",  25.0,  2)
                ),
                CustomerType.REGULAR,
                null
        );
        PricingResult result1 = pricingService.calculate(order1);
        System.out.println(result1);

        // ─── Test 2: Regular customer, SAVE10 ────────
        System.out.println("\n📦 Order 2 — REGULAR, SAVE10");
        Order order2 = new Order(
                List.of(
                        new LineItem("Phone",  500.0, 1),
                        new LineItem("Case",   20.0,  1)
                ),
                CustomerType.REGULAR,
                "SAVE10"
        );
        PricingResult result2 = pricingService.calculate(order2);
        System.out.println(result2);

        // ─── Test 3: VIP customer, SAVE20 ────────────
        System.out.println("\n📦 Order 3 — VIP, SAVE20");
        Order order3 = new Order(
                List.of(
                        new LineItem("TV",     1000.0, 1),
                        new LineItem("Remote", 30.0,   1)
                ),
                CustomerType.VIP,
                "SAVE20"
        );
        PricingResult result3 = pricingService.calculate(order3);
        System.out.println(result3);

        // ─── Test 4: VIP customer, no discount ───────
        System.out.println("\n📦 Order 4 — VIP, no discount code");
        Order order4 = new Order(
                List.of(
                        new LineItem("Tablet", 300.0, 2)
                ),
                CustomerType.VIP,
                null
        );
        PricingResult result4 = pricingService.calculate(order4);
        System.out.println(result4);

        System.out.println("\n===========================================");
    }
}