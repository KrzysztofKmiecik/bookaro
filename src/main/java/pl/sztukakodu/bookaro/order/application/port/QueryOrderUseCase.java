package pl.sztukakodu.bookaro.order.application.port;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.sztukakodu.bookaro.order.domain.Order;
import pl.sztukakodu.bookaro.order.domain.OrderItem;
import pl.sztukakodu.bookaro.order.domain.OrderStatus;
import pl.sztukakodu.bookaro.order.domain.Recipient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QueryOrderUseCase {
    List<Order> findAll();


    Optional<Order> findById(Long id);


    @Data
    @AllArgsConstructor
    class CreateOrderCommand {
        private OrderStatus status = OrderStatus.NEW;
        private List<OrderItem> items;
        private Recipient recipient;
        private LocalDateTime createdAt;

        public Order toOrder() {
            Order order = Order
                    .builder()
                    .status(this.getStatus())
                    .items(this.getItems())
                    .recipient(this.getRecipient())
                    .createdAt(this.getCreatedAt())
                    .build();
            return order;
        }

    }
}
