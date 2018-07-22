package com.hsowl.seta.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hsowl.seta.R;

public class ErrorFragment extends Fragment {

    // Widgets
    private Button btnRefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
//        errorDialogFragment.show(getActivity().getFragmentManager(), "ErrorDialogFragment");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_error, container, false);

        btnRefresh = (Button)view.findViewById(R.id.btnErrorRefresh);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
