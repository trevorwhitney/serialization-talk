package manual

data class Thermostat(
        val id: String,
        val currentTemperature: Double,
        val mode: String,
        val location: Location
)