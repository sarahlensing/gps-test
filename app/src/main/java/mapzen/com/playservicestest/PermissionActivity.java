package mapzen.com.playservicestest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class PermissionActivity extends AppCompatActivity {

  int LOCATION_PERMISSION_REQUEST = 1;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (!hasLocationPermission()) {
      requestLocationPermission();
      return;
    } else {
      launchActivity();
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (grantResults[0] == PERMISSION_GRANTED) {
      launchActivity();
    }
  }

  private boolean hasLocationPermission() {
    return ActivityCompat.checkSelfPermission(this,
        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
  }

  private void requestLocationPermission() {
    ActivityCompat.requestPermissions(
        this, new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION },
        LOCATION_PERMISSION_REQUEST);
  }

  private void launchActivity() {
    //Intent intent = new Intent(this, PlayServicesActivity.class);
    //Intent intent = new Intent(this, LostActivity.class);
    Intent intent = new Intent(this, BgLostActivity.class);
    startActivity(intent);
  }
}

