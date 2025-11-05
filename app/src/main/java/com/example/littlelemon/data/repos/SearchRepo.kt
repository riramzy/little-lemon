package com.example.littlelemon.data.repos

import com.example.littlelemon.data.local.menu.LocalMenuItem
import com.example.littlelemon.data.local.search.LocalSearchDao

class SearchRepo(private val dao: LocalSearchDao) {
    fun searchByName(name: String) = dao.searchMenuItemsByName(name)
    fun getAllMenuItems() = dao.getAllMenuItems()
}
