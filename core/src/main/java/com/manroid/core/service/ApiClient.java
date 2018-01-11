package com.manroid.core.service;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.manroid.core.constant.Constant;
import com.manroid.core.data.model.RequestInfor;
import com.manroid.core.service.volley.VolleyMultipartRequest;
import com.manroid.core.service.volley.VolleySingleton;
import com.manroid.core.util.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Mr.Sen on 10/23/2017.
 */

public class ApiClient {
    private static ApiClient instance;

    private ApiClient() {

    }

    public static ApiClient getInstance() {
        return instance == null ? new ApiClient() : instance;
    }

    public <T> void post(String url, final Class<T> clazz, final Map<String, String> headers, final String requestBody, final IOnRequestListener requestListener) {
        // Todo delete when release
        final RequestInfor requestInfor = new RequestInfor(url, headers, requestBody);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (requestListener != null)
                requestListener.onError(parseError(error));
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                // Todo delete when release
                requestInfor.setResponse(new String(response.data));
                RequestHandler.getInstance().addRequest(requestInfor);
                return parseResponse(response);
            }
        };

//        StringRequest request = new StringRequest(Request.Method.POST, url, (String response) -> {
//            Gson gson = new Gson();
//            T result = gson.fromJson(response, clazz);
//            if (requestListener != null)
//                requestListener.onResponse(result);
//        }, (VolleyError error) -> {
//            if (requestListener != null)
//                requestListener.onError(parseError(error));
//        }) {
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    return requestBody == null ? null : requestBody.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//                    return null;
//                }
//            }
//
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                // Todo delete when release
//                requestInfor.setResponse(new String(response.data));
//                RequestHandler.getInstance().addRequest(requestInfor);
//                return parseResponse(response);
//            }
//
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                return headers != null ? headers : super.getHeaders();
//            }
//        };
        request.setRetryPolicy(new DefaultRetryPolicy(Constant.REQUEST_TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().addToRequestQueue(request);
    }

    public <T> void get(String url, final Class<T> clazz, final Map<String, String> headers, final IOnRequestListener requestListener) {
        // Todo delete when release
        RequestInfor requestInfor = new RequestInfor(url, headers, null);
        final StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(Constant.REQUEST_TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().addToRequestQueue(request);
    }

//    public <T> void postMultiPart(String url, Map<String, String> headers, final Map<String, String> params, final Map<String, VolleyMultipartRequest.DataPart> datas, final Class<T> clazz, final IOnRequestListener listener) {
//        // Todo delete when release
//        RequestInfor requestInfor = new RequestInfor(url, headers, null);
//        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(url, headers, (NetworkResponse response) -> {
//            String resultResponse = new String(response.data);
//            Gson gson = new Gson();
//            T result = gson.fromJson(resultResponse, clazz);
//            if (listener != null)
//                listener.onResponse(result);
//        }, (VolleyError error) -> {
//            Logger.e(error.getMessage());
//            NetworkResponse networkResponse = error.networkResponse;
//            String errorMessage = "Unknown error";
//            if (networkResponse == null) {
//                if (error.getClass().equals(TimeoutError.class)) {
//                    errorMessage = "Request timeout";
//                } else if (error.getClass().equals(NoConnectionError.class)) {
//                    errorMessage = "Failed to connect server";
//                }
//            } else {
//                String result = new String(networkResponse.data);
//                try {
//                    JSONObject response = new JSONObject(result);
//                    String status = response.getString("status");
//                    String message = response.getString("message");
//
//                    Log.e("Error Status", status);
//                    Log.e("Error Message", message);
//
//                    if (networkResponse.statusCode == 404) {
//                        errorMessage = "Resource not found";
//                    } else if (networkResponse.statusCode == 401) {
//                        errorMessage = message + " Please login again";
//                    } else if (networkResponse.statusCode == 400) {
//                        errorMessage = message + " Check your inputs";
//                    } else if (networkResponse.statusCode == 500) {
//                        errorMessage = message + " Something is getting wrong";
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            Log.i("Error", errorMessage);
//            if (listener != null)
//                listener.onError(parseError(error));
//            error.printStackTrace();
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                return params;
//            }
//
//            @Override
//            protected Map<String, DataPart> getByteData() {
//                return datas;
//            }
//
//            @Override
//            protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
//                // Todo delete when release
//                requestInfor.setResponse(new String(response.data));
//                RequestHandler.getInstance().addRequest(requestInfor);
//                int statusCode = response.statusCode;
//                if (Constant.STATUS_CODE_SUCCESS == statusCode)
//                    return super.parseNetworkResponse(response);
//                VolleyError error = new VolleyError(response);
//                return Response.error(error);
//            }
//        };
//        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(Constant.REQUEST_TIME_OUT,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        VolleySingleton.getInstance().addToRequestQueue(multipartRequest);
//    }
//
//    public <T> void request(String url, int requestType, final Class<T> clazz, final Map<String, String> headers, final String requestBody, final IOnRequestListener requestListener) {
//        RequestInfor requestInfor = new RequestInfor(url, headers, requestBody);
//        StringRequest request = new StringRequest(requestType, url, (String response) -> {
//            Gson gson = new Gson();
//            T result = gson.fromJson(response, clazz);
//            if (requestListener != null)
//                requestListener.onResponse(result);
//        }, (VolleyError error) -> {
//            if (requestListener != null)
//                requestListener.onError(parseError(error));
//        }) {
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    return requestBody == null ? null : requestBody.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//                    return null;
//                }
//            }
//
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                return headers != null ? headers : super.getHeaders();
//            }
//
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                // Todo delete when release
//                requestInfor.setResponse(new String(response.data));
//                RequestHandler.getInstance().addRequest(requestInfor);
//                return parseResponse(response);
//            }
//        };
//        request.setRetryPolicy(new DefaultRetryPolicy(Constant.REQUEST_TIME_OUT,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        VolleySingleton.getInstance().addToRequestQueue(request);
//    }

    public Response<String> parseResponse(NetworkResponse response) {
        int statusCode = response.statusCode;
        if (Constant.STATUS_CODE_SUCCESS == statusCode) {
            String parsed;
            try {
                String charset = HttpHeaderParser.parseCharset(response.headers);
                parsed = new String(response.data, charset);
            } catch (UnsupportedEncodingException e) {
                parsed = new String(response.data);
            }
            return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
        }
        VolleyError error = new VolleyError(response);
        return Response.error(error);
    }

    public int parseError(VolleyError error) {
        if (error == null || error.networkResponse == null)
            return -1;
        NetworkResponse response = error.networkResponse;
        return response.statusCode;
    }
}
