package com.samiun.mycricket.model.venue

data class Data(
    val capacity: Int,
    val city: String,
    val country_id: Int,
    val floodlight: Boolean,
    val id: Int,
    val image_path: String,
    val name: String,
    val resource: String,
    val updated_at: String
)