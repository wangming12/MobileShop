package com.example.mobileshop.person.register;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileshop.R;
import com.example.mobileshop.fragment.PersonFragment;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private DBOpenHelper dbOpenHelper;
    private EditText uname;
    private EditText upassword;
    private Button register;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbOpenHelper = new DBOpenHelper(this);
        init();
    }
    public void init(){
        uname = findViewById(R.id.uname);
        upassword = findViewById(R.id.upassword);
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击注册按钮
            case R.id.register:
                //获取用户输入的用户名、密码
                String username = uname.getText().toString();
                String userpassword = upassword.getText().toString();
                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(userpassword)) {
                    //将用户名和密码加入到数据库中
                    dbOpenHelper.add(username,userpassword);
                    Intent intent = new Intent(this, PersonFragment.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(this,  "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
