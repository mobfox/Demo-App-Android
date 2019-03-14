package sdk.mobfox.com.mobfox_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by shahafsh on 6/12/18.
 */

public class Tab5_Native_View extends Fragment {

    ImageView nativeBtn;

    Context c;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab5_native_view, container, false);

        c = getContext();

        nativeBtn = rootView.findViewById(R.id.image300x250);
        nativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, Tab5_Native.class);
                startActivity(intent);
            }
        });
        return rootView;
    }


}
