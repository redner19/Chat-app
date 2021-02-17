package com.sololearner.chatapp.presentation.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sololearner.chatapp.databinding.ProgressDialogLayoutBinding;

public class ProgressDialog extends DialogFragment {
    private ProgressDialogLayoutBinding mProgressView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProgressView = ProgressDialogLayoutBinding.inflate(inflater,container,false);
        return mProgressView.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog()!= null){
            getDialog().setCancelable(false);
            getDialog().setCanceledOnTouchOutside(false);
            getDialog()
                    .getWindow()
                    .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mProgressView = null;
    }
}
