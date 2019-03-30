/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.fragmentexample;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements DynamicFragment.OnFragmentInteractionListener {

    static final String STATE_FRAGMENT = "state_fragment";

    private Button showFragmentButton;
    private boolean isFragmentDisplayed = false;
    private String sendButtonString = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFragmentButton = findViewById(R.id.show_fragment);

        showFragmentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!isFragmentDisplayed) {
                    displayFragment();
                } else {
                    closeFragment();
                }
            }
        });

        if (savedInstanceState != null) {
            isFragmentDisplayed =
                    savedInstanceState.getBoolean(STATE_FRAGMENT);
            if (isFragmentDisplayed) {
                showFragmentButton.setText(R.string.close);
            }
        }

    }

    public void displayFragment() {
        if (!sendButtonString.equals("0")) {
            String[] stringSlice = sendButtonString.split("x");
            sendButtonString = stringSlice[1];
        }

        DynamicFragment dynamicFragment = DynamicFragment.newInstance(sendButtonString);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment_container,
                dynamicFragment).addToBackStack(null).commit();

        showFragmentButton.setText(R.string.close);
        isFragmentDisplayed = true;
    }

    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        DynamicFragment dynamicFragment = (DynamicFragment) fragmentManager.
                findFragmentById(R.id.fragment_container);

        if (dynamicFragment != null) {
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(dynamicFragment).commit();
        }
        showFragmentButton.setText(R.string.open);
        isFragmentDisplayed = false;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onSendButtonClick(String text) {
        sendButtonString = text;
        TextView textView = findViewById(R.id.title);
        textView.setText(text);

    }
}
