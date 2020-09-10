package com.example.delivery;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class rider_register2_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/rider_register_location.php";
    private Map<String, String> map;

    public rider_register2_db(Double rider_lat, Double rider_long, String rider_address, String rider_id, String rider_address_detail, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("rider_lat",rider_lat+"");
        map.put("rider_long",rider_long+"");
        map.put("rider_address",rider_address);
        map.put("rider_id",rider_id);
        map.put("rider_address_detail",rider_address_detail);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
