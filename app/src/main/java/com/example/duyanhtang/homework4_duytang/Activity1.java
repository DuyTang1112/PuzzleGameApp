package com.example.duyanhtang.homework4_duytang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Activity1 extends AppCompatActivity {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        btn=(Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity1.this,Activity2.class);
                startActivity(intent);
            }
        });
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==0) {
            Intent intent=new Intent(Activity1.this,Activity2.class);
            startActivityForResult(intent,1);
        }
    }*/
}
