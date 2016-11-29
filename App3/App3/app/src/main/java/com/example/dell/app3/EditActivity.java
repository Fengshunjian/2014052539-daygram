package com.example.dell.app3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.data;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final Intent return_data=getIntent();
         TextView r_c= (TextView) findViewById(R.id.a_content);
         final EditText edit_c= (EditText) findViewById(R.id.Edit_content);

        TextView w_and_d= (TextView) findViewById(R.id.Edit_wd);

        final String w=return_data.getStringExtra("week");
        final String d=return_data.getStringExtra("date");
        String year=return_data.getStringExtra("year");
        String month=return_data.getStringExtra("month");
        String c=return_data.getStringExtra("content");
        w_and_d.setText(""+w+" / "+d+" /  "+month+" / "+year);
        edit_c.setText(""+c);
        String return_c=edit_c.getText().toString();

        Button done = (Button) findViewById(R.id.button_done);
        done.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               //采用Intent绑定Bundle的形式回传值
                 Intent intent_r =getIntent();

                    String return_c=edit_c.getText().toString();
                    //将Bundle赋给Intent
                    intent_r.putExtra("return_w",w);
                    intent_r.putExtra("return_d",d);
                    intent_r.putExtra("return_c",return_c);
                    intent_r.putExtra("panduan","y");
                    //跳转回MainActivity
                    //注意下面的RESULT_OK常量要与回传接收的Activity中onActivityResult（）方法一致
                    EditActivity.this.setResult(RESULT_OK, intent_r);
                    //关闭当前activity
                    EditActivity.this.finish();

            }
        });
        Button delete = (Button) findViewById(R.id.detele);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_r =getIntent();

                String return_c=edit_c.getText().toString();
                //将Bundle赋给Intent
                intent_r.putExtra("return_w",w);
                intent_r.putExtra("return_d",d);
                intent_r.putExtra("return_c",return_c);
                intent_r.putExtra("panduan","d");
                //跳转回MainActivity
                //注意下面的RESULT_OK常量要与回传接收的Activity中onActivityResult（）方法一致
                EditActivity.this.setResult(RESULT_OK, intent_r);
                Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_LONG).show();
                //关闭当前activity
                EditActivity.this.finish();
            }
        });
    }
}
