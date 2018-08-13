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

/**
 * Created by shahafsh on 6/12/18.
 */

public class Tab3_300x250 extends Activity implements AdapterView.OnItemSelectedListener {

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
    LinearLayout view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3_300x250);

        c = getApplicationContext();

        requestParams = new MobfoxRequestParams();

        floorText = findViewById(R.id.floor_etext);
        logText = findViewById(R.id.logText);
        invhText = findViewById(R.id.invhText);
        loadBtn = findViewById(R.id.load_btn);
        qrcode = findViewById(R.id.qrcode);
        view = findViewById(R.id.banner_container300x250);


        Spinner serverSpinner = findViewById(R.id.server_spinner);

        ArrayAdapter<CharSequence> serverSpinnerAdapter = ArrayAdapter.createFromResource(c,
                R.array.servers_array, android.R.layout.simple_spinner_item);

        serverSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serverSpinner.setAdapter(serverSpinnerAdapter);
        serverSpinner.setOnItemSelectedListener(this);
        serverSpinner.setPrompt("Server");


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
                loadBanner();
            }
        });
    }


    public void loadBanner() {


        invh = invhText.getText().toString();

        banner = new Banner(c, 300, 250, invh, new Banner.Listener() {
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

        if (!server.equals("")) {
            if (server.equals("http://nvirginia-my.mobfox.com")) {
                requestParams.setParam("debugResponseURL", server);
                banner.addParams(requestParams);
            }
            if (server.equals("http://tokyo-my.mobfox.com")) {
                requestParams.setParam("debugResponseURL", server);
                banner.addParams(requestParams);

            }

        }

        banner.load();


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
        } else super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String spinnerId = parent.getItemAtPosition(position).toString();
//        int parentId = parent.getId();

        switch (spinnerId) {
            case "Default":
                server = "";
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
                mediation = 0;
                break;
            case "AdMob":
                mediation = 1;
                break;
            case "MoPub":
                mediation = 2;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}