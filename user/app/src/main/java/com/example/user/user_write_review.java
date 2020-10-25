package com.example.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class user_write_review extends Activity {

    private final String TAG = "Connect FTP";
    public FTPClient mFTPClient = null;

    private user_write_review ConnectFTP;

    ImageView iv1, giveimage1, giveimage2;
    Bitmap uritobitmap = null;

    String temp_date;
    String image_address;

    private final int GET_GALLERY_IMAGE1 = 1;
    private final int GET_GALLERY_IMAGE2 = 2;
    private final int GET_GALLERY_IMAGE3 = 3;

    String user_name1, user_address1, user_id1, user_address_detail1, store_name1;
    Double user_lat1, user_long1;
    int date1;
    int rate;

    RatingBar rb1;
    ImageView iv2,iv3;
    Button back,b1;
    TextView tv1,tv2,tv3,tv4,tv5;
    EditText et1;

    Uri uri;
    String image1 ="a";
    String image2 = "b";
    String image3 = "c";

    int temp_date1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_write_review);

        Date currentTime = Calendar.getInstance().getTime();

        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
        SimpleDateFormat minFormat = new SimpleDateFormat("mm", Locale.getDefault());
        SimpleDateFormat secFormat = new SimpleDateFormat("ss", Locale.getDefault());


        final String month1 = monthFormat.format(currentTime);
        final String day1 = dayFormat.format(currentTime);
        final String hour1 = hourFormat.format(currentTime);
        final String min1 = minFormat.format(currentTime);
        final String sec1 = secFormat.format(currentTime);

        temp_date = month1+day1+hour1+min1+sec1;
        temp_date1 = Integer.parseInt(temp_date);

        ConnectFTP = new user_write_review();

        Intent intent = getIntent();
        user_name1 = intent.getStringExtra("user_name");
        user_address1 = intent.getStringExtra("user_address");
        user_lat1 = intent.getDoubleExtra("user_lat",0.0);
        user_long1 = intent.getDoubleExtra("user_long",0.0);
        user_id1 = intent.getStringExtra("user_id");
        user_address_detail1 = intent.getStringExtra("user_address_detail");
        store_name1 = intent.getStringExtra("store_name");
        date1 = intent.getIntExtra("date",0);


        rb1 = (RatingBar) findViewById(R.id.rating);
        rb1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate = (int)rating;
            }
        });

        iv1 = findViewById(R.id.image1);
        iv1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, GET_GALLERY_IMAGE1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

