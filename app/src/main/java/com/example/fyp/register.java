package com.example.fyp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    Button register_btn, login_btn;
    EditText input_email, input_password, user_first_name, user_last_name, investment, purpose;
    Spinner sex, age, occupation, marriage, income;
    FirebaseAuth mAuth;
    static final String TAG="Register_page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_btn = findViewById(R.id.register_btn);
        login_btn = findViewById(R.id.login_btn);
        input_email = findViewById(R.id.user_input_email);
        input_password = findViewById(R.id.user_input_password);
        user_first_name = findViewById(R.id.user_first_name);
        user_last_name = findViewById(R.id.user_last_name);
        sex = findViewById(R.id.sex_choice);
        age = findViewById(R.id.age_group_choice);
        occupation = findViewById(R.id.occupation_choice);
        marriage = findViewById(R.id.marriage_choice);
        income = findViewById(R.id.income_choice);
        investment = findViewById(R.id.investment_time);
        purpose = findViewById(R.id.purpose);

        mAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==login_btn)   {
                    startActivity(new Intent(register.this, login.class));
                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==register_btn)   {
                    register();
                }
            }
        });
    }

    public void register() {
        String email=input_email.getText().toString().trim();
        String password=input_password.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())   {
                            try {
                                throw task.getException();
                            } catch (Exception e)   {
                                Log.e(TAG, e.getMessage());
                            }
                        } else  {
                            String user_id=mAuth.getCurrentUser().getUid();
                            DatabaseReference user_database= FirebaseDatabase.getInstance().getReference().child("User").child(user_id);

                            String first_name=user_first_name.getText().toString();
                            String last_name=user_last_name.getText().toString();
                            String usersex=sex.getSelectedItem().toString();
                            String userage=age.getSelectedItem().toString();
                            String userocc=occupation.getSelectedItem().toString();
                            String usermarr=marriage.getSelectedItem().toString();
                            String userincome=income.getSelectedItem().toString();
                            String userinvestment=investment.getText().toString();
                            String userpurpose=purpose.getText().toString();


                            Map newPost=new HashMap();
                            newPost.put("First Name", first_name);
                            newPost.put("Last Name", last_name);
                            newPost.put("Sex", usersex);
                            newPost.put("Age", userage);
                            newPost.put("Occupation", userocc);
                            newPost.put("Marriage", usermarr);
                            newPost.put("Yearly Income", userincome);
                            newPost.put("Investment time period(Month)", userinvestment);
                            newPost.put("Purpose of investment", userpurpose);

                            user_database.setValue(newPost);

                        }
                    }
                });
    }
}
