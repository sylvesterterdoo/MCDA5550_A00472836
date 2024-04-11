
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotel_reservation_system_project.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/*
class HotelsListFragment : Fragment(), ItemClickListener {

    lateinit var rootView: View
    lateinit var headingTextView: TextView
    lateinit var progressBar: ProgressBar
    lateinit var userListResponseData: List<HotelListData>
    lateinit var hotelListAdapter: HotelListAdapter
    private lateinit var nextButton: Button
    private lateinit var recyclerView: RecyclerView

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
        nextButton = rootView.findViewById(R.id.next_button)
        recyclerView = rootView.findViewById(R.id.hotel_list_recyclerView)

        val checkInDate = arguments?.getString(ReservationInfo.CHECK_IN_DATE.value)
        val checkOutDate = arguments?.getString(ReservationInfo.CHECK_OUT_DATE.value)
        val numberOfGuests = arguments?.getString(ReservationInfo.NUMBER_OF_GUESTS.value)

        val tempText =
            "Welcome user, displaying hotel for $numberOfGuests guests staying from $checkInDate to $checkOutDate"
        headingTextView.text = tempText

        getHotelsListsData()
    }

    private fun setupRecyclerView() {
        progressBar.visibility = View.GONE
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        hotelListAdapter = HotelListAdapter(requireActivity(), userListResponseData)
        recyclerView.adapter = hotelListAdapter

        val checkInDate = arguments?.getString(ReservationInfo.CHECK_IN_DATE.value)
        val checkOutDate = arguments?.getString(ReservationInfo.CHECK_OUT_DATE.value)
        val numberOfGuests = arguments?.getString(ReservationInfo.NUMBER_OF_GUESTS.value)

        nextButton.setOnClickListener {
            if (hotelListAdapter.selectedPosition != -1) {
                val selectedHotel = userListResponseData[hotelListAdapter.selectedPosition]

                // Prepare data bundle to send to next activity
                val bundle = Bundle().apply {
                    putString("hotelName", selectedHotel.name)
                    putString("hotelPrice", selectedHotel.price)
                    putString("hotelAvailability", selectedHotel.availability)
                    putString("checkInDate", checkInDate)
                    putString("checkOutDate", checkOutDate)
                    putString("numberOfGuests", numberOfGuests)
                }

                // Navigate to next activity and pass the data bundle
                val intent = Intent(requireContext(), HotelGuestDetails::class.java)
                intent.putExtras(bundle)
                startActivity(intent)

            } else {
                Toast.makeText(requireContext(), "Please select a hotel", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getHotelsListsData() {
        progressBar.visibility = View.VISIBLE
        Api.getClient().getHotelsLists().enqueue(object : Callback<List<HotelListData>> {
            override fun onResponse(
                call: Call<List<HotelListData>>,
                response: Response<List<HotelListData>>
            ) {
                if (response.isSuccessful) {
                    userListResponseData = response.body() ?: emptyList()
                    setupRecyclerView()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Failed to fetch hotel lists",
                        Toast.LENGTH_LONG
                    ).show()
                }
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<HotelListData>>, t: Throwable) {
                Toast.makeText(
                    requireActivity(),
                    "Network error: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
                progressBar.visibility = View.GONE
            }
        })
    }

    override fun onClick(view: View, position: Int) {
        // Update selected position in adapter
        hotelListAdapter.selectedPosition = position
        hotelListAdapter.notifyDataSetChanged()
    }
}
*/

class HotelsListFragment : Fragment(), ItemClickListener {

    private lateinit var headingTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var nextButton: Button
    private lateinit var recyclerView: RecyclerView

    private var userListResponseData: List<HotelListData> = emptyList()
    private lateinit var hotelListAdapter: HotelListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hotel_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        headingTextView = view.findViewById(R.id.heading_text_view)
        progressBar = view.findViewById(R.id.progress_bar)
        nextButton = view.findViewById(R.id.next_button)
        recyclerView = view.findViewById(R.id.hotel_list_recyclerView)

        val checkInDate = arguments?.getString(ReservationInfo.CHECK_IN_DATE.value)
        val checkOutDate = arguments?.getString(ReservationInfo.CHECK_OUT_DATE.value)
        val numberOfGuests = arguments?.getString(ReservationInfo.NUMBER_OF_GUESTS.value)

        headingTextView.text =
            "Welcome user, displaying hotels for $numberOfGuests guests staying from $checkInDate to $checkOutDate"

        getHotelsListsData()
    }

    private fun setupRecyclerView() {
        progressBar.visibility = View.GONE
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        hotelListAdapter = HotelListAdapter(requireActivity(), userListResponseData)
        recyclerView.adapter = hotelListAdapter

        val checkInDate = arguments?.getString(ReservationInfo.CHECK_IN_DATE.value)
        val checkOutDate = arguments?.getString(ReservationInfo.CHECK_OUT_DATE.value)
        val numberOfGuests = arguments?.getString(ReservationInfo.NUMBER_OF_GUESTS.value)

        nextButton.setOnClickListener {
            val selectedPosition = hotelListAdapter.selectedPosition
            if (selectedPosition != RecyclerView.NO_POSITION) {
                val selectedHotel = userListResponseData[selectedPosition]

                // Prepare data bundle to send to next activity
                val bundle = Bundle().apply {
                    putString("hotelName", selectedHotel.name)
                    putString("hotelPrice", selectedHotel.price)
                    putString("hotelAvailability", selectedHotel.availability)
                    putString("checkInDate", checkInDate)
                    putString("checkOutDate", checkOutDate)
                    putString("numberOfGuests", numberOfGuests)
                }

                // Navigate to next activity and pass the data bundle
                val intent = Intent(requireContext(), HotelGuestDetails::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Please select a hotel", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getHotelsListsData() {
        progressBar.visibility = View.VISIBLE
        Api.getClient().getHotelsLists().enqueue(object : Callback<List<HotelListData>> {
            override fun onResponse(call: Call<List<HotelListData>>, response: Response<List<HotelListData>>) {
                if (response.isSuccessful) {
                    userListResponseData = response.body() ?: emptyList()
                    setupRecyclerView()
                } else {
                    Toast.makeText(requireActivity(), "Failed to fetch hotel lists", Toast.LENGTH_LONG).show()
                }
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<HotelListData>>, t: Throwable) {
                println(t.message)
                Toast.makeText(requireActivity(), "Network error: ${t.message}", Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
            }
        })
    }

    override fun onClick(view: View, position: Int) {
        // Update selected position in adapter
        hotelListAdapter.selectedPosition = position
        hotelListAdapter.notifyDataSetChanged()
    }
}
