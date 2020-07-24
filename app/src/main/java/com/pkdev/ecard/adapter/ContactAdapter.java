package com.pkdev.ecard.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pkdev.ecard.ContactDetails;
import com.pkdev.ecard.R;
import com.pkdev.ecard.model.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.TestViewHolder>{

    Context mCtx;
    List<Contact> contactList;

    public ContactAdapter(Context mCtx,List<Contact> contactList)
    {
        this.mCtx = mCtx;
        this.contactList = contactList;
    }
    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.list_contact,
                parent, false);
        TestViewHolder testViewHolder = new TestViewHolder(view);
        return testViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        final Contact contact = contactList.get(position);

        holder.contactName.setText(contact.getName());
        holder.contactTitle.setText(contact.getTitle());

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx, ContactDetails.class);
                intent.putExtra("USER_ID",contact.getUserid());
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder
    {
        ImageView contactImage;
        TextView contactName,contactTitle;
        LinearLayout contact;
        public TestViewHolder(View itemView) {
            super(itemView);
            contactImage = (ImageView) itemView.findViewById(R.id.listContact_image);
            contactName = (TextView) itemView.findViewById(R.id.listContact_name);
            contact = (LinearLayout) itemView.findViewById(R.id.listContact_list);
            contactTitle = (TextView) itemView.findViewById(R.id.listContact_desc);
        }
    }
}
