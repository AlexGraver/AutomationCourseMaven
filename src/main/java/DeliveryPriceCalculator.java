import deliveryOrder.DeliveryLoad;
import deliveryOrder.DeliveryOrder;

public class DeliveryPriceCalculator {
    private static final double minimalPrice = 400;

    public double calculateDeliveryPrice(DeliveryOrder order){
        double finalPrice = minimalPrice;

        if(deliveryIsPossible(order)){
            finalPrice = (finalPrice +
                    extraAmountForDistance(order) +
                    extraAmountForSize(order) +
                    extraAmountForFragile(order)) * extraCoefficientForLoad(order);
            System.out.println("=====================================");
            System.out.println("|  Delivery final price: " + finalPrice + " RUB |");
            System.out.println("=====================================");
            return finalPrice;
        }else{
            throw new IllegalArgumentException("Fragile product delivery is possible only to distance less then 30 km");
        }
    }


    private boolean deliveryIsPossible(DeliveryOrder order){
        if(order.isFragile() && order.getDistance() > 30){
            return false;
        }else{
            return true;
        }
    }

    private double extraAmountForDistance(DeliveryOrder order){
        double distance = order.getDistance();
        double extraAmount = 0;

        if(distance < 0){
            throw new IllegalArgumentException("Distance should be a positive number");
        }else if(distance > 30){
            extraAmount = 300;
        }else if(distance > 10 && distance <= 30){
            extraAmount = 200;
        }else if(distance > 2 && distance <= 10){
            extraAmount = 100;
        }else if(distance > 0 && distance <= 2){
            extraAmount = 50;
        }
        return extraAmount;
    }

    private double extraAmountForSize(DeliveryOrder order){
        double extraAmount;
        if(order.isBigSize()){
            extraAmount = 200;
        }else{
            extraAmount = 100;
        }
        return extraAmount;
    }

    private double extraAmountForFragile(DeliveryOrder order){
        double extraAmount = 0;
        if(order.isFragile()){
            extraAmount = 300;
        }
        return extraAmount;
    }

    private double extraCoefficientForLoad(DeliveryOrder order){
        double coefficient = 1;
        DeliveryLoad load = order.getLoad();

        switch (load){
            case VERY_HIGH:
                coefficient = DeliveryLoad.VERY_HIGH.getCoefficient();
                break;
            case HIGH:
                coefficient = DeliveryLoad.HIGH.getCoefficient();
                break;
            case DeliveryLoad.MODERATE:
                coefficient = DeliveryLoad.MODERATE.getCoefficient();
                break;
            case DeliveryLoad.NORMAL:
                coefficient = DeliveryLoad.NORMAL.getCoefficient();
                break;
        }
        return coefficient;
    }
}
