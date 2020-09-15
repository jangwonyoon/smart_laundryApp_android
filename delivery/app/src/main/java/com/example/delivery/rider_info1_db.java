package com.example.delivery;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class rider_info1_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/rider_info1_db.php";
    private Map<String, String> map;

    public rider_info1_db(String rider_password, String rider_number, String rider_email, String rider_id, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("rider_password",rider_password);
        map.put("rider_number",rider_number);
        map.put("rider_email",rider_email);
        map.put("rider_id",rider_id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
