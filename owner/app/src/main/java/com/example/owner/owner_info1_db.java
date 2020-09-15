package com.example.owner;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class owner_info1_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/owner_info1_db.php";
    private Map<String, String> map;

    public owner_info1_db(String owner_password, int owner_number, String owner_email, int owner_store_number, String store_name, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("owner_password",owner_password);
        map.put("owner_number",owner_number+"");
        map.put("owner_email",owner_email);
        map.put("owner_store_number",owner_store_number+"");
        map.put("store_name",store_name);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
