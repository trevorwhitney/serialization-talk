# Why You Should Write Your Own Serializer/Deserializer

This repo was quickly put together for a talk. It is in defense of an opinion I have that one should always write their
own serialization/deserialization logic when using Jackson in Java/Kotlin.

## Abstract 

Annotation driven development requires tribal knowledge and is hard to debug. Jackson can be done "easily" with a
lot of annotations if you have a lot of experience with Jackson, or it can be done "simply" by writing your own
serialization and deserialization logic. 

Writing your own serialization and deserialization logic has the following benefits:
    * It is easier for future developers to understand complex serialization.
    * Better isolation of responsibility (decouple the domain from external integrations).
    * Is easier to debug when something isn't working.
    * JSON is the transport mechanism, which might change. Your domain model is used throughout your code and
      should not need to change unless your business logic changes.

## Debugging

* `jsonNode.path("devices.thermostats")` vs `jsonNode.path("devices").path("thermostats")`
    * Solved by placing a debugger in code I control
* Instantiation of [simple type, class annotation.Thermostat] value failed for JSON property id due to 
  missing (therefore NULL) value for creator parameter id which is a non-nullable type
* Exception in thread "main" com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: 
  Unrecognized field "locale" (class annotation.Thermostat), not marked as ignorable (3 known properties: "hvac_mode", "device_id", "ambient_temperature_f"])
    - `objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)`
    - `@JsonIgnoreProperties(ignoreUnknown = true)`
* How do I deserialize the Location object?
* How do I turn HvacMode into an sealed class/enum? Using the compiler to check my enum cases makes my business logic
  more sound.
* There are a lot of jackson annotations to implement custom deserialization, and a whole articles on the the
  hard to understand exceptions they produce: http://www.baeldung.com/jackson-exception

## Talking Points

* Strive for code that is easy to change. Things always change. You don't know what your integrations will look like in a year.
* Leaky implementation details of serialization / json object structure. All I want is my domain object.
    ```kotlin
    val thermostat = objectMapper
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .readValue<NestJson>(json).devices.thermostats.values.first()
    ```
    
    vs.
    
    ```kotlin
    val thermostat = objectMapper.readValue<Thermostat>(json)
    ```
* Isolate responsibility. Domain objects should not care about Json structure or serialization.
    * `@JsonProperty("device_id")`
    * Prevents the need to create data classes to mirror JSON structure, allowing the developer to focus on modeling
      their data/objects after their domain.
    * Decouples the structure of data coming from integrations from the structure for data that makes sense for your app
      and domain.
* Testability
    * A serializer you write can be broken down into smaller units that can be unit tested.
* Simple vs. Complex (From "Simple made Easy" by Rich Hickey)
    * Simple is often erroneously mistaken for easy. 
    * "Easy" means "to be at hand", "to be approachable". 
    * "Simple" is the opposite of "complex" which means "being intertwined", "being tied together".
    * Simple code is easier to change, and easier to understand.
    * Simple means knowing and understanding the code paths that will be executed.