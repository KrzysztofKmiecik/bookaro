package pl.sztukakodu.bookaro.order.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.sztukakodu.bookaro.order.application.port.ManipulateOrderUseCase;
import pl.sztukakodu.bookaro.order.application.port.PlaceOrderUseCase;
import pl.sztukakodu.bookaro.order.application.port.QueryOrderUseCase;
import pl.sztukakodu.bookaro.order.domain.Order;
import pl.sztukakodu.bookaro.order.domain.OrderItem;
import pl.sztukakodu.bookaro.order.domain.OrderStatus;
import pl.sztukakodu.bookaro.order.domain.Recipient;

import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.util.List;

import static pl.sztukakodu.bookaro.order.application.port.ManipulateOrderUseCase.UpdateOrderCommand;
import static pl.sztukakodu.bookaro.order.application.port.ManipulateOrderUseCase.UpdateOrderResponse;
import static pl.sztukakodu.bookaro.order.application.port.PlaceOrderUseCase.PlaceOrderCommand;
import static pl.sztukakodu.bookaro.order.application.port.PlaceOrderUseCase.PlaceOrderResponse;

@RequestMapping("/orders")
@RestController
@AllArgsConstructor
public class OrderController {

    private final QueryOrderUseCase queryOrder;
    private final PlaceOrderUseCase placeOrder;
    private final ManipulateOrderUseCase manipulateOrder;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAll() {
        return queryOrder.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return queryOrder.findById(id)
                .map(order -> ResponseEntity.ok(order))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Order> addOrder(@RequestBody RestOrderCommand command) {
        PlaceOrderResponse placeOrderResponse = placeOrder.placeOrder(command.toPlaceOrderCommand());
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/" + placeOrderResponse.getOrderId().toString())
                .build()
                .toUri();

        return ResponseEntity.created(uri).build();
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        manipulateOrder.removeById(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateOrder(@PathVariable Long id, @RequestBody RestOrderCommand command) {
        UpdateOrderResponse updateOrderResponse = manipulateOrder.updateOrder(command.toUpdateOrderCommand(id));
        if (!updateOrderResponse.isSuccess()) {
            String message = String.join(",", updateOrderResponse.getErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        manipulateOrder.updateStatus(id, status);

    }


    @Data
    private static class RestOrderCommand {
        @NotEmpty
        private List<OrderItem> items;
        @NotEmpty
        private Recipient recipient;


        public PlaceOrderCommand toPlaceOrderCommand() {
            return PlaceOrderCommand
                    .builder()
                    .items(this.getItems())
                    .recipient(this.getRecipient())
                    .build();
        }

        public UpdateOrderCommand toUpdateOrderCommand(Long id) {
            return UpdateOrderCommand
                    .builder()
                    .id(id)
                    .items(this.getItems())
                    .recipient(this.getRecipient())
                    .build();
        }
    }
}
