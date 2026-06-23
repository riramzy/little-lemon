package com.riramzy.littlelemon.data.remote

import com.riramzy.littlelemon.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

interface MenuApiService {
    suspend fun fetchMenu(): NetworkMenu
}

@Singleton
class MenuApiServiceImpl @Inject constructor(
    private val client: HttpClient
) : MenuApiService {
    override suspend fun fetchMenu(): NetworkMenu {
        return client.get(Constants.MENU_URL).body()
    }
}