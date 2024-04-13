
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

class HotelsListFragment : Fragment(), ItemClickListener {

    private lateinit var headingTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var nextButton: Button
    private lateinit var recyclerView: RecyclerView

    private lateinit var hotelListAdapter: HotelListAdapter

    private lateinit var checkInDate: String
    private lateinit var checkOutDate: String
    private lateinit var numberOfGuests: String

    private lateinit var checkInDateTextView: TextView
    private lateinit var checkOutDateTextView: TextView
    private lateinit var numberOfGuestsTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.hotel_list_fragment, container, false)

        headingTextView = view.findViewById(R.id.heading_text_view)
        progressBar = view.findViewById(R.id.progress_bar)
        nextButton = view.findViewById(R.id.next_button)
        recyclerView = view.findViewById(R.id.hotel_list_recyclerView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve values from arguments
        val guestName = arguments?.getString(ReservationInfo.GUEST_NAME.value) ?: ""
        checkInDate = arguments?.getString(ReservationInfo.CHECK_IN_DATE.value) ?: ""
        checkOutDate = arguments?.getString(ReservationInfo.CHECK_OUT_DATE.value) ?: ""
        numberOfGuests = arguments?.getString(ReservationInfo.NUMBER_OF_GUESTS.value) ?: ""

        checkInDateTextView = view.findViewById(R.id.check_in_date_label)
        checkOutDateTextView = view.findViewById(R.id.check_out_date_label)
        numberOfGuestsTextView = view.findViewById(R.id.num_guests_label)


        // Set text for check-in date, check-out date, and number of guests
        checkInDateTextView.text = "Check-in Date: $checkInDate"
        checkOutDateTextView.text = "Check-out Date: $checkOutDate"
        numberOfGuestsTextView.text = "Number of Guests: $numberOfGuests"

        // Set text for heading and guest name
        headingTextView.text = "Welcome $guestName"

        getHotelsListsData()
    }

    private fun getHotelsListsData() {
        progressBar.visibility = View.VISIBLE
        Api.getClient().getHotelsLists().enqueue(object : Callback<List<HotelListData>> {
            override fun onResponse(call: Call<List<HotelListData>>, response: Response<List<HotelListData>>) {
                if (response.isSuccessful) {
                    val userListResponseData = response.body() ?: emptyList()
                    setupRecyclerView(userListResponseData)
                } else {
                    Toast.makeText(requireActivity(), "Failed to fetch hotel lists", Toast.LENGTH_LONG).show()
                }
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<HotelListData>>, t: Throwable) {
                Toast.makeText(requireActivity(), "Network error: ${t.message}", Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun setupRecyclerView(userListResponseData: List<HotelListData>) {
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        hotelListAdapter = HotelListAdapter(requireActivity(), userListResponseData)
        recyclerView.adapter = hotelListAdapter

        nextButton.setOnClickListener {
            val selectedPosition = hotelListAdapter.selectedPosition

            if (selectedPosition != RecyclerView.NO_POSITION) {
                val selectedHotel = userListResponseData[selectedPosition]

                val bundle = Bundle().apply {
                    putString("hotelName", selectedHotel.name)
                    putString("hotelPrice", selectedHotel.price)
                    putString("hotelAvailability", selectedHotel.availability)
                    putString(ReservationInfo.CHECK_IN_DATE.value, checkInDate)
                    putString(ReservationInfo.CHECK_OUT_DATE.value, checkOutDate)
                    putString(ReservationInfo.NUMBER_OF_GUESTS.value, numberOfGuests)
                }

                val hotelGuestDetailsFragment = HotelReservationDetailsFragment()
                hotelGuestDetailsFragment.arguments = bundle

                requireFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, hotelGuestDetailsFragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Please select a hotel", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(view: View, position: Int) {
        // Update selected position in adapter
        hotelListAdapter.selectedPosition = position
        hotelListAdapter.notifyDataSetChanged()
    }
}
