package deliveryOrder;

public class DeliveryOrder {
    private double distance;
    private boolean bigSize;
    private boolean fragile;
    private DeliveryLoad load;

    public DeliveryOrder(double distance, boolean bigSize, boolean fragile, DeliveryLoad load) {
        this.distance = distance;
        this.bigSize = bigSize;
        this.fragile = fragile;
        this.load = load;
    }

    public double getDistance() {
        return distance;
    }

    public boolean isBigSize() {
        return bigSize;
    }

    public boolean isFragile() {
        return fragile;
    }

    public DeliveryLoad getLoad() {
        return load;
    }
}
