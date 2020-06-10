package com.aress.retrofitlibrary.retrofitnetwork;

import com.aress.retrofitlibrary.utils.Constants;

public class Resource<T> implements Constants {
    public String status, apiError;
    public T data;

    Resource(String status, T data, String apiError) {
        this.status = status;
        this.data = data;
        this.apiError = apiError;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource(SUCCESS, data, null);
    }

    public static <T> Resource<T> loading(T data) {
        return new Resource(LOADING, null, null);
    }

    public static <T> Resource<T> error(String apiError) {
        return new Resource(ERROR, null, apiError);
    }
}
