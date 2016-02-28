package com.zxinsight.magicwindow.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zxinsight.MagicWindowSDK;
import com.zxinsight.magicwindow.R;
import com.zxinsight.magicwindow.UrlDispatcher;
import com.zxinsight.magicwindow.domain.User;


/**
 * @author Aaron
 * @date 16/02/27
 */
public class LoginActivity extends BaseAppCompatActivity {

    View coverLayout;

    EditText username;

    EditText password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initData();
    }

    private void initViews() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        coverLayout = findViewById(R.id.coverLayout);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    Drawable drawable = getResources().getDrawable(R.drawable.login_password_pressed);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    password.setCompoundDrawables(drawable, null, null, null);
                } else {
                    Drawable drawable = getResources().getDrawable(R.drawable.login_password_normal);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    password.setCompoundDrawables(drawable, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        TextView loginBtn = (TextView) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(username.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())) {
                    final String usernameStr = username.getText().toString();
                    final String passwordStr = password.getText().toString();

                    showLoading();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            User.currentUser().login(usernameStr, passwordStr);
//                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
//                            startActivity(i);
//                            finish();
                            registerAndRouter();
                        }
                    }, 500);
                } else {
                    Toast.makeText(LoginActivity.this, R.string.please_enter_username_and_password, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initData() {

        if (User.currentUser().isLoggedIn()) {
            username.setText(User.currentUser().username);
            password.setText(User.currentUser().password);
//
//            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
//            startActivity(i);
//            finish();
            registerAndRouter();
        }
    }


    private void registerAndRouter() {
        if (MagicWindowSDK.getMLink() != null) {
            UrlDispatcher.registerWithMLinkCallback(LoginActivity.this);
        }
        Uri mLink = getIntent().getData();
        MagicWindowSDK.getMLink().router(mLink);
        finish();
    }


    public Dialog showLoading() {
        coverLayout.setVisibility(View.VISIBLE);
        coverLayout.getBackground().setAlpha(55);
        return super.showLoading();
    }
}
