package com.example.user.moviesseries;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button=findViewById(R.id.srch);
        editText=findViewById(R.id.search);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search=editText.getText().toString();
                Intent intent=new Intent(MainActivity.this,Result.class);
                if(search.length()>0){
                    search = search.replaceAll(" ","_");
                    intent.putExtra("name",search);
                    startActivity(intent);
                }
            }
        });
        /*editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                Intent intent=new Intent(MainActivity.this,Result.class);
                String search=editText.getText().toString();
                if(search.length()>0){
                    search.replaceAll("","_");
                    Toast.makeText(getApplicationContext(),search,Toast.LENGTH_LONG).show();
                    intent.putExtra("name",search);
                    startActivity(intent);
                }
                return false;
            }
        });*/
    }
}
