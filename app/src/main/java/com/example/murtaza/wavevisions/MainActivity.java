package com.example.murtaza.wavevisions;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    EditText name,age,uid,email,password;
    String name_emp,age_emp,uid_emp,email_emp,password_emp,type_emp;
    Button register, signin;
    private FirebaseAuth mAuth;
    UUID uuid;
    RandomStringUtils randomStringUtils;

    FirebaseDatabase firebaseDatabase;
    SharedPreferences sharedPreferences;
    RadioGroup radioGroup;
    RadioButton rb;
    boolean logged=false;
    String type_of_user="undefined";
    boolean persis=true;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         sharedPreferences=getSharedPreferences("main",0);
         persis=sharedPreferences.getBoolean("persis",true);
         if (persis) {
             FirebaseDatabase.getInstance().setPersistenceEnabled(true);
             SharedPreferences.Editor editor=sharedPreferences.edit();
             editor.putBoolean("persis",false);
             editor.apply();
         }


         logged=sharedPreferences.getBoolean("logged",false);
         if (logged){
             type_of_user=sharedPreferences.getString("type_of_user","undefined");
             if (type_of_user.equals("Employee")){
                 Intent employee=     new Intent(MainActivity.this,Employee.class);
                 startActivity(employee);
                 finish();
             }else if (type_of_user.equals("Employer")){
                 Intent employee=     new Intent(MainActivity.this,Employer.class);
                 startActivity(employee);
                 finish();
             }
             else if (type_of_user.equals("Admin")){
                 Intent employee=     new Intent(MainActivity.this,Admin.class);
                 startActivity(employee);
                 finish();
             }
         }
         else {


         }
         name= (EditText) findViewById(R.id.name);
         age= (EditText) findViewById(R.id.age);
         uid= (EditText) findViewById(R.id.companyid);
         email= (EditText) findViewById(R.id.email);
         password= (EditText) findViewById(R.id.password);
         register= (Button) findViewById(R.id.register);
         signin= (Button) findViewById(R.id.signin);
         radioGroup= (RadioGroup) findViewById(R.id.rg);

         signin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
Intent i = new Intent(MainActivity.this,Siginin.class);
                 startActivity(i);
                 finish();
             }
         });


         register.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 tos("wait for response");
            name_emp=name.getText().toString().trim();
            age_emp=age.getText().toString().trim();
            uid_emp=uid.getText().toString().trim();
            email_emp=email.getText().toString().trim();
            password_emp=password.getText().toString().trim();

                 if (name_emp.isEmpty() || age_emp.isEmpty() || uid_emp.isEmpty() || email_emp.isEmpty() || password_emp.isEmpty()){
                     tos("Please fill details properly");
                 }
                 else {
                     saveData();
                 }

             }
         });
     }
     public void tos(String msg){
         Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
     }
     public void saveData(){
         Emp user= new Emp();
         user.name=name_emp;
         user.age=age_emp;
         user.companyid=uid_emp;
         user.email=email_emp;
         user.password=password_emp;
         int id = radioGroup.getCheckedRadioButtonId();
         rb= (RadioButton) findViewById(id);
         type_emp=rb.getText().toString();
         user.type=type_emp;
         FirebaseDatabase.getInstance().getReference("user").child(type_emp).child(name_emp+" "+uid_emp).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void aVoid) {
                 tos("user created succesfully \n Sign In with name and ID");
                 Intent i =getIntent();
                 startActivity(i);
                 //finish();
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                tos(e.toString());
             }
         });
     }

}
