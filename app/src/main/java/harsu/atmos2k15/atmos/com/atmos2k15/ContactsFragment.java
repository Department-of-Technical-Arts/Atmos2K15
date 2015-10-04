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
/*created by tejeshwar reddy

 */

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
            Uri number = Uri.parse("tel:" + data.get(position).getMobile());
            startActivity(new Intent(Intent.ACTION_DIAL, number));


        } else if (view.getId() == R.id.email) {

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/html").putExtra(Intent.EXTRA_EMAIL, new String[]{data.get(position).getEmail()});
            startActivity(emailIntent);


        }
    }
}