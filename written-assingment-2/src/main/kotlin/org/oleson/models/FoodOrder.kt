package org.oleson.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Serdeable.Serializable
@Serdeable.Deserializable
@Introspected
@JacksonXmlRootElement(localName = "foodOrder")
data class FoodOrder(
    val orderId: Int,
    val customerName: String,
    val items: List<FoodItem>,
    val totalPrice: Double
) {
    // Add a default constructor
    constructor() : this(0, "", emptyList(), 0.0)
}
