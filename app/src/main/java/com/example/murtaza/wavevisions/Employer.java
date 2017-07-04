package com.example.murtaza.wavevisions;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Employer extends AppCompatActivity {
    ListView listView;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);
        listView= (ListView) findViewById(R.id.lv);

        FirebaseDatabase.getInstance().getReference("user/Employee").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter=new ArrayAdapter(Employer.this,android.R.layout.simple_dropdown_item_1line);
                for (DataSnapshot d:dataSnapshot.getChildren()){
                 String key=d.getKey();
                    FirebaseDatabase.getInstance().getReference("user/Employee/"+key+"/info").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Emp emp=dataSnapshot.getValue(Emp.class);
                            adapter.add(emp.name+" "+emp.companyid);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String key= parent.getItemAtPosition(position).toString();
            final Dialog dialog=new Dialog(Employer.this);
            dialog.setContentView(R.layout.employee_popup);
            Button track= (Button) dialog.findViewById(R.id.track);
            Button progress= (Button) dialog.findViewById(R.id.progress);
            Button share= (Button) dialog.findViewById(R.id.share);
            Button schedule= (Button) dialog.findViewById(R.id.schedule);
            Button reports= (Button) dialog.findViewById(R.id.reports);
            dialog.show();
            track.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Employer.this,Track.class);
                    startActivity(i);
                    dialog.dismiss();
                }
            });
            progress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Employer.this,Progress.class);
                    startActivity(i);
                    dialog.dismiss();
                }
            });
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Employer.this,Share.class);
                    startActivity(i); dialog.dismiss();
                }
            });
            schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Employer.this,Schedule.class);
                    startActivity(i); dialog.dismiss();
                }
            });
            reports.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Employer.this,Reports.class);
                    startActivity(i); dialog.dismiss();
                }
            });

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
    }}
