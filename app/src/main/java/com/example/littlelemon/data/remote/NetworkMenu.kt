package com.example.littlelemon.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//Represent raw data from the server

@Serializable
data class NetworkMenu(
    @SerialName("menu")
    val menu: List<NetworkMenuItem>
)

@Serializable
data class NetworkMenuItem(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("price")
    val price: String,
    @SerialName("image")
    val image: String,
    @SerialName("category")
    val category: String
)