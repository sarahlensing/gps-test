package mapzen.com.playservicestest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class PendingIntentService extends Service {

  private static final String TAG = PendingIntentService.class.getSimpleName();

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(TAG, "[started]");
    return START_NOT_STICKY;
  }

  @Override public void onDestroy() {
    Log.d(TAG, "[stopped]");
  }
}
