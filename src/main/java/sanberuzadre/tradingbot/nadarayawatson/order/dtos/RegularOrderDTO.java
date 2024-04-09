package sanberuzadre.tradingbot.nadarayawatson.order.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Getter
@Setter
@Accessors(chain = true)
public class RegularOrderDTO {

        public String symbol;
        public Long orderId;
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
        public ArrayList<Object> fills;
        public String selfTradePreventionMode;

}
