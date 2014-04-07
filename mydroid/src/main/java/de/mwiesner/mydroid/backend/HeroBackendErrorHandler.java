package de.mwiesner.mydroid.backend;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;


class HeroBackendErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
        System.out.println(r.getHeaders());
        System.out.println(r.getBody().toString());
        if (r != null && r.getStatus() == 401) {
            new Exception("UnauthorizedException");
        }
        return cause;
    }
}
