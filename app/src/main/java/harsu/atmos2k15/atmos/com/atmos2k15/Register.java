package harsu.atmos2k15.atmos.com.atmos2k15;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Register extends Fragment {


    Button CampusAmbassasor;


    public Register() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner yearspinner = (Spinner) view.findViewById(R.id.year_spinner);
        ArrayAdapter<CharSequence> yearadapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.year_array, android.R.layout.simple_spinner_item);
        yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearspinner.setAdapter(yearadapter);

        CampusAmbassasor = (Button) view.findViewById(R.id.registerambassador);
        CampusAmbassasor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse("http://bits-atmos.org/register/campus_register.php");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });


/*        Spinner spinner = (Spinner) view.findViewById(R.id.events_spinner);
        ArrayAdapter<CharSequence> eventsadapter = ArrayAdapter.createFromResource(this,
                R.array.events_array, android.R.layout.simple_spinner_item);

        eventsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(eventsadapter);*/


    }



}
