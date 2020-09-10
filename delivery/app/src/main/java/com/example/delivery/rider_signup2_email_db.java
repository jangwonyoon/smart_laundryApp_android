package com.example.delivery;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class rider_signup2_email_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/rider_signup_email_db.php";
    private Map<String, String> map;

    public rider_signup2_email_db(String rider_email, String num, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("rider_email",rider_email);
        map.put("num",num);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
