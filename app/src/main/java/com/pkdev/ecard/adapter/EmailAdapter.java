package com.pkdev.ecard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pkdev.ecard.R;
import com.pkdev.ecard.model.Email;

import java.util.List;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.TestViewHolder>{

    Context mCtx;
    List<Email> emailList;

    public EmailAdapter(Context mCtx,List<Email> emailList)
    {
        this.mCtx = mCtx;
        this.emailList = emailList;
    }
    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.list_email,
                parent, false);
        TestViewHolder testViewHolder = new TestViewHolder(view);
        return testViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        Email email = emailList.get(position);
        holder.emailAddress.setText(email.getEmail());
        holder.emailType.setText(email.getType());
    }

    @Override
    public int getItemCount() {
        return emailList.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder
    {
        TextView emailAddress,emailType;
        public TestViewHolder(View itemView) {
            super(itemView);
            emailAddress = (TextView) itemView.findViewById(R.id.listEmail_email);
            emailType = (TextView) itemView.findViewById(R.id.listEmail_type);
        }
    }
}
