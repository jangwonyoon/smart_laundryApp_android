package com.example.owner;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ImageActivity_owner2_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/ImageActivity_owner2_db.php";
    private Map<String, String> map;

    public ImageActivity_owner2_db(String store_name, String user_id, int t_price, int date, String memo, int yes_no,
                                   String user_address, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("store_name",store_name);
        map.put("user_id",user_id);
        map.put("t_price",t_price+"");
        map.put("date",date+"");
        map.put("memo",memo);
        map.put("yes_no",yes_no+"");
        map.put("user_address",user_address);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
