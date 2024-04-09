package sanberuzadre.tradingbot.nadarayawatson.order.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateLimitDTO {
    public String rateLimitType;
    public String interval;
    public int intervalNum;
    public int limit;
    public int count;
}
