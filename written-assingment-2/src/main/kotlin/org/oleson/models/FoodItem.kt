package org.oleson.models

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Serdeable.Serializable
@Serdeable.Deserializable
@Introspected
data class FoodItem(
    val quantity: Int,
    val name: String,
    val price: Double
) {
    // Add a default constructor
    constructor() : this(0, "", 0.0)
}
