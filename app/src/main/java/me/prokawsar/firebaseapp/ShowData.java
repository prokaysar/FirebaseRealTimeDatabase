package me.prokawsar.firebaseapp;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

public class ShowData extends AppCompatActivity {
    private ListView listView;
    private List<User> userList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        listView = findViewById(R.id.user_list_id);
        userList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("User Info");

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                User user = userList.get(position);
                showUpdateDiallodg(user.getId(),user.getName(),user.getEmail(),user.getPhone(),user.getGender(),user.getAge());
                return false;
            }
        });
    }
    //Show Alert Dialog
    private void showUpdateDiallodg(final String userId, String userName,String email,String phone,String gender, String age){
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowData.this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_layout,null);
        builder.setView(dialogView);
        final EditText nameText = dialogView.findViewById(R.id.new_name_id);
        final EditText emailText = dialogView.findViewById(R.id.new_email_id);
        final EditText phoneText = dialogView.findViewById(R.id.new_phone_id);
        final EditText genderText = dialogView.findViewById(R.id.new_gender_id);
        final EditText ageText = dialogView.findViewById(R.id.new_age_id);
        Button updateButton = dialogView.findViewById(R.id.update_id);
        builder.setTitle("Update Info : "+userName);
        nameText.setText(userName);
        emailText.setText(email);
        phoneText.setText(phone);
        genderText.setText(gender);
        ageText.setText(age);

        //delete operation are here
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deleteData(userId);
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameText.getText().toString().trim();
                String email = emailText.getText().toString().trim();
                String phone = phoneText.getText().toString().trim();
                String gender = genderText.getText().toString().trim();
                String age = ageText.getText().toString().trim();

                update(userId,name,email,phone,gender,age);

                alertDialog.dismiss();
            }
        });

    }

    private void deleteData(String userId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User Info").child(userId);
        reference.removeValue();
        Toast.makeText(this, "Data Deleted..", Toast.LENGTH_SHORT).show();

    }

    private boolean update(String id,String name,String email,String phone,String gender,String age) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User Info").child(id);
         User user = new User(id,name,email,phone,gender,age);
        databaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ShowData.this, "Data updated successfully.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ShowData.this, "Data not updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userData : dataSnapshot.getChildren()){
                    User uData = userData.getValue(User.class);
                    userList.add(uData);
                }
                ArrayAdapter<User> adapter = new ArrayAdapter<>(ShowData.this,android.R.layout.simple_list_item_1,userList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
