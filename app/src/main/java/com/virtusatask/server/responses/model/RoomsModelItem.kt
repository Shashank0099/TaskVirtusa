package com.virtusatask.server.responses.model

data class RoomsModelItem(
    val createdAt: String,
    val id: String,
    val isOccupied: Boolean,
    val maxOccupancy: Int
)