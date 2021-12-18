package com.appsfeature.login;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

class BaseActivity extends AppCompatActivity {

    protected void addFragmentWithoutBackstack(Fragment fragment, String tag) {
        addFragmentWithoutBackstack(fragment,android.R.id.content, tag);
    }
    protected void addFragmentWithoutBackstack(Fragment fragment, int container, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(container, fragment, tag);
        transaction.commit();
    }


    protected void addFragment(Fragment fragment, String tag) {
        addFragment(fragment, android.R.id.content, tag);
    }

    protected void addFragment(Fragment fragment, int container, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(container, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

}
