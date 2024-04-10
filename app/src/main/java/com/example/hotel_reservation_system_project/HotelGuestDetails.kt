package com.example.hotel_reservation_system_project

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class HotelGuestDetails : AppCompatActivity() {

    lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotel_guest_details_layout)

        // Retrieve data passed from previous activity
        val hotelName = intent.getStringExtra("hotelName")
        val hotelPrice = intent.getStringExtra("hotelPrice")
        val hotelAvailability = intent.getStringExtra("hotelAvailability")
        val numberOfGuests = intent.getStringExtra("numberOfGuests")?.toInt() ?: 0

        // Update TextViews with retrieved data
        val container = findViewById<LinearLayout>(R.id.container)
        submitButton = findViewById(R.id.submit_button)
        findViewById<TextView>(R.id.hotel_name_text_view).text = hotelName
        findViewById<TextView>(R.id.hotel_price_text_view).text = "Price: $hotelPrice"
        findViewById<TextView>(R.id.hotel_availability_text_view).text = "Availability: $hotelAvailability"

        // validate all guest details are entered
        for (i in 0 until numberOfGuests) {
            // Create EditText for guest name
            val editText = EditText(this)
            editText.hint = "Guest ${i + 1} Name"
            container.addView(editText)

            // Create RadioButton for guest gender
            val radioButtonMale = RadioButton(this)
            radioButtonMale.text = "Male"
            container.addView(radioButtonMale)

            val radioButtonFemale = RadioButton(this)
            radioButtonFemale.text = "Female"
            container.addView(radioButtonFemale)
        }

        submitButton.setOnClickListener{
            handleSubmit()
        }
    }

    private fun handleSubmit() {
        // Retrieve guest details from dynamic views and perform service call
        val container: LinearLayout = findViewById(R.id.container)
        val guestCount = container.childCount

        // Iterate through the dynamic views to retrieve guest information
        // add all the details to a list and make the service call

        for (i in 0 until guestCount) {
            val childView = container.getChildAt(i)

            // Example: Retrieve guest name from EditText and gender from RadioButton
            if (childView is EditText) {
                val guestName = childView.text.toString()
                // Perform service call with guestName and gender
                // Example: callService(guestName, gender)
            }
            // Handle other view types (e.g., RadioButton) as needed
        }

        // Show a toast or perform other action to indicate successful submission
        Toast.makeText(this, "Guest details submitted", Toast.LENGTH_SHORT).show()
    }
}