package sdk.mobfox.com.mobfoxdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.mobfox.sdk.bannerads.SizeUtils;
import com.mobfox.sdk.networking.MobfoxRequestParams;
import com.mobfox.sdk.tags.BannerTag;

import sdk.mobfox.com.mobfoxdemo.barcode.BarcodeCaptureActivity;

public class URLBanner extends Activity implements AdapterView.OnItemSelectedListener{


    private static final int BARCODE_READER_REQUEST_CODE = 1;
    EditText creativeURL;

//    Button size320;
//    Button size250;
    Button qrcode;
    Button loadUrlBanner;

    BannerTag banner;
    LinearLayout view;
    int width, height;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlbanner);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(144,202,249));


        view = (LinearLayout) findViewById(R.id.banner_container);

        c = getApplicationContext();

        loadUrlBanner = (Button) findViewById(R.id.loadurl_bnr);
        loadUrlBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBanner(width, height);
            }
        });

//        size320 = (Button) findViewById(R.id.button3);
//        size250 = (Button) findViewById(R.id.button4);
//
////
//        size320.setOnClickListener(new SizeListener(SizeUtils.DEFAULT_BANNER_WIDTH, SizeUtils.DEFAULT_BANNER_HEIGHT));
//        size250.setOnClickListener(new SizeListener(300, 250));


//        SizeListener sizeListener = new SizeListener(width, height);

        Spinner sizeSpinner = (Spinner) findViewById(R.id.bnrsize_spinner);
        ArrayAdapter<CharSequence> sizeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.sizes_array, android.R.layout.simple_spinner_item);
        sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);
        sizeSpinner.setOnItemSelectedListener(this);

        creativeURL = (EditText) findViewById(R.id.creativeUrl);

        qrcode = (Button) findViewById(R.id.qrcode);
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });

    }

    public void loadBanner(int width, int height) {

        if (width ==0 || height == 0){
            Toast.makeText(c, "Please select size", Toast.LENGTH_SHORT).show();
        }else {

//        if(banner!=null) {
//            view.removeView(banner);
//        }
        banner = new BannerTag(this, width, height, "xxx");
        banner.setListener(new BannerTag.Listener() {
            @Override
            public void onBannerError(View banner, String e) {

            }

            @Override
            public void onBannerLoaded(View banner) {
                view.addView(banner);
            }

            @Override
            public void onBannerClosed(View banner) {

            }

            @Override
            public void onBannerFinished() {

            }

            @Override
            public void onBannerClicked(View banner) {

            }

            @Override
            public void onNoFill(View banner) {

            }
        });

        String creativeURLStr = creativeURL.getText().toString();

        MobfoxRequestParams rqp = new MobfoxRequestParams();

        rqp.setParam(MobfoxRequestParams.CREATIVE_URL, creativeURLStr);
        banner.setParams(rqp);

        banner.loadCreative();
        
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String spinnerId = parent.getItemAtPosition(position).toString();
//        int parentId = parent.getId();

        //        size320.setOnClickListener(new SizeListener(SizeUtils.DEFAULT_BANNER_WIDTH, SizeUtils.DEFAULT_BANNER_HEIGHT));
//        size250.setOnClickListener(new SizeListener(300, 250));
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
            case "NorthVir":
                //set server to north virginia
                Toast.makeText(c,"North Virginia",Toast.LENGTH_SHORT).show();
                break;
            case "Tokyo":
                //set server to tokyo
                Toast.makeText(c,"Tokyo",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                    creativeURL.setText(barcode.displayValue, TextView.BufferType.EDITABLE);
                }
            }
        }
        else super.onActivityResult(requestCode, resultCode, data);
    }


}
