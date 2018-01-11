package com.manroid.baseconnection.api;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.manroid.baseconnection.util.VConstant;
import com.manroid.core.service.ApiClient;
import com.manroid.core.service.IOnRequestListener;
import com.manroid.core.service.volley.VolleySingleton;
import com.manroid.core.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.Sen on 10/19/2017.
 */

public class ApiManager {
    public static ApiManager instance;
    private Context mContext;

    private ApiManager() {

    }

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

//    public void getTimeline(int page, IOnRequestListener listener) {
//        Map<String, String> params = new HashMap<>();
//        params.put(NSConstant.REQUEST_KEY_OFFSET, String.valueOf(NSConstant.NUMBER_ITEM_PAGE));
//        params.put(NSConstant.REQUEST_KEY_PAGE, String.valueOf(page));
//        String url = getUrl(NSConstant.PORT_8083, NSConstant.TIME_LINE_URL);
//        url = appendParamsRequest(url, params);
//        ApiClient.getInstance().get(url, TimeLineResult.class, getHeader(), listener);
//    }
//
//    public void uploadImage(Bitmap bitmap, String infor, IOnRequestListener listener, String type) {
//        Map<String, String> params = new HashMap<>();
//        params.put(NSConstant.REQUEST_KEY_UPLOAD_INFOR, infor);
//        if (StringUtil.isNullOrEmpty(type))
//            params.put(NSConstant.REQUEST_KEY_TYPE, NSConstant.TYPE_UPLOAD_DEFAULT);
//        else
//            params.put(NSConstant.REQUEST_KEY_TYPE, type);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
//        Map<String, VolleyMultipartRequest.DataPart> datas = new HashMap<>();
//        VolleyMultipartRequest.DataPart dataPart = new VolleyMultipartRequest.DataPart("images.jpg", byteArrayOutputStream.toByteArray(), "image/jpeg");
//        datas.put(NSConstant.REQUEST_KEY_UPLOAD_FILE, dataPart);
//        // Header
//        Map<String, String> header = getHeader();
//        String url = getUrl(NSConstant.PORT_8083, NSConstant.UPLOAD_IMAGE_URL);
//        ApiClient.getInstance().postMultiPart(url, header, params, datas, BaseResult.class, listener);
//    }

    public String getParamsRequest(Map<String, String> paramsRequest) {
        JSONObject params = new JSONObject();
        try {
            params.put("appid", "ns");
            for (String key : paramsRequest.keySet())
                params.put(key, paramsRequest.get(key));
        } catch (JSONException e) {
            return null;
        }
        return params.toString();
    }

    public String getUrl(String port, String api) {
        StringBuilder sb = new StringBuilder(VConstant.BASE_URL);
        // get ip
        if (StringUtil.isNullOrEmpty(port))
            sb.append(VConstant.PORT_8082);
        else
            sb.append(port);
        // get api
        if (!StringUtil.isNullOrEmpty(api))
            sb.append(api);
        return sb.toString();
    }

    public String appendParamsRequest(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && !params.isEmpty()) {
            int size = params.size();
            int count = 0;
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key));
                count++;
                if (count < size)
                    sb.append("&");
            }
        }
        return sb.toString();
    }

    public Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        //save to local
//        String authenToken = PrefManager.getInstance().getString(VConstant.REMEMBER_AUTHEN_TOKEN);
        String authenToken = "my_token";
        if (!StringUtil.isNullOrEmpty(authenToken))
            headers.put(VConstant.REQUEST_KEY_AUTHEN_TOKEN, authenToken);
        return headers;
    }

