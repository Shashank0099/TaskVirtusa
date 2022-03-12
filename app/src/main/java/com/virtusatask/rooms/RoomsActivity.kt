package com.virtusatask.rooms

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.virtusatask.R
import com.virtusatask.common.BaseActivity
import com.virtusatask.databinding.ActivityRoomsBinding
import com.virtusatask.server.responses.BaseResponse
import com.virtusatask.server.responses.model.RoomsModel

class RoomsActivity : BaseActivity() {
    private lateinit var binding: ActivityRoomsBinding
    private lateinit var viewModel: RoomViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setTitle("Rooms")
        showLoader(getString(R.string.loading))
        viewModel = ViewModelProvider(this)[RoomViewModel::class.java]
        viewModel.setRoomAdapter(this, binding.recyclerView)
        viewModel.roomLiveData.observe(this) {
            dismissLoader()
            when (it) {
                is BaseResponse.Error -> showToast(it.error)
                is BaseResponse.Success -> handleList(it.data)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun handleList(data: RoomsModel) {
        viewModel.setAdapterList(binding.recyclerView, data)
    }


}