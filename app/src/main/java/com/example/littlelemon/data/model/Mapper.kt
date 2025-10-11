package com.example.littlelemon.data.model

import com.example.littlelemon.data.local.LocalMenuItem
import com.example.littlelemon.data.remote.NetworkMenuItem

//Converts between network and local models using extension functions

fun NetworkMenuItem.toEntity(): LocalMenuItem = LocalMenuItem(
    id = this.id,
    title = this.title,
    description = this.description,
    price = this.price.toDouble(),
    image = this.image,
    category = this.category
)