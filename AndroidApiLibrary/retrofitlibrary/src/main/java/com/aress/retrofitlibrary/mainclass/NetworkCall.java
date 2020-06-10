package com.aress.retrofitlibrary.mainclass;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.aress.retrofitlibrary.retrofitnetwork.DataSourceCallback;
import com.aress.retrofitlibrary.utils.RXJavaHelper;
import com.aress.retrofitlibrary.retrofitnetwork.Resource;
import com.aress.retrofitlibrary.utils.Constants;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

public class NetworkCall<T> implements Constants {
    private static final String TAG = NetworkCall.class.getSimpleName();
    Single<T> single;

    //when no need of callback then call this method
    public MutableLiveData<Resource<T>> apiCall(Single<T> single) {
        return apiCall(single, null, new MutableLiveData());
    }

    //when need of callback
    public MutableLiveData<Resource<T>> apiCall(Single<T> single, DataSourceCallback callback) {
        return apiCall(single, callback, new MutableLiveData());
    }

    public MutableLiveData<Resource<T>> apiCall(Single<T> single, final DataSourceCallback callback, final MutableLiveData<Resource<T>> result) {
        this.single = single;
        result.postValue((Resource<T>) Resource.loading(null));
        //fetch api
        RXJavaHelper.getInstance().dispose(single, new DisposableSingleObserver<T>() {
            @Override
            public void onSuccess(T value) {
                if (value != null) {
                    //when callback is by default null
                    result.postValue(Resource.success(value));
                    Log.d("value != null", value.toString());
                    if (callback != null) {
                        callback.onAPIFetched(value);
                        result.postValue(Resource.success(value));
                        Log.d("RespInNetworkCall", value.toString());
                    }
                } else {
                    result.postValue(Resource.<T>error(API_ERROR));
                }
            }

            @Override
            public void onError(Throwable e) {
                result.postValue(Resource.<T>error(e.getLocalizedMessage()));
                Log.d("APIERROR", "ERROR");
                e.printStackTrace();
            }
        });
        return result;
    }
}
