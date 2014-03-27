package de.mwiesner.mydroid.tasks.backend;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.mwiesner.mydroid.model.Weeks;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.RestAdapter;
import retrofit.http.Query;

class MyErrorHandler implements ErrorHandler {
    @Override public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
        System.out.println(r.getHeaders());
        System.out.println(r.getBody().toString());
        if (r != null && r.getStatus() == 401) {
            new Exception("UnauthorizedException");
        }
        return cause;
    }
}

public class HeroBackend {
    private static final String URL = "https://hero.cloudcontrolled.com";

    interface LoadCalendarInterface {
        @GET("/calendar/load")
        Weeks weeks(
            @Query("init_date") String initDate,
            @Query("fetch_type") String fetchType
        );
    }

    public static Weeks loadCalendar(Date initDate) {
        RestAdapter restAdapter = new RestAdapter.Builder()
            .setErrorHandler(new MyErrorHandler())
            .setEndpoint(HeroBackend.URL).build();
        LoadCalendarInterface loadCalendarInterface = restAdapter.create(LoadCalendarInterface.class);

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String currDate = formatter.format(initDate);

        return loadCalendarInterface.weeks(currDate, "next");
    }
}
