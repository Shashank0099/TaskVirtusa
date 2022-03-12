package com.virtusatask.rooms

import com.virtusatask.server.APIService

class RoomRepository(private val apiService: APIService) {


    suspend fun getRooms() =  apiService.getRooms()
}