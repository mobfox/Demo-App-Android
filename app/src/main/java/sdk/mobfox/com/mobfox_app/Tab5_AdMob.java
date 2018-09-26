package sdk.mobfox.com.mobfox_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.mobfox.adapter.MobFoxAdapter;
import com.mopub.mobileads.MoPubRewardedVideos;

import sdk.mobfox.com.mobfox_app.barcode.BarcodeCaptureActivity;

public class Tab5_AdMob extends Activity implements AdapterView.OnItemSelectedListener {

    public Context c;
    LinearLayout bannerContainer;

    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private static String admobBannerInvh = "ca-app-pub-6224828323195096/4350674761";
    private static String admobInterstitialInvh = "ca-app-pub-6224828323195096/1031427961";
    private static String admobRewardedInvh = "ca-app-pub-6224828323195096/5018083420";


    public TextView logText;
    public EditText invhText, floorText;
    public Button qrcode, loadBtn;

    InterstitialAd mInterstitialAd;
    RewardedVideoAd mRewardedAd;
    AdView admobBanner;
    AdListener admobBannerListener, admobInterstitialListener;
    RewardedVideoAdListener admobRewardedListener;

    Activity self;

    int adType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab5_admob);

        c = getApplicationContext();
        self = this;

        floorText = findViewById(R.id.floor_etext);
        logText = findViewById(R.id.logText);
        invhText = findViewById(R.id.invhText);
        loadBtn = findViewById(R.id.load_btn);
        qrcode = findViewById(R.id.qrcode);
        bannerContainer = findViewById(R.id.admob_banner_container);

        invhText.setText(admobBannerInvh);

        Spinner sizeSpinner = findViewById(R.id.am_spinner);
        ArrayAdapter<CharSequence> sizeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.adapter_sizes_array, android.R.layout.simple_spinner_item);

        sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);
        sizeSpinner.setOnItemSelectedListener(this);


        //////////// admob banner listener
        admobBannerListener = new AdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(c, "AdMob Banner loaded", Toast.LENGTH_SHORT).show();
                Log.d("AdMobBanner", "onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                logText.setText(errorCode);
                Toast.makeText(c, "AdMob Banner failed", Toast.LENGTH_SHORT).show();
                Log.d("AdMobBanner", "onAdClosed: " + errorCode);
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(c, "AdMob Banner overlay", Toast.LENGTH_SHORT).show();
                Log.d("AdMobBanner", "onAdClosed");
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(c, "AdMob Banner clicked", Toast.LENGTH_SHORT).show();
                Log.d("AdMobBanner", "onAdClosed");
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(c, "AdMob Banner closed", Toast.LENGTH_SHORT).show();
                Log.d("AdMobBanner", "onAdClosed");
            }
        };


        mInterstitialAd = new InterstitialAd(self);
        mInterstitialAd.setAdUnitId(admobInterstitialInvh);
        //////////// admob interstitial listener
        admobInterstitialListener = new AdListener() {
            @Override
            public void onAdClosed() {
                Log.d("AdMobInterstitial", "onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("AdMobInterstitial", "onAdFailedToLoad: "+ errorCode);
            }

            @Override
            public void onAdLeftApplication() {
                Log.d("AdMobInterstitial", "onAdLeftApplication");
            }

            @Override
            public void onAdOpened() {
                Log.d("AdMobInterstitial", "onAdOpened");
            }

            @Override
            public void onAdLoaded() {
                Log.d("AdMobInterstitial", "onAdLoaded");
                mInterstitialAd.show();
            }
        };


        //////////// admob rewarded listener
        admobRewardedListener = new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                Log.d("AdmMobRewarded", "onRewardedVideoAdLoaded");
                mRewardedAd.show();
            }

            @Override
            public void onRewardedVideoAdOpened() {
                Log.d("AdmMobRewarded", "onRewardedVideoAdOpened");
            }

            @Override
            public void onRewardedVideoStarted() {
                Log.d("AdmMobRewarded", "onRewardedVideoStarted");
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Log.d("AdmMobRewarded", "onRewardedVideoAdClosed");
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

                int rewardAmount = rewardItem.getAmount();
                String rewardType = rewardItem.getType();
                Toast.makeText(c,"Received "+ rewardAmount + " " + rewardType + "!",Toast.LENGTH_SHORT).show();
                Log.d("AdmMobRewarded", "Received "+ rewardAmount + " " + rewardType + "!");
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Log.d("AdmMobRewarded", "onRewardedVideoAdLeftApplication");
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Log.d("AdmMobRewarded", "onRewardedVideoAdFailedToLoad: "+ i);
            }

            @Override
            public void onRewardedVideoCompleted() {
                Log.d("AdmMobRewarded", "onRewardedVideoCompleted");
            }

        };


        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putBoolean("gdpr", true);
                bundle.putString("gdpr_consent", "YES");

                switch (adType) {
                    case 0:
                        admobBannerInvh = invhText.getText().toString();

                        admobBanner = new AdView(c);
                        admobBanner.setAdSize(AdSize.BANNER);
                        admobBanner.setAdUnitId(admobBannerInvh);

                        admobBanner.setAdListener(admobBannerListener);
                        AdRequest adRequestBanner = new AdRequest.Builder()
                                .addNetworkExtrasBundle(MobFoxAdapter.class, bundle)
                                .build();
                        admobBanner.loadAd(adRequestBanner);

                        bannerContainer.addView(admobBanner);
                        break;
                    case 1:
                        mInterstitialAd.setAdListener(admobInterstitialListener);
                        AdRequest adRequestInterstitial = new AdRequest.Builder()
                                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                                .addNetworkExtrasBundle(MobFoxAdapter.class, bundle)
                                .build();
                        mInterstitialAd.loadAd(adRequestInterstitial);
                        break;
                    case 2:
                        mRewardedAd = MobileAds.getRewardedVideoAdInstance(self);
                        mRewardedAd.setRewardedVideoAdListener(admobRewardedListener);
                        mRewardedAd.loadAd(admobRewardedInvh, new AdRequest.Builder()
                                .addNetworkExtrasBundle(MobFoxAdapter.class, bundle)
                                .build());
                        break;
                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    // mResultTextView.setText(barcode.displayValue);
                    //invh = barcode.displayValue;
                    invhText.setText(barcode.displayValue);
                }
            }
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinnerId = parent.getItemAtPosition(position).toString();

        switch (spinnerId) {
            case "Banner":
                //Toast.makeText(c, spinnerId, Toast.LENGTH_SHORT).show();
                adType = 0;
                break;
            case "Interstitial":
                //Toast.makeText(c, spinnerId, Toast.LENGTH_SHORT).show();
                adType = 1;
                break;
            case "Rewarded":
                Toast.makeText(c, "Please wait until video is loaded.", Toast.LENGTH_SHORT).show();
                adType = 2;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
