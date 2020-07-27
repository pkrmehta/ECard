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

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.TestViewHolder>{

    Context mCtx;
    List<Phone> phoneList;

    public PhoneAdapter(Context mCtx,List<Phone> phoneList)
    {
        this.mCtx = mCtx;
        this.phoneList = phoneList;
    }
    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.list_phone,
                parent, false);
        TestViewHolder testViewHolder = new TestViewHolder(view);
        return testViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        Phone phone = phoneList.get(position);

        holder.number.setText(phone.getNumber());
    }

    @Override
    public int getItemCount() {
        return phoneList.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder
    {
        TextView countrycode,number;
        public TestViewHolder(View itemView) {
            super(itemView);
            countrycode = (TextView) itemView.findViewById(R.id.listPhone_countrycode);
            number = (TextView) itemView.findViewById(R.id.listPhone_phone);
        }
    }
}
