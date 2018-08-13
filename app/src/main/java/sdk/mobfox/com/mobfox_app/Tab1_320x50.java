package sdk.mobfox.com.mobfox_app;

/**
 * Created by shahafsh on 6/12/18.
 */

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
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.mobfox.sdk.banner.Banner;
import com.mobfox.sdk.networking.MobfoxRequestParams;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

import sdk.mobfox.com.mobfox_app.barcode.BarcodeCaptureActivity;


public class Tab1_320x50 extends Activity implements AdapterView.OnItemSelectedListener {

    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private static String invh = "fe96717d9875b9da4339ea5367eff1ec";

    public TextView logText;
    public EditText invhText, floorText;
    public Button qrcode, loadBtn;
    public Context c;
    public MobfoxRequestParams requestParams;
    public int mediation;

    float floor = -1;
    String server = "";

    Banner banner;
    AdView adMobBanner;
    MoPubView moPubBanner;
    LinearLayout view;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1_320x50);

        c = getApplicationContext();

        if (!MoPub.isSdkInitialized()){
            SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder("4ad212b1d0104c5998b288e7a8e35967")
                    .build();
            MoPub.initializeSdk(this,sdkConfiguration, initSdkListener());
        }

        requestParams = new MobfoxRequestParams();

        floorText   = (EditText)    findViewById(R.id.floor_etext);
        logText     = (TextView)    findViewById(R.id.logText);
        invhText    = (EditText)    findViewById(R.id.invhText);
        loadBtn     = (Button)      findViewById(R.id.load_btn);
        qrcode      = (Button)      findViewById(R.id.qrcode);
        view        = (LinearLayout)findViewById(R.id.banner_container);


        Spinner serverSpinner   = (Spinner) findViewById(R.id.server_spinner);
        Spinner mediationSpinner = (Spinner) findViewById(R.id.mediation_spinner);


        ArrayAdapter<CharSequence> serverSpinnerAdapter = ArrayAdapter.createFromResource(c,
                R.array.servers_array, android.R.layout.simple_spinner_item);

        serverSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serverSpinner.setAdapter(serverSpinnerAdapter);
        serverSpinner.setOnItemSelectedListener(this);
        serverSpinner.setPrompt("Server");


        ArrayAdapter<CharSequence> mediationSpinnerAdapter = ArrayAdapter.createFromResource(c,
                R.array.mediation_array, android.R.layout.simple_spinner_item);

        mediationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mediationSpinner.setAdapter(mediationSpinnerAdapter);
        mediationSpinner.setOnItemSelectedListener(this);


        invhText.setText(invh);


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
//                banner = (Banner) findViewById(R.id.banner320);

                loadBanner();
            }
        });
    }



    public void loadBanner() {

            invh = invhText.getText().toString();

            banner = new Banner(c, 320, 50, invh, new Banner.Listener() {
                @Override
                public void onBannerError(Banner banner, Exception e) {
                    view.removeAllViews();
                    Toast.makeText(c, e.toString(), Toast.LENGTH_SHORT).show();
                    logText.setText(e.toString());
                }

                @Override
                public void onBannerLoaded(Banner banner) {
                    Toast.makeText(c, "MobFox Banner loaded", Toast.LENGTH_SHORT).show();
                    view.addView(banner);
                    logText.setText("");
                }

                @Override
                public void onBannerClosed(Banner banner) {
                    Toast.makeText(c, "MobFox Banner closed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onBannerFinished() {
                    Toast.makeText(c, "MobFox Banner finished", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onBannerClicked(Banner banner) {
                    Toast.makeText(c, "MobFox Banner clicked", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNoFill(Banner banner) {
                    Toast.makeText(c, "MobFox Banner - no fill", Toast.LENGTH_SHORT).show();
                }
            });

            if (floorText.getText().length() > 0) {
                floor = Float.parseFloat(floorText.getText().toString());
                if (floor > 0) {
                    requestParams.setParam(MobfoxRequestParams.R_FLOOR, floor);
                    banner.addParams(requestParams);
                }
            }

            if (!server.equals("")){
                if (server.equals("http://nvirginia-my.mobfox.com")){
                    requestParams.setParam("debugResponseURL", server);
                    banner.addParams(requestParams);
                }
                if (server.equals("http://tokyo-my.mobfox.com")){
                    requestParams.setParam("debugResponseURL", server);
                    banner.addParams(requestParams);

                }

            }


            banner.load();

        //AM Adapter

//            view.removeAllViews();
//
////            Bundle bundle = new Bundle();
////            bundle.putBoolean("gdpr", true);
////            bundle.putString("gdpr_consent", "YES");
//
//            adMobBanner = findViewById(R.id.adView);
//            adMobBanner.setAdListener(new AdListener(){
//
//                @Override
//                public void onAdLoaded() {
//                    Toast.makeText(c, "AdMob Banner loaded",Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onAdFailedToLoad(int errorCode) {
//                    Toast.makeText(c, "AdMob Banner failed",Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onAdOpened() {
//                    Toast.makeText(c, "AdMob Banner overlay",Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onAdLeftApplication() {
//                    Toast.makeText(c, "AdMob Banner clicked",Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onAdClosed() {
//                    Toast.makeText(c, "AdMob Banner closed",Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            AdRequest adRequest = new AdRequest.Builder().build();
//            adMobBanner.loadAd(adRequest);
//
//


            //MP Adapter

//            moPubBanner = (MoPubView) findViewById(R.id.mopubadview);
//            moPubBanner.setAdUnitId("4ad212b1d0104c5998b288e7a8e35967");
//
//            moPubBanner.setBannerAdListener(new MoPubView.BannerAdListener() {
//                @Override
//                public void onBannerLoaded(MoPubView banner) {
//                    Toast.makeText(c, "MoPub Banner loaded", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
//                    Toast.makeText(c, "MoPub Banner failed", Toast.LENGTH_SHORT).show();
//                    banner.destroy();
//                }
//
//                @Override
//                public void onBannerClicked(MoPubView banner) {
//                    Toast.makeText(c, "MoPub Banner clicked", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onBannerExpanded(MoPubView banner) {
//                    Toast.makeText(c, "MoPub Banner expanded", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onBannerCollapsed(MoPubView banner) {
//                    Toast.makeText(c, "MoPub Banner collapsed", Toast.LENGTH_SHORT).show();
//                    banner.destroy();
//                }
//            });

//            if (MoPub.isSdkInitialized()){
//                moPubBanner.loadAd();
//            }



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
                    Log.d("TAG", "TAG");
                }
            }
        }
        else super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String spinnerId = parent.getItemAtPosition(position).toString();
//        int parentId = parent.getId();

        switch (spinnerId){
            case "Default":
                server="";
                break;
            case "North Virginia":
                //set server to north virginia
                server = "http://nvirginia-my.mobfox.com";
                break;
            case "Tokyo":
                //set server to tokyo
                server = "http://tokyo-my.mobfox.com";
                break;
            case "None":
                invhText.setText("fe96717d9875b9da4339ea5367eff1ec");
                break;
            case "AdMob":
                invhText.setText("0daece1ffdca327ae9e7644f3db869e2");
                break;
            case "MoPub":
                invhText.setText("30d36ccbff22b2c7dae0e3d2669be832");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
