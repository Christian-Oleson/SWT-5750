package org.oleson

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.micronaut.configuration.picocli.PicocliRunner
import io.micronaut.serde.ObjectMapper
import org.oleson.models.FoodOrder
import picocli.CommandLine.Command
import picocli.CommandLine.Option

@Command(
    name = "oleson", description = ["..."],
    mixinStandardHelpOptions = true
)
class OlesonCommand : Runnable {

    @Option(names = ["-v", "--verbose"], description = ["..."])
    private var verbose: Boolean = false


    private val mapper: ObjectMapper = ObjectMapper.getDefault()
    private val xmlMapper: XmlMapper = XmlMapper.builder().build()

    override fun run() {
        // business logic here
        if (verbose) {
            println("Hi!")
        }

        val json = getJson()
        val xml = getXML()
        val printJson = { println(json) }
        val printXml = { println(xml) }

        // pass the json string to the printSeparator function
        printSeparator(printJson, "json")

        // pass the xml string to the printSeparator function
        printSeparator(printXml, "xml")

        // create an object mapper
        val orderJsonObject = mapper.readValue(json, FoodOrder::class.java)

        println(orderJsonObject)

        // read from XML
        val orderXmlObject = xmlMapper.readValue(xml, FoodOrder::class.java)
        println(orderXmlObject)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            PicocliRunner.run(OlesonCommand::class.java, *args)
        }
    }

    fun printSeparator(function: () -> Unit, name: String) {
        println("----------------${name}------------------")
        function()
        println("----------------${name}------------------")
    }

    fun getJson() : String {
        return "{\n" +
                "  \"orderId\": 12345,\n" +
                "  \"customerName\": \"Christian Oleson\",\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"name\": \"Cheeseburger\",\n" +
                "      \"quantity\": 1,\n" +
                "      \"price\": 5.99\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Fries\",\n" +
                "      \"quantity\": 1,\n" +
                "      \"price\": 2.49\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Chicken Nuggets\",\n" +
                "      \"quantity\": 2,\n" +
                "      \"price\": 3.99\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Milkshake\",\n" +
                "      \"quantity\": 2,\n" +
                "      \"price\": 2.99\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Soda\",\n" +
                "      \"quantity\": 2,\n" +
                "      \"price\": 1.99\n" +
                "    }\n" +
                "  ],\n" +
                "  \"totalPrice\": 23.43\n" +
                "}\n"
    }

    fun getXML() : String {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<order>\n" +
                "  <orderId>12345</orderId>\n" +
                "  <customerName>Christian Oleson</customerName>\n" +
                "  <items>\n" +
                "    <item>\n" +
                "      <name>Cheeseburger</name>\n" +
                "      <quantity>1</quantity>\n" +
                "      <price>5.99</price>\n" +
                "    </item>\n" +
                "    <item>\n" +
                "      <name>Fries</name>\n" +
                "      <quantity>1</quantity>\n" +
                "      <price>2.49</price>\n" +
                "    </item>\n" +
                "    <item>\n" +
                "      <name>Chicken Nuggets</name>\n" +
                "      <quantity>2</quantity>\n" +
                "      <price>3.99</price>\n" +
                "    </item>\n" +
                "    <item>\n" +
                "      <name>Milkshake</name>\n" +
                "      <quantity>2</quantity>\n" +
                "      <price>2.99</price>\n" +
                "    </item>\n" +
                "    <item>\n" +
                "      <name>Soda</name>\n" +
                "      <quantity>2</quantity>\n" +
                "      <price>1.99</price>\n" +
                "    </item>\n" +
                "  </items>\n" +
                "  <totalPrice>23.43</totalPrice>\n" +
                "</order>\n"
    }
}
