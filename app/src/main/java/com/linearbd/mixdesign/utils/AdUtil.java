package com.linearbd.mixdesign.utils;

import android.app.Activity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.linearbd.mixdesign.R;

/**
 * Created by sohel on 30-09-17.
 */

public class AdUtil {

    private Activity activity;


    public AdUtil(Activity activity) {
        this.activity = activity;

        initMobileAd();
    }

    private void initMobileAd() {
        MobileAds.initialize(activity,
                activity.getString(R.string.ad_app_id));

        AdView mAdView = activity.findViewById(R.id.adView);
        //mAdView.setAdSize(AdSize.SMART_BANNER);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
    }
}
