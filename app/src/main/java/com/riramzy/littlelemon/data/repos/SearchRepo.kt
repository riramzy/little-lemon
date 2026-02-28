package com.riramzy.littlelemon.data.repos

import com.riramzy.littlelemon.data.local.search.LocalSearchDao

class SearchRepo(private val dao: LocalSearchDao) {
    fun searchByName(name: String) = dao.searchMenuItemsByName(name)
    fun getAllMenuItems() = dao.getAllMenuItems()
    fun searchByCategory(category: String) = dao.searchMenuItemsByCategory(category)
}
