package com.example.owner;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class owner_forgot_pw_change_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/owner_forgot_pw_change_db.php";
    private Map<String, String> map;

    public owner_forgot_pw_change_db(String owner_email, String owner_id, String o_pw, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("owner_email",owner_email);
        map.put("owner_id",owner_id);
        map.put("o_pw",o_pw);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
