package com.example.owner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class owner_review_management_Adpter extends RecyclerView.Adapter<owner_review_management_Adpter.CustomViewHolder> {
    final String TAG = "Connect FTP";
    FTPClient mFTPClient = null;

    owner_review_management_Adpter ConnectFTP;

    String result="";
    String newFilePath="";

    private ArrayList<owner_review_management_list> mList = null;
    private Activity context = null;
    /*String var_name = ((user_main1)user_main1.context).var_name;*/

    String owner_name;
    String owner_address;
    Double owner_lat;
    Double owner_long;
    String store_name;



    public owner_review_management_Adpter(Activity context, ArrayList<owner_review_management_list> list, String store_name, String owner_name, String owner_address,
                                          Double owner_lat, Double owner_long) {
        this.context = context;
        this.mList = list;
        this.store_name = store_name;
        this.owner_name = owner_name;
        this.owner_address = owner_address;
        this.owner_lat = owner_lat;
        this.owner_long = owner_long;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView u_id;
        protected TextView date;
        protected RatingBar rating;
        protected TextView content;
        protected TextView items;
        protected LinearLayout privatereview;
        protected TextView temp,start;
        protected ImageView iv1,iv2,iv3;



        public CustomViewHolder(View view) {
            super(view);
            this.u_id = (TextView) view.findViewById(R.id.u_id);
            this.rating = (RatingBar) view.findViewById(R.id.rating);
            this.date = (TextView) view.findViewById(R.id.date);
            this.content = (TextView) view.findViewById(R.id.content);
            this.items = (TextView) view.findViewById(R.id.items);
            this.privatereview = (LinearLayout) view.findViewById(R.id.privatereview);
            this.temp = (TextView) view.findViewById(R.id.temp);
            this.start = (TextView) view.findViewById(R.id.start);
            this.iv1 = (ImageView) view.findViewById(R.id.image1);
            /*this.iv2 = (ImageView) view.findViewById(R.id.image2);
            this.iv3 = (ImageView) view.findViewById(R.id.image3);*/

        }


    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.owner_review_management_info, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position) {
        ConnectFTP = new owner_review_management_Adpter();

        viewholder.u_id.setText(mList.get(position).getMember_u_id());
        viewholder.rating.setRating(Integer.parseInt(mList.get(position).getMember_rating()));
        viewholder.date.setText("2020"+mList.get(position).getMember_date());
        viewholder.content.setText(mList.get(position).getMember_content());
        viewholder.items.setText(mList.get(position).getMember_items());

        File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/saveimage"); // 저장 경로
        if (!saveFile.exists()) {
            saveFile.mkdir();
        } else {
            Log.d(TAG, "이미 만들어졌음");
        }


        String path = mList.get(position).getMember_image1();
        if(path.equals("a")){
        }
        else{
            for(int i=0;i<5;i++) {
                result = path.substring(path.lastIndexOf("/") + 1);
            }
            newFilePath = mList.get(position).getMember_image1();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean status = false;
                    status = ConnectFTP.ftpConnect("edit0.dothome.co.kr", "edit0", "whdtjf1q!", 21);
                    if (status == true) {
                        Log.d(TAG, "Connection Success");

                        String currentPath = ConnectFTP.ftpGetDirectory();

                        Log.d(TAG, result+"_hello_"+newFilePath);
                        ConnectFTP.ftpDownloadFile(currentPath + "/saveimage/" + result, newFilePath);

                    } else {
                        Log.d(TAG, "Connection failed");
                    }
                }
            }).start();
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,result);
        Log.d(TAG,"여기는 가지고와서 보여주는 부분");
        String imgpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/saveimage/"+result;
        Bitmap bm = BitmapFactory.decodeFile(imgpath);
        viewholder.iv1.setImageBitmap(bm);
        del_file();

        if(mList.get(position).getMember_o_comment().length()==0){
            viewholder.start.setText(null);
            viewholder.temp.setText(null);
        }
        else{
            viewholder.start.setText("사장님: ");
            viewholder.temp.setText(mList.get(position).getMember_o_comment());

        }


        /*viewholder.b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), user_order_record2.class);
                intent.putExtra("user_name",user_name);
                intent.putExtra("user_address",user_address);
                intent.putExtra("user_lat",user_lat);
                intent.putExtra("user_long",user_long);
                intent.putExtra("user_id",user_id);
                intent.putExtra("user_address_detail",user_address_detail);
                intent.putExtra("store_name",s_name);
                intent.putExtra("date",date);

                context.startActivity(intent);
            }
        });*/


        viewholder.privatereview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), owner_review_management_reply.class);
                intent.putExtra("owner_name",owner_name);
                intent.putExtra("owner_address",owner_address);
                intent.putExtra("owner_lat",owner_lat);
                intent.putExtra("owner_long",owner_long);
                intent.putExtra("now_user_id",mList.get(position).getMember_u_id());;
                intent.putExtra("date",mList.get(position).getMember_date());
                intent.putExtra("store_name",store_name);

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


    public owner_review_management_Adpter(){  //생성자추가
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


    public String ftpGetDirectory(){ //현재작업경로가져오기
        String directory = null;
        try{
            directory = mFTPClient.printWorkingDirectory();
        } catch (Exception e){
            Log.d(TAG, "Couldn't get current directory");
        }
        return directory;
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
    void del_file(){
        Log.d(TAG,"지워져라");
        String a = Environment.getExternalStorageDirectory().getAbsolutePath() + "/saveimage";
        File dir = new File(a);
        File[] childFileList = dir.listFiles();
        if (dir.exists()) {
            for (File childFile : childFileList) {
                childFile.delete(); //하위 파일
            }

            dir.delete();
        }
    }


}

