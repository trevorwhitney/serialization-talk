package manual

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.commons.io.IOUtils

class Manual {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val objectMapper = jacksonObjectMapper()
            val jsonStream = Manual::class.java.classLoader.getResourceAsStream("nest.json")
            val json = IOUtils.toString(jsonStream, "utf-8")

            val thermostat = objectMapper.readValue<Thermostat>(json)
            println("Parsed thermostat: $thermostat")
        }
    }
}

