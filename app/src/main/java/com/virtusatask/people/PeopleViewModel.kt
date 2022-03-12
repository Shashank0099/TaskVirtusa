package com.virtusatask.people

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
import com.virtusatask.server.responses.model.PeopleResponse
import com.virtusatask.server.responses.model.PeopleResponseItem
import kotlinx.coroutines.launch

class PeopleViewModel : ViewModel() {
    private var peopleRepository: PeopleRepository
    private var peopleMutableLiveData = MutableLiveData<BaseResponse<PeopleResponse>>()

    val peopleLiveData: LiveData<BaseResponse<PeopleResponse>> = peopleMutableLiveData

    init {
        val apiService = RetrofitHelper.getInstance().create(APIService::class.java)
        peopleRepository = PeopleRepository(apiService)
        getPeoples()
    }


    private fun getPeoples() {
        viewModelScope.launch {
            try {
                val response = peopleRepository.getPeople()

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        body.let {
                            peopleMutableLiveData.postValue(BaseResponse.Success(it))
                        }
                    } else {
                        peopleMutableLiveData.value = BaseResponse.Error("Some Error Occur")
                    }

                } else {
                    peopleMutableLiveData.value = BaseResponse.Error("Some Error Occur : ${response.code()}")
                }
            } catch (exp: Exception) {
                peopleMutableLiveData.value = BaseResponse.Error("Some Error Occur ${exp.message} ")
            }

        }
    }

    fun setPeopleAdapter(context: Context, rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = PeopleAdapter()

    }

    fun setAdapterList(rv: RecyclerView, roomList: List<PeopleResponseItem> ){
        (rv.adapter as PeopleAdapter) .setList(roomList)
    }

}