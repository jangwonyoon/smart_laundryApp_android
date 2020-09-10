package com.example.user;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class user_forgot_pw_change_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/user_forgot_pw_change_db.php";
    private Map<String, String> map;

    public user_forgot_pw_change_db(String user_email, String user_id, String u_pw, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("user_email",user_email);
        map.put("user_id",user_id);
        map.put("u_pw",u_pw);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
