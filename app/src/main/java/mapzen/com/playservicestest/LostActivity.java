package mapzen.com.playservicestest;

import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class LostActivity extends AppCompatActivity implements LostApiClient.ConnectionCallbacks {
  private LostApiClient client;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    connectClient();
  }

  private void connectClient() {
    client = new LostApiClient.Builder(this).addConnectionCallbacks(this).build();
    client.connect();
  }

  @Override public void onConnected() {
    requestUpdates();
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
}
