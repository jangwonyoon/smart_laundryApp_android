package com.example.owner;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class owner_item_delete_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/owner_item_add_del_delete_db.php";
    private Map<String, String> map;

    public owner_item_delete_db(String item_name, int item_price, String laundry_list_s_name, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("laundry_list_s_name",laundry_list_s_name);
        map.put("item_name",item_name);
        map.put("item_price",item_price+"");


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
