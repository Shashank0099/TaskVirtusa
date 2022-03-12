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
    private var isLoading  = false
    private var isLastPage = false
    private var page = 1
    private val itemCount = 10
    lateinit var mainList: List<PeopleResponseItem>
    var paginationList =  PeopleResponse()
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
                            mainList = it
                            manageRVData()
                        }
                    } else {
                        peopleMutableLiveData.value = BaseResponse.Error("Some Error Occur")
                    }

                } else {
                    peopleMutableLiveData.value =
                        BaseResponse.Error("Some Error Occur : ${response.code()}")
                }
            } catch (exp: Exception) {
                peopleMutableLiveData.value = BaseResponse.Error("Some Error Occur ${exp.message} ")
            }

        }
    }

    fun setPeopleAdapter(context: Context, rv: RecyclerView) {
        val  manager  = LinearLayoutManager(context)
        rv.layoutManager = manager
        rv.adapter = PeopleAdapter()

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = manager.childCount
                    val totalItemCount = manager.itemCount
                    val firstVisibleItemPosition = manager.findFirstVisibleItemPosition()
                    if (!isLoading && !isLastPage) {
                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= paginationList.size) {
                            page = page + 1
                            isLoading = true
                            manageRVData()
                        }
                    }
                }
            }
        })

    }

    private fun manageRVData(){
        if ((paginationList.size + itemCount) < mainList.size)
            paginationList.addAll(mainList.subList(paginationList.size, (paginationList.size + itemCount)))
        else {
            isLastPage  = true
            paginationList.addAll(mainList.subList(paginationList.size, mainList.size))
        }
        peopleMutableLiveData.postValue(BaseResponse.Success(paginationList))
        isLoading = false
    }

    fun setAdapterList(rv: RecyclerView, prevSize: Int, roomList: MutableList<PeopleResponseItem>) {
        (rv.adapter as PeopleAdapter).setList(prevSize, roomList)
    }

}