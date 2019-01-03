package sdk.mobfox.com.mobfox_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by shahafsh on 6/12/18.
 */

public class Tab4_Inter_View extends Fragment {

    ImageView interBtn;

    Context c;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab4_inter_view, container, false);

        c = getContext();

        interBtn = (ImageView) rootView.findViewById(R.id.image320x480);
        interBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, Tab4_Inter.class);
                startActivity(intent);
            }
        });
        return rootView;
    }


}
