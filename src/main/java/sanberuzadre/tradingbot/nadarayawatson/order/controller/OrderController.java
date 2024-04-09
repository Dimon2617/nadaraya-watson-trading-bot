package sanberuzadre.tradingbot.nadarayawatson.order.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.OrderDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.services.OrderService;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bot/nadaraya-watson/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public String createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @GetMapping("/{orderId}")
    public String getOrder(@PathVariable @NotNull Long orderId) { // ToDo: must return appropriate OrderResult object
        return orderService.getOrder(orderId);
    }

    @PostMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable @NotNull Long orderId) { // ToDo: must return appropriate OrderResult object
        return orderService.cancelOrder(orderId);
    }


    public static void main(String[] args) {
        BigDecimal number = new BigDecimal("11.1"); // ваше десяткове число
        BigDecimal roundedNumber = number.setScale(0, RoundingMode.DOWN);
        System.out.println("Округлений результат: " + roundedNumber);
    }
}
