package com.library.apple.food;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddAddress extends AppCompatActivity {

    EditText add_line1,add_phone_number1;

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

    Button btn_create_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        add_line1 = (EditText)findViewById(R.id.add_line11);
        add_phone_number1 = (EditText)findViewById(R.id.add_phone_number11);

        radioSexGroup=(RadioGroup)findViewById(R.id.radioGroup);

        btn_create_address = (Button)findViewById(R.id.btn_create_address);

        btn_create_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId=radioSexGroup.getCheckedRadioButtonId();
                radioSexButton=(RadioButton)findViewById(selectedId);
            }
        });


    }
}
