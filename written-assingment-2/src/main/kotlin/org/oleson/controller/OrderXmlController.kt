package org.oleson.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.Put
import io.micronaut.serde.ObjectMapper
import org.oleson.models.FoodItem
import org.oleson.models.FoodOrder
import reactor.core.publisher.Mono

@Controller("/OrderXml")
class OrderXmlController {

    private val mapper: ObjectMapper = ObjectMapper.getDefault()
    private val orderMap = mutableMapOf(
        1 to FoodOrder(1, "Christian", listOf(FoodItem(1, "Bacon Cheeseburger", 15.00)), 100.50),
        2 to FoodOrder()
    )

    @Produces("application/xml")
    @Get("/{orderId}")
    fun getOrder(
        @PathVariable
        orderId: Int
    ) : Mono<FoodOrder> {
        return Mono.just(orderMap.filter { it.key == orderId }.firstNotNullOf { it.value })
    }

    @Consumes("application/xml")
    @Produces("application/xml")
    @Post("/{orderId}")
    fun addOrder(
        @PathVariable
        orderId: Int,
        @Body
        order: FoodOrder
    ) : Mono<MutableHttpResponse<FoodOrder?>> {
        return Mono.fromCallable {
            require(!(orderMap.containsKey(orderId))) { "Order with ID $order.orderId already exists" }

            orderMap[orderId] = order
            HttpResponse.created(orderMap[orderId])
        }.onErrorResume {
            Mono.just(HttpResponse.badRequest())
        }
    }

    @Consumes("application/xml")
    @Produces("application/xml")
    @Put("/{orderId}")
    fun updateOrder(
        @PathVariable
        orderId: Int,
        @Body
        order: FoodOrder
    ) : Mono<MutableHttpResponse<FoodOrder?>> {
        return Mono.fromCallable {
            require(orderMap.containsKey(orderId)) { "Order with ID $order.orderId does not exist" }

            orderMap[orderId] = order
            HttpResponse.ok(orderMap[orderId])
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
            require(orderMap.containsKey(orderId)) { "Order with ID $orderId does not exist" }

            orderMap.remove(orderId)
            HttpResponse.ok("Deleted order with ID $orderId")
        }.onErrorResume {
            Mono.just(HttpResponse.badRequest())
        }
    }
}