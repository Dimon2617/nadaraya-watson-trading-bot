package sanberuzadre.tradingbot.nadarayawatson.order.dtos.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Getter
@Setter
@Accessors(chain = true)
public class NewOrderResponseDTO {
    public String symbol;
    public long orderId;
    public int orderListId;
    public String clientOrderId;
    public long transactTime;
    public String price;
    public String origQty;
    public String executedQty;
    public String cummulativeQuoteQty;
    public String status;
    public String timeInForce;
    public String type;
    public String side;
    public long workingTime;
    public ArrayList<FillsResponseDTO> fills;
    public String selfTradePreventionMode;
}