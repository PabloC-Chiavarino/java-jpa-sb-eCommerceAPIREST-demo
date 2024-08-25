package com.pdev.ecdemo.controller;


import com.pdev.ecdemo.dto.OrderDTO;
import com.pdev.ecdemo.entity.Order;
import com.pdev.ecdemo.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/orders")
@Tag(name = "Orden / Order", description = "Operaciones específicas para ordenes / Specific operations for orders")
public class OrderController extends _GenericController<Order, OrderDTO> {

    OrderService orderService;

    @Autowired
    public OrderController(OrderService service) {
        super(service);
        this.orderService = service;
    }

    @Operation(
            summary = "Obtener una orden por ID de cliente / Get an order by client ID",
            description = "Recupera una orden asociada al cliente especificado por el ID del cliente. / Retrieves an order associated with the client specified by the client ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden encontrada / Order found"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada / Order not found"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @GetMapping(path = "/client/{id}")
    public ResponseEntity<OrderDTO> findByClientId(@Valid @PathVariable Long id) {
        try {
            Optional<OrderDTO> order = orderService.findByClient(id);
            if (order.isPresent()) {

                return ResponseEntity.ok(order.get());
            } else {

                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Eliminar una entrada de la orden / Delete an entry from the order",
            description = "Elimina una entrada de la orden del cliente especificado por el ID del cliente y el ID del producto. / Deletes an entry from the order of the client specified by the client ID and product ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entrada eliminada / Entry deleted"),
            @ApiResponse(responseCode = "404", description = "Entrada no encontrada / Entry not found"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @DeleteMapping(path = "/delete/entry/{clientId}")
    public ResponseEntity<Order.OrderEntry> deleteEntry(@RequestBody @Valid Order.OrderEntry data, @PathVariable Long clientId) {
        try {
            Optional<Order.OrderEntry> order = orderService.deleteFromOrder(data.getProductId(), clientId);
            if (order.isPresent()) {

                return ResponseEntity.noContent().build();
            } else {

                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Agregar una entrada a la orden / Add an entry to the order",
            description = "Agrega una nueva entrada a la orden del cliente especificado por el ID del cliente y el ID del producto. / Adds a new entry to the order of the client specified by the client ID and product ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Entrada añadida / Entry added"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada / Order not found"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @PostMapping(path = "/add_entry/{clientId}")
    public ResponseEntity<Order.OrderEntry> addToOrder(@RequestBody @Valid Order.OrderEntry data, @PathVariable Long clientId) {
        try {
            Optional<Order.OrderEntry> order = orderService.addToOrder(data.getProductId(), data.getAmount(), clientId);
            if (order.isPresent()) {

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(order.get());
            } else {

                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Retirar una entrada de la orden / Withdraw an entry from the order",
            description = "Retira una entrada de la orden del cliente especificado por el ID del cliente y el ID del producto. / Withdraws an entry from the order of the client specified by the client ID and product ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Entrada retirada / Entry withdrawn"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada / Order not found"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @DeleteMapping(path = "/withdraw/entry/{clientId}")
    public ResponseEntity<Order.OrderEntry> withdrawFromOrder(@Valid @RequestBody Order.OrderEntry data, @PathVariable Long clientId) {
        try {
            Optional<Order.OrderEntry> order = orderService.withdrawFromOrder(data.getProductId(), data.getAmount(), clientId);
            if (order.isPresent()) {

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(order.get());
            } else {

                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }
}
