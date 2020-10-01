package ru.storytellers.utils

import com.amplitude.api.AmplitudeClient
import org.json.JSONObject

class AmplitudeWrapper(private val client: AmplitudeClient) {

    fun riseEvent(eventName: String) {
        client.logEvent(eventName)
    }

    fun riseEvent(eventName: String, properties: JSONObject) {
        client.logEvent(eventName, properties)
    }

    //Identify identify = new Identify().set("plan", "premium");
    //Amplitude.getInstance().identify(identify);
}