package com.example.hotel_reservation_system_project

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HotelListAdapter(
    private val context: Context,
    private val hotelListData: List<HotelListData>
) : RecyclerView.Adapter<HotelListAdapter.ViewHolder>() {

    var selectedPosition = -1
    var selectedItemView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.hotel_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotelData = hotelListData[position]
        holder.bind(hotelData)

        // Highlight the selected item
        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E0E0E0")) // Set your highlight color
            holder.itemView.isSelected = true
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF")) // Default color
            holder.itemView.isSelected = false
        }

        // Set click listener
        holder.itemView.setOnClickListener {
            if (selectedPosition != holder.adapterPosition) {
                // Clear previous selection
                notifyItemChanged(selectedPosition)
                selectedPosition = holder.adapterPosition
                notifyItemChanged(selectedPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return hotelListData.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hotelName: TextView = itemView.findViewById(R.id.hotel_name_text_view)
        private val hotelAvailability: TextView = itemView.findViewById(R.id.availability_text_view)
        private val hotelPrice: TextView = itemView.findViewById(R.id.price_text_view)

        fun bind(hotelData: HotelListData) {
            hotelName.text = hotelData.name
            hotelAvailability.text = "Availability: ${hotelData.availability}"
            hotelPrice.text = "Price: ${hotelData.price}"
        }
    }
}
