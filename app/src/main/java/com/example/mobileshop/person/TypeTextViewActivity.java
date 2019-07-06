package com.example.mobileshop.person;

import android.content.Intent;
import android.view.View;

import com.example.mobileshop.R;
import com.example.mobileshop.activity.Main1Activity;

import android.content.Intent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class TypeTextViewActivity extends BaseActivity{
    private TypeTextView mTypeTextView = null;
    private static final String TEST_DATA =
            "/**\n" +

                    "*2019-01-28.\n" +

                    "*/\n" +

                    "Boy name =王明\n" +

                    "Girl name =某某某\n" +

                    "// Fall in love river. \n" +

                    "The boy love the girl;\n" +

                    "// They love each other.\n" +

                    "The girl loved the boy;\n" +

                    "// AS time goes on.\n" +

                    "The boy can not be separated the girl;\n" +

                    "// At the same time.\n" +

                    "The girl can not be separated the boy;\n" +

                    "// Both wind and snow all over the sky.\n" +

                    "// Whether on foot or 5 kilometers.\n" +

                    "The boy very happy;\n" +

                    "The girl is also very happy;\n" +

                    "// Whether it is right now\n" +

                    "// Still in the distant future.\n" +

                    "The boy has but one dream;\n" +

                    "// The boy wants the girl could well have been happy.\n" +

                    "\n" +

                    "\n" +

                    "I want to say:\n" +

                    "Baby, I love you forever;";


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login3);
    }

    @Override
    public void findViews() {
        mTypeTextView = findViewById(R.id.typeTxtId);
        mTypeTextView.setOnTypeViewListener( new TypeTextView.OnTypeViewListener( ) {
            @Override
            public void onTypeStart() {
                print("onTypeStart");
            }

            @Override
            public void onTypeOver() {
                print( "onTypeOver" );
            }
        });

        //你要转向的Activity
        final Intent it = new Intent(this, Main1Activity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //执行跳转
                startActivity(it);
            }
        };
        //10秒后
        timer.schedule(task,1000*70);
    }

    @Override
    public void getData() {

    }

    @Override
    public void showContent() {
        mTypeTextView.start(TEST_DATA);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.showBtnId) {
            showContent();
        }
    }
    private void print(String printStr) {
        System.out.println("TAG == TypeTextViewActivity, info == " + printStr);
    }
}
