package harsu.atmos2k15.atmos.com.atmos2015;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Animation animFadein;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        animFadein = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.logo_anim);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView logo_img = (ImageView) rootView.findViewById(R.id.logo_image_view);
        logo_img.setAnimation(animFadein);
        return rootView;
    }


}
