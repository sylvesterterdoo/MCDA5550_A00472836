package com.example.hotel_reservation_system_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/*
class MainActivity : AppCompatActivity() {
    // TODO: after this initial step
    // add good UI
    // add validations, date, name
    // use toast where necessary (like error)
    // screen 3 and 4 main assignment to be done next
    // number of passenger recycler view, school view -> screen 3send to backend get confirmation number
    // fragment or shared preferences
    // screen 4 confirmation page
    // try to add image of the hotels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frame_layout, HotelSearchFragment())

        fragmentTransaction.commit()
    }
}

 */


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, HotelSearchFragment())
                .commit()
        }
    }
}
