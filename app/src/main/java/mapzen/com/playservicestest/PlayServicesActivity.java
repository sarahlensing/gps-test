package mapzen.com.playservicestest;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class PlayServicesActivity extends AppCompatActivity implements
    GoogleApiClient.ConnectionCallbacks {

  private GoogleApiClient client;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    connectClient();
  }

  private void connectClient() {
    client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).
        addApi(LocationServices.API).build();
    client.connect();
  }

  @Override public void onConnected(@Nullable Bundle bundle) {
    requestUpdates();
  }

  @Override public void onConnectionSuspended(int i) {

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
