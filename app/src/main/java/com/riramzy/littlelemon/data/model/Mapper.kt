package com.riramzy.littlelemon.data.model

import com.riramzy.littlelemon.data.local.cart.LocalCartItem
import com.riramzy.littlelemon.data.local.menu.LocalMenuItem
import com.riramzy.littlelemon.data.remote.NetworkMenuItem

//Converts between network and local models using extension functions
fun NetworkMenuItem.toEntity(): LocalMenuItem = LocalMenuItem(
    id = this.id,
    title = this.title,
    description = this.description,
    price = this.price.toDouble(),
    image = this.image,
    category = this.category
)

fun LocalMenuItem.toCartItems(): LocalCartItem = LocalCartItem(
    id = this.id,
    title = this.title,
    price = this.price,
    image = this.image,
)