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

import com.mobfox.sdk.banner.Banner;
import com.mobfox.sdk.networking.MobfoxRequestParams;

import sdk.mobfox.com.mobfox_app.barcode.BarcodeCaptureActivity;

/**
 * Created by shahafsh on 6/12/18.
 */

public class Tab3_300x250  extends Activity implements AdapterView.OnItemSelectedListener{

    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private static String invh = "fe96717d9875b9da4339ea5367eff1ec";

    public TextView logText;
    public EditText invhText, floorText;
    public Button qrcode, loadBtn;
    public Context c;
    public MobfoxRequestParams requestParams;

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

        floorText   = (EditText)    findViewById(R.id.floor_etext);
        logText     = (TextView)    findViewById(R.id.logText);
        invhText    = (EditText)    findViewById(R.id.invhText);
        loadBtn     = (Button)      findViewById(R.id.load_btn);
        qrcode      = (Button)      findViewById(R.id.qrcode);
        banner      = (Banner)      findViewById(R.id.banner250);

        Spinner serverSpinner   = (Spinner) findViewById(R.id.server_spinner);

        invhText.setText(invh);


        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });


        ArrayAdapter<CharSequence> serverSpinnerAdapter = ArrayAdapter.createFromResource(c,
                R.array.servers_array, android.R.layout.simple_spinner_item);

        serverSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serverSpinner.setAdapter(serverSpinnerAdapter);
        serverSpinner.setOnItemSelectedListener(this);


        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBanner();
            }
        });

    }



    public void loadBanner() {

        banner.setListener(new Banner.Listener() {
            @Override
            public void onBannerError(Banner banner, Exception e) {
                Toast.makeText(c, e.toString(), Toast.LENGTH_SHORT).show();
                logText.setText(e.toString());
            }

            @Override
            public void onBannerLoaded(Banner banner) {
                Toast.makeText(c, "ad loaded", Toast.LENGTH_SHORT).show();
                view.addView(banner);
                logText.setText("");
            }

            @Override
            public void onBannerClosed(Banner banner) {
                Toast.makeText(c, "ad closed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onBannerFinished() {
                Toast.makeText(c, "ad finished", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onBannerClicked(Banner banner) {
                Toast.makeText(c, "ad clicked", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNoFill(Banner banner) {
                Toast.makeText(c, "no fill", Toast.LENGTH_SHORT).show();

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

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String spinnerId = parent.getItemAtPosition(position).toString();
//        int parentId = parent.getId();

        switch (spinnerId){
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
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}