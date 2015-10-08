package harsu.atmos2k15.atmos.com.atmos2015.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import harsu.atmos2k15.atmos.com.atmos2015.R;
import harsu.atmos2k15.atmos.com.atmos2015.set.Contacts;
import helper.RecyclerClickListener;


/**
 * Created by TEJESHWAR REDDY on 20-09-2015.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    ArrayList<Contacts> arrayList;
    Context context;
    LayoutInflater inflater;

    RecyclerClickListener clickListener;

    public void setClickListener(RecyclerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ContactAdapter (Context context){
        inflater=LayoutInflater.from(context);
        arrayList = new ArrayList<>();
        this.context=context;
    }

    public void setArrayList(ArrayList<Contacts> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(inflater.inflate(R.layout.custom_contact_row,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(arrayList.get(i).getName());
        myViewHolder.designation.setText((arrayList.get(i).getDesignation()));
        myViewHolder.imageView.setImageResource(arrayList.get(i).getImage());
        myViewHolder.numberTV.setText(arrayList.get(i).getMobile());
        myViewHolder.emailTV.setText(arrayList.get(i).getEmail());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name,designation,numberTV,emailTV;
        RelativeLayout email,mobile;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.contact_image);
            name=(TextView) itemView.findViewById(R.id.contact_name);
            designation=(TextView)itemView.findViewById(R.id.contact_designation);
            mobile=(RelativeLayout)itemView.findViewById(R.id.call);
            email=(RelativeLayout)itemView.findViewById(R.id.email);
            numberTV=(TextView) itemView.findViewById(R.id.phoneno);
            emailTV=(TextView) itemView.findViewById(R.id.emailTV);
            if(clickListener!=null)
            {
                mobile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onClick(v, getAdapterPosition());
                    }
                });
                email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onClick(v, getAdapterPosition());
                    }
                });
            }
        }
    }
}
