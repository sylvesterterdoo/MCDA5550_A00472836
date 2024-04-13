package com.example.hotel_reservation_system_project

import HotelsListFragment
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

    private var checkInDate: LocalDate? = null
    private var checkOutDate: LocalDate? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.hotel_search_layout, container, false)

        selectedCheckInDate = rootView.findViewById(R.id.check_in_date_edit_text)
        selectedCheckOutDate = rootView.findViewById(R.id.check_out_date_edit_text)
        guestCountEditText = rootView.findViewById(R.id.num_guests_edit_text)

        setupDatePickers()

        rootView.findViewById<Button>(R.id.search_button).setOnClickListener {
            handleSubmit()
        }

        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupDatePickers() {
        selectedCheckInDate.setOnClickListener {
            getDateFromCalendar(selectedCheckInDate)
        }

        selectedCheckOutDate.setOnClickListener {
            getDateFromCalendar(selectedCheckOutDate)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDateFromCalendar(datePicker: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            { _, currentYear, monthOfYear, dayOfMonth ->
                datePicker.text = "$dayOfMonth-${monthOfYear + 1}-$currentYear"
                if (datePicker == selectedCheckInDate) {
                    checkInDate = LocalDate.of(currentYear, monthOfYear + 1, dayOfMonth)
                } else {
                    checkOutDate = LocalDate.of(currentYear, monthOfYear + 1, dayOfMonth)
                }
            },
            year,
            month,
            day
        ).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleSubmit() {
        val guestNameEditText = rootView.findViewById<EditText>(R.id.guest_name_edit_text)
        val numberOfGuestsEditText = guestCountEditText

        val guestName = guestNameEditText.text.toString()
        val numberOfGuests = numberOfGuestsEditText.text.toString()

        // Check for empty guest name or number of guests
        if (guestName.isEmpty()) {
            guestNameEditText.error = "Guest name cannot be empty"
            return
        }

        if (numberOfGuests.isEmpty()) {
            numberOfGuestsEditText.error = "Number of guests cannot be empty"
            return
        }

        // Convert number of guests to integer and validate
        val numGuests = numberOfGuests.toIntOrNull()
        if (numGuests == null || numGuests <= 0) {
            numberOfGuestsEditText.error = "Please enter a valid number of guests"
            return
        }

        // Check for valid dates
        if (checkInDate == null ) {
            selectedCheckInDate.error = "Please enter a valid Check in date"
            return
        }

        if (checkOutDate == null ) {
            selectedCheckOutDate.error = "Please enter a valid Check out date"
            return
        }

        if (checkInDate!!.isAfter(checkOutDate)) {
            Toast.makeText(requireContext(), "Check-in date should be before check-out date", Toast.LENGTH_SHORT).show()
            return
        }


        // Proceed if all validations pass
        val bundle = Bundle().apply {
            putString(ReservationInfo.GUEST_NAME.value, guestName)
            putString(ReservationInfo.CHECK_IN_DATE.value, selectedCheckInDate.text.toString())
            putString(ReservationInfo.CHECK_OUT_DATE.value, selectedCheckOutDate.text.toString())
            putString(ReservationInfo.NUMBER_OF_GUESTS.value, numberOfGuests)
        }

        val hotelsListFragment = HotelsListFragment().apply {
            arguments = bundle
        }

        requireFragmentManager().beginTransaction()
            .replace(R.id.frame_layout, hotelsListFragment)
            .addToBackStack(null)
            .commit()
    }

    /*
    private fun handleSubmit() {
        val guestName = rootView.findViewById<EditText>(R.id.guest_name_edit_text).text.toString()
        val numberOfGuests = guestCountEditText.text.toString()

        if (guestName.isEmpty() || numberOfGuests.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter guest details", Toast.LENGTH_SHORT).show()
            return
        }

        if (checkInDate == null || checkOutDate == null || checkInDate!!.isAfter(checkOutDate)) {
            Toast.makeText(requireContext(), "In dates should come before out date", Toast.LENGTH_SHORT).show()
            return
        }

        val bundle = Bundle().apply {
            putString(ReservationInfo.GUEST_NAME.value, guestName)
            putString(ReservationInfo.CHECK_IN_DATE.value, selectedCheckInDate.text.toString())
            putString(ReservationInfo.CHECK_OUT_DATE.value, selectedCheckOutDate.text.toString())
            putString(ReservationInfo.NUMBER_OF_GUESTS.value, numberOfGuests)
        }

        val hotelsListFragment = HotelsListFragment().apply {
            arguments = bundle
        }

        requireFragmentManager().beginTransaction()
            .replace(R.id.frame_layout, hotelsListFragment)
            .addToBackStack(null)
            .commit()
    }

     */
}
