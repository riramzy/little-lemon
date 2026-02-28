package com.riramzy.littlelemon.data.local.orders

import androidx.room.Embedded
import androidx.room.Relation

data class LocalOrderWithItems(
    @Embedded val order: LocalOrder,
    @Relation(
        parentColumn = "id",
        entityColumn = "order_id"
    )
    val items: List<LocalOrderItem>
)