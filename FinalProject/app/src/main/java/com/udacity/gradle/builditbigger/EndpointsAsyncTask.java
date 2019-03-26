package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {

    private static final String TAG = EndpointsAsyncTask.class.getSimpleName();
    private static MyApi sApiService = null;
    private final OnRetrieveJokeListener mCallback;

    private EndpointsAsyncTask(OnRetrieveJokeListener callback) {
        mCallback = callback;
    }

    public static void getInstance(OnRetrieveJokeListener listener) {
        new EndpointsAsyncTask(listener).execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mCallback.onRetrieveJokeStart();
    }

    @Override
    protected String doInBackground(Void... params) {
        if (sApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(abstractGoogleClientRequest -> abstractGoogleClientRequest.setDisableGZipContent(true));
            // end options for devappserver

            sApiService = builder.build();
        }

        try {
            return sApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.d(TAG, "AsyncTaskError: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        mCallback.onRetrieveJokeFinish(result);
    }
}
