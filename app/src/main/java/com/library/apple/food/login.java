package com.library.apple.food;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    EditText et_email,et_password;
    Button btn_login;

    @Override
    protected void onStart() {

        SharedPreferences sharedPreferences = getSharedPreferences("loginToken",Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("token","error");

        if(!(name.equalsIgnoreCase("error"))){
            Intent intent = new Intent(login.this,MainActivity.class);
            intent.putExtra("token",name);
            startActivity(intent);
        }

        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = (EditText)findViewById(R.id.input_email);
        et_password = (EditText)findViewById(R.id.input_password);

        TextView link_signup = (TextView)findViewById(R.id.link_signup);
        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,Register.class);
                startActivity(intent);
            }
        });



        btn_login = (Button)findViewById(R.id.btn_login);



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = et_email.getText().toString().trim();
                final String password = et_password.getText().toString().trim();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


                String url = "https://www.hungermela.com/api/v1/login/";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                // response
                                Log.d("Response", response);
                                try {

                                    JSONObject jsonObject = new JSONObject(response);

                                    JSONObject user = jsonObject.getJSONObject("user");
                                    String id = user.getString("id");
                                    String last_login = user.getString("last_login");
                                    String name = user.getString("name");
                                    String first_name = user.getString("first_name");
                                    String last_name = user.getString("last_name");
                                    String email = user.getString("email");


                                    //Store token in shared preference
                                    SharedPreferences sharedPreferences = getSharedPreferences("loginToken", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("token",jsonObject.getString("token"));
                                    editor.putString("id",id);
                                    editor.putString("last_login",last_login);
                                    editor.putString("name",name);
                                    editor.putString("first_name",first_name);
                                    editor.putString("last_name",last_name);
                                    editor.putString("email",email);
                                    editor.commit();


                                    Intent intent = new Intent(login.this,MainActivity.class);
                                    intent.putExtra("token",jsonObject.getString("token"));
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.d("Error.Response", error.toString());
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                ) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", email);
                        params.put("password", password);

                        return params;
                    }
                };
                queue.add(postRequest);



            }
        });
    }
}
