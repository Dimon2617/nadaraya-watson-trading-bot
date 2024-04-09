package sanberuzadre.tradingbot.nadarayawatson.order.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.OrderDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    public static final Logger fileLogger = LoggerFactory.getLogger("fileLogger");

    //private final OrderRepository orderRepository;

    // ToDo: resolve issue with parsing in WebSockets, and figure out what to return, to return that order was cancelled
    // ToDo: check WebFlux
    public String createOrder(OrderDTO orderDTO) {
        final String order = null;

        // OrderEntity orderEntity = orderMapper.toEntity(orderDTO);
        // orderRepository.save(orderEntity);

        fileLogger.info("Order: {}", order);
        return order;
    }

    // ToDo: resolve issue with parsing in WebSockets, and figure out what to return, to return that order was cancelled
    // ToDo: check WebFlux
    public String getOrder(Long orderId) {
        //var orderEntity = orderRepository
                //.findById(orderId);
                //.orElseThrow(() -> new OrderNotFoundException("Order with id " + orderId + " does not exist"));

        // OrderDTO orderDTO = orderMapper.toDTO(orderEntity);
        //final String testOrder = binanceSpotFacade.getOrder(orderDTO);

        //fileLogger.info("Get Order: {}", testOrder);
        //return testOrder;
        return "";
    }

    // ToDo: resolve issue with parsing in WebSockets, and figure out what to return, to return that order was cancelled
    // ToDo: check WebFlux
    public String cancelOrder(Long orderId) {
        //var orderEntity = orderRepository
                //.findById(orderId);
                //.orElseThrow(() -> new OrderNotFoundException("Order with id " + orderId + " does not exist"));

       /* OrderDTO orderDTO = orderMapper.toDTO(orderEntity);
        final String testOrder = binanceSpotFacade.cancelOrder(orderDTO);

        fileLogger.info("Cancel Order: {}", testOrder);
        return testOrder;*/
        return "";
    }

}
