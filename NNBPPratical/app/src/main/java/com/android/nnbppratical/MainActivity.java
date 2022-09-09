package com.android.nnbppratical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.nnbppratical.database.AppDatabase;
import com.android.nnbppratical.entity.UserEntity;
import com.android.nnbppratical.util.EditTextValidation;

public class MainActivity extends AppCompatActivity {

    EditText edUsername,edEmail, edDescription;
    Spinner spGender;
    Button btnRegister;
    CheckBox cbAccept;
    String gender = "Male";
    String[] listGender = {"Male", "Female", "Other"};
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getAppDatabase(this);

        initView();

        setSpSex();

        setBtnRegister();
    }

    private void initView() {
        edUsername = findViewById(R.id.edUsername);

        edEmail = findViewById(R.id.edEmail);

        edDescription = findViewById(R.id.edDescription);

        spGender = findViewById(R.id.spGender);

        cbAccept = findViewById(R.id.cbAccept);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void setBtnRegister() {
        UserEntity user = new UserEntity();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String description = edDescription.getText().toString().trim();
                if (username.isEmpty() || description.isEmpty() ||email.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Username,Email and description is required", Toast.LENGTH_SHORT).show();
                }else {
                    if (EditTextValidation.isValidUsername(username)) {
                        if (cbAccept.isChecked()) {
                            user.setUsername(username);
                            user.setEmail(email);
                            user.setDescription(description);
                            user.setSex(gender);
                            db.userDao().insertUser(user);
                            Toast.makeText(MainActivity.this,
                                    "Feedback success", Toast.LENGTH_SHORT).show();
                            toListUser();
                        } else
                            Toast.makeText(MainActivity.this,
                                    "Check the checkbox", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(MainActivity.this,
                                "Username's characters > 4 and < 20", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void toListUser() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void setSpSex() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listGender);
        spGender.setAdapter(adapter);
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = listGender[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}