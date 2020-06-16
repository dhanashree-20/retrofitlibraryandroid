package com.aress.retrofitlibrary.retrofitnetwork;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.aress.retrofitlibrary.utils.MissingRetrofitInitialization;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceBuilder {
    private static RetrofitServiceBuilder INSTANCE;
    private static Object InterfaceClass;
    private RetrofitServiceBuilder() {
    }

    //method for getting interface class and base url from demo app
    private <T> void setRetrofitBuilder(@NotNull Class<T> serviceType,@NotNull String Base_Url) {
        OkHttpClient.Builder okHttp = new OkHttpClient.Builder();
        Retrofit.Builder builder = (new Retrofit.Builder())
                .baseUrl(Base_Url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory((Converter.Factory) GsonConverterFactory.create())
                .client(okHttp.build());
        Retrofit retrofit = builder.build();
        InterfaceClass = retrofit.create(serviceType);
    }

    //method for sending instance of interface pass by user
    public static <T> T getInstance() throws MissingRetrofitInitialization {
        if(INSTANCE == null) {
            throw new MissingRetrofitInitialization("Please initialize retrofit instance");
        }
        return (T) InterfaceClass;
    }

    //method for getting interface and base url
    public static <T> void with(@NotNull Class<T> serviceType,@NotNull String Base_Url) {
        if(INSTANCE == null) {
            INSTANCE = new RetrofitServiceBuilder();
        }
        INSTANCE.setRetrofitBuilder(serviceType,Base_Url);
    }

    //when storage permission needed for file upload and download
    public static void askForPermission(String permission, Integer requestCode, Context context, Activity activity) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);

            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
        } else if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(context, "Permission was denied", Toast.LENGTH_SHORT).show();
        }
    }
}


