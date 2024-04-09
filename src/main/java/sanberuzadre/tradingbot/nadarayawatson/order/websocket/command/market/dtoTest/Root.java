package sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.market.dtoTest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Root {

    @JsonProperty("o")
    public String open;

    //region ATR calculation
    @JsonProperty("c")
    public String close;
    @JsonProperty("h")
    public String high;
    @JsonProperty("l")
    public String low;
    //endregion

    public long t;
    @JsonProperty("T")
    public long T;
    public String s;
    public String i;
    public long f;
    @JsonProperty("L")
    public long L;
    public String v;
    public int n;
    public boolean x;
    public String q;
    @JsonProperty("V")
    public String V;
    @JsonProperty("Q")
    public String Q;
    @JsonProperty("B")
    public String b;

    @Override
    public String toString() {
        return "Root{" +
                "currencyChain='" + s + '\'' +
                ", openPrice='" + open + '\'' +
                ", closePrice='" + close + '\'' +
                ", highPrice='" + high + '\'' +
                ", lowPrice='" + low + '\'' +
                '}';
    }
}