package com.example.hotel_reservation_system_project

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HotelGuestDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotel_guest_details_layout)

        // Retrieve data passed from previous activity
        val hotelName = intent.getStringExtra("hotelName")
        val hotelPrice = intent.getStringExtra("hotelPrice")
        val hotelAvailability = intent.getStringExtra("hotelAvailability")

        // Update TextViews with retrieved data
        findViewById<TextView>(R.id.hotel_name_text_view).text = hotelName
        findViewById<TextView>(R.id.hotel_price_text_view).text = "Price: $hotelPrice"
        findViewById<TextView>(R.id.hotel_availability_text_view).text = "Availability: $hotelAvailability"
    }
}
