package sanberuzadre.tradingbot.nadarayawatson.order.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class WebSocketOcoOrderDTO {

    private  String currencyChain;
    private  String operation;
    private String timeInForce;
    private  Double quantity;
    private  double price;
    private  double stopPrice;
    private  double stopLimitPrice;
    private  Long orderId;

    public WebSocketOcoOrderDTO(String currencyChain, String operation, double stopPrice, double stopLimitPrice, double price, Long orderId, Double quantity,String timeInForce) {
        this.currencyChain = currencyChain;
        this.operation = operation;
        this.stopPrice = stopPrice;
        this.stopLimitPrice = stopLimitPrice;
        this.price = price;
        this.orderId = orderId;
        this.quantity = quantity;
        this.timeInForce = timeInForce;
    }

    @Override
    public String toString() {
        return "WebSocketOcoOrderDTO{" +
                "\n  currencyChain='" + currencyChain + '\'' +
                "\n  operation='" + operation + '\'' +
                "\n  timeInForce='" + timeInForce + '\'' +
                "\n  quantity=" + quantity +
                "\n  price=" + price +
                "\n  stopPrice=" + stopPrice +
                "\n  stopLimitPrice=" + stopLimitPrice +
                "\n  orderId=" + orderId +
                "\n}";
    }

}
