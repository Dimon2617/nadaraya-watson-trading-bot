package sanberuzadre.tradingbot.nadarayawatson.order.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WebSocketOrderDTO {

    private final String currencyChain;
    private final String operation;
    private final String type;
    private final String orderId;
    private final Double quantity;
    private final Double price;

}
