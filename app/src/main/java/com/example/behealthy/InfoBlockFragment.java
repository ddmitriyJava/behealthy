package com.example.behealthy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoBlockFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_block, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.close_info_block)
    void onCloseInfoBlockImageClicked(){
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new MainBlockFragment())
                .commit();
    }
}