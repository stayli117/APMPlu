package net.people.apmplu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.act.NetUtil2;
import net.act.OkGoUtil;
import net.act.OkUtils;
import net.people.R;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkGoUtil.exceNet("http://www.baidu.com");
        NetUtil2.startRequestWithOkHttp3("http://www.baidu.com");
        OkUtils.ex("http://www.baidu.com");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    NetUtil2.startWithOkHttp3("http://www.baidu.com");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
