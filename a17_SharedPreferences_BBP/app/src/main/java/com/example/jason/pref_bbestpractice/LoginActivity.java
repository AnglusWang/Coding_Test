package com.example.jason.pref_bbestpractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;
    private CheckBox rememberPass;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        rememberPass = (CheckBox) findViewById(R.id.remeber_pass);
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);

        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            //将账号和密码设置到文本框中
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if (account.equals("admin") && password.equals("123456")) {
                    editor = pref.edit();
                    if (rememberPass.isChecked()) {
                        //检查复选框是否被选中
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", account);
                        editor.putString("password", password);
                    }else {
                        editor.clear();
                    }
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Account or Password is Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
