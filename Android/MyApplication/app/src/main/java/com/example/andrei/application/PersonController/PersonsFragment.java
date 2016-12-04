package com.example.andrei.application.PersonController;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.andrei.application.Authentication.LoginActivity;
import com.example.andrei.application.Database.DBController;
import com.example.andrei.application.Model.Person;
import com.example.andrei.application.Util.OnFailureListener;
import com.example.andrei.application.Util.OnSuccessListener;
import com.example.andrei.myapplication.R;
import java.util.ArrayList;

public class PersonsFragment extends Fragment {

    private Person[] PERSONS = new Person[]{};
    private DBController dbController;
    private ListView listView;
    private ListAdapter la;

    public PersonsFragment() {
        // Required empty public constructor
    }
    private boolean isOnline(){
        ConnectivityManager cm=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo=cm.getActiveNetworkInfo();
        return netInfo!=null && netInfo.isConnectedOrConnecting();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v=inflater.inflate(R.layout.fragment_all_persons, container, false);
        dbController=new DBController();
        listView= (ListView) v.findViewById(R.id.personsList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("personID", PERSONS[position].getID());
                    editor.putString("personFirstName", PERSONS[position].getFirstName());
                    editor.putString("personLastName", PERSONS[position].getLastName());
                    editor.putString("personEmail", PERSONS[position].getEmail());
                    editor.putString("personPhone",PERSONS[position].getPhone());
                    editor.putString("personCountry", PERSONS[position].getCountry());
                    editor.putInt("personAge", PERSONS[position].getAge());
                    editor.commit();
                    PatientDetails fragment=new PatientDetails();
                    FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    fragmentTransaction.commit();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        //get PERSONS for doctors
        final ProgressDialog progDailog = ProgressDialog.show(
                getContext(), "", "Loading...", true);
        new Thread(){
            public void run(){
                try {
                    Thread.sleep(500);
                    OnSuccessListener<ArrayList<Patient>> onSuccessListener = new OnSuccessListener<ArrayList<Patient>>() {
                        @Override
                        public void onSuccess(final ArrayList<Patient> array) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progDailog.dismiss();
                                    PERSONS = array.toArray(new Patient[0]);
                                    la = new ArrayAdapter<Patient>(getContext(), android.R.layout.simple_list_item_1, PERSONS);
                                    listView.setAdapter(la);
                                }
                            });
                        }
                    };
                    OnFailureListener<Exception> onFailureListener = new OnFailureListener<Exception>() {
                        @Override
                        public void onFailure(final Exception e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progDailog.dismiss();
                                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    };
                    MyPersons(onSuccessListener,onFailureListener);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    progDailog.dismiss();
                }
            }
        }.start();
        return v;
    }
    private void MyPersons(OnSuccessListener<ArrayList<Patient>> onSuccessListener, OnFailureListener<Exception> onFailureListener){
        dbController=new DBController();
        SharedPreferences settings= PreferenceManager.getDefaultSharedPreferences(getContext());
        int UserID=settings.getInt("id",0);
        dbController.myPersons(UserID,onSuccessListener,onFailureListener);
    }

}
