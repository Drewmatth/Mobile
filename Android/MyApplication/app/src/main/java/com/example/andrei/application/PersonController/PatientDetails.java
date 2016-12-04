package com.example.andrei.application.PersonController;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.andrei.application.Database.DBController;
import com.example.andrei.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonDetails extends Fragment {

    private DBController dbController;
    public PersonDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_patient_details, container, false);
        final TextView FN=(TextView) v.findViewById(R.id.PersonFirstName);
        final TextView LN=(TextView) v.findViewById(R.id.PersonLastName);
        final TextView Email=(TextView) v.findViewById(R.id.PersonEmail);
        final TextView Phone=(TextView) v.findViewById(R.id.PersonPhone);
        final TextView Country=(TextView) v.findViewById(R.id.PersonCountry);
        final TextView Age=(TextView) v.findViewById(R.id.PersonAge);
        final Button backButton=(Button) v.findViewById(R.id.BackButton);
        final Button deleteButton=(Button) v.findViewById(R.id.DeleteButton);
        //set values from session
        SharedPreferences settings= PreferenceManager.getDefaultSharedPreferences(getContext());
        FN.setText(settings.getString("personFirstName",""));
        LN.setText(settings.getString("personLastName",""));
        Email.setText(settings.getString("personEmail",""));
        Phone.setText(settings.getString("personPhone",""));
        Country.setText(settings.getString("personCountry",""));
        Age.setText(Integer.toString(settings.getInt("personAge",0)));

        //back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PeronsFragment fragment=new PersonsFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,fragment);
                fragmentTransaction.commit();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbController=new DBController();
                final ProgressDialog progDailog = ProgressDialog.show(
                        getActivity(), "Delete", "Delete Person...", true);
                new Thread() {
                    public void run() {
                        try {
                            SharedPreferences settings= PreferenceManager.getDefaultSharedPreferences(getContext());
                            int id=settings.getInt("personID",0);
                            int result = dbController.DeletePerson(id);
                            Thread.sleep(1000);
                            switch(result){
                                case 1:
                                    PersonsFragment fragment=new PersonsFragment();
                                    FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                                    fragmentTransaction.commit();
                                    break;
                                case -1:
                                    getActivity().runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(getActivity(), "Request Error !", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    break;
                            }
                        } catch (Exception e) {
                        }
                        progDailog.dismiss();
                    }
                }.start();
            }
        });
        return v;
    }

}
