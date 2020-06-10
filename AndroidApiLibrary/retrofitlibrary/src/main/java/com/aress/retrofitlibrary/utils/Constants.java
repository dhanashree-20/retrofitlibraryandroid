package com.aress.retrofitlibrary.utils;

import java.util.HashMap;
import java.util.Map;

public interface Constants {
    //API fetch status
    String SUCCESS = "SUCCESS";
    String LOADING = "LOADING";
    String ERROR = "ERROR";
    //error message
    String API_ERROR = "An unexpected error has been occurred.";
    //get headers and body from user
    Map<String, String> headers = new HashMap<>();
    HashMap<String,String> body = new HashMap<>();
}
