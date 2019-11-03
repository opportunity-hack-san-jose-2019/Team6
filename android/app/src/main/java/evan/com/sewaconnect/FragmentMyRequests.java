package evan.com.sewaconnect;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class FragmentMyRequests extends Fragment {

    private Context mContext;

    private RecyclerView mRecycler;
    private RequestsAdapter mRequestsAdapter;
    private ArrayList<String> mType = new ArrayList<>();
    private ArrayList<String> mStatus = new ArrayList<>();
    private ArrayList<String> mMessage = new ArrayList<>();
    private ArrayList<String> mTime = new ArrayList<>();
    private ArrayList<String> mImage = new ArrayList<>();





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myrequests, container, false);

        mRecycler = (RecyclerView) view.findViewById(R.id.fragmyrequests_recycler);
        mRequestsAdapter = new RequestsAdapter(mType, mStatus, mMessage, mTime, mImage, mContext);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(mRequestsAdapter);




        mType.add("Family Services");
        mStatus.add("Completed");
        mMessage.add("test input 1");
        mTime.add("Created Oct. 21st, 2019");
        mImage.add("https://i.imgur.com/h9gla36.jpg");


        mType.add("Disaster Recovery");
        mStatus.add("In progress");
        mMessage.add("I would like to partake in helping disaster victims");
        mTime.add("Created Oct. 24th, 2019");
        mImage.add("https://i.imgur.com/q3XVRYl.png");

        mType.add("Volunteering");
        mStatus.add("In progress");
        mMessage.add("I am free to volunteer this weekend");
        mTime.add("Created Oct. 23rd, 2019");
        mImage.add("https://i.imgur.com/Sjwu8rw.jpg");

        mType.add("Sponser a child");
        mStatus.add("In progress");
        mMessage.add("Donating $10 to buy clean water for a child");
        mTime.add("Created Oct. 22nd, 2019");
        mImage.add("https://i.imgur.com/4hwzKcy.jpg");

        mRequestsAdapter.notifyDataSetChanged();



        return view;
    }
}
