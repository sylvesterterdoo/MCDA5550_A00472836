package com.example.hotel_reservation_system_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Array.newInstance
import javax.xml.datatype.DatatypeFactory.newInstance

/*
class HotelGuestDetails : AppCompatActivity() {

    lateinit var submitButton: Button
    lateinit var hotelReservationRequest: HotelReservationRequest
    lateinit var guestListRequestData: MutableList<HotelGuestData>

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
            var guestName: String = ""
            var guestGender: String = ""
            // Example: Retrieve guest name from EditText and gender from RadioButton
            if (childView is EditText) {
                guestName = childView.text.toString()
//                println("Guest ${i} name: ${guestName}")
                // Perform service call with guestName and gender
                // Example: callService(guestName, gender)
            }
            if (childView is RadioButton && childView.isChecked) {
                // Handle other view types (e.g., RadioButton) as needed
                guestGender = childView.text.toString()
//                println("Guest ${i} gender: ${guestGender}")
            }
            if (guestName.isNotEmpty() && guestGender.isNotEmpty()) {
                val hotelGuest = HotelGuestData(guestName, guestGender)
                guestListRequestData.add(hotelGuest)

            }
        }
        println(guestListRequestData)

        // Show a toast or perform other action to indicate successful submission
        Toast.makeText(this, "Guest details submitted", Toast.LENGTH_SHORT).show()
        makeReservation()
    }

    private fun makeReservation() {
        val hotelName = intent.getStringExtra("hotelName")
        val checkInDate = intent.getStringExtra("checkInDate")
        val checkOutDate = intent.getStringExtra("checkOutDate")

        // Create sample data for HotelReservationRequest
        val guestList = listOf(
            HotelGuestData("John Doe", "Male"),
            HotelGuestData("Jane Smith", "Female")
        )
        val request = HotelReservationRequest("Hotel ABC", "2024-07-01", "2024-07-03", guestList)

        // Make the reservation request
//        progressBar.visibility = View.VISIBLE
        Api.getClient().makeReservation(request).enqueue(object : Callback<HotelReservationResponse> {
            override fun onResponse(call: Call<HotelReservationResponse>, response: Response<HotelReservationResponse>) {
                if (response.isSuccessful) {
                    val reservationResponse = response.body()
                    if (reservationResponse != null) {
                        // Handle successful reservation response
                        val confirmationNumber = reservationResponse.confirmationNumber
                        val hotelName = reservationResponse.hotelName
                        Toast.makeText(applicationContext, "Reservation confirmed: Confirmation Number $confirmationNumber, Hotel Name: $hotelName", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, "Failed to get reservation response", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Failed to make reservation", Toast.LENGTH_LONG).show()
                }
//                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<HotelReservationResponse>, t: Throwable) {
                println(t.message)
                Toast.makeText(applicationContext, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
//                progressBar.visibility = View.GONE
            }
        })
    }
}
 */


class HotelGuestDetails : AppCompatActivity() {

