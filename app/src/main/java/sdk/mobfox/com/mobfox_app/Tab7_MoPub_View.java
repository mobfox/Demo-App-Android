package sdk.mobfox.com.mobfox_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Tab7_MoPub_View extends Fragment {


    ImageView mopubImg;

    Context c;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab7_mopub_view, container, false);

        c = getContext();

        mopubImg = (ImageView) rootView.findViewById(R.id.mopublogo);
        mopubImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, Tab7_MoPub.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
