import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {

    private PricingService pricingService;

    @BeforeEach
    void setUp() {
        pricingService = new PricingService();
    }

    // ─── Subtotal Tests ───────────────────────────────

    @Test
    void testSubtotal_singleItem() {
        Order order = new Order(
                List.of(new LineItem("Book", 10.0, 3)),
                CustomerType.REGULAR,
                null
        );
        PricingResult result = pricingService.calculate(order);
        assertEquals(30.0, result.getSubtotal(), 0.01);
    }

    @Test
    void testSubtotal_multipleItems() {
        Order order = new Order(
                List.of(
                        new LineItem("Pen",  2.0, 5),
                        new LineItem("Book", 15.0, 2)
                ),
                CustomerType.REGULAR,
                null
        );
        PricingResult result = pricingService.calculate(order);
        assertEquals(40.0, result.getSubtotal(), 0.01);
    }

    // ─── Discount Code Tests ──────────────────────────

    @Test
    void testDiscount_SAVE10() {
        Order order = new Order(
                List.of(new LineItem("Laptop", 100.0, 1)),
                CustomerType.REGULAR,
                "SAVE10"
        );
        PricingResult result = pricingService.calculate(order);
        assertEquals(10.0, result.getDiscount(), 0.01);
    }

    @Test
    void testDiscount_SAVE20() {
        Order order = new Order(
                List.of(new LineItem("Phone", 200.0, 1)),
                CustomerType.REGULAR,
                "SAVE20"
        );
        PricingResult result = pricingService.calculate(order);
        assertEquals(40.0, result.getDiscount(), 0.01);
    }

    @Test
    void testDiscount_SAVE5() {
        Order order = new Order(
                List.of(new LineItem("Bag", 100.0, 1)),
                CustomerType.REGULAR,
                "SAVE5"
        );
        PricingResult result = pricingService.calculate(order);
        assertEquals(5.0, result.getDiscount(), 0.01);
    }

    @Test
    void testDiscount_noCode() {
        Order order = new Order(
                List.of(new LineItem("Chair", 50.0, 2)),
                CustomerType.REGULAR,
                null
        );
        PricingResult result = pricingService.calculate(order);
        assertEquals(0.0, result.getDiscount(), 0.01);
    }

    // ─── VIP Tests ────────────────────────────────────

    @Test
    void testDiscount_VIP_noCode() {
        Order order = new Order(
                List.of(new LineItem("TV", 100.0, 1)),
                CustomerType.VIP,
                null
        );
        PricingResult result = pricingService.calculate(order);
        assertEquals(15.0, result.getDiscount(), 0.01);
    }

    @Test
    void testDiscount_VIP_withSAVE10() {
        Order order = new Order(
                List.of(new LineItem("TV", 100.0, 1)),
                CustomerType.VIP,
                "SAVE10"
        );
        PricingResult result = pricingService.calculate(order);
        // 15% VIP + 10% code = 25%
        assertEquals(25.0, result.getDiscount(), 0.01);
    }

    // ─── Tax Tests ────────────────────────────────────

    @Test
    void testTax_regularNoDiscount() {
        Order order = new Order(
                List.of(new LineItem("Desk", 100.0, 1)),
                CustomerType.REGULAR,
                null
        );
        PricingResult result = pricingService.calculate(order);
        // tax = 100 * 0.08 = 8
        assertEquals(8.0, result.getTax(), 0.01);
    }

    @Test
    void testTax_afterDiscount() {
        Order order = new Order(
                List.of(new LineItem("Desk", 100.0, 1)),
                CustomerType.REGULAR,
                "SAVE10"
        );
        PricingResult result = pricingService.calculate(order);
        // after discount = 90, tax = 90 * 0.08 = 7.2
        assertEquals(7.2, result.getTax(), 0.01);
    }

    // ─── Final Price Tests ────────────────────────────

    @Test
    void testFinalPrice_regularNoDiscount() {
        Order order = new Order(
                List.of(new LineItem("Item", 100.0, 1)),
                CustomerType.REGULAR,
                null
        );
        PricingResult result = pricingService.calculate(order);
        // 100 + 8% tax = 108
        assertEquals(108.0, result.getFinalPrice(), 0.01);
    }

    @Test
    void testFinalPrice_VIP_SAVE20() {
        Order order = new Order(
                List.of(new LineItem("Item", 100.0, 1)),
                CustomerType.VIP,
                "SAVE20"
        );
        PricingResult result = pricingService.calculate(order);
        // discount = 35%, after = 65, tax = 5.2, final = 70.2
        assertEquals(70.2, result.getFinalPrice(), 0.01);
    }

    // ─── Edge Cases ───────────────────────────────────

    @Test
    void testDiscount_doesNotExceedSubtotal() {
        Order order = new Order(
                List.of(new LineItem("Item", 10.0, 1)),
                CustomerType.VIP,
                "SAVE20"
        );
        PricingResult result = pricingService.calculate(order);
        assertTrue(result.getDiscount() <= result.getSubtotal());
    }
}