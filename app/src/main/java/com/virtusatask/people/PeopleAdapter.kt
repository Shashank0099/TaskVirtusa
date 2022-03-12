package com.virtusatask.people

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.virtusatask.R
import com.virtusatask.databinding.ItemPeopleBinding
import com.virtusatask.server.responses.model.PeopleResponseItem

class PeopleAdapter : RecyclerView.Adapter<PeopleAdapter.Holder>() {
    var peopleList = mutableListOf<PeopleResponseItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemPeopleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }


    inner class Holder(private val binding: ItemPeopleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            (peopleList.get(position).firstName + " " + peopleList.get(position).lastName).also {
                binding.personNameTv.text = it
            }
            binding.personEmailTv.text = peopleList.get(position).email
            binding.jobTitleTv.text = peopleList.get(position).jobtitle
            binding.favColorTv.text = peopleList.get(position).favouriteColor
            Glide.with(binding.personIv)
                .load(peopleList.get(position).avatar)
                .transform(CenterInside(), RoundedCorners(24))
                .placeholder(
                    R.drawable.placeholder
                ).into(binding.personIv)

        }
    }

    fun setList(prevSize: Int, peopleList: MutableList<PeopleResponseItem>) {
        this.peopleList = peopleList
        notifyItemRangeInserted(prevSize, peopleList.size)
    }

}