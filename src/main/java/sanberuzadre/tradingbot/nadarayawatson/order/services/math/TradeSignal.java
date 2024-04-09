package sanberuzadre.tradingbot.nadarayawatson.order.services.math;

@FunctionalInterface
public interface TradeSignal {
    void onSignal(double price);
}