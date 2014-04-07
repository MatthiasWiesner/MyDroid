package de.mwiesner.mydroid.calendar;

import de.mwiesner.mydroid.calendar.model.Weeks;
import retrofit.http.GET;
import retrofit.http.Query;


public interface CalendarBackendInterface {
    @GET("/calendar/load")
    Weeks weeks(
            @Query("init_date") String initDate,
            @Query("fetch_type") String fetchType
    );
}