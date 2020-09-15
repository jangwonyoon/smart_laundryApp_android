package com.example.user;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class user_write_review_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/user_write_review_db.php";
    private Map<String, String> map;

    public user_write_review_db(String s_name, String user_id, int date, int rate, String content, String image1,
                                String image2, String image3, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("s_name",s_name);
        map.put("user_id",user_id);
        map.put("date",date+"");
        map.put("rate",rate+"");
        map.put("content",content);
        map.put("image1",image1);
        map.put("image2",image2);
        map.put("image3",image3);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
