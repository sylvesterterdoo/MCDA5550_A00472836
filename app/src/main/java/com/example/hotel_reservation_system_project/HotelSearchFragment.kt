package com.example.hotel_reservation_system_project

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import java.time.LocalDate
import java.util.*

class HotelSearchFragment : Fragment() {
    private lateinit var rootView: View
    private lateinit var selectedCheckInDate: TextView
    private lateinit var selectedCheckOutDate: TextView
    private lateinit var guestCountEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var numberOfGuests: String
    private lateinit var nameEditText: EditText
    var checkInDate: LocalDate? = null
    var checkOutDate: LocalDate? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.hotel_search_layout, container, false)

        nameEditText = rootView.findViewById(R.id.guest_name_edit_text)
        selectedCheckInDate = rootView.findViewById(R.id.check_in_date_edit_text)
        selectedCheckOutDate = rootView.findViewById(R.id.check_out_date_edit_text)
        guestCountEditText = rootView.findViewById(R.id.num_guests_edit_text)
        searchButton = rootView.findViewById(R.id.search_button)

        selectedCheckInDate.apply {
            isFocusable = false
            isClickable = true
            isCursorVisible = false
        }

        selectedCheckOutDate.apply {
            isFocusable = false
            isClickable = true
            isCursorVisible = false
        }

        selectedCheckInDate.setOnClickListener {
            checkInDate = getDateFromCalendar(selectedCheckInDate)
        }

        selectedCheckOutDate.setOnClickListener {
            checkOutDate = getDateFromCalendar(selectedCheckOutDate )
        }


        searchButton.setOnClickListener {
            // TODO:
            // check that all the fields are inputted
            // check the check in is before check out

            val guestName = nameEditText.text.toString()
            val guestsCount = guestCountEditText.text.toString()
            val checkInStr = selectedCheckInDate.text.toString()
            val checkOutStr = selectedCheckOutDate.text.toString()

            if (guestName.isEmpty() || guestsCount.isEmpty()) {
                val temp = "Check-out date should be after Check-in date"
                Toast.makeText(activity, temp, Toast.LENGTH_LONG).show()
            }
            if ((checkInDate != null) && (checkOutDate != null)) {
                assertDatesIsValid(checkInDate!!, checkOutDate!!)
            }

            numberOfGuests = guestCountEditText.text.toString()

//            val bundle = Bundle().apply {
//                putString("check in date", checkInStr)
//                putString("check out date", checkOutStr)
//                putString("numbers of guests", numberOfGuests)
//            }

            val bundle = Bundle().apply {
                putString(ReservationInfo.CHECK_IN_DATE.value, selectedCheckInDate.text.toString())
                putString(ReservationInfo.CHECK_OUT_DATE.value, selectedCheckOutDate.text.toString())
                putString(ReservationInfo.NUMBER_OF_GUESTS.value, selectedCheckOutDate.toString())
            }

            println("checkIn: $checkInStr, checkOut: $checkOutStr, guest count: $numberOfGuests")
            val hotelsListFragment = HotelsListFragment().apply {
                arguments = bundle
            }

            val fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction.replace(R.id.main_layout, hotelsListFragment)
            fragmentTransaction.remove(this@HotelSearchFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }



        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun assertDatesIsValid(checkInDate: LocalDate, checkOutDate: LocalDate) {
        if (checkInDate.isAfter(checkOutDate)) {
            val temp = "Check-out date should be after Check-in date"
            Toast.makeText(activity, temp, Toast.LENGTH_LONG).show()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDateFromCalendar(datePicker: TextView): LocalDate {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        var localDate: LocalDate = LocalDate.now()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, currentYear, monthOfYear, dayOfMonth ->
                datePicker.text = "$dayOfMonth-${monthOfYear + 1}-$currentYear"
                localDate = LocalDate.of(currentYear, monthOfYear + 1, dayOfMonth)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
        return localDate
    }
}
