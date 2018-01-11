package com.manroid.core.util;


import com.manroid.core.data.model.RequestInfor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Sen on 11/13/2017.
 */

public class RequestHandler {
    private static RequestHandler instance;
    private List<RequestInfor> listRequestInfor = new ArrayList<>();

    private RequestHandler() {

    }

    public static RequestHandler getInstance() {
        if (instance == null)
            instance = new RequestHandler();
        return instance;
    }

    public void addRequest(RequestInfor requestInfor) {
        listRequestInfor.add(requestInfor);
    }

    public List<RequestInfor> getListRequestInfor() {
        return listRequestInfor;
    }
}
