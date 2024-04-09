/*
//package com.example.hotel_reservation_system_project
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ProgressBar
//import android.widget.TextView
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class HotelsListFragment: Fragment(), ItemClickListener {
//
//    lateinit var rootView: View
//    lateinit var headingTextView: TextView
//    lateinit var progressBar: ProgressBar
//    lateinit var userListResponseData: List<HotelListData>
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        super.onCreateView(inflater, container, savedInstanceState)
//        rootView = inflater.inflate(R.layout.hotel_list_fragment, container, false)
//        return rootView
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        headingTextView = view.findViewById(R.id.heading_text_view)
//        progressBar = view.findViewById(R.id.progress_bar)
//
//        val checkInDate = arguments?.getString(ReservationInfo.CHECK_IN_DATE.value)
//        val checkOutDate = arguments?.getString(ReservationInfo.CHECK_OUT_DATE.value)
//        val numberOfGuests = arguments?.getString(ReservationInfo.NUMBER_OF_GUESTS.value)
//
//        val tempText = "Welcome user, displaying hotel for $numberOfGuests guests staying from $checkInDate to $checkOutDate"
//        headingTextView.setText(tempText)
//
//        getHotelsListsData()
//    }
//
//    private fun setupRecyclerView() {
//        progressBar.visibility = View.GONE
//        val recyclerView: RecyclerView = rootView.findViewById(R.id.hotel_list_recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
//        val hotelListAdapter = HotelListAdapter(requireActivity(), userListResponseData)
//        recyclerView.adapter = hotelListAdapter
//
//        // Bind the click listener
////        hotelListAdapter.setClickListener()
//    }
//
//    private fun getHotelsListsData() {
//        progressBar.visibility = View.VISIBLE
//        Api.getClient().getHotelsLists().enqueue(object : Callback<List<HotelListData>> {
//            override fun onResponse(call: Call<List<HotelListData>>, response: Response<List<HotelListData>>) {
//                if (response.isSuccessful) {
//                    val userListResponses = response.body()
//                    // Process the response data here
//                    if (userListResponses != null) {
//                        userListResponseData = userListResponses
//                    }
//
//                    // Set up the RecyclerView
//                    setupRecyclerView()
//                } else {
//                    // Handle unsuccessful response
//                    Toast.makeText(requireActivity(), "Failed to fetch hotel lists", Toast.LENGTH_LONG).show()
//                }
//                progressBar.visibility = View.GONE
//            }
//
//            override fun onFailure(call: Call<List<HotelListData>>, t: Throwable) {
//                // Handle network errors
//                Toast.makeText(requireActivity(), "Network error: ${t.message}", Toast.LENGTH_LONG).show()
//                progressBar.visibility = View.GONE
//            }
//        })
//    }
//
//
//
//    override fun onClick(view: View, position: Int) {
//        Toast.makeText(context, "Item clicked at position " + position, Toast.LENGTH_SHORT).show();
//    }
//
//}
//


//    fun mockHotelListData(): List<HotelListData> {
//        val list = mutableListOf<HotelListData>()
//
//        list.add(HotelListData("Halifax Regional Hotel", "2000$", "true"))
//        list.add(HotelListData("Hotel Pearl", "500$", "false"))
//        list.add(HotelListData("Hotel Amano", "800$", "true"))
//        list.add(HotelListData("San Jones", "250$", "false"))
//        list.add(HotelListData("Halifax Regional Hotel", "2000$", "true"))
//        list.add(HotelListData("Hotel Pearl", "500$", "false"))
//        list.add(HotelListData("Hotel Amano", "800$", "true"))
//        list.add(HotelListData("San Jones", "250$", "false"))
//
//        return list
//    }


*/

package com.example.hotel_reservation_system_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelsListFragment : Fragment(), HotelListAdapter.ItemClickListener {

    private lateinit var rootView: View
    private lateinit var headingTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var userListResponseData: List<HotelListData>
    private lateinit var hotelListAdapter: HotelListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.hotel_list_fragment, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        headingTextView = view.findViewById(R.id.heading_text_view)
        progressBar = view.findViewById(R.id.progress_bar)

        val checkInDate = arguments?.getString(ReservationInfo.CHECK_IN_DATE.value)
        val checkOutDate = arguments?.getString(ReservationInfo.CHECK_OUT_DATE.value)
        val numberOfGuests = arguments?.getString(ReservationInfo.NUMBER_OF_GUESTS.value)

        val tempText = "Welcome user, displaying hotels for $numberOfGuests guests staying from $checkInDate to $checkOutDate"
        headingTextView.text = tempText

        getHotelsListsData()
    }

    private fun setupRecyclerView() {
        progressBar.visibility = View.GONE
        val recyclerView: RecyclerView = rootView.findViewById(R.id.hotel_list_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        hotelListAdapter = HotelListAdapter(requireActivity(), userListResponseData)
        recyclerView.adapter = hotelListAdapter

        // Set the click listener for item clicks
        hotelListAdapter.setClickListener(this)
    }

    private fun getHotelsListsData() {
        progressBar.visibility = View.VISIBLE
        Api.getClient().getHotelsLists().enqueue(object : Callback<List<HotelListData>> {
            override fun onResponse(call: Call<List<HotelListData>>, response: Response<List<HotelListData>>) {
                if (response.isSuccessful) {
                    val userListResponses = response.body()
                    // Process the response data here
                    userListResponseData = userListResponses ?: emptyList()

                    // Set up the RecyclerView
                    setupRecyclerView()
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(requireActivity(), "Failed to fetch hotel lists", Toast.LENGTH_LONG).show()
                }
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<HotelListData>>, t: Throwable) {
                // Handle network errors
                Toast.makeText(requireActivity(), "Network error: ${t.message}", Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
            }
        })
    }

    override fun onClick(view: View, position: Int) {
        val selectedHotel = userListResponseData[position]
        Toast.makeText(requireContext(), "Clicked on hotel: ${selectedHotel.name}", Toast.LENGTH_SHORT).show()

        // Perform actions based on the clicked item, e.g., navigate to details screen
    }
}
