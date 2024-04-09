package sanberuzadre.tradingbot.nadarayawatson.order.dtos.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDTO {
    public String id;
    public int status;
    public Object result;
    public Object error;
    public List<RateLimitDTO> rateLimits;

}