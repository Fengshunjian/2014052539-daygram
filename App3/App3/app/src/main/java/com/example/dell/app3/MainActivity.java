package com.example.dell.app3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    private ABAdapter abAdapter;
    private ArrayList<Object> data = new ArrayList<>();
    //定义显示接收Activity传入值的TextView 的A类
    private A wdc = null;
    private String year=null;
    private String month=null;
    //定义一个startActivityForResult（）方法用到的整型值
   // private final int requestCode = 1500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //月
        Spinner spinner_month = (Spinner) this.findViewById(R.id.spinner_month);
        List<String> list_month=new ArrayList<>();
        list_month.add("January");
        list_month.add("February");
        list_month.add("March");
        list_month.add("April");
        list_month.add("May");
        list_month.add("June");
        list_month.add("July");
        list_month.add("August");
        list_month.add("September");
        list_month.add("October");
        list_month.add("November");
        list_month.add("December");
        ArrayAdapter<String> adapter_month=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list_month);
        adapter_month.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_month.setAdapter(adapter_month);
        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String result_month = parent.getItemAtPosition(position).toString();
                month=result_month;
                Log.i("spinner select month",result_month);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//年
        Spinner spinner_year = (Spinner) this.findViewById(R.id.spinner_Year);
        List<String> list_year=new ArrayList<>();
        for(int i=1990;i<2100;i++) {
            list_year.add(""+i);
        }
        ArrayAdapter<String> adapter_year=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list_year);
        adapter_year.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_year.setAdapter(adapter_year);
        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String result_year = parent.getItemAtPosition(position).toString();
                year=result_year;
                Log.i("spinner select year",result_year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //得到保存于本地路径的对象
        data = (ArrayList) getObject("object.dat");
        abAdapter = new ABAdapter(MainActivity.this, data);
        //setlist();
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(abAdapter);
        ImageButton button_add = (ImageButton) findViewById(R.id.Button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = data.size()+1;
                A adda = new A("week", ""+day, "");
                data.add(adda);
                abAdapter.notifyDataSetChanged();
                //listView.setAdapter(abAdapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object wdc = data.get(position);
                if (wdc instanceof A) {
                    A a_wdc = (A) wdc;
                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    //采用Intent普通传值的方式
                    intent.putExtra("week", a_wdc.getWeek());
                    intent.putExtra("date", a_wdc.getDate());
                    intent.putExtra("content", a_wdc.getContent());
                    intent.putExtra("month",month);
                    intent.putExtra("year",year);
                    //跳转Activity
                    startActivityForResult(intent, position);
                }else if(wdc instanceof B){
                    B b_wdc= (B) wdc;
                    String b_w=b_wdc.getWeek();
                    String b_d=b_wdc.getDate();
                    String b_c=b_wdc.getContent();
                    A a_from_b = new A(b_w,b_d,b_c);
                    data.set(position,a_from_b);
                    abAdapter.notifyDataSetChanged();

                }

            }
        });

        //保存对象到本地
        saveObject("object.dat", data);

    }


/**
 * 接收当前Activity跳转后，目标Activity关闭后的回传值
 */
    protected void onActivityResult(int requestCode, int resultCode, Intent return_data) {
        super.onActivityResult(requestCode, resultCode, return_data);
        switch (resultCode) {
            case RESULT_OK: {//接收并显示Activity传过来的值

                String w_r = return_data.getStringExtra("return_w");
                String d_r = return_data.getStringExtra("return_d");
                String c_r = return_data.getStringExtra("return_c");
                String panduan = return_data.getStringExtra("panduan");
                if(panduan.equals("y")){

                    for(int i=0;i<data.size();i++){
                        if(data.get(i) instanceof A){
                            A a_r = (A) data.get(i);
                            if(a_r.getDate().equals(""+d_r)){
                                if(!c_r.equals("")){
                                    a_r.setContent("" + c_r);
                                    data.set(i, a_r);

                                    saveObject("object.dat", data);
                                    abAdapter.notifyDataSetChanged();
                                }else {
                                    B point = new B(R.drawable.point,w_r,d_r,"");
                                    data.set(i,point);
                                    saveObject("object.dat", data);
                                    abAdapter.notifyDataSetChanged();

                                }
                            }

                        }
                    }
                }else if (panduan.equals("d")){
                    for(int i=0;i<data.size();i++){
                        if(data.get(i) instanceof A){
                            A a_r = (A) data.get(i);
                            if(a_r.getDate().equals(""+d_r)){
                                data.remove(i);

                                saveObject("object.dat", data);
                                abAdapter.notifyDataSetChanged();
                            /*data = (ArrayList) getObject("object.dat");
                            abAdapter = new ABAdapter(MainActivity.this, data);
                            ListView listView1 = (ListView) findViewById(R.id.listView);
                            listView1.setAdapter(abAdapter);*/
                            }
                        }
                    }
                }
              // A a_from_edit= (A) data.get(requestCode);
               // c_return.setText(""+c_r);
               //abAdapter.notifyDataSetChanged();

                //abAdapter.notifyDataSetChanged();
                break;
            }
            default:
                break;
        }
    }




  /*  public void setlist(){
        A a1 = new A("MON","1","我是周一");
        data.add(a1);
        B point = new B(R.drawable.point,"TUE","2",null);
        data.add(point);
        A a2 = new A("WEN","3","我是周三");
        data.add(a2);
    }
*/


    //保存对象
    private void saveObject(String name,ArrayList data){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = this.openFileOutput(name, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
        } catch (Exception e) {
            e.printStackTrace();
            //这里是保存文件产生异常
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    //oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }
    //取得对象
    private Object getObject(String name){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        ArrayList list = new ArrayList();
        try {
            fis = this.openFileInput(name);
            ois = new ObjectInputStream(fis);
            list = (ArrayList) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            //这里是读取文件产生异常
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    //fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    //ois流关闭异常
                    e.printStackTrace();
                }
            }
        }
        //读取产生异常，返回null
        return list;
    }

}
