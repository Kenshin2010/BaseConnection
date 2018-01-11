package com.manroid.baseconnection.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.manroid.baseconnection.R;
import com.manroid.baseconnection.api.ApiManager;
import com.manroid.baseconnection.model.AlbumResult;
import com.manroid.core.service.IOnRequestListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void getVolley(){
        ApiManager.getInstance().getAlbum(AlbumResult.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                AlbumResult response = AlbumResult.class.cast(result);
                if (response != null) {
                    //get data in here
                }
            }

            @Override
            public void onError(int statusCode) {

            }
        });
    }
}
