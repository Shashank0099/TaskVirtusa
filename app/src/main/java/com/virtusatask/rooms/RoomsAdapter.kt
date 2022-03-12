package com.virtusatask.rooms

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.virtusatask.databinding.ItemsRoomsBinding
import com.virtusatask.server.responses.model.RoomsModelItem

class RoomsAdapter() : RecyclerView.Adapter<RoomsAdapter.Holder>() {
    var roomList = mutableListOf<RoomsModelItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemsRoomsBinding.inflate(
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
        return roomList.size
    }


    inner class Holder(private val binding: ItemsRoomsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            binding.roomIdTv.text = "Room Id: ${roomList.get(position).id} "
            binding.roomMaxTv.text = "Max Occupancy: ${roomList.get(position).maxOccupancy}"
            if (roomList.get(position).isOccupied){
                binding.roomOccupiedTv.text = "Occupied"
                binding.roomOccupiedTv.setTextColor(Color.RED)

            }else {
                binding.roomOccupiedTv.text = "Available"
                binding.roomOccupiedTv.setTextColor(Color.GREEN)
            }
        }
    }

    fun setList(roomList : List<RoomsModelItem>){
        this.roomList.addAll(roomList)
        notifyDataSetChanged()
    }

}