package com.sololearner.chatapp.presentation;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sololearner.chatapp.adapters.MessageAdapter;
import com.sololearner.chatapp.databinding.ActivityChatBinding;
import com.sololearner.chatapp.utils.AppUtils;
import com.sololearner.chatapp.utils.Constants;
import com.sololearner.chatapp.viewmodel.ChatViewModel;

public class Chat extends Fragment {

    private ChatViewModel mChatViewModel;

    private ActivityChatBinding mChatView;

    private MessageAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSharedElementEnterTransition(TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mChatView = ActivityChatBinding.inflate(inflater, container, false);

        return mChatView.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        initViewModelProvider();

        initFragmentViews();

        initAdapter();
    }

    private void initFragmentViews() {
        //Set Button Visibility and SetOnClickListener
        mChatView
                .toolbarLayout
                .toolbarSignOut
                .setVisibility(View.VISIBLE);

        mChatView
                .toolbarLayout
                .toolbarSignOut
                .setOnClickListener(v -> {
                    mChatViewModel
                            .firebaseAuth()
                            .signOut();

                    NavDirections action = ChatDirections.actionChatToIndex();
                    Navigation.findNavController(v).navigate(action);

                    // close/hide keyboard
                    AppUtils
                            .hideKeyboardFrom(requireContext(),
                                    mChatView
                                            .toolbarLayout
                                            .toolbarSignOut);
                });

        mChatView.chatSendBtn.setOnClickListener(v -> {
            // get message in editText
            String message = mChatView.chatTIET.getText().toString();

            // Validate Message, if not empty call clear text after sending
            mChatViewModel.validateMessage(message, () -> mChatView.chatTIET.setText(Constants.EMPTY));

        });
    }

    private void initViewModelProvider() {
        //init viewModel
        mChatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
    }

    private void initAdapter() {
        // init adapter
        adapter = new MessageAdapter(mChatViewModel.options());

        //observe data adapter
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mChatView.recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        });

        // apply adapter to recyclerView
        mChatView.recyclerView.setAdapter(adapter);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mChatView = null;
    }
}