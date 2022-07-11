package com.example.smslocator;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ForegroundServiceForAndroid8 extends Service implements LocationListener {

    public static final int DEFAULT_NOTIFICATION_ID = 101;
    LocationManager locationManager;
    String phone;
    String notificationChannelId;
    int attempt;
    public void onCreate() {
        notificationChannelId = createNotificationChannel();
        super.onCreate();
    }



    public int onStartCommand(Intent intent, int flags, int startId) throws SecurityException{

        Context context = getApplicationContext();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        phone = intent.getExtras().getString("phone");
        Notification notification = getNotification(getString(R.string.foreground_start),getString(R.string.by_request)+phone);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.gan4x4.LOCATION_UPDATE");
        BroadcastReceiver myReceiver = new SmsListener();
        registerReceiver(myReceiver, filter);



/*
        Intent intentp = new Intent("com.gan4x4.LOCATION_UPDATE");
        //intentp.putExtra("phone",intent.getStringExtra("phone"));
        //intentp.putExtra("attempt",attempt++);
        intentp.putExtra("satellites",7);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentp,PendingIntent.FLAG_UPDATE_CURRENT);
*/
        //Intent i = new Intent(context, ForegroundServiceForAndroid8.class);
        //context.startService(i);
        startForeground(DEFAULT_NOTIFICATION_ID,notification);
        //locationManager.requestSingleUpdate (LocationManager.GPS_PROVIDER,pendingIntent);



        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 70, this);


        return super.onStartCommand(intent, flags, startId);
    }

    //Send custom notification
    @TargetApi(26)
    public Notification getNotification(String title,String text) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification =
                new Notification.Builder(this, notificationChannelId)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntent)
                        //.setTicker(getText(R.string.ticker_text))
                        .build();
        return notification;
    }

    @TargetApi(26)
    private String createNotificationChannel(){

        String NOTIFICATION_CHANNEL_ID = "com.gan4x4.simplesmslocator";
        String channelName = "Simple SMS locator";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_MIN);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);
        return NOTIFICATION_CHANNEL_ID;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        attempt++;


        if (SmsListener.minimalAccuracy > location.getAccuracy()) {

            String key = LocationManager.KEY_LOCATION_CHANGED;
            Intent intent = new Intent("com.gan4x4.LOCATION_UPDATE");
            intent.putExtra("phone", phone);
            intent.putExtra("attempt", attempt);
            intent.putExtra(key, location);
            sendBroadcast(intent);
        }
        // stopService must be called from broadcast receiver after sam was send to user

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {

    }

    @Override
    public void onFlushComplete(int requestCode) {

    }

    @Override
    public void onDestroy(){
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
