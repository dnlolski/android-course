package com.example.android.fragmentexample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleFragment extends Fragment {

    private static final int ZERO = 0;
    private static final int ONE = 1;

    public SimpleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =
                inflater.inflate(R.layout.fragment_simple, container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);

        final Button buttonClear = (Button) rootView.findViewById(R.id.button_clear);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                TextView textView =
                        rootView.findViewById(R.id.fragment_header);
                radioGroup.clearCheck();
                textView.setText(R.string.question_article);
            }
        });

        radioGroup.setOnCheckedChangeListener(new
                                  RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {
              View radioButton = radioGroup.findViewById(checkedId);
              int index = radioGroup.indexOfChild(radioButton);
              TextView textView =
                      rootView.findViewById(R.id.fragment_header);
              switch (index) {
                  case ZERO:
                      textView.setText(R.string.zero_message);
                      Toast.makeText(getContext(), "zero",
                              Toast.LENGTH_SHORT).show();
                      break;
                  case ONE:
                      textView.setText(R.string.one_message);
                      Toast.makeText(getContext(), "one",
                              Toast.LENGTH_SHORT).show();
                      break;
                  default:

                      break;
              }
          }
        });



        // Return the View for the fragment's UI.
        return rootView;
    }

}
