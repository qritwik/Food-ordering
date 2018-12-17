package com.library.apple.food;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class ExploreFragment extends Fragment {

    View myView;

    private EditText searchFeild;
    private FloatingActionButton floatingActionButton1;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter12;
    RecyclerView.LayoutManager layoutManager;
    private List<Res> resList;

    private String res_url = "http://www.hungermela.com/api/v1/restaurants/";

    private ShimmerFrameLayout mShimmerViewContainer;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_explore,container,false);

        mShimmerViewContainer = myView.findViewById(R.id.shimmer_view_container12);

        mShimmerViewContainer.startShimmerAnimation();

        resList = new ArrayList<>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginToken",Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("token","error");


        searchFeild = (EditText)myView.findViewById(R.id.editText);
        floatingActionButton1 = (FloatingActionButton)myView.findViewById(R.id.floatingActionButton);

        recyclerView = (RecyclerView)myView.findViewById(R.id.search_list);
        adapter12 = new ResAdapter(getActivity(),resList,name);
        recyclerView.setAdapter(adapter12);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        getData();



        searchFeild.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String query = charSequence.toString().toLowerCase();
                final List<Res> filteredList = new ArrayList<>();

                for(Res item : resList){
                    if(item.getRes_name().toLowerCase().contains(query.toLowerCase())){
                        filteredList.add(item);
                    }
                }

                recyclerView.setLayoutManager(layoutManager);
                adapter12 = new ResAdapter(getActivity(),filteredList,name);
                recyclerView.setAdapter(adapter12);
                adapter12.notifyDataSetChanged();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return myView;

    }




    private void getData() {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(res_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        final Res res = new Res();

                        res.setRes_id(jsonObject.getString("id"));
                        res.setRes_phone_number(jsonObject.getString("phone_number"));
                        res.setRes_line_1(jsonObject.getString("line_1"));
                        res.setRes_line_2(jsonObject.getString("line_2"));
                        res.setRes_city(jsonObject.getString("city"));
                        res.setRes_state(jsonObject.getString("state"));
                        res.setRes_zip_code(jsonObject.getString("zip_code"));
                        res.setRes_chain(jsonObject.getString("chain"));
                        res.setRes_part_of(jsonObject.getString("part_of"));

                        res.setRes_name(jsonObject.getString("name"));


                        String cat1 = jsonObject.getString("category");


                        //Json object request for category

                        String cat_url = "http://www.hungermela.com/api/v1/categories/"+cat1;


                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                cat_url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response1) {
                                Log.d(TAG, response1.toString());

                                try {

                                    res.setRes_cat(response1.getString("name"));

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d(TAG, "Error: " + error.getMessage());
                                Toast.makeText(getActivity(),
                                        error.getMessage(), Toast.LENGTH_SHORT).show();
                                // hide the progress dialog

                            }
                        });

                        MySingleton1.getInstance(getActivity()).addToRequestQueue(jsonObjReq);













                        //Ends here



                        res.setRes_image(jsonObject.getString("image"));



                        res.setRes_loc(jsonObject.getString("city"));
                        res.setRes_open(jsonObject.getString("open_time"));
                        res.setRes_close(jsonObject.getString("close_time"));


                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);



                        resList.add(res);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
                adapter12.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();


            }
        });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);

    }



}
