package com.library.apple.food;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AccountFragment extends Fragment {

    TextView name1,emailPhon1,address1;
    View myView;

    EditText et_name,et_email,et_phone,et_address;

    TextView btn_edit;
    TextView btn_address;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_account,container,false);

        name1 = (TextView)myView.findViewById(R.id.toolbar_account);
        emailPhon1 = (TextView)myView.findViewById(R.id.editde10);
        address1 = (TextView)myView.findViewById(R.id.editde8);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginToken",Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("token","error");
        String first_name = sharedPreferences.getString("first_name","First Name");
        String last_name = sharedPreferences.getString("last_name","Last Name");
        String email = sharedPreferences.getString("email","Email");

        String name2 = first_name+" "+last_name;
        name1.setText(name2);

        emailPhon1.setText(email);

        String address = "BMSIT BOYS HOSTEL";
        address1.setText(address);




        et_name = (EditText)myView.findViewById(R.id.name);
        et_email = (EditText)myView.findViewById(R.id.email);
        et_phone = (EditText)myView.findViewById(R.id.phone);
        et_address = (EditText)myView.findViewById(R.id.address);



        et_name.setText(name2);
        et_email.setText(email);






        btn_edit = (TextView)myView.findViewById(R.id.editde);
        btn_address = (TextView)myView.findViewById(R.id.editde3);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name2 = et_name.getText().toString().trim();
                String email2 = et_email.getText().toString().trim();
                String phone2 = et_phone.getText().toString().trim();

                Toast.makeText(getActivity(),"POST"+name2,Toast.LENGTH_LONG).show();

            }
        });


        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String address2 = et_address.getText().toString().trim();

            }
        });


        Button logout = (Button)myView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginToken", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();

                Intent intent = new Intent(getActivity(),login.class);
                startActivity(intent);

            }
        });





        return myView;
    }
}
