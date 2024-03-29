package com.example.hotel_reservation_system_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HotelsListFragment: Fragment(), ItemClickListener {

    lateinit var rootView: View
    lateinit var headingTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.hotel_list_fragment, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        headingTextView = view.findViewById(R.id.heading_text_view)

        val checkInDate = arguments?.getString(ReservationInfo.CHECK_IN_DATE.value)
        val checkOutDate = arguments?.getString(ReservationInfo.CHECK_OUT_DATE.value)
        val numberOfGuests = arguments?.getString(ReservationInfo.NUMBER_OF_GUESTS.value)

        val tempText = "Welcome user, displaying hotel for $numberOfGuests guests staying from $checkInDate to $checkOutDate"
        headingTextView.setText(tempText)

        // setup recycler view
        val hotelListData = mockHotelListData()
        val recyclerView = view.findViewById<RecyclerView>(R.id.hotel_list_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val hotelListAdapter = activity?.let { HotelListAdapter(it, hotelListData) }
        recyclerView.adapter = hotelListAdapter
    }

    override fun onClick(view: View, position: Int) {
        TODO("Not yet implemented")
    }

    fun mockHotelListData(): List<HotelListData> {
        val list = mutableListOf<HotelListData>()

        list.add(HotelListData("Halifax Regional Hotel", "2000$", "true"))
        list.add(HotelListData("Hotel Pearl", "500$", "false"))
        list.add(HotelListData("Hotel Amano", "800$", "true"))
        list.add(HotelListData("San Jones", "250$", "false"))
        list.add(HotelListData("Halifax Regional Hotel", "2000$", "true"))
        list.add(HotelListData("Hotel Pearl", "500$", "false"))
        list.add(HotelListData("Hotel Amano", "800$", "true"))
        list.add(HotelListData("San Jones", "250$", "false"))

        return list
    }
}