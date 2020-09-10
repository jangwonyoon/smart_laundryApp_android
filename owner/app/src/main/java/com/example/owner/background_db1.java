package com.example.owner;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class background_db1 extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/background_db1.php";
    private Map<String, String> map;

    public background_db1(String s_name, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("s_name",s_name);
        /*map.put("user_id",user_id);
        map.put("date",date+"");*/


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
