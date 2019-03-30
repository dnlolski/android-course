package com.example.android.fragmentexample;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DynamicFragment extends Fragment {

    public DynamicFragment() {
        // Required empty public constructor
    }

    interface OnFragmentInteractionListener {
        void onSendButtonClick(String text);
    }

    int count = 0;

    OnFragmentInteractionListener buttonListener;

    private static final String TEXT = "Pressed ";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            buttonListener = (OnFragmentInteractionListener) context;

        } else {
            throw new ClassCastException(context.toString()
            + getResources().getString(R.string.exception_message));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =
                inflater.inflate(R.layout.fragment_dynamic, container, false);

        final Button buttonSend = (Button) rootView.findViewById(R.id.button_send);

        final Button buttonGet = (Button) rootView.findViewById(R.id.button_get);

        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments().containsKey(TEXT)) {
                    TextView textView = rootView.findViewById(R.id.fragment_text);
                    String count = getArguments().getString(TEXT);
                    textView.setText(count);
                }
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                count += 1;
                String text = "Kaboom x" + count;
                buttonListener.onSendButtonClick(text);
            }
        });



        // Return the View for the fragment's UI.
        return rootView;
    }

    public static DynamicFragment newInstance(String text) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle arguments = new Bundle();
        arguments.putString(TEXT, text);
        fragment.setArguments(arguments);
        return fragment;
    }

}
