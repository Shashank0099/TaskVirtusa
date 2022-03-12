package com.virtusatask.activity

import android.content.Intent
import android.os.Bundle
import com.virtusatask.common.BaseActivity
import com.virtusatask.databinding.ActivityMainBinding
import com.virtusatask.people.PeopleActivity
import com.virtusatask.rooms.RoomsActivity

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.roomsBtn.setOnClickListener {
            startActivity(Intent(this, RoomsActivity::class.java))
        }

        binding.peopleBtn.setOnClickListener {
            startActivity(Intent(this, PeopleActivity::class.java))

        }
    }
}