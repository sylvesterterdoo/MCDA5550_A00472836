package com.example.hotel_reservation_system_project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Array.newInstance
import javax.xml.datatype.DatatypeFactory.newInstance

/*
class HotelReservationDetailsFragment : Fragment() {

    private lateinit var submitButton: Button
    private lateinit var guestListRequestData: MutableList<HotelGuestData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hotel_guest_details_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        guestListRequestData = mutableListOf()

        val hotelName = arguments?.getString("hotelName")
        val hotelPrice = arguments?.getString("hotelPrice")
        val hotelAvailability = arguments?.getString("hotelAvailability")
        val numberOfGuests = arguments?.getString(ReservationInfo.NUMBER_OF_GUESTS.value)?.toInt() ?: 0
        val checkInDate = arguments?.getString(ReservationInfo.CHECK_IN_DATE.value)
        val checkOutDate = arguments?.getString(ReservationInfo.CHECK_OUT_DATE.value)

        view.findViewById<TextView>(R.id.hotel_name_text_view).text = hotelName
        view.findViewById<TextView>(R.id.hotel_price_text_view).text = "Price: $hotelPrice"
        view.findViewById<TextView>(R.id.hotel_availability_text_view).text = "Availability: $hotelAvailability"
        view.findViewById<TextView>(R.id.check_in_date_label).text = "Check-In Date: $checkInDate"
        view.findViewById<TextView>(R.id.check_out_date_label).text = "Check-Out Date: $checkOutDate"

        val container = view.findViewById<LinearLayout>(R.id.container)
        submitButton = view.findViewById(R.id.submit_button)

        for (i in 0 until numberOfGuests) {
            val editText = EditText(requireContext())
            editText.hint = "Guest ${i + 1} Name"
            container.addView(editText)

            val radioButtonMale = RadioButton(requireContext())
            radioButtonMale.text = "Male"
            container.addView(radioButtonMale)

            val radioButtonFemale = RadioButton(requireContext())
            radioButtonFemale.text = "Female"
            container.addView(radioButtonFemale)
        }

        submitButton.setOnClickListener {
            handleSubmit()
        }
    }

    private fun handleSubmit() {
        val container: LinearLayout = requireView().findViewById(R.id.container)
        val guestCount = container.childCount
        val iterations = Math.ceil(guestCount / 3.0).toInt()

        guestListRequestData.clear() // Clear existing data before adding new data

        for (i in 0 until iterations) {
            val guestName = (container.getChildAt(i * 3) as? EditText)?.text.toString()
            val radioButtonMale = container.getChildAt(i * 3 + 1) as? RadioButton
            val radioButtonFemale = container.getChildAt(i * 3 + 2) as? RadioButton

            val guestGender = when {
                radioButtonMale?.isChecked == true -> "Male"
                radioButtonFemale?.isChecked == true -> "Female"
                else -> ""
            }

            if (guestName.isNotEmpty() && guestGender.isNotEmpty()) {
                guestListRequestData.add(HotelGuestData(guestName, guestGender))
            }
        }

        if (guestListRequestData.isNotEmpty()) {
            makeReservation()
        } else {
            Toast.makeText(requireContext(), "Please enter guest details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun makeReservation() {
        val hotelName = arguments?.getString("hotelName")
        val checkInDate = arguments?.getString(ReservationInfo.CHECK_IN_DATE.value)
        val checkOutDate = arguments?.getString(ReservationInfo.CHECK_OUT_DATE.value)

        if (hotelName.isNullOrEmpty() || checkInDate.isNullOrEmpty() || checkOutDate.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Invalid hotel details", Toast.LENGTH_LONG).show()
            return
        }

        val request = HotelReservationRequest(hotelName!!, checkInDate!!, checkOutDate!!, guestListRequestData)

        Api.getClient().makeReservation(request).enqueue(object : Callback<HotelReservationResponse> {
            override fun onResponse(call: Call<HotelReservationResponse>, response: Response<HotelReservationResponse>) {
                if (response.isSuccessful) {
                    val reservationResponse = response.body()
                    if (reservationResponse != null) {
                        val confirmationNumber = reservationResponse.confirmationNumber
                        displayConfirmationFragment(confirmationNumber, hotelName)
                    } else {
                        Toast.makeText(requireContext(), "Failed to get reservation response", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to make reservation", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<HotelReservationResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Network error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun displayConfirmationFragment(confirmationNumber: String, hotelName: String?) {
        val bundle = Bundle().apply {
            putString("confirmationNumber", confirmationNumber)
            putString("hotelName", hotelName)
        }
        val confirmationFragment = ReservationConfirmationFragment().apply {
            arguments = bundle
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, confirmationFragment)
            .addToBackStack(null)
            .commit()

//        requireFragmentManager().beginTransaction()
//            .replace(R.id.fragment_container, confirmationFragment)
//            .addToBackStack(null)
//            .commit()
    }
}

*/

