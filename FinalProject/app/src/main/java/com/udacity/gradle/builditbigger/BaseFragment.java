package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.test.jokeview.JokeViewActivity;

public abstract class BaseFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private EndpointsAsyncTask.EndpointAsyncTaskCallback asyncTaskCallback;
    private Button tellJokeButton;
    private ProgressBar progressBar;
    private String jokeResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        tellJokeButton = root.findViewById(R.id.tell_joke_b);
        progressBar = root.findViewById(R.id.loading_indicator_pb);
        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJokeButton.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                new EndpointsAsyncTask(asyncTaskCallback).execute();
            }
        });
        asyncTaskCallback = new EndpointsAsyncTask.EndpointAsyncTaskCallback() {
            @Override
            public void onResultReceived(boolean fetchSuccessful, String result) {
                tellJokeButton.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                if (fetchSuccessful) {
                    jokeResult = result;
                    showJoke();
                } else {
                    Toast.makeText(getContext(), R.string.fetch_error, Toast.LENGTH_LONG).show();
                }
            }
        };
        setupFragment(root);
        return root;
    }

    protected void showJokeView() {
        Intent intent = new Intent(getContext(), JokeViewActivity.class);
        intent.putExtra(JokeViewActivity.JOKE_KEY, jokeResult);
        startActivity(intent);
    }

    protected void setupFragment(View root) {
    }

    protected abstract void showJoke();

}
