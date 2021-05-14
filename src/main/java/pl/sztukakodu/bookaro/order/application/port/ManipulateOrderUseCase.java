package pl.sztukakodu.bookaro.order.application.port;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import pl.sztukakodu.bookaro.order.domain.Order;
import pl.sztukakodu.bookaro.order.domain.OrderItem;
import pl.sztukakodu.bookaro.order.domain.OrderStatus;
import pl.sztukakodu.bookaro.order.domain.Recipient;

import java.util.Collections;
import java.util.List;

public interface ManipulateOrderUseCase {

    void removeById(Long id);


    UpdateOrderResponse updateOrder(UpdateOrderCommand command);

    void updateStatus(Long id, OrderStatus status);

    @Value
    @Builder
    @AllArgsConstructor
    class UpdateOrderCommand {

        Long id;
        OrderStatus status;
        List<OrderItem> items;
        Recipient recipient;


        public Order updateFields(Order order) {

            if (status != null) {
                order.setId(id);
            }
            if (items != null) {
                order.setItems(items);
            }
            if (recipient != null) {
                order.setRecipient(recipient);
            }
            return order;
        }
    }

    @Value
    class UpdateOrderResponse {

        public static final UpdateOrderResponse SUCCESS = new UpdateOrderResponse(true, Collections.emptyList());

        boolean success;
        List<String> errors;
    }
}
