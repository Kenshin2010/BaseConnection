package com.manroid.core.service;

/**
 * Created by manro on 11/01/2018.
 */

public interface IOnRequestListener {
    <T> void onResponse(T result);

    void onError(int statusCode);
}
