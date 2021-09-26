package com.example.triple_c;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Car;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    List<Car> carList = new ArrayList<Car>();

    public CarAdapter(List<Car> carList) {
        this.carList = carList;
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        public Car car;
        View itemView;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            itemView.setOnClickListener((view -> {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                sharedPreferencesEditor.putString("carId", car.getId());
                sharedPreferencesEditor.apply();
            }));
        }
    }
    
    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_car, parent, false);
        CarViewHolder carViewHolder = new CarViewHolder(view);
        return carViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CarViewHolder holder, int position) {
        holder.car = carList.get(position);
        RadioButton title = holder.itemView.findViewById(R.id.typeInFrag);
//        TextView body = holder.itemView.findViewById(R.id.modelInFrag);
        title.setText(holder.car.getType());
//        body.setText(holder.car.getModel());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

}
