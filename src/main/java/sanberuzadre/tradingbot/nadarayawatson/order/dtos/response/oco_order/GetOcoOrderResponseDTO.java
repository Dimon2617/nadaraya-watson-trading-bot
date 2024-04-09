package sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Getter
@Setter
@Accessors(chain = true)
public class GetOcoOrderResponseDTO {
    public int orderListId;
    public String contingencyType;
    public String listStatusType;
    public String listOrderStatus;
    public String listClientOrderId;
    public long transactionTime;
    public String symbol;
    public ArrayList<OcoOrderDTO> orders;
}
