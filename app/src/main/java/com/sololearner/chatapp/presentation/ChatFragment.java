package com.sololearner.chatapp.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.sololearner.chatapp.R;
import com.sololearner.chatapp.adapters.MessageAdapter;
import com.sololearner.chatapp.utils.Constants;
import com.sololearner.chatapp.utils.NavigationUtils;
import com.sololearner.chatapp.viewmodel.ChatViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatFragment extends Fragment {
    private ChatViewModel chatViewModel;

    private MessageAdapter adapter;

    @BindView(R.id.toolbar_sign_out)
    MaterialButton mSignOutBTN;

    @BindView(R.id.chat_TIET)
    TextInputEditText mMessage;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        // Bind ButterKnife
        ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniChatViewModel();

        initRecyclerView();

        initAdapter();
    }

    private void iniChatViewModel() {
        //init viewModel
        chatViewModel = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);
    }

    private void initRecyclerView() {
        // set logout button to visible
        mSignOutBTN.setVisibility(View.VISIBLE);

        // init linearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
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
                chatViewModel.firebaseAuth().signOut();
                NavigationUtils
                        .navigate(mSignOutBTN,R.id.action_chatFragment_to_signupFragment);
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