package com.example.owner;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.owner.owner_main.NOTIFICATION_CHANNEL_ID;
import static com.example.owner.owner_main.owner_address1;
import static com.example.owner.owner_main.owner_lat1;
import static com.example.owner.owner_main.owner_long1;
import static com.example.owner.owner_main.owner_name1;
import static com.example.owner.owner_main.store_name1;
import com.example.owner.owner_main;


public class BackgroundService extends IntentService implements BackgroundResultReceiver.Receiver {
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) { //핸들링 하는 부분
        Log.e("Error","onTaskRemoved - 강제 종료 " + rootIntent);
        /*Toast.makeText(this, "onTaskRemoved ", Toast.LENGTH_SHORT).show();*/
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.deleteNotificationChannel("Noti");
        stopSelf(); //서비스 종료
    }



    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;
    ResultReceiver receiver = null;
    /*int count;*/
    boolean isRunning = true;


    public BackgroundService() {
        super("BackgroundService");
    }






    @Override
    protected void onHandleIntent(Intent workIntent) {
        /*count = workIntent.getExtras().getInt("count");*/
        receiver = workIntent.getParcelableExtra("receiver");

        String command = workIntent.getStringExtra("command");

        if (command.equals("increase count")) {
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);
        }

        int count=0;

        while(isRunning) {
            /*count = count + 1;*/
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            if(count==150){

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @SuppressLint("InvalidWakeLockTag")
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                /*Toast.makeText(getApplicationContext(),"성공1",Toast.LENGTH_SHORT).show();*/

                                int importance = NotificationManager.IMPORTANCE_HIGH;
                                String Noti_Channel_ID = "Noti";
                                String Noti_Channel_Group_ID = "Noti_Group";

                                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                NotificationChannel notificationChannel = new NotificationChannel(Noti_Channel_ID,Noti_Channel_Group_ID,importance);

                                notificationManager.createNotificationChannel(notificationChannel);

                                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),Noti_Channel_ID)
                                        .setLargeIcon(null).setSmallIcon(R.mipmap.ic_launcher)
                                        .setWhen(System.currentTimeMillis()).setShowWhen(true)
                                        .setAutoCancel(true)
                                        .setSmallIcon(R.drawable.washing_machine)
                                        /*.setPriority(NotificationCompat.PRIORITY_MAX)*/
                                        .setContentTitle("현재 '세탁이'가 동작 중 입니다.")
                                        /*.setContentText("")*/
                                        .setOngoing(false);

                                notificationManager.notify(0,builder.build());
                                powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);

                                wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "WAKELOCK");
                                wakeLock.acquire();

                            }
                            //실패한 경우
                            else{
                                /*Toast.makeText(getApplicationContext(),"실패1",Toast.LENGTH_SHORT).show();*/
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //서버로 Volley를 이용해서 요청을 함
                background_db1 registerRequest = new background_db1(store_name1, responseListener);
                RequestQueue queue = Volley.newRequestQueue(BackgroundService.this);
                queue.add(registerRequest);
                count=0;
            }
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if(success){
                            /*Toast.makeText(getApplicationContext(),"성공1",Toast.LENGTH_SHORT).show();*/
                            NotificationSomethings();
                        }
                        //실패한 경우
                        else{
                            /*Toast.makeText(getApplicationContext(),"실패1",Toast.LENGTH_SHORT).show();*/
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            //서버로 Volley를 이용해서 요청을 함
            background_db registerRequest = new background_db(store_name1, responseListener);
            RequestQueue queue = Volley.newRequestQueue(BackgroundService.this);
            queue.add(registerRequest);




        }
    }

    @Override
    public void onDestroy () {
        //카운트 값 증가시키는 루프를 중단시키고
        isRunning = true;

        //증가된 카운트 값을 리턴한다.
        Bundle b = new Bundle();
        try {
            /*b.putInt( "back", count);*/
            receiver.send(STATUS_FINISHED, b);

        } catch(Exception e) {
            b.putString(Intent.EXTRA_TEXT, e.toString());
            receiver.send(STATUS_ERROR, b);
        }
    }

    @SuppressLint("InvalidWakeLockTag")
    public void NotificationSomethings() {

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(this, owner_order_y_n.class);

        notificationIntent.putExtra("owner_name", owner_name1); //전달할 값
        notificationIntent.putExtra("owner_address", owner_address1); //전달할 값
        notificationIntent.putExtra("owner_lat", owner_lat1); //전달할 값
        notificationIntent.putExtra("owner_long", owner_long1); //전달할 값
        notificationIntent.putExtra("store_name", store_name1); //전달할 값

        powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);

        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "WAKELOCK");
        wakeLock.acquire();


        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,  PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.washing_machine)) //BitMap 이미지 요구
                .setContentTitle("세탁!")
                .setContentText("주문이 들어왔습니다! 확인해주세요!")
                // 더 많은 내용이라서 일부만 보여줘야 하는 경우 아래 주석을 제거하면 setContentText에 있는 문자열 대신 아래 문자열을 보여줌
                //.setStyle(new NotificationCompat.BigTextStyle().bigText("더 많은 내용을 보여줘야 하는 경우..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent) // 사용자가 노티피케이션을 탭시 ResultActivity로 이동하도록 설정
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{1,1000});

        //OREO API 26 이상에서는 채널 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남
            CharSequence channelName  = "노티페케이션 채널";
            String description = "오레오 이상을 위한 것임";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName , importance);
            channel.setDescription(description);

            // 노티피케이션 채널을 시스템에 등록
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);

        }else builder.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남

        assert notificationManager != null;
        notificationManager.notify(1234, builder.build()); // 고유숫자로 노티피케이션 동작시킴

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

    }
}