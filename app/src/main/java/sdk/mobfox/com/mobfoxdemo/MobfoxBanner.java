package sdk.mobfox.com.mobfoxdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.mobfox.sdk.bannerads.SizeUtils;
import com.mobfox.sdk.networking.MobfoxRequestParams;
import com.mobfox.sdk.tags.BannerTag;

import sdk.mobfox.com.mobfoxdemo.barcode.BarcodeCaptureActivity;

public class MobfoxBanner extends Activity implements AdapterView.OnItemSelectedListener {

    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private static String invh = "fe96717d9875b9da4339ea5367eff1ec";

    public TextView logText;
    public EditText invhText,floorText;
    public Button qrcode,loadBtn;
    public Context c;
    public MobfoxRequestParams requestParams;

///    Button size300;
///    Button size320;
///    Button size250;

    float floor = -1;
    int width,height = 0;
    String server="";

    BannerTag banner;
    LinearLayout view;
    View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ad);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(144,202,249));

        c = getApplicationContext();

        requestParams = new MobfoxRequestParams();

        view        = (LinearLayout)findViewById(R.id.banner_container);

        floorText   = (EditText)    findViewById(R.id.floor_etext);
        logText     = (TextView)    findViewById(R.id.logText);
        invhText    = (EditText)    findViewById(R.id.invhText);
        loadBtn     = (Button)      findViewById(R.id.load_btn);
        qrcode      = (Button)      findViewById(R.id.qrcode);

        Spinner sizeSpinner     = (Spinner) findViewById(R.id.size_spinner);
        Spinner serverSpinner   = (Spinner) findViewById(R.id.server_spinner);

        invhText.setText(invh);


        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });

        ArrayAdapter<CharSequence> sizeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.sizes_array, android.R.layout.simple_spinner_item);

        sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);
        sizeSpinner.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> serverSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.servers_array, android.R.layout.simple_spinner_item);

        serverSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serverSpinner.setAdapter(serverSpinnerAdapter);
        serverSpinner.setOnItemSelectedListener(this);


        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBanner(width,height);
            }
        });


    }

    public void loadBanner(int width, int height) {

        invh = invhText.getText().toString();
        if (height == 0 || width == 0) {
            Toast.makeText(c, "Please select Banner size.", Toast.LENGTH_SHORT).show();
            logText.setText("No Banner size selected.");
        } else {

            banner = new BannerTag(this, width, height, invh);
            banner.setListener(new BannerTag.Listener() {
                @Override
                public void onBannerError(View banner, String e) {
                    Toast.makeText(c, e, Toast.LENGTH_SHORT).show();
                    logText.setText(e);
                }

                @Override
                public void onBannerLoaded(View banner) {
                    Toast.makeText(c, "ad loaded", Toast.LENGTH_SHORT).show();
                    view.addView(banner);
                    logText.setText("");
                }

                @Override
                public void onBannerClosed(View banner) {
                    Toast.makeText(c, "closed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onBannerFinished() {
                    Toast.makeText(c, "finished", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onBannerClicked(View banner) {
                    Toast.makeText(c, "clicked", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNoFill(View banner) {
                    Toast.makeText(c, "no fill", Toast.LENGTH_SHORT).show();
                }
            });

            Log.d("TAG", "TAG");

            if (floorText.getText().length() > 0) {
                floor = Float.parseFloat(floorText.getText().toString());
                if (floor > 0) {
                    requestParams.setParam(MobfoxRequestParams.R_FLOOR, floor);
                    banner.setParams(requestParams);
                }
            }

            if (!server.equals("")){
                if (server.equals("http://nvirginia-my.mobfox.com")){
                    requestParams.setParam("debugResponseURL", server);
                    banner.setParams(requestParams);
                }
                if (server.equals("http://tokyo-my.mobfox.com")){
                    requestParams.setParam("debugResponseURL", server);
                    banner.setParams(requestParams);

                }

            }



            banner.load();
        }
    }


    public static void makeToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(banner != null) {
            banner.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(banner != null) {
            banner.onResume();
        }
    }



    ///////////// Listener \\\\\\\\\\\\

    class SizeListener implements Button.OnClickListener {

        int width;
        int height;

        SizeListener(int w, int h) {
            width  = w;
            height = h;
        }

        @Override
        public void onClick(View view) {
            loadBanner(width, height);
        }
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
            case "300x50":
                width=300;
                height=50;
                break;
            case "320x50":
                width=320;
                height=50;
                break;
            case "300x250":
                width=300;
                height=250;
                break;
            case "Size":
                width=0;
                height=0;
                break;
            case "Server":
                server="";
                break;
            case "North Virginia":
                //set server to north virginia
                server = "http://nvirginia-my.mobfox.com";
//                Toast.makeText(c,"North Virginia",Toast.LENGTH_SHORT).show();
                break;
            case "Tokyo":
                //set server to tokyo
                server = "http://tokyo-my.mobfox.com";
//                Toast.makeText(c,"Tokyo",Toast.LENGTH_SHORT).show();
                break;
        }


        Log.d("val", spinnerId);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}