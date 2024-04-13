package com.example.hotel_reservation_system_project

class HotelReservationRequest(
    val hotel_name: String,
    val checkin: String,
    val checkout: String,
    val guest_list: List<HotelGuestData>
) {
}