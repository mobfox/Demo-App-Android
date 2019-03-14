package sdk.mobfox.com.mobfox_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class Tab6_AdMob_View extends Fragment {

    ImageView admobImg;

    Context c;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab6_admob_view, container, false);

        c = getContext();

        admobImg = (ImageView) rootView.findViewById(R.id.admoblogo);
        admobImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, Tab6_AdMob.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
