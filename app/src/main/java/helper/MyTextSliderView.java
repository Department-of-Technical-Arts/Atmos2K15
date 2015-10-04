package helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

/**
 * This is a slider with a description TextView.
 */
public class MyTextSliderView extends BaseSliderView {
    public MyTextSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(com.daimajia.slider.library.R.layout.render_type_default,null);
        ImageView target = (ImageView)v.findViewById(com.daimajia.slider.library.R.id.daimajia_slider_image);
//        TextView description = (TextView)v.findViewById(R.id.description);
//        description.setText(getDescription());
        bindEventAndShow(v, target);
        return v;
    }
}
