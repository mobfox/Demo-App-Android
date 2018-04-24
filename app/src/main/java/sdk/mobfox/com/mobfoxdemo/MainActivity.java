package sdk.mobfox.com.mobfoxdemo;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mobfox.sdk.bannerads.Banner;
import com.mobfox.sdk.interstitialads.InterstitialAd;

public class MainActivity extends ListActivity {

    static String cheesePackage = "sdk.mobfox.com.mobfoxdemo";

    String[] classes = {"MobfoxBanner", "MobfoxInterstitial", "MobfoxNative", "URLBanner", "URLInterstitial", "URLVastInterstitial"};
    Intent myIntent;
    Class myClass;
    Context myContext = MainActivity.this;
    String cheese;

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        cheese = classes[position];
        try {
            myClass = Class.forName(cheesePackage + "." + cheese);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        myIntent = new Intent(myContext, myClass);
        startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getListView().setBackgroundColor(Color.rgb(144,202,249));


        setListAdapter(new ArrayAdapter<>(myContext, android.R.layout.simple_expandable_list_item_1, classes));

        LinearLayout llMethods = new LinearLayout(myContext);
        LinearLayout.LayoutParams methodsP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llMethods.setOrientation(LinearLayout.HORIZONTAL);
        llMethods.setWeightSum(32f);
        llMethods.setLayoutParams(methodsP);

        CheckBox secure = new CheckBox(myContext);
        LinearLayout.LayoutParams secure_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        secure_params.weight = 8;
        secure.setText("secure");
        secure.setLayoutParams(secure_params);

        CheckBox inter_secure = new CheckBox(myContext);
        LinearLayout.LayoutParams inter_secure_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        inter_secure_params.weight = 8;
        inter_secure.setText("secure interstitial");
        inter_secure.setLayoutParams(inter_secure_params);

        llMethods.addView(secure);
//        llMethods.addView(inter_secure);

        ViewGroup lv = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        ViewGroup parent = (ViewGroup) lv.getParent();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.BOTTOM;

        parent.addView(llMethods, params);

        secure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Banner.setSecure(true);

                else
                    Banner.setSecure(false);
            }
        });

        inter_secure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    InterstitialAd.setSecure(true);
                else
                    InterstitialAd.setSecure(false);
            }
        });
    }
}