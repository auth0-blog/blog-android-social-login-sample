package auth0.com.socialloginsampleauth0;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.result.Credentials;
import com.auth0.android.Auth0;
import com.auth0.android.provider.WebAuthProvider;

public class LoggedInActivity extends AppCompatActivity {

    private Credentials credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if(credentials == null) {
            final Auth0 account = new Auth0(getApplicationContext());
            WebAuthProvider.init(account).start(this, new AuthCallback() {
                @Override
                public void onFailure(@NonNull Dialog dialog) {
                    credentials = null;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final TextView t = (TextView) findViewById(R.id.textView);
                            t.setText("Logged-out");
                        }
                    });

                    // Handle error
                }

                @Override
                public void onFailure(AuthenticationException exception) {
                    credentials = null;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final TextView t = (TextView) findViewById(R.id.textView);
                            t.setText("Logged-out");
                        }
                    });
                    // Handle error
                }

                @Override
                public void onSuccess(@NonNull Credentials credentials_) {
                    credentials = credentials_;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final TextView t = (TextView) findViewById(R.id.textView);
                            t.setText("Logged-in");
                        }
                    });
                    // Do stuff
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logged_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
