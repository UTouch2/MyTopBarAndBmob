package com.example.nb_mis_100002.myapplication;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nb_mis_100002.myapplication.TopBar.TopBarClickListener;

import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {
    private EditText mName,mFeedback,mQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "b87d89d09ae6e24364c7b97c7c5fe278");

        //Push SDK的初始化并启动
        BmobInstallation.getCurrentInstallation(this).save();
        BmobPush.startWork(this, "b87d89d09ae6e24364c7b97c7c5fe278");
        mName = (EditText) findViewById(R.id.name);
        mFeedback = (EditText) findViewById(R.id.feedback);
        mQuery = (EditText) findViewById(R.id.query_et);

        TopBar topBar = (TopBar) findViewById(R.id.topBar);
        topBar.setOnTopBarClickListener(new TopBarClickListener() {
            @Override
            public void leftClick() {
                Toast.makeText(MainActivity.this, "left topbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick() {
                Toast.makeText(MainActivity.this, "right topbar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void commit(View view){
        String name = mName.getText().toString();
        String feedback = mFeedback.getText().toString();
        if(name.equals("") || feedback.equals("")){
            return;
        }
        FeedBack feedBackObj = new FeedBack();
        feedBackObj.setName(name);
        feedBackObj.setFeedback(feedback);
        feedBackObj.save(MainActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void queryAll(View view){
        BmobQuery<FeedBack> query = new BmobQuery<FeedBack>();
        query.findObjects(MainActivity.this, new FindListener<FeedBack>() {
            @Override
            public void onSuccess(List<FeedBack> list) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("查询结果");
                String str = "";
                for (FeedBack feedbacks : list) {
                    str += feedbacks.getName() + ":" + feedbacks.getFeedback() + "\n";
                }
                builder.setMessage(str);
                builder.create().show();
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public void queryFeedback(View view){
        String str = mQuery.getText().toString();
        if(str.equals("")){
            return;
        }
        BmobQuery<FeedBack> query = new BmobQuery<FeedBack>();
        query.addWhereEqualTo("name",str);
        query.findObjects(MainActivity.this, new FindListener<FeedBack>() {
            @Override
            public void onSuccess(List<FeedBack> list) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("查询结果");
                String str = "";
                for (FeedBack feedbacks : list) {
                    str += feedbacks.getName() + ":" + feedbacks.getFeedback() + "\n";
                }
                builder.setMessage(str);
                builder.create().show();
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public void pushAll(View view){
        BmobPushManager push = new BmobPushManager(MainActivity.this);
        push.pushMessageAll("Test");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
