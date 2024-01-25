package org.oleson.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.micronaut.serde.annotation.Serdeable

@JacksonXmlRootElement(localName = "items")
@Serdeable.Serializable
@Serdeable.Deserializable
data class FoodItem(
    val quantity: Int,
    val name: String,
    val price: Double
) {
    // Add a default constructor
    constructor() : this(0, "", 0.0)
}

