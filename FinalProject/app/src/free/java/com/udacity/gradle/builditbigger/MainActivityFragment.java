package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jokedisplay.JokeDisplayActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ProgressBar mLoadingIndicator;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);


        //Initialize mobile ad
        MobileAds.initialize(getContext(), getString(R.string.admob_app_id));

        AdView mAdView = root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest
                .Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        Button button = root.findViewById(R.id.button_tell_joke);
        button.setOnClickListener(v -> getJoke());

        mLoadingIndicator = root.findViewById(R.id.pb_loading_indicator);
        return root;
    }

    //This method retrieves a joke string from AsyncTask and open activity
    private void getJoke() {
        EndpointsAsyncTask.getInstance(new OnRetrieveJokeListener() {
            @Override
            public void onRetrieveJokeStart() {
                //Show progress bar
                mLoadingIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRetrieveJokeFinish(@Nullable String result) {
                //Hide progress bar
                mLoadingIndicator.setVisibility(View.GONE);
                // If result is empty, show toast ith message
                if (TextUtils.isEmpty(result)) {
                    Toast.makeText(getActivity(), "No jokes retrieved", Toast.LENGTH_LONG).show();
                    return;
                }
                launchJokeDisplayActivity(result);
            }
        });
    }

    // This method launches the joke display activity from Android library
    private void launchJokeDisplayActivity(@Nullable String result) {
        final Intent intent = new Intent(getActivity(), JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayActivity.JOKE_EXTRA, result);
        startActivity(intent);
    }

}
