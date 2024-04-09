package sanberuzadre.tradingbot.nadarayawatson.order.exceptions;

public class OrderNotFoundException extends IllegalStateException {

    public OrderNotFoundException(String message) {
        super(message);
    }

}
