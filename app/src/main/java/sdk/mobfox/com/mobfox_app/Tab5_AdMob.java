package sdk.mobfox.com.mobfox_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import sdk.mobfox.com.mobfox_app.barcode.BarcodeCaptureActivity;

public class Tab5_AdMob extends Activity implements AdapterView.OnItemSelectedListener {

    public Context c;
    LinearLayout bannerContainer;

    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private static String admobInvh = "ca-app-pub-6224828323195096/4350674761";


    public TextView logText;
    public EditText invhText, floorText;
    public Button qrcode, loadBtn;
    AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab5_admob);

        c = getApplicationContext();

        floorText             = findViewById(R.id.floor_etext);
        logText               = findViewById(R.id.logText);
        invhText              = findViewById(R.id.invhText);
        loadBtn               = findViewById(R.id.load_btn);
        qrcode                = findViewById(R.id.qrcode);
        bannerContainer       = findViewById(R.id.admob_banner_container);

        invhText.setText(admobInvh);




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

                admobInvh = invhText.getText().toString();

                adView = new AdView(c);
                adView.setAdSize(AdSize.BANNER);
                adView.setAdUnitId(admobInvh);

                adView.setAdListener(new AdListener() {

                    @Override
                    public void onAdLoaded() {
                        Toast.makeText(c, "AdMob Banner loaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        logText.setText(errorCode);
                        Toast.makeText(c, "AdMob Banner failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdOpened() {
                        Toast.makeText(c, "AdMob Banner overlay", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onAdLeftApplication() {
                        Toast.makeText(c, "AdMob Banner clicked", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClosed() {
                        Toast.makeText(c, "AdMob Banner closed", Toast.LENGTH_SHORT).show();
                    }
                });
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);

                bannerContainer.addView(adView);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
