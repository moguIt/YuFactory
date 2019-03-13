package com.mogui.yufactory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText mJidView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mJidView = (EditText) findViewById(R.id.jid);
        mPasswordView = (EditText) findViewById(R.id.password);
    }

    public void onClickSignIn(View view) {
        this.attemptLogin();
    }

    private void attemptLogin() {
        // Reset errors.
        mJidView.setError(null);
        mPasswordView.setError(null);

        String email = mJidView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mJidView.setError(getString(R.string.error_field_required));
            focusView = mJidView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mJidView.setError(getString(R.string.error_invalid_jid));
            focusView = mJidView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //Save the credentials and login
            saveCredentialsAndLogin(email, password);

        }
    }

    private void saveCredentialsAndLogin(String jid, String password) {
        //Start the connect
        Intent intent = new Intent(this,YuFactoryConnection.class);
        intent.putExtra("xmpp_jid", jid);
        intent.putExtra("xmpp_password", password);
        startActivity(intent);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}
