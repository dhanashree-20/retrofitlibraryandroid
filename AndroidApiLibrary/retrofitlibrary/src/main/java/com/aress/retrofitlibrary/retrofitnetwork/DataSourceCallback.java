package com.aress.retrofitlibrary.retrofitnetwork;

public interface DataSourceCallback<T> {
    void onAPIFetched(T data);
}
