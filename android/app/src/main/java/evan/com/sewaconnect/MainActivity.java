package evan.com.sewaconnect;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MainActivity";
    private DrawerLayout drawer;
    private NavigationView mNavigationView;
    private FirebaseAuth mAuth;
    private String mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGetDeviceEndpoint();

        initToolbar();
        initUserProfile();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    new FragmentHome(), "HOME_FRAGMENT").commit();
        }

        mAuth = FirebaseAuth.getInstance();
        //mCurrentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Log.d(TAG, "onCreate: USERID: " + mCurrentUser);





    }

    private void initGetDeviceEndpoint() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.d(TAG, "Firebase Device Token: Success: " + newToken);

            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Firebase Device Token: Failure: " + e);
            }
        });

    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerSlideAnimationEnabled(false);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.getMenu().getItem(0).setChecked(true);
        mNavigationView.setNavigationItemSelectedListener(this);

        ImageView iconProfile = findViewById(R.id.mainactivity_icon_profile);
        iconProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, new FragmentMyRequests())
                            .addToBackStack(null).commit();
            }
        });

    }

    private void initUserProfile(){
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = mNavigationView.getHeaderView(0);
        ImageView drawerProfilePic = (ImageView) hView.findViewById(R.id.drawer_profile_pic);
        Glide.with(this).load(R.drawable.photo_jon_snow).apply(RequestOptions.centerCropTransform()).into(drawerProfilePic);
    }

    @Override
    public void onBackPressed() {

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().executePendingTransactions();
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentHome(), "HOME_FRAGMENT").commit();
                break;

            case R.id.nav_messages:

                break;

            case R.id.nav_requests:

                break;

            case R.id.nav_volunteers:

                return false;
            case R.id.nav_settings:

                break;

            case R.id.nav_about:

                break;

            case R.id.nav_signout:

                //AWSMobileClient.getInstance().signOut();
                //Replace with Firebase signout

                Intent intentSignOut = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentSignOut);
                this.finish();

                return false;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


}
