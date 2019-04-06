package com.example.bleh.myapplication;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bleh.myapplication.DB.AppDatabase;
import com.example.bleh.myapplication.DB.Plan;
import com.example.bleh.myapplication.DB.User;

import java.util.ArrayList;
import java.util.List;

public class SignUp2 extends AppCompatActivity {

    private Button signUp;
    private String username;
    private String lname;
    private String fname;
    private String password;

    Spinner sex;


    private AppDatabase mydb;


    private View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signUpClicked(v);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        signUp = findViewById(R.id.signup);
        signUp.setOnClickListener(OnClickListener);
        Intent intent = getIntent();
        username = intent.getExtras().getString("username");
        lname = intent.getExtras().getString("lastname");
        fname = intent.getExtras().getString("firstname");
        password = intent.getExtras().getString("password");
        mydb = AppDatabase.getInstance(SignUp2.this);

        List<String> spinnerSex =  new ArrayList<String>();
        spinnerSex.add("female");
        spinnerSex.add("male");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerSex);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex = (Spinner) findViewById(R.id.sex);
        sex.setAdapter(adapter);
    }


    public void signUpClicked(View view) {
        User user = new User();//make a constructor for user to receive all the data to auto fill
        user.birthDay=Integer.parseInt(((EditText)findViewById(R.id.yob)).getText().toString());
        user.height=Double.parseDouble(((EditText)findViewById(R.id.height)).getText().toString());
        user.weight=Double.parseDouble(((EditText)findViewById(R.id.weight)).getText().toString());
        user.sex=  sex.getSelectedItem().toString();
        user.username=username;
        user.lastName=lname;
        user.firstName=fname;
        user.password=password;

        long uid = mydb.getUserDao(SignUp2.this).insert(user);
        Plan plan = new Plan();
        plan.uid=(int) uid;
        plan.workoutPerWeek =Integer.parseInt(((EditText)findViewById(R.id.workoutweekly)).getText().toString());
        long planid = mydb.getPlanDao(SignUp2.this).insert(plan);

        Intent intent = new Intent(this, feature1.class);
        intent.putExtra("uid",uid);
        intent.putExtra("planid",planid);
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("EnterCount",-1);
        editor.putLong("UserID",uid);
        editor.putLong("PlanID",planid);
        editor.putInt("WaterCount",0);
        editor.apply();
        Toast.makeText(this,"Welcome to the Well-Being Application",Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }
}