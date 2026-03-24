## Names and CWID
- Ju Yeong Kim (885745315)
- Kshitji Pingle (885626978)
- Mariia Nikitash (885919944)

## Overview of the project

- This project is an event ticketing system backend that allows users to choose between being an organizer or an attendee. An organizer is able to create and manage events, while an attendee is able to book tickets to events they chose. The system is able to track bookings made by attendees, along with details such as payment status and other information.

## Screenshots

1. POST /api/organizers (Create a new organizer)
<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/7c715bf6-6bf9-48df-a2a5-10305da3ecac" />

2. POST /api/venues (Create a new venue)
<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/97a93a6b-a6ea-412f-908d-7f28611f440e" />

3. POST /api/events (Create a new event)
``` {
  "title": "Bobby's Epic Concert",
  "description": "Something exciting?",
  "event_date": "2026-04-10T20:00:00",
  "status": "UPCOMING",
  "organizer_id": 9,
  "venue_id": 10,
  "ticketTypes": [
    {
      "name": "VIP",
      "price": 200,
      "quantity_available": 10
    },
    {
      "name": "General",
      "price": 100,
      "quantity_available": 20
    },
    {
      "name": "Student",
      "price": 60,
      "quantity_available": 10
    }
  ]
}
```
<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/8e61775c-66db-463c-9f24-eabb0d5f585d" />

4. GET /api/events (List all upcoming events)
<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/0d2e68d3-a676-4e55-9bbb-8d0b2d2900da" />

5. GET /api/events/{id} (Get event details with ticket types)
``` http://localhost:8080/api/events/13 ```
<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/da0ffc8a-5b01-42a9-bc88-8a482f10c4f5" />

7. POST /api/attendees (Register a new attendee)
<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/ecb1c43d-ec88-4b74-b287-cc86398bdf70" />

9. POST /api/bookings (Book a ticket)
<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/cceae3fa-11a6-4c98-b415-b6d9ea0b0a1e" />

10. PUT /api/bookings/{id}/cancel (Cancel a booking)
<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/cf830fe4-0208-4b5f-acb5-2e0f6880d962" />

11. GET /api/events/{id}/revenue (Get a total revenue for an event)
<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/8bc7bc98-9119-47de-93d2-3c8735cac5f6" />

12. GET /api/attendees/{id}/bookings (Get all bookings for an attendee)
<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/cd8c6888-4355-4e2c-957d-219269f91ca6" />


## Demo Video
https://youtu.be/NomDinOYjy8
