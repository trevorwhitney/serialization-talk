package manual

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer

data class Location(
    val id: String,
    val name: String
)

sealed class HvacMode {
    companion object {
        @JvmStatic
        fun fromJson(mode: String, p: JsonParser) = when (mode) {
            "heat" -> Heat()
            "cool" -> Cool()
            else -> throw JsonParseException(p, "invalid hvac_mode")
        }
    }
}

class Heat : HvacMode() {
    override fun toString(): String {
        return "Heat"
    }
}
class Cool : HvacMode() {
    override fun toString(): String {
        return "Cool"
    }
}

@JsonDeserialize(using = ThermostatDeserializer::class)
data class Thermostat(
    val id: String,
    val currentTemperature: Double,
    val mode: HvacMode,
    val location: Location
)

class ThermostatDeserializer : StdDeserializer<Thermostat>(Thermostat::class.java) {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Thermostat {
        val json = p?.codec?.readTree<JsonNode>(p) ?: throw JsonParseException(p, "Error parsing json")
        val thermostat = json.path("devices").path("thermostats").first()

        return Thermostat(
            id = thermostat.get("device_id").asText(),
            currentTemperature = thermostat.get("ambient_temperature_f").asDouble(),
            mode = HvacMode.fromJson(thermostat.get("hvac_mode").asText(), p),
            location = Location(
                id = thermostat.get("where_id").asText(),
                name = thermostat.get("where_name").asText()
            )
        )
    }
}