package evan.com.sewaconnect;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class FragmentLookingForHelp extends Fragment {

    private FirebaseFirestore db;
    private EditText editTextName, editTextAge, editTextMore,
            editTextPhone, editTextEmail, editTextLocation;
    private String mName, mAge, mMore, mPhone, mEmail, mLocation;
    private double mLng, mLat;

    private TextView m1, m2, m3, m4, m5;

    //private FusedLocationProviderClient fusedLocationClient;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();


        db = FirebaseFirestore.getInstance();


        //too many methods


        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        //if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
         //       PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
         //       != PackageManager.PERMISSION_GRANTED) {
          //  // TODO: Consider calling
          //  //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
         //   requestPermissions(new String[]{
         //                   android.Manifest.permission.ACCESS_FINE_LOCATION},
          //          1);


          //  return;
        //}

        //fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
        //    @Override
        //    public void onSuccess(Location location) {
        //        Log.d(TAG, "onSuccess: " + location.getLatitude());
       //         Log.d(TAG, "onSuccess: " + location.getLongitude());
        //        mLat = location.getLatitude();
        //        mLng = location.getLongitude();
        //    }
        //});


    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lookingforhelp, container, false);

        editTextName = (EditText) view.findViewById(R.id.fragform_name);
        editTextAge = (EditText) view.findViewById(R.id.fragform_age);
        editTextMore = (EditText) view.findViewById(R.id.fragform_moreinfo);
        editTextPhone = (EditText) view.findViewById(R.id.fragform_edittext_phone);
        editTextEmail = (EditText) view.findViewById(R.id.fragform_edittext_email);
        editTextLocation = (EditText) view.findViewById(R.id.fragform_edittext_location);

        Button submitButton = (Button) view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mName = editTextName.getText().toString().trim();
                mAge = editTextAge.getText().toString().trim();
                mMore = editTextMore.getText().toString().trim();
                mPhone = editTextPhone.getText().toString().trim();
                mEmail = editTextEmail.getText().toString().trim();
                mLocation = editTextLocation.getText().toString().trim();

                submitData();

            }
        });


        m1= view.findViewById(R.id.textview_spanish);
        m2= view.findViewById(R.id.textview_arabic);
        m3= view.findViewById(R.id.textview_mandarin);
        m4= view.findViewById(R.id.textview_bengali);
        m5= view.findViewById(R.id.textview_other);

        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m1.setBackgroundResource(R.drawable.select_days_checked);
                m1.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        });

        m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m2.setBackgroundResource(R.drawable.select_days_checked);
                m2.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        });

        m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m3.setBackgroundResource(R.drawable.select_days_checked);
                m3.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        });

        m4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m4.setBackgroundResource(R.drawable.select_days_checked);
                m4.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        });

        m5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m5.setBackgroundResource(R.drawable.select_days_checked);
                m5.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        });

        return view;
    }

    private void submitData(){

        Map<String, Object> user = new HashMap<>();
        user.put("name", mName);
        user.put("age", mAge);
        user.put("additionalInfo", mMore);
        user.put("phone", mPhone);
        user.put("email", mEmail);




        Map<String, Object> location = new HashMap<>();
        location.put("lat", mLat);
        location.put("lon", mLng);

        user.put("location", location);



        db.collection("help_request")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        closeKeyboard(getActivity());
                        Toast.makeText(getContext(), "User info submitted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

    public void closeKeyboard(FragmentActivity activity){
        View view = activity.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null){
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
