package com.example.murtaza.wavevisions;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Siginin extends AppCompatActivity {
    EditText name,uid;
    RadioGroup rg;
    FirebaseDatabase database;
    Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siginin);
          name= (EditText) findViewById(R.id.name);
          uid= (EditText) findViewById(R.id.id);
          rg= (RadioGroup) findViewById(R.id.radioGroup);
         signin = (Button) findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String namee=name.getText().toString().trim();
                final String uidd=uid.getText().toString().trim();
                int id = rg.getCheckedRadioButtonId();
                RadioButton rb= (RadioButton) findViewById(id);
                final String type = rb.getText().toString();
                if (namee.isEmpty() || uidd.isEmpty()){
                    Toast.makeText(Siginin.this,"Enter proper name and UID ",Toast.LENGTH_LONG).show();
                }
                else {
                    FirebaseDatabase.getInstance().getReference("user/"+type).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                             for (DataSnapshot s: dataSnapshot.getChildren()){
                                 String key=s.getKey();
                                 String user=namee+" "+uidd;
                                 if (key.equals(user)){
                                     SharedPreferences sharedPreferences=getSharedPreferences("main",0);
                                     SharedPreferences.Editor editor=sharedPreferences.edit();
                                     editor.putString("type_of_user",type);
                                     editor.putBoolean("logged",true);
                                     editor.apply();
                                     if (type.equals("Employee")){
                                         Intent employee= new Intent(Siginin.this,Employee.class);
                                         startActivity(employee);
                                         finish();
                                     }else if (type.equals("Employer")){
                                         Intent employee= new Intent(Siginin.this,Employer.class);
                                         startActivity(employee);
                                         finish();
                                     }
                                     else if (type.equals("Admin")){
                                         Intent employee= new Intent(Siginin.this,Admin.class);
                                         startActivity(employee);
                                         finish();
                                     }
                                 }
                             }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }


            }
        });
    }
}
