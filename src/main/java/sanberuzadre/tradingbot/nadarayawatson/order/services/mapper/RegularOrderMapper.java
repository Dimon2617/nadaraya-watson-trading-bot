package sanberuzadre.tradingbot.nadarayawatson.order.services.mapper;

import org.springframework.stereotype.Service;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.RegularOrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.NewOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.entities.RegularOrderEntity;

import java.math.BigDecimal;

@Service
public class RegularOrderMapper {

    public RegularOrderEntity toEntity(RegularOrderDTO regularOrderDTO) {
        return new RegularOrderEntity()
                .setOrderId(regularOrderDTO.getOrderId())
                .setCurrencyChain(regularOrderDTO.getSymbol())
                .setPrice(BigDecimal.valueOf(Double.parseDouble(regularOrderDTO.getPrice())))
                .setOperation(regularOrderDTO.getSide())
                .setType(regularOrderDTO.getType())
                .setQuantity(BigDecimal.valueOf(Double.parseDouble(regularOrderDTO.getOrigQty())));
    }

    public RegularOrderEntity newOrderResponseToEntity(NewOrderResponseDTO responseDTO) {
        return new RegularOrderEntity()
        .setOrderId(responseDTO.orderId)
        .setCurrencyChain(responseDTO.symbol)
        .setOperation(responseDTO.side)
        .setType(responseDTO.type)
        .setQuantity(new BigDecimal(responseDTO.origQty))
        .setPrice(new BigDecimal(responseDTO.price));
    }

}
