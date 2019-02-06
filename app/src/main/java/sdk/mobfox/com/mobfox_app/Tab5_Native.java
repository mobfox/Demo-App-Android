package sdk.mobfox.com.mobfox_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.mobfox.sdk.customevents.CustomEventNative;
import com.mobfox.sdk.nativeads.Native;
import com.mobfox.sdk.nativeads.NativeAd;
import com.mobfox.sdk.nativeads.NativeListener;
import com.mobfox.sdk.networking.MobfoxRequestParams;
import com.mobfox.sdk.networking.RequestParams;

import sdk.mobfox.com.mobfox_app.barcode.BarcodeCaptureActivity;

public class Tab5_Native extends Activity implements AdapterView.OnItemSelectedListener {

    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private static String invh = "a764347547748896b84e0b8ccd90fd62";

    public EditText invhText;
    public EditText floorText;
    public Button load;
    public Button qrcode;
    public TextView logText;
//    public String server="";
    public ProgressBar progressBar;
    private Native aNative;
    private NativeListener listener;

    TextView nativeHeadline, nativeDesc;
    ImageView nativeIcon, nativeMainImg;
    ConstraintLayout layout, nativeLayout;
    Button nativeCta;


    RequestParams params;


    float floor = -1;

    Context c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab4_native);



        c = this;

        params = new RequestParams();

        logText     = findViewById(R.id.logText);
        floorText   = findViewById(R.id.floor_etext);
        invhText    = findViewById(R.id.invhText);
        load        = findViewById(R.id.load_native_btn);
        progressBar = findViewById(R.id.mfBannerInterPB);

        progressBar.setVisibility(View.GONE);



        invhText.setText(invh);


        nativeHeadline = findViewById(R.id.nativeheadline);
        nativeDesc = findViewById(R.id.nativedesc);
        nativeIcon = findViewById(R.id.nativeIcon);
        nativeMainImg = findViewById(R.id.nativeMainImg);
        nativeCta = findViewById(R.id.nativecta);
        nativeLayout = findViewById(R.id.nativeLayout);
        layout = findViewById(R.id.layout);
        layout.removeView(nativeLayout);

        aNative = new Native(this);


        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.removeView(nativeLayout);

                progressBar.setVisibility(View.VISIBLE);

                invh = invhText.getText().toString();

                listener = new NativeListener() {
                    @Override
                    public void onNativeReady(Native aNative, CustomEventNative customEventNative, NativeAd nativeAd) {
                        Toast.makeText(c, "on native ready", Toast.LENGTH_SHORT).show();

                        //register custom layout click
                        customEventNative.registerViewForInteraction(nativeLayout);
                        //fire trackers
                        nativeAd.fireTrackers(c);

                        nativeHeadline.setText(nativeAd.getTexts().get(0).getText());
                        nativeDesc.setText(nativeAd.getTexts().get(1).getText());
                        nativeCta.setText(nativeAd.getTexts().get(2).getText());

                        nativeCta.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(c,"CTA clicked",Toast.LENGTH_SHORT).show();
                            }
                        });

                        nativeAd.loadImages(c, new NativeAd.ImagesLoadedListener() {
                            @Override
                            public void onImagesLoaded(NativeAd ad) {
                                Toast.makeText(c, "on images ready", Toast.LENGTH_SHORT).show();
                                logText.setText("");
                                progressBar.setVisibility(View.GONE);
                                nativeIcon.setImageBitmap(ad.getImages().get(0).getImg());
                                nativeMainImg.setImageBitmap(ad.getImages().get(1).getImg());
                                layout.addView(nativeLayout);
                            }
                        });
                    }

                    @Override
                    public void onNativeError(Exception e) {
                        progressBar.setVisibility(View.GONE);
                        logText.setText("Native Error: " + e.toString());

                    }

                    @Override
                    public void onNativeClick(NativeAd nativeAd) {
                        Toast.makeText(c, "clicked", Toast.LENGTH_SHORT).show();
                    }
                };

                aNative.setListener(listener);
                aNative.load(invh);


                if (floorText.getText().length() > 0){
                    floor = Float.parseFloat(floorText.getText().toString());
                    if(floor > 0){
                        params.setParam(MobfoxRequestParams.R_FLOOR,floor);
                        aNative.setParams(params);
                    }
                }

            }
        });

        qrcode = (Button) findViewById(R.id.qrcode);
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, BarcodeCaptureActivity.class);
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
                    //invh = barcode.displayValue;
                    invhText.setText(barcode.displayValue);
                }
            }
        } else super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
