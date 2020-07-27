package com.pkdev.ecard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pkdev.ecard.R;
import com.pkdev.ecard.model.Phone;
import com.pkdev.ecard.model.Website;

import java.util.List;

public class WebsiteAdapter extends RecyclerView.Adapter<WebsiteAdapter.TestViewHolder>{

    Context mCtx;
    List<Website> websiteList;

    public WebsiteAdapter(Context mCtx,List<Website> websiteList)
    {
        this.mCtx = mCtx;
        this.websiteList = websiteList;
    }
    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.list_website,
                parent, false);
        TestViewHolder testViewHolder = new TestViewHolder(view);
        return testViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        Website website = websiteList.get(position);
        holder.websiteAddress.setText(website.getWebsite());
    }

    @Override
    public int getItemCount() {
        return websiteList.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder
    {
        TextView websiteAddress;
        public TestViewHolder(View itemView) {
            super(itemView);
            websiteAddress = (TextView) itemView.findViewById(R.id.listAddress_address);
        }
    }
}
