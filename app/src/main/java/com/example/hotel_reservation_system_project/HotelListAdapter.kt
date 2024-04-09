package com.example.hotel_reservation_system_project

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HotelListAdapter(private val context: Context, private val hotelListData: List<HotelListData>) :
    RecyclerView.Adapter<HotelListAdapter.ViewHolder>() {

    var selectedPosition = -1;

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.hotel_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (selectedPosition == position)
            holder.itemView.setBackgroundColor(Color.parseColor("#000000"))
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
        val hotelName = hotelListData[position].name
        val hotelPrice = hotelListData[position].price
        val hotelAvailability = hotelListData[position].availability

        // set up the text
        holder.hotelName.text = hotelName
        holder.hotelAvailability.text = hotelAvailability
        holder.hotelPrice.text = hotelPrice
    }

    override fun getItemCount(): Int {
        return hotelListData.size
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        clickListener = itemClickListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var hotelName: TextView = itemView.findViewById(R.id.hotel_name_text_view)
        var hotelPrice: TextView = itemView.findViewById(R.id.price_text_view)
        var hotelAvailability: TextView = itemView.findViewById(R.id.availability_text_view)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
//            println("Position: ${adapterPosition}")
//            clickListener?.onClick(view, adapterPosition)
            if (clickListener != null) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    clickListener?.onClick(view, adapterPosition)
                }
            }
        }
    }
}
