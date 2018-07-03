package sdk.mobfox.com.mobfox_app;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by shahafsh on 6/19/18.
 */

public class SplashDelay extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(2000);
    }
}
