package kr.ac.kumoh.ce20180727.smartAppTermProject

import retrofit2.http.GET

interface CompletedItemApi {
    @GET("CompletedItems")
    suspend fun getCompletedItems(): List<CompletedItem>
}