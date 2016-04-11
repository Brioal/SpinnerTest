#SimpleDateFormat的使用
##`SImpleDateFormat`是用于将其他对象规范化为日期的操作类,下面用一个小demo介绍一个基本使用方法
###首先看常用的格式化语句(大小写对应):
| 小写字符| 符号表示的格式 |大写字符|符号表示的格式
| - | -|-|-|
| G | 年代 |无|无|
|M|  月|m|分钟
| d |一个月的第几天 |D|一年中的第一天
|h|小时 1~12|H|小时 0~23|
|k|小时 1~24|K|小时 0~11|
|s|秒|S|毫秒|
|E|星期几|无|无|
|F|一月中的第几个星期几|无|无|
|w|一年中的第几个星期|W|一个月的第几个星期|
|a|上午下午标记|无|无|
|z|时区|无|无|


###测试`Java`代码,晚些会放一个`Android`上的根据不同的格式显示当前时间的`Demo`
```
long current = System.currentTimeMillis();
SimpleDateFormat format1 = new SimpleDateFormat("yyyy年M月dd日HH时mm分ss秒");
SimpleDateFormat format2 = new SimpleDateFormat("第DDD天");
SimpleDateFormat format3 = new SimpleDateFormat("一个月中的第F个星期E");
SimpleDateFormat format4 = new SimpleDateFormat("一年中的第w个星期");
SimpleDateFormat format5 = new SimpleDateFormat("一月中的第W个星期");
SimpleDateFormat format6 = new SimpleDateFormat("a");
SimpleDateFormat format7 = new SimpleDateFormat("k时");
SimpleDateFormat format8 = new SimpleDateFormat("KK时");
SimpleDateFormat format9 = new SimpleDateFormat("z时区");
SimpleDateFormat format10 = new SimpleDateFormat("G年代");
...输出每个format.format(current);省略
```
###输出结果
```
2016年4月11日20时17分53秒
第102天
一个月中的第2个星期星期一
一年中的第16个星期
一月中的第3个星期
下午
20时
08时
CST时区
公元年代
```

##在`Android`上也写了个小`Demo`,主要还是复习一下`Spinner`的使用方法
###图例:
![这里写图片描述](http://img.blog.csdn.net/20160411232844813)
###总的来说`Spinner`的使用与`ListView`相差不大,适配器是相同的,适配器的使用方法也是一样的
###`Spinner`的常用`xml`属性:
#####来自`Android API` [Spinner](http://developer.android.com/reference/android/widget/Spinner.html) ,英语不是太好翻译的有偏差请指出
|属性名|代码中的方法|方法功能|
|-|-|-|
|android:dropDownHorizontalOffset|setDropDownHorizontalOffset(int)|下拉列表的水平偏移|
|android:dropDownSelector|无|设置选择项的selector,只在mode为下拉列表时生效|
|android:dropDownVerticalOffset|setDropDownVerticalOffset(int)|下拉列表的垂直方向偏移量|
|android:dropDownWidth|setDropDownWidth(int)|下拉选择列表的宽度|
|android:gravity|setGravity(int)|当前选中项的的位置|
|android:popupBackground|无|下拉列表的背景|
|android:prompt|无|显示模式为Dialog时候的标题|
|android:spinnerMode|无|设置显示模式|

####显示模式有两种
|参数 |功能|
|-|-|
|MODE_DIALOG|Dialog模式|
|MODE_DROPDOWN|下拉列表模式
####常用的属性
###`setAdapter` 设置适配器,与`ListView`相同
###`setOnItemSelectListener` 列表点击事件 ,与`ListView`有区别

###使用方法与`ListView`基本相同,不同的在于可以选择显示模式,就不细说了,上测试的`Demo`代码吧
###布局:
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"
    tools:context="com.brioal.spinnertest.MainActivity"
    tools:showIn="@layout/activity_main">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:gravity="center"
        android:orientation="vertical">

        <TextView
            android:id="@+id/main_tv"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_gravity="center"
            android:gravity="center"
            android:text="Hello World!" />

        <Spinner
            android:spinnerMode="dropdown"
            android:layout_marginTop="20dp"
            android:id="@+id/main_spinner"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:gravity="center" />

    </LinearLayout>
</RelativeLayout>

```
###`MainActivity`内容
```
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

```
###代码比较简单,就介绍到这

