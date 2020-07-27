package com.pkdev.ecard.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pkdev.ecard.ContactDetails;
import com.pkdev.ecard.MyContacts;
import com.pkdev.ecard.R;
import com.pkdev.ecard.model.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.TestViewHolder> {

    Context mCtx;
    List<Contact> contactList;
    MyContacts myContacts;

    public ContactAdapter(Context mCtx, List<Contact> contactList) {
        this.mCtx = mCtx;
        this.contactList = contactList;
        this.myContacts = (MyContacts) mCtx;
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
    public void onBindViewHolder(@NonNull final TestViewHolder holder, final int position) {
        final Contact contact = contactList.get(position);

        if (myContacts.pos == position) {
            holder.checkBox.setChecked(true);
            myContacts.pos = -1;
        }
        holder.contactName.setText(contact.getName());
        holder.contactTitle.setText(contact.getTitle());

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!myContacts.isActionMode) {
                    Intent intent = new Intent(mCtx, ContactDetails.class);
                    intent.putExtra("USER_ID", contact.getUserid());
                    mCtx.startActivity(intent);
                }
            }
        });

        if (myContacts.isActionMode) {
            Anim anim = new Anim(100, holder.layoutCheck);
            anim.setDuration(300);
            holder.layoutCheck.setAnimation(anim);
        } else {
            Anim anim = new Anim(0, holder.layoutCheck);
            anim.setDuration(300);
            holder.layoutCheck.setAnimation(anim);
            holder.checkBox.setChecked(false);
        }

        holder.contact.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                myContacts.startSelection(position);
                return true;
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myContacts.check(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder {
        ImageView contactImage;
        TextView contactName, contactTitle;
        LinearLayout contact;
        RelativeLayout layoutCheck;
        CheckBox checkBox;

        public TestViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.list_check);
            layoutCheck = (RelativeLayout) itemView.findViewById(R.id.listContact_checkbox);
            contactImage = (ImageView) itemView.findViewById(R.id.listContact_image);
            contactName = (TextView) itemView.findViewById(R.id.listContact_name);
            contact = (LinearLayout) itemView.findViewById(R.id.listContact_list);
            contactTitle = (TextView) itemView.findViewById(R.id.listContact_desc);
        }
    }

    class Anim extends Animation {
        private int width, startWidth;
        private View view;

        public Anim(int width, View v) {
            this.width = width;
            this.view = v;
            this.startWidth = view.getWidth();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newWidth = startWidth + (int) ((width - startWidth) * interpolatedTime);
            view.getLayoutParams().width = newWidth;
            view.requestLayout();

            super.applyTransformation(interpolatedTime, t);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }
}
