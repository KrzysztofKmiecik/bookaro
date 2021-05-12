package pl.sztukakodu.bookaro.order.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sztukakodu.bookaro.order.application.port.ManipulateOrderUseCase;
import pl.sztukakodu.bookaro.order.domain.Order;
import pl.sztukakodu.bookaro.order.domain.OrderRepository;

import java.util.Collections;

@Service
@AllArgsConstructor
public class ManipulateOrderService implements ManipulateOrderUseCase {
private final OrderRepository repository;

    @Override
    public void removeById(Long id) {
        repository.removeById(id);
    }

    @Override
    public UpdateOrderResponse updateOrder(UpdateOrderCommand command) {
     return   repository.findById(command.getId())
             .map(order->{
                Order updatedOrder= command.updateFields(order);
                repository.save(updatedOrder);
                return UpdateOrderResponse.SUCCESS;
             })
             .orElseGet(()->new UpdateOrderResponse(false, Collections.singletonList("Order not found with id: "+command.getId())));
    }
}
