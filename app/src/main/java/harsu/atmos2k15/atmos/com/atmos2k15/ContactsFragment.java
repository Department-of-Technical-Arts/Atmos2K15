package harsu.atmos2k15.atmos.com.atmos2k15;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import harsu.atmos2k15.atmos.com.atmos2k15.adapter.ContactAdapter;
import harsu.atmos2k15.atmos.com.atmos2k15.set.Contacts;
import helper.RecyclerClickListener;


public class ContactsFragment extends Fragment implements RecyclerClickListener {

    ContactAdapter contactAdapter;
    RecyclerView recyclerView;
    ArrayList<Contacts> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.contacts_recycler);
        contactAdapter = new ContactAdapter(getActivity());
        contactAdapter.setClickListener(this);
        recyclerView.setAdapter(contactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactAdapter.setArrayList(data);
        feedData();

    }

    private void feedData() {
        Contacts temp1 = new Contacts("Prudhvi Potluri", "President", "+918187874280", "prudhvi@bits-atmos.org", R.drawable.prudhvilastrof);
        Contacts temp2 = new Contacts("Kaushal Raut", "General Secretary", "+918501926741", "kaushal@bits-atmos.org", R.drawable.kaushalastrof);
        Contacts temp3 = new Contacts("Kashyap Chaganti", "Sponsorship and Marketing", "+918186848186", "kashyap@bits-atmos.org", R.drawable.kashyapastrof);
        Contacts temp4 = new Contacts("Ashishkumar Alla", "Publicity and Social Media Relations", "+919705235153", "ashish@bits-atmos.org", R.drawable.ashishastro);
        Contacts temp5 = new Contacts("Saumya Kumaar Saksena", "Technical Convener", "+917730903488", "saumya@bits-atmos.org", R.drawable.saumyaastro);
        Contacts temp6 = new Contacts("Chanakya Madireddy", "Logistics & Operations", "+918185903454", "chanakya@bits-atmos.org", R.drawable.chanakyaastrof);
        Contacts temp7 = new Contacts("Aditya Taank", "Hospitality & Accommodation", "+918185092055", "aditya@bits-atmos.org", R.drawable.taaankastrof);
        Contacts temp8 = new Contacts("Shaik Mohammad Owais", "Website & Creatives", "+919010009455", "owais@bits-atmos.org", R.drawable.owaisastrof);
        data.add(temp1);
        data.add(temp2);
        data.add(temp3);
        data.add(temp4);
        data.add(temp5);
        data.add(temp6);
        data.add(temp7);
        data.add(temp8);
        contactAdapter.notifyItemRangeInserted(0, data.size() - 1);
    }


    @Override
    public void onClick(View view, int position) {
        if (view.getId() == R.id.call) {
            switch (position) {
                case 0:
                    Uri number0 = Uri.parse("tel:+918187874280");
                    startActivity(new Intent(Intent.ACTION_DIAL, number0));
                    break;
                case 1:
                    Uri number1 = Uri.parse("tel:+918501926741");
                    startActivity(new Intent(Intent.ACTION_DIAL, number1));
                    break;
                case 2:
                    Uri number2 = Uri.parse("tel:+918186848186");
                    startActivity(new Intent(Intent.ACTION_DIAL, number2));
                    break;
                case 3:
                    Uri number3 = Uri.parse("tel:+919705235153");
                    startActivity(new Intent(Intent.ACTION_DIAL, number3));
                    break;
                case 4:
                    Uri number4 = Uri.parse("tel:+917730903488");
                    startActivity(new Intent(Intent.ACTION_DIAL, number4));
                    break;
                case 5:
                    Uri number5 = Uri.parse("tel:+918185903454");
                    startActivity(new Intent(Intent.ACTION_DIAL, number5));
                    break;
                case 6:
                    Uri number6 = Uri.parse("tel:+918185092055");
                    startActivity(new Intent(Intent.ACTION_DIAL, number6));
                    break;
                case 7:
                    Uri number7 = Uri.parse("tel:+919010009455");
                    startActivity(new Intent(Intent.ACTION_DIAL, number7));
                    break;
            }
        }
        if (view.getId() == R.id.email) {
            switch (position) {
                case 0:
                    Intent emailIntent0 = new Intent(Intent.ACTION_SEND);
                    emailIntent0.setType("text/html").putExtra(Intent.EXTRA_EMAIL, new String[]{"prudhvi@bits-atmos.org"});
                    startActivity(emailIntent0);
                    break;
                case 1:
                    Intent emailIntent1 = new Intent(Intent.ACTION_SEND);
                    emailIntent1.setType("text/html").putExtra(Intent.EXTRA_EMAIL, new String[]{"kaushal@bits-atmos.org"});
                    startActivity(emailIntent1);
                    break;
                case 2:
                    Intent emailIntent2 = new Intent(Intent.ACTION_SEND);
                    emailIntent2.setType("text/html").putExtra(Intent.EXTRA_EMAIL, new String[]{"kashyap@bits-atmos.org"});
                    startActivity(emailIntent2);
                    break;
                case 3:
                    Intent emailIntent3 = new Intent(Intent.ACTION_SEND);
                    emailIntent3.setType("text/html").putExtra(Intent.EXTRA_EMAIL, new String[]{"ashish@bits-atmos.org"});
                    startActivity(emailIntent3);
                    break;
                case 4:
                    Intent emailIntent4 = new Intent(Intent.ACTION_SEND);
                    emailIntent4.setType("text/html").putExtra(Intent.EXTRA_EMAIL, new String[]{"saumya@bits-atmos.org"});
                    startActivity(emailIntent4);
                    break;
                case 5:
                    Intent emailIntent5 = new Intent(Intent.ACTION_SEND);
                    emailIntent5.setType("text/html").putExtra(Intent.EXTRA_EMAIL, new String[]{"chanakya@bits-atmos.org"});
                    startActivity(emailIntent5);
                    break;
                case 6:
                    Intent emailIntent6 = new Intent(Intent.ACTION_SEND);
                    emailIntent6.setType("text/html").putExtra(Intent.EXTRA_EMAIL, new String[]{"aditya@bits-atmos.org"});
                    startActivity(emailIntent6);
                    break;
                case 7:
                    Intent emailIntent7 = new Intent(Intent.ACTION_SEND);
                    emailIntent7.setType("text/html").putExtra(Intent.EXTRA_EMAIL, new String[]{"owais@bits-atmos.org"});
                    startActivity(emailIntent7);
                    break;
            }
        }
    }
}