//    public void login(String socialType, Map<String, String> params, IOnRequestListener listener) {
//        String url = getUrl(null, NSConstant.LOGIN_URL);
//        if (NSConstant.SOCIAL_TYPE_FACEBOOK.equals(socialType)) {
//            url = getUrl(null, NSConstant.LOGIN_FACEBOOK_URL);
//        } else if (NSConstant.SOCIAL_TYPE_GOOGLE.equals(socialType)) {
//            url = getUrl(null, NSConstant.LOGIN_GOOGLE_URL);
//        }
//        String paramRequest = getParamsRequest(params);
//        ApiClient.getInstance().post(url, LoginResult.class, getHeader(), paramRequest, listener);
//    }
//
//    public void forgotPassword(Map<String, String> params, IOnRequestListener listener) {
//        String url = getUrl(null, NSConstant.FORGOT_PASSWORD_URL);
//        String paramRequest = getParamsRequest(params);
//        ApiClient.getInstance().post(url, BaseResult.class, null, paramRequest, listener);
//    }
//
//    public void changePassword(Map<String, String> params, IOnRequestListener listener) {
//        String url = getUrl(null, NSConstant.CHANGE_PASSWORD_URL);
//        String paramRequest = getParamsRequest(params);
//        ApiClient.getInstance().post(url, BaseResult.class, null, paramRequest, listener);
//    }
//
//    public void register(Map<String, String> params, IOnRequestListener listener) {
//        String url = getUrl(null, NSConstant.REGISTER_URL);
//        String paramRequest = getParamsRequest(params);
//        ApiClient.getInstance().post(url, LoginResult.class, null, paramRequest, listener);
//    }
//
//    public void likeImage(Map<String, String> params, IOnRequestListener listener) {
//        String url = getUrl(NSConstant.PORT_8083, NSConstant.LIKE_IMAGE_URL);
//        String paramRequest = getParamsRequest(params);
//        ApiClient.getInstance().post(url, BaseResult.class, getHeader(), paramRequest, listener);
//    }
//
//    public void createNewAlbum(Map<String, String> params, IOnRequestListener listener) {
//        String url = getUrl(NSConstant.PORT_8083, NSConstant.REQUEST_KEY_ALBUM);
//        String paramRequest = getParamsRequest(params);
//        Map header = getHeader();
//        ApiClient.getInstance().post(url, BaseResult.class, header, paramRequest, listener);
//    }
//
    public <T> void getAlbum(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl("my_port", "request_key_album");
        Map<String, String> header = getHeader();
        getAllAlbum(url, clazz, header, requestListener);
    }
//
//    public void updateOneSignalPlayerId(Map<String, String> params, IOnRequestListener listener) {
//        String url = getUrl(NSConstant.PORT_8084, NSConstant.UPDATE_ONE_SIGNAL_PLAYER_ID_URL);
//        Map<String, String> header = getHeader();
//        ApiClient.getInstance().post(url, BaseResult.class, header, getParamsRequest(params), listener);
//    }

    public <T> void getAllAlbum(String url, final Class<T> clazz, final Map<String, String> headers, final IOnRequestListener requestListener) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                T result = gson.fromJson(response, clazz);
                if (requestListener != null)
                    requestListener.onResponse(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (requestListener != null)
                    requestListener.onError(ApiClient.getInstance().parseError(error));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers != null ? headers : super.getHeaders();
            }
        };
        VolleySingleton.getInstance().addToRequestQueue(request);
    }

//    public void deleteAlbum(Map<String, String> params, String id, IOnRequestListener listener) {
//        String url = getUrl(NSConstant.PORT_8083, NSConstant.REQUEST_KEY_ALBUM + "/" + id);
//        String paramRequest = getParamsRequest(params);
//        Map<String, String> header = getHeader();
//        delete(url, BaseResult.class, header, paramRequest, listener);
//    }
//
//    public <T> void delete(String url, final Class<T> clazz, final Map<String, String> headers, final String requestBody, final IOnRequestListener requestListener) {
//        StringRequest request = new StringRequest(Request.Method.DELETE, url, (String response) -> {
//            Gson gson = new Gson();
//            T result = gson.fromJson(response, clazz);
//            if (requestListener != null)
//                requestListener.onResponse(result);
//        }, (VolleyError error) -> {
//            if (requestListener != null)
//                requestListener.onError(ApiClient.getInstance().parseError(error));
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
//                return ApiClient.getInstance().parseResponse(response);
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
//        request.setRetryPolicy(new DefaultRetryPolicy(5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        VolleySingleton.getInstance().addToRequestQueue(request);
//    }


