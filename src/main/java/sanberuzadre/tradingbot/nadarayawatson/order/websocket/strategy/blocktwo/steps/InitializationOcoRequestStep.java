package sanberuzadre.tradingbot.nadarayawatson.order.websocket.strategy.blocktwo.steps;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.WebSocketOcoOrderDTO;

import java.text.DecimalFormat;


@Component
@RequiredArgsConstructor
public class InitializationOcoRequestStep {

    private final VerifyStopPriceStep verifyStopPriceStep;
    private final GetAvrgPriceStep getAvrgPriceStep;

    private double quantity = 0.5;

    private DecimalFormat decimalFormat;

    @Value("${order.stopLossCoefficient}")
    private double stopLossCoefficient = 0.05;

    @Value("${order.stopLimitPriceCoefficient}")
    private double stopLimitPriceCoefficient = 0.06;

    public WebSocketOcoOrderDTO performOcoOrderOpeningStopLoss(double price, String operation) {
        operation = invertSide(operation);
        decimalFormat = new DecimalFormat("#.##");
        String formattedNumber = decimalFormat.format(price);
        String formattedStopPrice = decimalFormat.format(getModifierStopLoss(operation, price, stopLossCoefficient));
        String formattedStopLimitPrice = decimalFormat.format(getModifierStopLoss(operation, price, stopLimitPriceCoefficient));
        double origPrice = Double.parseDouble(formattedNumber.replace(',', '.'));
        double stopPrice = Double.parseDouble(formattedStopPrice.replace(',', '.'));
        double stopLimitPrice = Double.parseDouble(formattedStopLimitPrice.replace(',', '.'));
        WebSocketOcoOrderDTO webSocketOcoOrderDTO = new WebSocketOcoOrderDTO(
                "ETHUSDT",
                operation,
                stopPrice,
                stopLimitPrice,
                origPrice,
                1242L,
                quantity,
                "GTC");
        System.out.println("Stop loss " + webSocketOcoOrderDTO);
        return webSocketOcoOrderDTO;
    }

    public WebSocketOcoOrderDTO performOcoOrderOpeningTakeProfit(double price, String operation) {
        String invertedOperation = invertSide(operation);
        double takeProfitResult = getAvrgPriceStep.execute("").block();
        //todo: на цьому моменті програма зупиняється
        boolean verified = verifyStopPriceStep.execute(Pair.of(takeProfitResult, operation)).block();

        if (verified) {
            decimalFormat = new DecimalFormat("#.##");
            String formattedNumber = decimalFormat.format(price);
            String formattedStopPrice = decimalFormat.format(takeProfitResult - 5);
            String formattedStopLimitPrice = decimalFormat.format(takeProfitResult - 5);
            double origPrice = Double.parseDouble(formattedNumber.replace(',', '.'));
            double stopPrice = Double.parseDouble(formattedStopPrice.replace(',', '.'));
            double stopLimitPrice = Double.parseDouble(formattedStopLimitPrice.replace(',', '.'));

            WebSocketOcoOrderDTO webSocketOcoOrderDTO = new WebSocketOcoOrderDTO(
                    "ETHUSDT",
                    invertedOperation,
                    stopPrice,
                    stopLimitPrice,
                    origPrice,
                    1242L,
                    quantity,
                    "GTC");
            System.out.println("Take profit " + webSocketOcoOrderDTO);
            return webSocketOcoOrderDTO;
        }
        throw new RuntimeException("Take profit is not verified");
    }

    private double getModifierStopLoss(String operation, double price, double modifier) {
        if (operation.equals("SELL")) {
            return price * (1 - modifier);
        } else {
            return price * (1 + modifier);
        }
    }

    private String invertSide(String side) {
        return side.equals("BUY")
                ? "SELL"
                : "BUY";

    }
}
