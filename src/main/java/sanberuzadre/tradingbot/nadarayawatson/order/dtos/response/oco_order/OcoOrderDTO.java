package sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OcoOrderDTO {
    public String symbol;
    public Object orderId;
    public String clientOrderId;
}
