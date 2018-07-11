package annotation

import com.fasterxml.jackson.annotation.JsonProperty

data class NestJson(
    @JsonProperty val devices: NestDevices
)

data class NestDevices(
    @JsonProperty val thermostats: Map<String, Thermostat>
)

data class Thermostat(
    @JsonProperty("device_id") val id: String,
    @JsonProperty("ambient_temperature_f") val currentTemperature: Double,
    @JsonProperty("hvac_mode") val mode: String
//    @JsonProperty("id") val location: Location
)

data class Location(
    val id: String,
    val name: String
)