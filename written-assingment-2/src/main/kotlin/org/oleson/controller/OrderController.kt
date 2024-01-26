package org.oleson.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import org.oleson.models.FoodItem
import org.oleson.models.FoodOrder
import org.oleson.services.OrderService
import reactor.core.publisher.Mono

@Controller("/Order")
class OrderController(private val orderService: OrderService) {

    @Get("/{orderId}")
    fun getOrder(
        @PathVariable
        orderId: Int
    ) : Mono<FoodOrder> {
        return Mono.just(orderService.orderMap.filter { it.key == orderId }.firstNotNullOf { it.value })
    }

    @Post("/{orderId}")
    fun addOrder(
        @PathVariable
        orderId: Int,
        @Body
        order: FoodOrder
    ) : Mono<MutableHttpResponse<FoodOrder?>> {
        return Mono.fromCallable {
            require(!(orderService.orderMap.containsKey(orderId))) { "Order with ID $order.orderId already exists" }

            orderService.orderMap[orderId] = order
            HttpResponse.created(orderService.orderMap[orderId])
        }.onErrorResume {
            Mono.just(HttpResponse.badRequest())
        }
    }

    @Put("/{orderId}")
    fun updateOrder(
        @PathVariable
        orderId: Int,
        @Body
        order: FoodOrder
    ) : Mono<MutableHttpResponse<FoodOrder?>> {
        return Mono.fromCallable {
            require(orderService.orderMap.containsKey(orderId)) { "Order with ID $order.orderId does not exist" }

            orderService.orderMap[orderId] = order
            HttpResponse.ok(orderService.orderMap[orderId])
        }.onErrorResume {
            Mono.just(HttpResponse.badRequest())
        }
    }

    @Delete("/{orderId}")
    fun deleteOrder(
        @PathVariable
        orderId: Int
    ) : Mono<MutableHttpResponse<String?>> {
        return Mono.fromCallable {
            require(orderService.orderMap.containsKey(orderId)) { "Order with ID $orderId does not exist" }

            orderService.orderMap.remove(orderId)
            HttpResponse.ok("Deleted order with ID $orderId")
        }.onErrorResume {
            Mono.just(HttpResponse.badRequest())
        }
    }
}