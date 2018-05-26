package me.prokawsar.firebaseapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText nameText,emailText,phoneText,genderText,ageText;
    private Button button,showButton;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameText = findViewById(R.id.name_id);
        emailText = findViewById(R.id.email_id);
        emailText = findViewById(R.id.email_id);
        phoneText = findViewById(R.id.phone_id);
        genderText = findViewById(R.id.gender_id);
        ageText = findViewById(R.id.age_id);
        button = findViewById(R.id.button2);
        showButton = findViewById(R.id.button3);
        mDatabase = FirebaseDatabase.getInstance().getReference("User Info");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ShowData.class));
            }
        });

    }

    private void addUser() {
        String name = nameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String phone = phoneText.getText().toString().trim();
        String gender = genderText.getText().toString().trim();
        String age = ageText.getText().toString().trim();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone)&& !TextUtils.isEmpty(gender)&& !TextUtils.isEmpty(age) ){
            String id =  mDatabase.push().getKey();
            User user = new User(id,name,email,phone,gender,age);
            mDatabase.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Data inserted successflly", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, "Data not inserted.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(this, "Field must not be empty !", Toast.LENGTH_SHORT).show();
        }
    }


}
