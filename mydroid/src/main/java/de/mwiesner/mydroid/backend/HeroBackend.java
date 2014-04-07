package de.mwiesner.mydroid.backend;

import retrofit.RestAdapter;


public class HeroBackend {
    private static final String URL = "https://hero.cloudcontrolled.com";

    public static RestAdapter getAdapter(){
        return new RestAdapter.Builder()
            .setErrorHandler(new HeroBackendErrorHandler())
            .setEndpoint(HeroBackend.URL).build();
    }
}