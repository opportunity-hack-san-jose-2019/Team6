package evan.com.sewaconnect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    private ArrayList<String> mType;
    private ArrayList<String> mStatus;
    private ArrayList<String> mMessage;
    private ArrayList<String> mTime;
    private ArrayList<String> mImage;
    private Context mContext;


    public RequestsAdapter(ArrayList<String> mType, ArrayList<String> mStatus, ArrayList<String> mMessage,
                           ArrayList<String> mTime,
                           ArrayList<String> mImage, Context mContext) {
        this.mType = mType;
        this.mStatus = mStatus;
        this.mMessage = mMessage;
        this.mTime = mTime;
        this.mImage = mImage;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requests_model, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        if(mStatus.get(position).equals("Completed")){
            holder.viewHolderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.standardGreen));
        }

        holder.viewHolderType.setText(mType.get(position));
        holder.viewHolderStatus.setText(mStatus.get(position));
        holder.viewHolderMessage.setText(mMessage.get(position));
        holder.viewHolderTime.setText(mTime.get(position));

        Glide.with(mContext).load(mImage.get(position))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.viewHolderImage);




    }

    @Override
    public int getItemCount() {
        return mMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView viewHolderType, viewHolderStatus, viewHolderMessage, viewHolderTime;
        ImageView viewHolderImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewHolderType = itemView.findViewById(R.id.requestmodel_type);
            viewHolderStatus = itemView.findViewById(R.id.requestmodel_status);
            viewHolderMessage = itemView.findViewById(R.id.requestmodel_message);
            viewHolderTime = itemView.findViewById(R.id.requestmodel_time);
            viewHolderImage = itemView.findViewById(R.id.modelrv_following_image);

        }


    }
}
