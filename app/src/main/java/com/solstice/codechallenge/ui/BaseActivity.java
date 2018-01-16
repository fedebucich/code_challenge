package com.solstice.codechallenge.ui;

import android.support.v7.app.AppCompatActivity;

import com.solstice.codechallenge.AndroidApplication;
import com.solstice.codechallenge.data.ContactRepositoryComponent;

/**
 * Created by fbucich on 1/14/18.
 */
public class BaseActivity extends AppCompatActivity {

    protected ContactRepositoryComponent getContactRepositoryComponent() {
        return ((AndroidApplication) getApplication()).getContactRepositoryComponent();
    }
}
