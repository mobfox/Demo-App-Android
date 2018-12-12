package sdk.mobfox.com.mobfox_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mobfox.sdk.constants.Constants;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","sdk_support@mobfox.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MobFox Demo App: ");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPreferences !=null){
            String servrStr = sharedPreferences.getString("domain","");
            if (!servrStr.isEmpty()){
                //Constants.TAG_SERVER = servrStr +"/";
                //Toast.makeText(getApplicationContext(),servrStr,Toast.LENGTH_SHORT).show();
            } else {
                //Constants.TAG_SERVER = "https://sdk.starbolt.io/";
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            //Toast.makeText(this,"ABOUT..",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_settings){
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    Tab1_320x50_View tab1 = new Tab1_320x50_View();
                    return tab1;
                case 1:
                    Tab2_300x50_View tab2 = new Tab2_300x50_View();
                    return tab2;
                case 2:
                    Tab3_300x250_View tab3 = new Tab3_300x250_View();
                    return tab3;
                case 3:
                    Tab4_Inter_View tab4InterView = new Tab4_Inter_View();
                    return tab4InterView;
                case 4:
                    Tab5_AdMob_View tab5_adMob_view = new Tab5_AdMob_View();
                    return tab5_adMob_view;
                case 5:
                    Tab6_MoPub_View tab6_moPub_view = new Tab6_MoPub_View();
                    return tab6_moPub_view;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 6;
        }
    }


}
