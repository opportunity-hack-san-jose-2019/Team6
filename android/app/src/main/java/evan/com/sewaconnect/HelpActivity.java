package evan.com.sewaconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText mEditText;
    private Button mButton;
    private RequestQueue mQueue;
    private RecyclerView mRecyclerView;
    private ChatMessageAdapter mChatMessageAdapter;
    private List<String> mMessageList = new ArrayList<>();
    private List<String> mSentBy = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        mEditText = findViewById(R.id.activityvideo_sendmessage_edittext);
        mButton = findViewById(R.id.activityvideo_sendmessage_button);
        mRecyclerView = (RecyclerView) findViewById(R.id.activityvideo_recycler);

        mChatMessageAdapter = new ChatMessageAdapter(mMessageList, mSentBy, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mChatMessageAdapter);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mMessage = mEditText.getText().toString().trim();
                sendRequest(mMessage);
                mMessageList.add(mMessage);
                mSentBy.add("User");
                mChatMessageAdapter.notifyItemInserted(mMessageList.size()-1);
                mRecyclerView.smoothScrollToPosition(mMessageList.size()-1);
                mEditText.getText().clear();
            }
        });



    }

    private void sendRequest(String mMessage){
        mQueue = Volley.newRequestQueue(this);
        try{
            String URL = "https://a81qsmzelg.execute-api.us-east-1.amazonaws.com/FF/testapp";
            JSONObject jsonBody = new JSONObject();

            //dont need depth or parent_id

            jsonBody.put("message_string", mMessage);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try{
                        String lexMessage = response.getString("lex_message");
                        Log.d(TAG, "onResponse: LexMessage: " + lexMessage);

                        mMessageList.add(lexMessage);
                        mSentBy.add("Lex");
                        mChatMessageAdapter.notifyItemInserted(mMessageList.size()-1);
                        mRecyclerView.smoothScrollToPosition(mMessageList.size()-1);

                    }catch (Exception e){
                        Log.d(TAG, "onResponse: Exception LOL " + e);
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d(TAG, "onErrorResponse: " + error);

                }
            });

            mQueue.add(jsonObjectRequest);

        }catch (JSONException e){
            Log.d(TAG, "onCreate: ERROR" + e);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HelpActivity.this, MainActivity.class);
        startActivity(intent);
    }
}