class HotelReservationDetailsFragment : Fragment() {

    private lateinit var submitButton: Button
    private lateinit var guestListRequestData: MutableList<HotelGuestData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hotel_guest_details_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        guestListRequestData = mutableListOf()

        val hotelName = arguments?.getString("hotelName")
        val hotelPrice = arguments?.getString("hotelPrice")
        val hotelAvailability = arguments?.getString("hotelAvailability")
        val numberOfGuests = arguments?.getString(ReservationInfo.NUMBER_OF_GUESTS.value)?.toInt() ?: 0
        val checkInDate = arguments?.getString(ReservationInfo.CHECK_IN_DATE.value)
        val checkOutDate = arguments?.getString(ReservationInfo.CHECK_OUT_DATE.value)

        view.findViewById<TextView>(R.id.hotel_name_text_view).text = hotelName
        view.findViewById<TextView>(R.id.hotel_price_text_view).text = "Price: $hotelPrice"
        view.findViewById<TextView>(R.id.hotel_availability_text_view).text = "Availability: $hotelAvailability"
        view.findViewById<TextView>(R.id.check_in_date_label).text = "Check-In Date: $checkInDate"
        view.findViewById<TextView>(R.id.check_out_date_label).text = "Check-Out Date: $checkOutDate"

        val container = view.findViewById<LinearLayout>(R.id.container)
        submitButton = view.findViewById(R.id.submit_button)

        for (i in 0 until numberOfGuests) {
            val editText = EditText(requireContext())
            editText.hint = "Guest ${i + 1} Name"
            container.addView(editText)

            val radioButtonMale = RadioButton(requireContext())
            radioButtonMale.text = "Male"
            container.addView(radioButtonMale)

            val radioButtonFemale = RadioButton(requireContext())
            radioButtonFemale.text = "Female"
            container.addView(radioButtonFemale)
        }

        submitButton.setOnClickListener {
            handleSubmit()
        }
    }

    private fun handleSubmit() {
        val container: LinearLayout = requireView().findViewById(R.id.container)
        val guestCount = container.childCount
        val iterations = Math.ceil(guestCount / 3.0).toInt()

        guestListRequestData.clear() // Clear existing data before adding new data

        for (i in 0 until iterations) {
            val guestName = (container.getChildAt(i * 3) as? EditText)?.text.toString()
            val radioButtonMale = container.getChildAt(i * 3 + 1) as? RadioButton
            val radioButtonFemale = container.getChildAt(i * 3 + 2) as? RadioButton

            val guestGender = when {
                radioButtonMale?.isChecked == true -> "Male"
                radioButtonFemale?.isChecked == true -> "Female"
                else -> ""
            }

            if (guestName.isNotEmpty() && guestGender.isNotEmpty()) {
                guestListRequestData.add(HotelGuestData(guestName, guestGender))
            }
        }

        if (guestListRequestData.isNotEmpty()) {
            makeReservation()
        } else {
            Toast.makeText(requireContext(), "Please enter guest details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun makeReservation() {
        // Mock implementation to simulate successful reservation
        val confirmationNumber = "ABC123"
        displayConfirmationFragment(confirmationNumber)
    }

    private fun displayConfirmationFragment(confirmationNumber: String) {
//        val fragment = ReservationConfirmationFragment().apply {
//            arguments = Bundle().apply {
//                putString("confirmationNumber", confirmationNumber)
//            }
//        }
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, fragment)
//            .addToBackStack(null)
//            .commit()

        val intent = Intent(requireContext(), ReservationConfirmationFragment::class.java).apply {
            putExtra("confirmationNumber", confirmationNumber)
        }
        startActivity(intent)
    }
}

