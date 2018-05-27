package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

class EndpointsAsyncTask extends AsyncTask<EndpointsAsyncTask.EndpointAsyncTaskCallback, Void, String> {
    private static MyApi myApiService = null;
    private EndpointAsyncTaskCallback callback;
    private boolean fetchSuccessful = true;

    @Override
    protected String doInBackground(EndpointAsyncTaskCallback... callbacks) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
        }

        this.callback = callbacks[0];

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            fetchSuccessful = false;
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        callback.onResultReceived(fetchSuccessful, result);
    }

    public interface EndpointAsyncTaskCallback {
        void onResultReceived(boolean successful, String result);
    }
}