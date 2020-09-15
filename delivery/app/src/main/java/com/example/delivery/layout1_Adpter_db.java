package com.example.delivery;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class layout1_Adpter_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/layout1_Adpter_db.php";
    private Map<String, String> map;

    public layout1_Adpter_db(String rider_id, String u_address, String s_address,
                             String price, String items, String d_type,Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("u_address",u_address);
        map.put("rider_id",rider_id);
        map.put("s_address",s_address);
        map.put("price",price);
        map.put("items",items);
        map.put("d_type",d_type);



    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
