package sdk.mobfox.com.mobfoxdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.mobfox.sdk.interstitial.Interstitial;
import com.mobfox.sdk.interstitial.InterstitialListener;
import com.mobfox.sdk.networking.MobfoxRequestParams;

import sdk.mobfox.com.mobfoxdemo.barcode.BarcodeCaptureActivity;

public class URLInterstitial extends Activity {

    private static final int BARCODE_READER_REQUEST_CODE = 1;

    Interstitial interAd;
    Button load;
    Button qrcode;

    EditText vast_url_et;
    String invh = "267d72ac3f77a3f447b32cf7ebf20673";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlinterstitial);
//        getWindow().getDecorView().setBackgroundColor(Color.rgb(144,202,249));


        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.dummy_container);

        final URLInterstitial self = this;

        vast_url_et = (EditText) findViewById(R.id.vastUrl);

        load = (Button) findViewById(R.id.loadBtn);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                interAd = new Interstitial(self,invh);
                interAd.setListener(new InterstitialListener() {
                    @Override
                    public void onInterstitialLoaded(Interstitial interstitial) {
                        interAd.show();
                    }

                    @Override
                    public void onInterstitialFailed(String e) {
                        Toast.makeText(self,e, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onInterstitialClosed() {

                    }

                    @Override
                    public void onInterstitialClicked() {

                    }

                    @Override
                    public void onInterstitialShown() {

                    }

                    @Override
                    public void onInterstitialFinished() {
                        Toast.makeText(self,"finished",Toast.LENGTH_SHORT).show();
                    }
                });
                String vast_url = vast_url_et.getText().toString();

                MobfoxRequestParams rqp = new MobfoxRequestParams();
                rqp.setParam(MobfoxRequestParams.CREATIVE_URL,vast_url);
                interAd.setRequestParams(rqp);


                interAd.loadCreative();
            }
        });

        qrcode = (Button) findViewById(R.id.qrcode);
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
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
                    vast_url_et.setText(barcode.displayValue, TextView.BufferType.EDITABLE);
                }
            }
        }
        else super.onActivityResult(requestCode, resultCode, data);
    }
}
