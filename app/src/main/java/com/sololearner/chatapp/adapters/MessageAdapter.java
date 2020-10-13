package com.sololearner.chatapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sololearner.chatapp.R;
import com.sololearner.chatapp.core.MessageModel;

public class MessageAdapter extends FirestoreRecyclerAdapter<MessageModel, MessageAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MessageAdapter(@NonNull FirestoreRecyclerOptions<MessageModel> options) {
        super(options);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == MSG_TYPE_RIGHT)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_in, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_out, parent, false);

        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull MessageModel model) {
        if(!isActualUser(position)) holder.sender.setText(model.getSender());

        holder.message.setText(model.getMessage());
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView sender, message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.message_user);
            message = itemView.findViewById(R.id.message_text);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (isActualUser(position))
            return MSG_TYPE_RIGHT;
        else
            return MSG_TYPE_LEFT;
    }

    private Boolean isActualUser(int position){
        return getItem(position).getSenderId().equals(user.getUid());
    }
}
