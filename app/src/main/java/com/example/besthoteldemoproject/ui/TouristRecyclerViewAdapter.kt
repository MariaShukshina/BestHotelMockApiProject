package com.example.besthoteldemoproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.besthoteldemoproject.databinding.TouristRecyclerviewItemBinding

class TouristRecyclerViewAdapter: RecyclerView.Adapter<TouristRecyclerViewAdapter.ViewHolder>() {

    private var touristList: ArrayList<Tourist> = arrayListOf()

    fun setList(touristList: ArrayList<Tourist>) {
        this.touristList = touristList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TouristRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return touristList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tourist = touristList[position]
        if(position in 0..9) {
            holder.touristNumber.text = touristNumbersMap[position]
        } else {
            holder.touristNumber.text = "${position + 1}-й турист"
        }
        holder.arrowUp.setOnClickListener {
            holder.touristInfoLayout.visibility = View.GONE
            holder.arrowDown.visibility = View.VISIBLE
            holder.arrowUp.visibility = View.GONE
        }
        holder.arrowDown.setOnClickListener {
            holder.touristInfoLayout.visibility = View.VISIBLE
            holder.arrowDown.visibility = View.GONE
            holder.arrowUp.visibility = View.VISIBLE
        }

    }

    class ViewHolder(binding: TouristRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root) {
        val touristNumber = binding.nextTouristTextView
        val arrowUp = binding.nextTouristArrowUp
        val arrowDown = binding.nextTouristArrowDown
        val touristInfoLayout = binding.nextTouristInfoLayout
    }

    companion object {
        private val touristNumbersMap = mapOf(
            0 to "Первый турист",
            1 to "Второй турист",
            2 to "Третий турист",
            3 to "Четвертый турист",
            4 to "Пятый турист",
            5 to "Шестой турист",
            6 to "Седьмой турист",
            7 to "Восьмой турист",
            8 to "Девятый турист",
            9 to "Десятый турист",
        )
    }
}