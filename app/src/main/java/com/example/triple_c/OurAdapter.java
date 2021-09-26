package com.example.triple_c;

import android.icu.text.CaseMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Request;

import java.util.ArrayList;
import java.util.List;

public class OurAdapter extends RecyclerView.Adapter<OurAdapter.RequestViewHolder> {

    List<Request> allRequests = new ArrayList<Request>();

    public OurAdapter(List<Request> list) {
        this.allRequests = list;
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder{
        public Request request;
        View itemView ;
        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;

        }
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_requests_in_profile , parent , false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
    holder.request = allRequests.get(position);
        TextView requestName=holder.itemView.findViewById(R.id.requestNameInFragment);
        requestName.setText(holder.request.getName());
    }

    @Override
    public int getItemCount() {
        return allRequests.size();
    }

}
