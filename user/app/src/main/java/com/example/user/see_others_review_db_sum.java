package com.example.user;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class see_others_review_db_sum extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/see_others_review_db_sum.php";
    private Map<String, String> map;

    public see_others_review_db_sum(String now_user_id, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("now_user_id",now_user_id);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
