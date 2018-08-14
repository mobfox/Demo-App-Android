package sdk.mobfox.com.mobfox_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

import sdk.mobfox.com.mobfox_app.barcode.BarcodeCaptureActivity;

public class Tab6_MoPub extends AppCompatActivity {

    MoPubView moPubBanner;
    Context c;

    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private static String mopubInvh = "4ad212b1d0104c5998b288e7a8e35967";


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


        invhText.setText(mopubInvh);

        if (!MoPub.isSdkInitialized()){
            SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder(mopubInvh)
                    .build();
            MoPub.initializeSdk(this,sdkConfiguration, initSdkListener());
        }


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
                mopubInvh = invhText.getText().toString();

                moPubBanner.setAdUnitId(mopubInvh);
                moPubBanner.setBannerAdListener(new MoPubView.BannerAdListener() {
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
                });

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
}
