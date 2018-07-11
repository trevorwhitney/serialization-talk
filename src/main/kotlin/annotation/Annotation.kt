package annotation

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.commons.io.IOUtils

class Annotation {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val objectMapper = jacksonObjectMapper()
            val jsonStream = Annotation::class.java.classLoader.getResourceAsStream("nest.json")
            val json = IOUtils.toString(jsonStream, "utf-8")

            val thermostat = objectMapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue<NestJson>(json).devices.thermostats.values.first()

            println("Parsed thermostat: $thermostat")
        }
    }
}