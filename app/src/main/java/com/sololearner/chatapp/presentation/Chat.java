package com.sololearner.chatapp.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.sololearner.chatapp.R;
import com.sololearner.chatapp.adapters.MessageAdapter;
import com.sololearner.chatapp.presentation.base.BaseActivity;
import com.sololearner.chatapp.utils.Constants;
import com.sololearner.chatapp.viewmodel.ChatViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sololearner.chatapp.utils.NavigationUtils.goToSignUp;

public class Chat extends BaseActivity {
    private ChatViewModel chatViewModel;

    private MessageAdapter adapter;

    @BindView(R.id.toolbar_sign_out)
    Button mSignOutBTN;

    @BindView(R.id.chat_TIET)
    EditText mMessage;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Bind ButterKnife
        ButterKnife.bind(this);

        initViewModelProvider();

        initRecyclerView();

        initAdapter();
    }

    private void initViewModelProvider() {
        //init viewModel
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
    }

    private void initRecyclerView() {
        // set logout button to visible
        mSignOutBTN.setVisibility(View.VISIBLE);

        // init linearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        // setLayoutManager
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }


    private void initAdapter() {
        // init adapter
        adapter = new MessageAdapter(chatViewModel.options());

        //observe data adapter
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        });

        // apply adapter to recyclerView
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.toolbar_sign_out, R.id.chat_send_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_sign_out:
                //logout user
                chatViewModel
                        .firebaseAuth()
                        .signOut();

                goToSignUp(this);
                finish();
                break;
            case R.id.chat_send_btn:
                // send message to db / firestore
                chatViewModel
                        .collectionReference()
                        .add(chatViewModel.getMessageModel(mMessage.getText()));

                // remove text in mMessage
                mMessage.setText(Constants.EMPTY);
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        //Start listening in database changes
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Stop listening in database changes
        adapter.stopListening();
    }
}