//    public void followPeople(Map<String, String> params, IOnRequestListener listener, boolean isFollow) {
//        String url = null;
//        if (isFollow)
//            url = getUrl(NSConstant.PORT_8083, NSConstant.FOLLOW_PEOPLE_URL);
//        else
//            url = getUrl(NSConstant.PORT_8083, NSConstant.UNFOLLOW_PEOPLE_URL);
//        ApiClient.getInstance().post(url, BaseResult.class, getHeader(), getParamsRequest(params), listener);
//    }
//
//    public void getImageInfor(String id, IOnRequestListener listener) {
//        StringBuilder sb = new StringBuilder(getUrl(NSConstant.PORT_8083, NSConstant.IMAGE_DETAILS_URL));
//        if (!StringUtil.isNullOrEmpty(id)) {
//            sb.append(id);
//            ApiClient.getInstance().get(sb.toString(), ImageResult.class, getHeader(), listener);
//        }
//    }
//
//    public void viewImage(Map<String, String> params) {
//        String url = getUrl(NSConstant.PORT_8083, NSConstant.IMAGE_VIEW_URL);
//        ApiClient.getInstance().request(url, Request.Method.PUT, BaseResult.class, getHeader(), getParamsRequest(params), null);
//    }
//
//    public void getSuggestUserFollow(int page, IOnRequestListener listener) {
//        Map<String, String> params = new HashMap<>();
//        params.put(NSConstant.REQUEST_KEY_OFFSET, String.valueOf(NSConstant.NUMBER_ITEM_PAGE));
//        params.put(NSConstant.REQUEST_KEY_PAGE, String.valueOf(page));
//        String url = getUrl(NSConstant.PORT_8083, NSConstant.SUGGEST_PEOPLE_FOLLOW_URL);
//        url = appendParamsRequest(url, params);
//        ApiClient.getInstance().get(url, FollowPeopleResult.class, getHeader(), listener);
//    }
//
//    public void updateUser(Map<String, String> params, IOnRequestListener listener) {
//        String url = getUrl(NSConstant.PORT_8082, NSConstant.REQUEST_KEY_UPDATE_USER_INFOR);
//        String paramRequest = getParamsRequest(params);
//        Map header = getHeader();
//        ApiClient.getInstance().request(url, Request.Method.PUT, User.class, header, paramRequest, listener);
//    }
//
//    public void getListNotification(int pageNumber, IOnRequestListener listener) {
//        Map<String, String> params = new HashMap<>();
//        params.put(NSConstant.REQUEST_KEY_OFFSET, String.valueOf(NSConstant.NUMBER_ITEM_PAGE));
//        params.put(NSConstant.REQUEST_KEY_PAGE, String.valueOf(pageNumber));
//        String url = getUrl(NSConstant.PORT_8084, NSConstant.NOTIFICATION_URL);
//        url = appendParamsRequest(url, params);
//        ApiClient.getInstance().get(url, NotificationResult.class, getHeader(), listener);
//    }
//
//    public void getListComment(String id, int pageNumber, IOnRequestListener listener) {
//        Map<String, String> params = new HashMap<>();
//        params.put(NSConstant.REQUEST_KEY_OFFSET, String.valueOf(NSConstant.NUMBER_ITEM_PAGE));
//        params.put(NSConstant.REQUEST_KEY_PAGE, String.valueOf(pageNumber));
//        if (!StringUtil.isNullOrEmpty(id))
//            params.put(NSConstant.REQUEST_KEY_IMAGE_ID_2, id);
//        String url = getUrl(NSConstant.PORT_8083, NSConstant.LIST_COMMENT_URL);
//        url = appendParamsRequest(url, params);
//        ApiClient.getInstance().get(url, CommentsResult.class, getHeader(), listener);
//    }
//
//    public void commentImage(Map<String, String> params) {
//        String url = getUrl(NSConstant.PORT_8083, NSConstant.COMMENT_URL);
//        Map<String, String> header = getHeader();
//        ApiClient.getInstance().post(url, BaseResult.class, header, getParamsRequest(params), null);
//    }
//
//    public void addImageToAlbum(){
//        String url = getUrl(NSConstant.PORT_8083, NSConstant.REQUEST_KEY_ALBUM_DETAILS);
//        Map<String, String> header = getHeader();
//
//    }
}
