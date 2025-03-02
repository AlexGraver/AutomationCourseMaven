package deliveryOrder;

public enum DeliveryLoad {
    VERY_HIGH(1.6),
    HIGH(1.4),
    MODERATE(1.2),
    NORMAL(1);

    double coefficient;

    DeliveryLoad(double coefficient) {
        this.coefficient = coefficient;
    }

    public double getCoefficient() {
        return coefficient;
    }
}