//        iv2 = findViewById(R.id.image2);
//        iv2.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
//                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, GET_GALLERY_IMAGE2);
//
//            }
//        });
//
//        iv3 = findViewById(R.id.image3);
//        iv3.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
//                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, GET_GALLERY_IMAGE3);
//            }
//        });
//
//        tv1 = (TextView) findViewById(R.id.user_id);
//        tv1.setText(user_id1);

        et1 = (EditText) findViewById(R.id.content);

        final String temp_iv1 = "a";
        final String temp_iv2 = "b";
        final String temp_iv3 = "c";


        image_address = Environment.getExternalStorageDirectory().getAbsolutePath() + "/saveimage/"+user_id1+temp_date+".jpg";


        b1 = (Button) findViewById(R.id.write);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = et1.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean status = false;
                        status = ConnectFTP.ftpConnect("edit0.dothome.co.kr", "edit0", "whdtjf1q!", 21);
                        /*Toast.makeText(getApplicationContext(),status+"",Toast.LENGTH_SHORT).show();*/
                        if(status == true) {
                            Log.d(TAG, "Connection Success");

                            String currentPath = ConnectFTP.ftpGetDirectory();
                            Log.d(TAG, currentPath);

                            /*File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/1.png");*/
                            ConnectFTP.ftpUploadFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+user_id1+temp_date+".jpg", user_id1+temp_date+".jpg", currentPath + "/saveimage");

                            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+user_id1+temp_date+".jpg");
                            f.delete();
                        }
                        else {
                            Log.d(TAG, "Connection failed!");
                        }
                    }
                }).start();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우

                                Intent intent = new Intent(user_write_review.this, user_order_record.class);
                                intent.putExtra("user_name",user_name1);
                                intent.putExtra("user_address",user_address1);
                                intent.putExtra("user_lat",user_lat1);
                                intent.putExtra("user_long",user_long1);
                                intent.putExtra("user_id",user_id1);
                                intent.putExtra("user_address_detail",user_address_detail1);

                                startActivity(intent);

                            }
                            //실패한 경우
                            else{
                                Toast.makeText(getApplicationContext(),"리뷰작성 실패",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청을 함
                user_write_review_db registerRequest = new user_write_review_db(store_name1,user_id1, date1, rate,
                        content, image_address,image2,image3,temp_date1, responseListener);
                RequestQueue queue = Volley.newRequestQueue(user_write_review.this);
                queue.add(registerRequest);
            }
        });




        back = (Button) findViewById(R.id.layout2_b1);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(user_write_review.this, user_order_record.class);
                intent.putExtra("user_name",user_name1);
                intent.putExtra("user_address",user_address1);
                intent.putExtra("user_lat",user_lat1);
                intent.putExtra("user_long",user_long1);
                intent.putExtra("user_id",user_id1);
                intent.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent);
            }
        });





        /*intent.putExtra("user_name",user_name);
        intent.putExtra("user_address",user_address);
        intent.putExtra("user_lat",user_lat);
        intent.putExtra("user_long",user_long);
        intent.putExtra("user_id",user_id);
        intent.putExtra("user_address_detail",user_address_detail);
        intent.putExtra("title",a);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        uri = data.getData();

        if (requestCode == GET_GALLERY_IMAGE1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            iv1.setImageURI(uri);
            String file_name = uri.getLastPathSegment().toString();
            String file_path=uri.getPath();
            /*Toast.makeText(getApplicationContext(), file_name+" /오/ "+file_path,Toast.LENGTH_LONG).show();*/
            image1=uri.getPath().toString();

            try { //uri -> bitmap
                uritobitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), uri);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            final File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+user_id1+temp_date+".jpg");
            f.delete();


            File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+user_id1+temp_date+".jpg");// 저장 경로

            FileOutputStream out = null;
            try {
                out = new FileOutputStream(saveFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            uritobitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);


            if (f.exists()) {
                long lFileSize = f.length();
                //Toast.makeText(getApplicationContext(),""+lFileSize,Toast.LENGTH_LONG).show();
                if(lFileSize>160000){
                    f.delete();
                    iv1.setImageResource(R.drawable.camera);
                    Toast.makeText(getApplicationContext(),"파일크기가 너무 큽니다.",Toast.LENGTH_LONG).show();
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(user_write_review.this);
                    builder.setTitle("알림");
                    builder.setMessage("파일의 크기가 너무 큽니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            f.delete();
                        }
                    });*/
                }
                else{

                }
            }
            else {
                Toast.makeText(getApplicationContext(),"파일이 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
            }


        }
        else if(requestCode == GET_GALLERY_IMAGE2 && resultCode == RESULT_OK && data != null && data.getData() != null){
            iv2.setImageURI(uri);
            String file_name = uri.getLastPathSegment().toString();
            String file_path=uri.getPath();
            Toast.makeText(getApplicationContext(), file_name+" /오/ "+file_path,Toast.LENGTH_LONG).show();
            image2=uri.getPath().toString();
            Toast.makeText(getApplicationContext(), image2,Toast.LENGTH_LONG).show();
        }
        else{
            iv3.setImageURI(uri);
            image3=uri.toString();
        }

    }


    public user_write_review(){  //생성자추가
        mFTPClient = new FTPClient();
    }

    public boolean ftpConnect(String host, String username, String password, int port) { //ftp서버와연결
        boolean result = false;
        try{
            mFTPClient.connect(host, port);

            if(FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
                result = mFTPClient.login(username, password);
                mFTPClient.enterLocalPassiveMode();
            }
        }catch (Exception e){
            Log.d(TAG, "Couldn't connect to host");
        }
        return result;
    }

    public boolean ftpDisconnect() { //서버와연결끊기
        boolean result = false;
        try {
            mFTPClient.logout();
            mFTPClient.disconnect();
            result = true;
        } catch (Exception e) {
            Log.d(TAG, "Failed to disconnect with server");
        }
        return result;
    }

    public String ftpGetDirectory(){ //현재작업경로가져오기
        String directory = null;
        try{
            directory = mFTPClient.printWorkingDirectory();
        } catch (Exception e){
            Log.d(TAG, "Couldn't get current directory");
        }
        return directory;
    }

    public boolean ftpChangeDirctory(String directory) { //작업경로수정
        try{
            mFTPClient.changeWorkingDirectory(directory);
            return true;
        }catch (Exception e){
            Log.d(TAG, "Couldn't change the directory");
        }
        return false;
    }

    public String[] ftpGetFileList(String directory) { //디렉터리 내 파일 리스트 가져오기
        String[] fileList = null;
        int i = 0;
        try {
            FTPFile[] ftpFiles = mFTPClient.listFiles(directory);
            fileList = new String[ftpFiles.length];
            for(FTPFile file : ftpFiles) {
                String fileName = file.getName();

                if (file.isFile()) {
                    fileList[i] = "(File) " + fileName;
                } else {
                    fileList[i] = "(Directory) " + fileName;
                }

                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }

    public boolean ftpCreateDirectory(String directory) { //디렉터리 생성
        boolean result = false;
        try {
            result =  mFTPClient.makeDirectory(directory);
        } catch (Exception e){
            Log.d(TAG, "Couldn't make the directory");
        }
        return result;
    }

    public boolean ftpDeleteDirectory(String directory) { //디렉터리 제거
        boolean result = false;
        try {
            result = mFTPClient.removeDirectory(directory);
        } catch (Exception e) {
            Log.d(TAG, "Couldn't remove directory");
        }
        return result;
    }

    public boolean ftpDeleteFile(String file) { //파일 삭제
        boolean result = false;
        try{
            result = mFTPClient.deleteFile(file);
        } catch (Exception e) {
            Log.d(TAG, "Couldn't remove the file");
        }
        return result;
    }

    public boolean ftpDownloadFile(String srcFilePath, String desFilePath) { //파일다운로드
        boolean result = false;
        try{
            mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
            mFTPClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);

            FileOutputStream fos = new FileOutputStream(desFilePath);
            result = mFTPClient.retrieveFile(srcFilePath, fos);
            fos.close();
        } catch (Exception e){
            Log.d(TAG, "Download failed");
        }
        return result;
    }

    public boolean ftpUploadFile(String srcFilePath, String desFileName, String desDirectory) { //FTP서버에 파일 업로드
        boolean result = false;
        try {
            FileInputStream fis = new FileInputStream(srcFilePath);
            if(ftpChangeDirctory(desDirectory)) {
                result = mFTPClient.storeFile(desFileName, fis);
            }
            fis.close();
        } catch(Exception e){
            Log.d(TAG, "Couldn't upload the file");
        }
        return result;
    }
}
