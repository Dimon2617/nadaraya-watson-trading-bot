package sanberuzadre.tradingbot.nadarayawatson.order.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@Entity(name = "limit_order_entity")
public class LimitOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String currencyChain;

    @Column(nullable = false)
    private String operation;

    @Column(nullable = false)
    private String timeInForce;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Long orderListId;

    @Column(nullable = false)
    private BigDecimal stopPrice;

    @Column(nullable = false)
    private BigDecimal stopLimitPrice;

    @Column(nullable = false)
    private Long orderStopId;

    @Column(nullable = false)
    private String StopType;

}
