package org.oleson.services

import jakarta.inject.Singleton
import org.oleson.models.FoodItem
import org.oleson.models.FoodOrder

@Singleton
class OrderService {
    val foodMap = listOf(
        FoodItem(1, "Bacon Cheeseburger", 15.00),
        FoodItem(2, "Fries", 5.00),
        FoodItem(3, "Coke", 2.50),
        FoodItem(4, "Ice Cream", 3.00),
        FoodItem(5, "Chicken Sandwich", 10.00),
        FoodItem(6, "Salad", 8.00),
    )

    val orderMap = mutableMapOf(
        1 to FoodOrder(1, "Christian", foodMap, foodMap.sumOf { it.price * it.quantity }),
        2 to FoodOrder()
    )

    fun getFoodItem(id: Int): FoodItem {
        return foodMap.filter { it.quantity == id }.firstNotNullOf { it }
    }

    fun getAllOrders(): List<FoodOrder> {
        val orders = orderMap.values.toMutableList()
        // write a for loop
        for (i in 1..10000) {
            orders.add(orders[0])
        }
        return orders.toList()
    }
}