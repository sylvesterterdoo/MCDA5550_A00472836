package com.example.hotel_reservation_system_project

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/*
class ReservationConfirmationFragment  : Fragment() {

    private lateinit var confirmationNumberTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.hotel_reservation_confirmation_fragment, container, false)
        confirmationNumberTextView = view.findViewById(R.id.confirmation_number_text_view)
        return view
    }

    fun displayConfirmationNumber(confirmationNumber: String) {
        confirmationNumberTextView.text = "Confirmation Number: $confirmationNumber"
    }
}
*

 */



class ReservationConfirmation : AppCompatActivity() {

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
            confirmationNumberTextView.text = "Confirmation Number: $confirmationNumber"
        } else {
            confirmationNumberTextView.text = "No Confirmation Number"
        }
    }
}
