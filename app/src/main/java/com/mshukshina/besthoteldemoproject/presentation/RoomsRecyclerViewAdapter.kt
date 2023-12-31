package com.mshukshina.besthoteldemoproject.presentation

import SliderAdapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mshukshina.besthoteldemoproject.R
import com.mshukshina.besthoteldemoproject.databinding.RoomsLayoutItemBinding
import com.mshukshina.besthoteldemoproject.retrofit.Room
import java.util.Locale

class RoomsRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<RoomsRecyclerViewAdapter.ViewHolder>() {

    private var rooms: List<Room> = listOf()
    lateinit var buttonClickListener: () -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RoomsLayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setRoomList(rooms: List<Room>) {
        this.rooms = rooms
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room = rooms[position]
        val sliderView = holder.sliderView
        val adapter = SliderAdapter()
        for (i in room.image_urls) {
            adapter.addItem(i)
        }
        sliderView.setSliderAdapter(adapter)

        holder.roomNameTextView.text = room.name
        holder.roomPeculiarityTextView.text = room.peculiarities.getOrNull(0) ?: ""
        holder.roomPeculiarityTwoTextView.text = room.peculiarities.getOrNull(1) ?: ""
        holder.roomPeculiarityThreeTextView.text = room.peculiarities.getOrNull(2) ?: ""
        holder.priceTextView.text = context.getString(R.string.room_price, room.price)
        holder.roomPriceDetailsView.text = room.price_per.lowercase(Locale.ROOT)
        holder.bookRoomButton.setOnClickListener {
            buttonClickListener.invoke()
        }

    }

    class ViewHolder(binding: RoomsLayoutItemBinding): RecyclerView.ViewHolder(binding.root) {
        val sliderView = binding.imageSlider
        val roomNameTextView = binding.roomNameTextView
        val roomPeculiarityTextView = binding.roomPeculiarityTextView
        val roomPeculiarityTwoTextView = binding.roomPeculiarityTwoTextView
        val roomPeculiarityThreeTextView = binding.roomPeculiarityThreeTextView
        val priceTextView = binding.priceTextView
        val roomPriceDetailsView = binding.roomPriceDetailsView
        val bookRoomButton = binding.bookRoomButton
    }
}