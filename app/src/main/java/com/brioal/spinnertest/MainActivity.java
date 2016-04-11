package com.brioal.spinnertest;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView mText;
    private Spinner mSpinner;
    private List<DateFormat> lists;
    private Context mContent;
    private MyAdapter mAdapter;
    private String mFromat = "yyyy-mm-dd HH:mm:ss";//默认格式
    private Timer mTimer;
    private TimerTask mTask;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            long current = System.currentTimeMillis();
            SimpleDateFormat format = new SimpleDateFormat(mFromat);
            String mTime = format.format(current);
            mText.setText(mTime);
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContent = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        mTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask, 1000, 1000);

        initData();
    }

    private void initData() {
        lists = new ArrayList<>();
        DateFormat format1 = new DateFormat("G", "年代");
        DateFormat format2 = new DateFormat("dd", "一个月的第几天");
        DateFormat format3 = new DateFormat("DD", "一年的第几天");
        DateFormat format4 = new DateFormat("h", "标准的12进制");
        DateFormat format5 = new DateFormat("K", "不标准的12进制");
        DateFormat format6 = new DateFormat("mm", "分钟");
        DateFormat format7 = new DateFormat("MM", "月份");
        DateFormat format8 = new DateFormat("ss", "秒");
        DateFormat format9 = new DateFormat("SS", "毫秒");
        DateFormat format10 = new DateFormat("E", "星期几");
        DateFormat format11 = new DateFormat("yyyy", "年份");
        DateFormat format12 = new DateFormat("k", "标准的24进制");
        DateFormat format13 = new DateFormat("H", "不标准的24进制");
        DateFormat format14 = new DateFormat("F-E", "一个月的第几个星期几");
        DateFormat format15 = new DateFormat("w", "一年中的第几个星期");
        DateFormat format16 = new DateFormat("W", "一月中的第几个星期");
        DateFormat format17= new DateFormat("a", "上午下午");


        lists.add(format1);
        lists.add(format2);
        lists.add(format3);
        lists.add(format4);
        lists.add(format5);
        lists.add(format6);
        lists.add(format7);
        lists.add(format8);
        lists.add(format9);
        lists.add(format10);
        lists.add(format11);
        lists.add(format12);
        lists.add(format13);
        lists.add(format14);
        lists.add(format15);
        lists.add(format16);
        lists.add(format17);
        initView();
    }

    private void initView() {
        mText = (TextView) findViewById(R.id.main_tv);
        mSpinner = (Spinner) findViewById(R.id.main_spinner);
        mAdapter = new MyAdapter();
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mFromat = lists.get(position).getmCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(mContent);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setTextSize(20);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setText(lists.get(position).getmShow());
            return textView;
        }
    }


    private class DateFormat {
        private String mShow;
        private String mCode;

        public DateFormat(String mCode, String mShow) {
            this.mShow = mShow;
            this.mCode = mCode;
        }

        public String getmShow() {
            return mShow;
        }

        public String getmCode() {
            return mCode;
        }
    }

}
