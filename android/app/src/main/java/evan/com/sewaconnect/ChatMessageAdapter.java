package evan.com.sewaconnect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mMessage, mSentBy;
    private Context mContext;
    private final int BOT = 0;
    private final int USER = 1;


    public ChatMessageAdapter(List<String> mMessage, List<String> mSentBy, Context mContext) {
        this.mMessage = mMessage;
        this.mSentBy = mSentBy;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        if (mSentBy.get(position).equals("Lex")) {
            return BOT;
        } else if (mSentBy.get(position).equals("User")) {
            return USER;
        }

        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case USER:
                View v1 = inflater.inflate(R.layout.modelrv_user_message, viewGroup, false);
                viewHolder = new ViewHolderUser(v1);
                break;
            case BOT:
                View v2 = inflater.inflate(R.layout.modelrv_bot_message, viewGroup, false);
                viewHolder = new ViewHolderBot(v2);
                break;
            default:
                View v = inflater.inflate(R.layout.modelrv_user_message, viewGroup, false);
                viewHolder = new ViewHolderUser(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {
            case BOT:
                ViewHolderBot vh1 = (ViewHolderBot) viewHolder;

                vh1.viewHolderMessage.setText(mMessage.get(position));
                break;
            case USER:
                ViewHolderUser vh2 = (ViewHolderUser) viewHolder;
                vh2.viewHolderMessage.setText(mMessage.get(position));

                break;
            default:
                ViewHolderUser vh3 = (ViewHolderUser) viewHolder;

                vh3.viewHolderMessage.setText(mMessage.get(position));
                break;
        }


    }


    @Override
    public int getItemCount() {
        return mMessage.size();
    }

    public class ViewHolderBot extends RecyclerView.ViewHolder{

        TextView viewHolderMessage;

        public ViewHolderBot(@NonNull View itemView) {
            super(itemView);
            viewHolderMessage = itemView.findViewById(R.id.activityvideo_chatmodel_message);
        }
    }

    public class ViewHolderUser extends RecyclerView.ViewHolder{

        TextView viewHolderMessage;

        public ViewHolderUser(@NonNull View itemView) {
            super(itemView);
            viewHolderMessage = itemView.findViewById(R.id.activityvideo_chatmodel_message);
        }
    }
}
