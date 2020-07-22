package com.pkdev.ecard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pkdev.ecard.R;
import com.pkdev.ecard.model.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.TestViewHolder>{

    Context mCtx;
    List<Address> addressList;

    public AddressAdapter(Context mCtx,List<Address> addressList)
    {
        this.mCtx = mCtx;
        this.addressList = addressList;
    }
    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.list_address,
                parent, false);
        TestViewHolder testViewHolder = new TestViewHolder(view);
        return testViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        Address address = addressList.get(position);
        holder.address.setText(address.getAddress());
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder
    {
        TextView address;
        public TestViewHolder(View itemView) {
            super(itemView);
            address = (TextView) itemView.findViewById(R.id.listAddress_address);
        }
    }
}
