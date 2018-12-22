package com.example.bleh.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;


public class SignUp extends AppCompatActivity {

    private Button next;

    private View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onButtonClicked(v);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        next = findViewById(R.id.next1);

        next.setOnClickListener(OnClickListener);
        //AppDatabase db = AppDatabase.getInMemoryDatabase(getApplicationContext());
    }

    public void onButtonClicked(View view) {
        if(verifyInfo(((EditText)findViewById(R.id.password)).getText().toString(),((EditText)findViewById(R.id.confirmPassword)).getText().toString()))
        {
            Intent intent = new Intent(this, SignUp2.class);
            //putExtra can take keye + custom object (user for example), make user serializable or parcelable to send it as one object
            //make a variable for every edit text instead of converting it on spot like this
            intent.putExtra("username",((EditText)findViewById(R.id.username)).getText().toString());
            intent.putExtra("lastname",((EditText)findViewById(R.id.lname)).getText().toString());
            intent.putExtra("firstname",((EditText)findViewById(R.id.fname)).getText().toString());
            intent.putExtra("password",((EditText)findViewById(R.id.password)).getText().toString());//hash before passing

            startActivity(intent);
            finish();
        }
        else
        {
            next.setText("failed");
        }

    }

    public boolean verifyInfo(String p,String cp)
    {// can add check if username is a valid email or not
        if(p.equals(cp))
            return  true;
        return  false;
    }



}