    private lateinit var submitButton: Button
    private lateinit var guestListRequestData: MutableList<HotelGuestData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotel_guest_details_layout)

        guestListRequestData = mutableListOf() // Initialize the guest list

        val hotelName = intent.getStringExtra("hotelName")
        val hotelPrice = intent.getStringExtra("hotelPrice")
        val hotelAvailability = intent.getStringExtra("hotelAvailability")
        val numberOfGuests = intent.getStringExtra("numberOfGuests")?.toInt() ?: 0

        val container = findViewById<LinearLayout>(R.id.container)
        submitButton = findViewById(R.id.submit_button)
        findViewById<TextView>(R.id.hotel_name_text_view).text = hotelName
        findViewById<TextView>(R.id.hotel_price_text_view).text = "Price: $hotelPrice"
        findViewById<TextView>(R.id.hotel_availability_text_view).text = "Availability: $hotelAvailability"

        for (i in 0 until numberOfGuests) {
            val editText = EditText(this)
            editText.hint = "Guest ${i + 1} Name"
            container.addView(editText)

            val radioButtonMale = RadioButton(this)
            radioButtonMale.text = "Male"
            container.addView(radioButtonMale)

            val radioButtonFemale = RadioButton(this)
            radioButtonFemale.text = "Female"
            container.addView(radioButtonFemale)
        }

        submitButton.setOnClickListener {
            handleSubmit()
        }
    }

    private fun handleSubmit() {
        val container: LinearLayout = findViewById(R.id.container)
        val guestCount = container.childCount
        val iterations = Math.ceil(guestCount / 2.0).toInt()


        val hotelGuest = HotelGuestData("", "")
        var guestName: String = ""
        var guestGender: String = ""

        for (i in 0 until iterations) {
            val childView = container.getChildAt(i)

            if (childView is EditText) {
                guestName = childView.text.toString()
            }
            if (childView is RadioButton && childView.isChecked) {
                guestGender = childView.text.toString()
                if (guestName.isNotEmpty() || guestGender.isNotEmpty()) {
                    val hotelGuest = HotelGuestData(guestName, guestGender)
                    guestListRequestData.add(hotelGuest)
                }
                guestName = ""
                guestGender = ""
            }

        }

        if (guestListRequestData.isNotEmpty()) {
            makeReservation() // Call API to make reservation
        } else {
            Toast.makeText(this, "Please enter guest details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun makeReservation() {
        val hotelName = intent.getStringExtra("hotelName")
        val checkInDate = intent.getStringExtra("checkInDate")
        val checkOutDate = intent.getStringExtra("checkOutDate")

        if (hotelName.isNullOrEmpty() || checkInDate.isNullOrEmpty() || checkOutDate.isNullOrEmpty()) {
            Toast.makeText(applicationContext, "Invalid hotel details", Toast.LENGTH_LONG).show()
            return
        }

        val request = HotelReservationRequest(hotelName, checkInDate, checkOutDate, guestListRequestData)

        Api.getClient().makeReservation(request).enqueue(object : Callback<HotelReservationResponse> {
            override fun onResponse(call: Call<HotelReservationResponse>, response: Response<HotelReservationResponse>) {
                if (response.isSuccessful) {
                    val reservationResponse = response.body()
                    if (reservationResponse != null) {
                        val confirmationNumber = reservationResponse.confirmationNumber
                        val hotelName = reservationResponse.hotelName


                        val intent = Intent(this@HotelGuestDetails, ReservationConfirmation::class.java)
                        intent.putExtra("confirmationNumber", confirmationNumber)
                        startActivity(intent)
                        finish()
                        // Finish the current activity to prevent going back to it
//                        Toast.makeText(applicationContext, "Reservation confirmed: Confirmation Number $confirmationNumber, Hotel Name: $hotelName", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, "Failed to get reservation response", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Failed to make reservation", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<HotelReservationResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showConfirmationFragment(confirmationNumber: String) {
        // Launch ConfirmationActivity with the confirmation number

        // Finish the current activity to prevent going back to it
        finish()
    }

/*
    private fun showConfirmationFragment(confirmationNumber: String) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        val confirmationFragment = ReservationConfirmationFragment()
        confirmationFragment.displayConfirmationNumber(confirmationNumber)

        transaction.replace(android.R.id.content, confirmationFragment)
        transaction.addToBackStack(null) // Optional: add to back stack if needed
        transaction.commit()
    }
 */
//        val fragmentManager = supportFragmentManager
//        val transaction = fragmentManager.beginTransaction()
//
//        // Create a new instance of the confirmation fragment and pass the confirmation number
//        val confirmationFragment = ReservationConfirmationFragment.newInstance(confirmationNumber)
//
//        // Replace the entire activity content with the confirmation fragment
//        transaction.replace(R.id.fragment_container, confirmationFragment)
//        transaction.addToBackStack(null) // Optional: add to back stack if needed
//        transaction.commit()
}

