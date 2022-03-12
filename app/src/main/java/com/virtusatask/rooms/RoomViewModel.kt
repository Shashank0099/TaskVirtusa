package com.virtusatask.rooms

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.virtusatask.server.APIService
import com.virtusatask.server.RetrofitHelper
import com.virtusatask.server.responses.BaseResponse
import com.virtusatask.server.responses.model.RoomsModel
import com.virtusatask.server.responses.model.RoomsModelItem
import kotlinx.coroutines.launch

class RoomViewModel : ViewModel() {
    private var roomRepository: RoomRepository
    private var myLiveData = MutableLiveData<BaseResponse<RoomsModel>>()

    val roomLiveData: LiveData<BaseResponse<RoomsModel>> = myLiveData

    init {
        val apiService = RetrofitHelper.getInstance().create(APIService::class.java)
        roomRepository = RoomRepository(apiService)
        getRooms()
    }


    private fun getRooms() {
        viewModelScope.launch {
            try {
                val response = roomRepository.getRooms()

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        body.let {
                            myLiveData.postValue(BaseResponse.Success(it))
                        }
                    } else {
                        myLiveData.value = BaseResponse.Error("Some Error Occur")
                    }

                } else {
                    myLiveData.value = BaseResponse.Error("Some Error Occur : ${response.code()}")
                }
            } catch (exp: Exception) {
                myLiveData.value = BaseResponse.Error("Some Error Occur ${exp.message} ")
            }

        }
    }

    fun setRoomAdapter(context: Context, rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = RoomsAdapter()

    }

    fun setAdapterList(rv: RecyclerView, roomList: List<RoomsModelItem> ){
        (rv.adapter as RoomsAdapter) .setList(roomList)
    }

}