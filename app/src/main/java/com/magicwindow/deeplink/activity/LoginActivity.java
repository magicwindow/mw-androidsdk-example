package com.magicwindow.deeplink.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.app.BaseActivity;
import com.magicwindow.deeplink.domain.User;

import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.inject.annotation.OnClick;
import cn.salesuite.saf.utils.StringUtils;

/**
 * Created by Tony Shen on 15/11/29.
 */
public class LoginActivity extends BaseActivity {

    @InjectView
    View coverLayout;

    @InjectView
    EditText username;

    @InjectView
    EditText password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.magicwindow.deeplink.R.layout.activity_login);

        initViews();
    }

    private void initViews() {

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isNotBlank(s)) {
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
    }

    @OnClick(id = R.id.loginBtn)
    void clickLoginButton() {

        if (StringUtils.isNotBlank(username.getText().toString(), password.getText().toString())) {
            final String usernameStr = username.getText().toString();
            final String passwordStr = password.getText().toString();

            showLoading();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    User.currentUser().login(usernameStr, passwordStr);
                    Intent i = new Intent(mContext, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 500);
        } else {
            toast(com.magicwindow.deeplink.R.string.please_enter_username_and_password);
        }
    }

    public Dialog showLoading() {
        coverLayout.setVisibility(View.VISIBLE);
        coverLayout.getBackground().setAlpha(55);
        return super.showLoading();
    }
}
