package com.samiun.mycricket.model.league

data class Data(
    val code: String,
    val country_id: Int,
    val id: Int,
    val image_path: String,
    val name: String,
    val resource: String,
    val season_id: Int,
    val type: String,
    val updated_at: String
)