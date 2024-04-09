package sanberuzadre.tradingbot.nadarayawatson.order.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@Entity(name = "regular_order_entity")
public class RegularOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String currencyChain;

    @Column(nullable = false)
    private String operation;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false)
    private BigDecimal price;
}
