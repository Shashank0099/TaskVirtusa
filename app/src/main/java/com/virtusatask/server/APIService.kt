package com.virtusatask.server

import com.virtusatask.constants.people
import com.virtusatask.constants.rooms
import com.virtusatask.server.responses.model.PeopleResponse
import com.virtusatask.server.responses.model.RoomsModel
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @GET(rooms)
    suspend fun getRooms() : Response<RoomsModel>

    @GET(people)
    suspend fun getPeople() : Response<PeopleResponse>
}