package mapzen.com.playservicestest;

import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class BgPendingIntentService extends Service implements LostApiClient.ConnectionCallbacks {

  private static final String TAG = BgPendingIntentService.class.getSimpleName();

  LostApiClient client;

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }

  @Override public void onCreate() {
    super.onCreate();
    Log.d(TAG, "[bg create]");
    connectClient();
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(TAG, "[bg started]");
    connectClient();
    return START_NOT_STICKY;
  }

  @Override public void onDestroy() {
    Log.d(TAG, "[bg stopped]");
  }

  private void connectClient() {
    client = new LostApiClient.Builder(this).addConnectionCallbacks(this).build();
    client.connect();
  }

  @Override public void onConnected() {
    requestUpdates();
    fireUnregisterIntent();
  }

  @Override public void onConnectionSuspended() {

  }

  private void requestUpdates() {
    long interval = 10*1000; //10 sec
    LocationRequest request = LocationRequest.create();
    request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    request.setFastestInterval(interval);
    request.setInterval(interval);

    Intent intent = new Intent(this, PendingIntentService.class);
    final PendingIntent
        pendingIntent = PendingIntent.getService(this, 1, intent, 0);
    LocationServices.FusedLocationApi.requestLocationUpdates(client, request, pendingIntent);
  }

  private void fireUnregisterIntent() {
    Log.d("Sarah", "fireUnregisterIntent");
    Intent intent = new Intent("com.test.stop.main.thread.updates");
    sendBroadcast(intent);
  }
}

