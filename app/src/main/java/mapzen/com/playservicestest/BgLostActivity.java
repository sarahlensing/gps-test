package mapzen.com.playservicestest;

import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class BgLostActivity extends AppCompatActivity implements LostApiClient.ConnectionCallbacks {
  private LostApiClient client;

  PendingIntent pendingIntent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    connectClient();
    BroadcastReceiver receiver = new BroadcastReceiver() {
      @Override public void onReceive(Context context, Intent intent) {
        unregisterUpdates();
      }
    };
    IntentFilter filter = new IntentFilter("com.test.stop.main.thread.updates");
    registerReceiver(receiver, filter);
  }

  @Override public void onConnected() {
    requestUpdates();
  }

  @Override public void onConnectionSuspended() {

  }

  @Override protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    if (intent.getAction().equals("com.test.stop.main.thread.updates")) {
      unregisterUpdates();
    }
  }

  private void connectClient() {
    client = new LostApiClient.Builder(this).addConnectionCallbacks(this).build();
    client.connect();
  }

  private void requestUpdates() {
    long interval = 10*1000; //10 sec
    LocationRequest request = LocationRequest.create();
    request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    request.setFastestInterval(interval);
    request.setInterval(interval);

    Intent intent = new Intent(this, BgPendingIntentService.class);
    pendingIntent = PendingIntent.getService(this, 1, intent, 0);
    LocationServices.FusedLocationApi.requestLocationUpdates(client, request, pendingIntent);
  }

  private void unregisterUpdates() {
    Log.d("Sarah", "unregister main thread updates");
    LocationServices.FusedLocationApi.removeLocationUpdates(client, pendingIntent);
  }
}
