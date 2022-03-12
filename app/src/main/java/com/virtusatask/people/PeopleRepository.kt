package com.virtusatask.people

import com.virtusatask.server.APIService

class PeopleRepository(private val apiService: APIService) {


    suspend fun getRooms() =  apiService.getRooms()
    suspend fun getPeople() =  apiService.getPeople()
}