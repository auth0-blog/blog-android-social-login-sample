package auth0.com.socialloginsamplelock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.auth0.core.Token;
import com.auth0.core.UserProfile;
import com.auth0.lock.Lock;
import com.auth0.lock.LockContext;

public class Application extends android.app.Application {
    private static final String TAG = Application.class.getCanonicalName();

    private UserProfile userProfile;
    private Token accessToken;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            userProfile = intent.getParcelableExtra(Lock.AUTHENTICATION_ACTION_PROFILE_PARAMETER);
            accessToken = intent.getParcelableExtra(Lock.AUTHENTICATION_ACTION_TOKEN_PARAMETER);
            Log.i(TAG, "User " + userProfile.getName() + " logged in");

            final Intent loggedInIntent =
                    new Intent(getApplicationContext(), LoggedInActivity.class);
            loggedInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(loggedInIntent);
        }
    };

    public void onCreate() {
        super.onCreate();

        LockContext.configureLock(
                new Lock.Builder()
                        .loadFromApplication(this)
                        .closable(true));

        final LocalBroadcastManager broadcastManager =
                LocalBroadcastManager.getInstance(getApplicationContext());
        broadcastManager.registerReceiver(receiver, new IntentFilter(Lock.AUTHENTICATION_ACTION));
    }
}

