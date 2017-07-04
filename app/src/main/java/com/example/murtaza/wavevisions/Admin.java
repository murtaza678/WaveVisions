package com.example.murtaza.wavevisions;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.lang3.RandomStringUtils;

public class Admin extends AppCompatActivity {
    Button gen;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        gen= (Button) findViewById(R.id.gen);
        textView= (TextView) findViewById(R.id.textView);

        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=RandomStringUtils.randomAlphanumeric(12);
                textView.setText(key);
                FirebaseDatabase.getInstance().getReference("key").setValue(key);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the action bar if it is present.
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
            SharedPreferences sharedPreferences=getSharedPreferences("main",0);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("type_of_user","undefined");
            editor.putBoolean("logged",false);
            editor.apply();
            Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_LONG).show();
            finish();

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
