package com.example.mobileshop.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.widget.AdapterView.OnItemSelectedListener;
import com.example.mobileshop.R;
import com.example.mobileshop.person.TypeTextViewActivity;
import com.example.mobileshop.person.register.DBOpenHelper;
import com.example.mobileshop.person.register.RegisterActivity;
import com.example.mobileshop.person.register.User;

public class PersonFragment extends Fragment implements View.OnClickListener{
    /*声明自己写的 DBOpenHelper 对象
     *  DBOpenHelper(extends SQLiteOpenHelper) 主要用来
     * 创建数据表
     * 然后再进行数据表的增、删、改、查操作*/
    private DBOpenHelper dbOpenHelper;

    private EditText uname;
    private EditText upassword;

    private Button button1;
    private Button button2;

    private CheckBox remember;



    //private Spinner spinner;

    //private List<String> list;



    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_fragment_layout,container,false);

        //实例化 DBOpenHelper，待会进行登录验证的时候要用来进行数据查询
        dbOpenHelper = new DBOpenHelper(getActivity());
        //init();
        sharedPreferences = getActivity().getSharedPreferences("rememberpassword", Context.MODE_PRIVATE);
        boolean isRemember = sharedPreferences.getBoolean("rememberpassword",false);
        if (isRemember) {
            String name = sharedPreferences.getString("name", "");
            String password = sharedPreferences.getString("password", "");
            uname.setText(name);
            upassword.setText(password);
            remember.setChecked(true);
        }


        return view;
    }
    @Override
    //public void init() {

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        uname = getActivity().findViewById(R.id.uname);

        //uname.setOnClickListener(this);
        upassword = getActivity().findViewById(R.id.upassword);
        remember = getActivity().findViewById(R.id.remember);
        button1 = getActivity().findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = getActivity().findViewById(R.id.button2);
        button2.setOnClickListener(this);




        //spinner = findViewById(R.id.spinner);



    }
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        uname.setText(list.get(position));
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:

                String username = uname.getText().toString();
                String userpassword = upassword.getText().toString();
                username = uname.getText().toString();
//                list = new ArrayList<>();
//                list.add(username);
//                //声明一个下拉列表的数组适配器
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.item_select,list);
//                //设置数组适配器的布局样式
//                arrayAdapter.setDropDownViewResource(R.layout.item_dropdown);
//                //设置下拉框的标题
//                spinner.setPrompt("请选择用户名");
//                //设置下拉框的数组适配器
//                spinner.setAdapter(arrayAdapter);
//                //设置下拉框默认显示第一项
//                spinner.setSelection(0);
//                //注册监听器
//                spinner.setOnItemSelectedListener(this);

                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(userpassword)) {
                    ArrayList<User> arrayList = dbOpenHelper.getAllData();
                    boolean match = false;
                    for (int i=0;i<arrayList.size();i++) {
                        User user = arrayList.get(i);
                        if (username.equals(user.getName()) && userpassword.equals(user.getPassword())) {
                            match = true;
                            break;
                        }else {
                            match = false;
                        }
                    }

                    if (match || username.equals("王明") && userpassword.equals("123456")) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if (remember.isChecked()) {
                            editor.putBoolean("rememberpassword",true);
                            editor.putString("name",username);
                            editor.putString("password",userpassword);
                        }else {
                            editor.clear();
                        }
                        editor.commit();
                        Toast.makeText(getActivity(),"登录成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(),TypeTextViewActivity.class);
                        startActivity(intent);
                        //销毁此Activity
                        //finish();
                    }else {
                        Toast.makeText(getActivity(), "用户名或密码不正确，请重新输入", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "请输入你的用户名或密码", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.button2:
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                //销毁此Activity
                //finish();
                break;

        }
    }

}
