package com.pkdev.ecard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pkdev.ecard.R;
import com.pkdev.ecard.model.Work;

import java.util.List;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.TestViewHolder>{

    Context mCtx;
    List<Work> workList;

    public WorkAdapter(Context mCtx,List<Work> workList)
    {
        this.mCtx = mCtx;
        this.workList = workList;
    }
    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.list_work_experience,
                parent, false);
        TestViewHolder testViewHolder = new TestViewHolder(view);
        return testViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        Work work = workList.get(position);
        holder.company.setText(work.getCompany());
        holder.end.setText(work.getEnd());
        holder.start.setText(work.getStart());
        holder.position.setText(work.getPosition());
    }

    @Override
    public int getItemCount() {
        return workList.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder
    {
        TextView company,position,start,end;
        public TestViewHolder(View itemView) {
            super(itemView);
            company = (TextView) itemView.findViewById(R.id.listWork_company);
            position = (TextView) itemView.findViewById(R.id.listWork_designation);
            start = (TextView) itemView.findViewById(R.id.listWork_start);
            end = (TextView) itemView.findViewById(R.id.listWork_end);
        }
    }
}
