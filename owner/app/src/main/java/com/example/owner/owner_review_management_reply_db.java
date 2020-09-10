package com.example.owner;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class owner_review_management_reply_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/owner_review_management_reply_db.php";
    private Map<String, String> map;

    public owner_review_management_reply_db(String store_name, String now_user_id, int date, String content, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("store_name",store_name);
        map.put("now_user_id",now_user_id);
        map.put("date",date+"");
        map.put("content",content);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
