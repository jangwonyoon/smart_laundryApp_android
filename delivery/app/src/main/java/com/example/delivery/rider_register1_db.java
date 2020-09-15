package com.example.delivery;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class rider_register1_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/rider_register.php";
    private Map<String, String> map;

    public rider_register1_db(String rider_name, String rider_id, String rider_password, String rider_email, String rider_address,
                              String rider_number, Double rider_lat, Double rider_long, String rider_license_number, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("rider_name",rider_name);
        map.put("rider_id",rider_id);
        map.put("rider_password",rider_password);
        map.put("rider_email",rider_email);
        map.put("rider_address",rider_address);
        map.put("rider_number",rider_number);
        map.put("rider_lat",rider_lat+"");
        map.put("rider_long",rider_long+"");
        map.put("rider_license_number",rider_license_number);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
