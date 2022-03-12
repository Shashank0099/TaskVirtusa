package com.virtusatask.people

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.virtusatask.R
import com.virtusatask.common.BaseActivity
import com.virtusatask.databinding.ActivityRoomsBinding
import com.virtusatask.server.responses.BaseResponse
import com.virtusatask.server.responses.model.PeopleResponse

class PeopleActivity : BaseActivity() {
    private lateinit var binding: ActivityRoomsBinding
    private lateinit var viewModel: PeopleViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setTitle("People")
        showLoader(getString(R.string.loading))
        viewModel = ViewModelProvider(this)[PeopleViewModel::class.java]
        viewModel.setPeopleAdapter(this, binding.recyclerView)
        viewModel.peopleLiveData.observe(this) {
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


    private fun handleList(data: PeopleResponse) {
        viewModel.setAdapterList(binding.recyclerView, data)
    }


}