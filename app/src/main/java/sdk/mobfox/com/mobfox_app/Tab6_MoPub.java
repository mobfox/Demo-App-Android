package sdk.mobfox.com.mobfox_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.mopub.common.MoPub;
import com.mopub.common.MoPubReward;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubRewardedVideo;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.mopub.mobileads.MoPubView;

import java.util.Set;

import sdk.mobfox.com.mobfox_app.barcode.BarcodeCaptureActivity;

public class Tab6_MoPub extends Activity implements AdapterView.OnItemSelectedListener {

    MoPubView moPubBanner;
    private MoPubInterstitial mInterstitial;
    Context c;
    int adType;

    MoPubView.BannerAdListener mBannerListener;
    MoPubInterstitial.InterstitialAdListener mInterstitialListener;




    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private static String mopubBannerInvh = "4ad212b1d0104c5998b288e7a8e35967";
    final String mopubRewardedInvh = "005491feb31848a0ae7b9daf4a46c701";
    String mopubInterstitialInvh  = "3fd85a3e7a9d43ea993360a2536b7bbd";




    public TextView logText;
    public EditText invhText, floorText;
    public Button qrcode, loadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab6_mopub);

        c = getApplicationContext();

        floorText   = findViewById(R.id.floor_etext);
        logText     = findViewById(R.id.logText);
        invhText    = findViewById(R.id.invhText);
        loadBtn     = findViewById(R.id.load_btn);
        qrcode      = findViewById(R.id.qrcode);
        moPubBanner = findViewById(R.id.mopubview);


        Spinner sizeSpinner = findViewById(R.id.mp_spinner);
        ArrayAdapter<CharSequence> sizeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.adapter_sizes_array, android.R.layout.simple_spinner_item);

        sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);
        sizeSpinner.setOnItemSelectedListener(this);

        invhText.setText(mopubBannerInvh);

        if (!MoPub.isSdkInitialized()){
            SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder(mopubBannerInvh)
                    .build();
            MoPub.initializeSdk(this,sdkConfiguration, initSdkListener());
        }



        mInterstitial = new MoPubInterstitial(this,mopubInterstitialInvh);


        //////////// mopub banner listener
        mBannerListener = new MoPubView.BannerAdListener() {
            @Override
            public void onBannerLoaded(MoPubView banner) {
                Toast.makeText(c, "MoPub Banner loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
                logText.setText(errorCode.toString());
                Toast.makeText(c, "MoPub Banner failed", Toast.LENGTH_SHORT).show();
                banner.destroy();
            }

            @Override
            public void onBannerClicked(MoPubView banner) {
                Toast.makeText(c, "MoPub Banner clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerExpanded(MoPubView banner) {
                Toast.makeText(c, "MoPub Banner expanded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerCollapsed(MoPubView banner) {
                Toast.makeText(c, "MoPub Banner collapsed", Toast.LENGTH_SHORT).show();
                banner.destroy();
            }
        };


        //////////// mopub interstitial listener
        mInterstitialListener = new MoPubInterstitial.InterstitialAdListener() {
            @Override
            public void onInterstitialLoaded(MoPubInterstitial interstitial) {
                Toast.makeText(c, "loaded", Toast.LENGTH_SHORT).show();
                if (interstitial.isReady()) {
                    mInterstitial.show();
                } else {
                    Toast.makeText(c, "Interstitial is not ready yet !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
                Toast.makeText(c, "failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialShown(MoPubInterstitial interstitial) {
                Toast.makeText(c, "shown", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialClicked(MoPubInterstitial interstitial) {
                Toast.makeText(c, "clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialDismissed(MoPubInterstitial interstitial) {
                Toast.makeText(c, "dismissed", Toast.LENGTH_SHORT).show();
                interstitial.destroy();
            }
        };


        //////////// mopub rewarded listener
        final MoPubRewardedVideoListener moPubRewardedVideoListener = new MoPubRewardedVideoListener() {
            @Override
            public void onRewardedVideoLoadSuccess(@NonNull String adUnitId) {
                Toast.makeText(c, "Rewarded Load Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
                Toast.makeText(c, "Rewarded Load Failure: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoStarted(@NonNull String adUnitId) {
                Toast.makeText(c, "Rewarded Video Started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoPlaybackError(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
                Toast.makeText(c, "Rewarded Playback Error: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoClicked(@NonNull String adUnitId) {
                Toast.makeText(c, "Rewarded Video Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoClosed(@NonNull String adUnitId) {
                Toast.makeText(c, "Rewarded Video Closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoCompleted(@NonNull Set<String> adUnitIds, @NonNull MoPubReward reward) {
                Toast.makeText(c, "Completed", Toast.LENGTH_SHORT).show();
                MoPubRewardedVideos.getAvailableRewards(mopubRewardedInvh);
                String lable = reward.getLabel();
                int amount = reward.getAmount();
                Toast.makeText(c, "Recieved " + amount + " " + lable + "!", Toast.LENGTH_SHORT).show();
            }
        };

        MoPubRewardedVideos.setRewardedVideoListener(moPubRewardedVideoListener);


        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mopubBannerInvh = invhText.getText().toString();


                switch (adType) {
                    case 0:
                        moPubBanner.setAdUnitId(mopubBannerInvh);
                        moPubBanner.setBannerAdListener(mBannerListener);
                        break;
                    case 1:
                        mInterstitial.setInterstitialAdListener(mInterstitialListener);
                        mInterstitial.load();
                        break;
                    case 2:
                        if (MoPubRewardedVideos.hasRewardedVideo(mopubRewardedInvh)) {
                            MoPubRewardedVideos.showRewardedVideo(mopubRewardedInvh);
                        }else {
                            MoPubRewardedVideos.loadRewardedVideo(mopubRewardedInvh);
                            Toast.makeText(c,"Rewarded video is not ready !",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }


                if (MoPub.isSdkInitialized()) {
                    moPubBanner.loadAd();
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
        }
        else super.onActivityResult(requestCode, resultCode, data);
    }

    private SdkInitializationListener initSdkListener() {
        return new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
                // MoPub SDK initialized
                Log.d("mopub", "init");
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moPubBanner.destroy();
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
                MoPubRewardedVideos.loadRewardedVideo(mopubRewardedInvh);
                Toast.makeText(c, "Please wait until video is loaded.", Toast.LENGTH_SHORT).show();
                adType = 2;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
