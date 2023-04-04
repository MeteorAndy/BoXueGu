package com.boxuegu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.boxuegu.R;
import com.boxuegu.utils.MD5Utils;

public class LoginActivity extends AppCompatActivity {
    private String userName, psw, spPsw;
    private EditText et_user_name, et_psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        init();
    }

    private void init() {
        TextView tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        TextView tv_back = findViewById(R.id.tv_back);
        TextView tv_register = findViewById(R.id.tv_register);
        TextView tv_find_psw = findViewById(R.id.tv_find_psw);
        Button btn_login = findViewById(R.id.btn_login);
        et_user_name = findViewById(R.id.et_user_name);
        et_psw = findViewById(R.id.et_psw);
        tv_back.setOnClickListener(v -> LoginActivity.this.finish());
        tv_register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, 1);
        });
        tv_find_psw.setOnClickListener(v -> {
            // 跳转到找回密码界面
        });
        btn_login.setOnClickListener(v -> {
            userName = et_user_name.getText().toString().trim();
            psw = et_psw.getText().toString().trim();
            String md5Psw = MD5Utils.md5(psw);
            spPsw = readPsw(userName);
            if (TextUtils.isEmpty(userName)) {
                Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(psw)) {
                Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            } else if (md5Psw.equals(spPsw)) {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                // 保存登录状态和登录的用户名
                saveLoginStatus(userName);
                // 把登录成功的状态传递到MainActivity中
                Intent data = new Intent();
                data.putExtra("isLogin", true);
                setResult(RESULT_OK, data);
                LoginActivity.this.finish();
            } else if (!TextUtils.isEmpty(spPsw) && !md5Psw.equals(spPsw)) {
                Toast.makeText(LoginActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String readPsw(String userName) {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sp.getString(userName, "");
    }

    private void saveLoginStatus(String userName) {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin", true);
        editor.putString("loginUserName", userName);
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String userName = data.getStringExtra("userName");
            if (!TextUtils.isEmpty(userName)) {
                et_user_name.setText(userName);
                et_user_name.setSelection(userName.length());
            }
        }
    }
}