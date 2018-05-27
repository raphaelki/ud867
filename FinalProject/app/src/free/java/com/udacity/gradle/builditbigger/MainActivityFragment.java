package com.udacity.gradle.builditbigger;

import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivityFragment extends BaseFragment {

    private InterstitialAd interstitialAd;

    public MainActivityFragment() {
    }

    @Override
    protected void setupFragment(View root) {
        interstitialAd = new InterstitialAd(getContext());
        interstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id2));
        AdView mAdView = root.findViewById(R.id.adView);
        mAdView.loadAd(buildNewAddRequest());
        interstitialAd.loadAd(buildNewAddRequest());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                showJokeView();
                interstitialAd.loadAd(buildNewAddRequest());
            }
        });
    }

    private AdRequest buildNewAddRequest() {
        return new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
    }

    @Override
    protected void showJoke() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            showJokeView();
        }
    }
}
