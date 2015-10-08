package harsu.atmos2k15.atmos.com.atmos2015;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Register extends Fragment {


    WebView webView;
    Button CampusAmbassasor;
    Button register;


    public Register() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        webView= (WebView) rootView.findViewById(R.id.register_web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://bits-atmos.org/register/index.php");
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //CampusAmbassasor = (Button) view.findViewById(R.id.cam_register_btn);
        /**CampusAmbassasor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse("http://bits-atmos.org/register/campus_register.php");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });**/

    }



}
