package com.example.kuwait.Model

import com.beust.klaxon.Klaxon

data class Currencies(
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
    val success: Boolean,
    val timestamp: Long
) {
    public fun toJson() = Klaxon().toJsonString(this)

    companion object {
        public fun fromJson(json: String) = Klaxon().parse<Currencies>(json)
    }
}