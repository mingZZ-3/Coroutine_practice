package com.pfdy.coroutine_practice

import kotlinx.coroutines.*
import kotlin.system.*


fun main() {
    val time = measureTimeMillis{
        runBlocking {
            println("Weather forecast")
            try {
                println(getWeatherReport())
            } catch (e: AssertionError) {
                println("Caught exception in runBlocking(): $e")
                println("Report unavailable at this time")
            }
            println("Have a good day!")
        }
    }
    println("Execution time: ${time / 1000.0} seconds")
}

suspend fun getWeatherReport() = coroutineScope {
    val forecast : Deferred<String> = async { getForecast() }
    val temperature : Deferred<String> = async {
        try {
            getTemperature()
        } catch (e: AssertionError) {
            println("Caught exception $e")
            "{ No temperature found }"
        }
    }
    "${forecast.await()} ${temperature.await()}"
}

suspend fun getForecast(): String {
    delay(1000)
    return "Sunny"
}

suspend fun getTemperature(): String {
    delay(500)
    throw AssertionError("Temperature is invalid")
    return "30\u00b0C"
}