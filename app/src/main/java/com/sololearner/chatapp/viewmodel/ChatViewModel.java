package com.sololearner.chatapp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.sololearner.chatapp.core.MessageModel;
import com.sololearner.chatapp.utils.StringUtils;

import static com.sololearner.chatapp.utils.Constants.CHAT;
import static com.sololearner.chatapp.utils.Constants.MESSAGE_TIME;

public class ChatViewModel extends ViewModel {

    //Get Firebase Firestore instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Get FirebaseAuth instance
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //Get CollectionReference /chat
    private final CollectionReference collectionReference = db.collection(CHAT);

    //Get Query collection "chat" and orderBy messageTime and ascend result
    private final Query query = FirebaseFirestore
            .getInstance()
            .collection(CHAT)
            .orderBy(MESSAGE_TIME, Query.Direction.ASCENDING);

    //FirestoreRecyclerOptions builder
    private final FirestoreRecyclerOptions<MessageModel> options =
            new FirestoreRecyclerOptions
                    .Builder<MessageModel>().setQuery(query, MessageModel.class).build();

    //empty constructor
    public ChatViewModel() { }

    //Return FirebaseAuth
    public FirebaseAuth firebaseAuth(){
        return mAuth;
    }

    //Return CollectionReference
    public CollectionReference collectionReference(){ return collectionReference; }

    //Return FirestoreRecyclerOptions<MessageModel>
    public FirestoreRecyclerOptions<MessageModel> options(){ return options; }

    //Return MessageModel
    public MessageModel getMessageModel(String message){

        String name = StringUtils.getName(mAuth.getCurrentUser().getEmail());

        String id = mAuth.getCurrentUser().getUid();

        return new MessageModel(message, name, id);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
