package sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OrderReportDTO {
    public String symbol;
    public Long orderId;
    public int orderListId;
    public String clientOrderId;
    public Object transactTime;
    public String price;
    public String origQty;
    public String executedQty;
    public String cummulativeQuoteQty;
    public String status;
    public String timeInForce;
    public String type;
    public String side;
    public String stopPrice;
    public long workingTime;
    public String selfTradePreventionMode;
}
