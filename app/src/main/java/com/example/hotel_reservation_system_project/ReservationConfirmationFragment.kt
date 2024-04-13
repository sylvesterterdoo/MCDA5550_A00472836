package com.example.hotel_reservation_system_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class ReservationConfirmationFragment : AppCompatActivity() {

    private lateinit var confirmationNumberTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotel_reservation_confirmation)

        // Find the TextView in the activity layout
        confirmationNumberTextView = findViewById(R.id.confirmation_number_text_view)

        // Retrieve the confirmation number from intent
        val confirmationNumber = intent.getStringExtra("confirmationNumber")

        // Display the confirmation number in the TextView
        if (confirmationNumber != null) {
            confirmationNumberTextView.text = "Thank you for your reservation. Your Confirmation Number: $confirmationNumber"
        } else {
            confirmationNumberTextView.text = "No Confirmation Number"
        }
    }
}