package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivityFragment extends BaseFragment {

    @Override
    protected void showJoke() {
        // immediately display the joke
        showJokeView();
    }
}
