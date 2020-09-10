package com.example.user;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class user_itempage_temp2_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/user_itempage_temp2_db.php";
    private Map<String, String> map;

    public user_itempage_temp2_db(String menu, int price, String user_id, String owner_id, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("menu",menu);
        map.put("price",price+"");
        map.put("user_id",user_id);
        map.put("owner_id",owner_id);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
