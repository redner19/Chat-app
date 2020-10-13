package com.sololearner.chatapp.presentation;

import android.os.Bundle;
import android.view.View;

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

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sololearner.chatapp.utils.NavigationUtils.goToSignUp;

public class Chat extends BaseActivity {
    private ChatViewModel mChatViewModel;

    private MessageAdapter adapter;
    //MaterialButton
    @BindView(R.id.toolbar_sign_out)
    MaterialButton mSignOutBTN;
    //TextInputEditText
    @BindView(R.id.chat_TIET)
    TextInputEditText mMessage;
    //RecyclerView
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
        mChatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
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
        adapter = new MessageAdapter(mChatViewModel.options());

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
                mChatViewModel
                        .firebaseAuth()
                        .signOut();

                goToSignUp(this);
                finish();
                break;
            case R.id.chat_send_btn:
                // get message in editText
                String message = Objects.requireNonNull(mMessage.getText()).toString();

                // check if message is not empty
                if (message.length() != 0) {
                    // send message to db / fireStore
                    mChatViewModel
                            .collectionReference()
                            .add(mChatViewModel.getMessageModel(message));

                    // remove text in mMessage
                    mMessage.setText(Constants.EMPTY);
                }
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