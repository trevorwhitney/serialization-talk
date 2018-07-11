package manual

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer

@JsonDeserialize(using = ThermostatDeserializer::class)
data class Thermostat(
        val id: String,
        val currentTemperature: Double,
        val mode: String,
        val location: Location
)

data class Location(
        val id: String,
        val name: String
)

class ThermostatDeserializer : StdDeserializer<Thermostat>(Thermostat::class.java) {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Thermostat {
        val json = p?.codec?.readTree<JsonNode>(p) ?: throw JsonParseException(p, "Error parsing json")
        val thermostat = json.path("devices").path("thermostats").first()

        return Thermostat(
                id = thermostat.get("device_id").asText(),
                currentTemperature = thermostat.get("ambient_temperature_f").asDouble(),
                mode = thermostat.get("hvac_mode").asText(),
                location = Location(
                        id = thermostat.get("where_id").asText(),
                        name = thermostat.get("where_name").asText()
                )
        )
    }
}