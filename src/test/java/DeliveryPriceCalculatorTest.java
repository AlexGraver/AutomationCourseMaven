import deliveryOrder.DeliveryLoad;
import deliveryOrder.DeliveryOrder;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class DeliveryPriceCalculatorTest {

    private DeliveryPriceCalculator calculator;

    @DataProvider(name = "invalidDeliveries")
    public Object[][] invalidDeliveries() {
        return new Object[][]{
                {new DeliveryOrder(30.01, false, true, DeliveryLoad.NORMAL),
                        "Fragile product delivery is possible only to distance less then 30 km"},
                {new DeliveryOrder(-1, true, true, DeliveryLoad.HIGH),
                        "Distance should be a positive number"}
        };
    }

    @DataProvider(name = "distanceBoundaryValues")
    public Object[][] distanceBoundaryValues() {
        return new Object[][]{
                {new DeliveryOrder(0, false, false, DeliveryLoad.NORMAL), 400 + 100},
                {new DeliveryOrder(2, false, false, DeliveryLoad.NORMAL), 400 + 100 + 50},
                {new DeliveryOrder(10, false, false, DeliveryLoad.NORMAL), 400 + 100 + 100},
                {new DeliveryOrder(30, false, false, DeliveryLoad.NORMAL), 400 + 100 + 200},
                {new DeliveryOrder(30.001, false, false, DeliveryLoad.NORMAL), 400 + 100 + 300}
        };
    }

    @BeforeMethod
    public void setUp() {
        calculator = new DeliveryPriceCalculator();
    }

    @Test(dataProvider = "invalidDeliveries")
    public void testInvalidDelivery(DeliveryOrder order, String expectedMessage) {
        try {
            calculator.calculateDeliveryPrice(order);
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), expectedMessage);
        }
    }

    @Test
    public void testValidDelivery() {
        DeliveryOrder order = new DeliveryOrder(30, false, true, DeliveryLoad.NORMAL);
        try {
            calculator.calculateDeliveryPrice(order);
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testMaxDeliveryPrice() {
        double minimalDeliveryPrice = 400;
        double expectedPrice = (minimalDeliveryPrice + 200 + 200 + 300)*1.6;
        DeliveryOrder order = new DeliveryOrder(30, true, true, DeliveryLoad.VERY_HIGH);
        assertEquals(calculator.calculateDeliveryPrice(order), expectedPrice, 0.001);
    }

    @Test(dataProvider = "distanceBoundaryValues")
    public void testDistanceBoundaryValues(DeliveryOrder order, double expectedPrice) {
        assertEquals(calculator.calculateDeliveryPrice(order), expectedPrice, 0.001);
    }

}
