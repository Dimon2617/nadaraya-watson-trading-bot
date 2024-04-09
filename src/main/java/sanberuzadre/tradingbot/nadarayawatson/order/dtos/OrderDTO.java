package sanberuzadre.tradingbot.nadarayawatson.order.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Request Example:
 * <p>
 * {
 * "currencyChain": "BTCUSDT",
 * "operation": "SELL",
 * "type": "LIMIT",
 * "timeInForce": "GTC",
 * "quantity": 0.01,
 * "price": 9500
 * }
 */
@Data
@Accessors(chain = true)
public class OrderDTO {

    @NotBlank(message = "Currency chain cannot be blank")
    private String currencyChain;

    @NotBlank(message = "Operation cannot be blank")
    private String operation;

    @NotBlank(message = "Type cannot be blank")
    private String type;

    @NotNull(message = "Time in Force cannot be null")
    private String timeInForce;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be a positive number")
    private BigDecimal quantity;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive number")
    private BigDecimal price;

}
