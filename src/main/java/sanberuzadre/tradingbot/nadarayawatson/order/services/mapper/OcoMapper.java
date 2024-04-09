package sanberuzadre.tradingbot.nadarayawatson.order.services.mapper;

import org.springframework.stereotype.Service;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order.NewOcoOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order.OrderReportDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.entities.LimitOrderEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class OcoMapper {
    public LimitOrderEntity toEntity(NewOcoOrderResponseDTO responseDTO){
        ArrayList<OrderReportDTO> reportDTO = responseDTO.orderReports;
        OrderReportDTO orderStopDTO = reportDTO.get(0);
        OrderReportDTO orderLimitDTO = reportDTO.get(1);
        return new LimitOrderEntity()
                .setCurrencyChain(orderLimitDTO.symbol)
                .setOperation(orderLimitDTO.side)
                .setTimeInForce(orderLimitDTO.timeInForce)
                .setQuantity(BigDecimal.valueOf(Double.parseDouble(orderLimitDTO.origQty)))
                .setPrice(BigDecimal.valueOf(Double.parseDouble(orderLimitDTO.price)))
                .setOrderId(orderLimitDTO.orderId)
                .setType(orderLimitDTO.type)
                .setOrderListId((long) orderLimitDTO.orderListId)
                .setStopPrice(BigDecimal.valueOf(Double.parseDouble(orderStopDTO.price)))
                .setStopLimitPrice(BigDecimal.valueOf(Double.parseDouble(orderStopDTO.stopPrice)))
                .setOrderStopId(orderStopDTO.orderId)
                .setStopType(orderStopDTO.type);
                /*.setOrderStop(new OrderStopEntity()
                        .setCurrencyChain(orderStopDTO.symbol)
                        .setOperation(orderStopDTO.side)
                        .setTimeInForce(orderStopDTO.timeInForce)
                        .setQuantity(BigDecimal.valueOf(Double.parseDouble(orderStopDTO.origQty)))
                        .setStopPrice(BigDecimal.valueOf(Double.parseDouble(orderStopDTO.price)))
                        .setStopLimitPrice(BigDecimal.valueOf(Double.parseDouble(orderStopDTO.stopPrice)))
                        .setOrderId(orderStopDTO.orderId)
                        .setType(orderStopDTO.type)*/


    }
}
