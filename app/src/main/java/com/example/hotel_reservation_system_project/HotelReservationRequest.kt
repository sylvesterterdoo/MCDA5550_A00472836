package com.example.hotel_reservation_system_project

class HotelReservationRequest(
    var hotelName: String,
    var checkInDate: String,
    var checkOutDate: String,
    var guestList: List<HotelGuestData>
) {
}