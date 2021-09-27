package com.example.triple_c;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

            itemView.setOnClickListener(view -> {
                view.setBackgroundColor(Color.parseColor("#E6E6E6"));
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                sharedPreferencesEditor.putString("carId", car.getId());
                Toast.makeText(itemView.getContext(), "Car Selected Successfully!", Toast.LENGTH_LONG).show();
                sharedPreferencesEditor.apply();
                itemView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }, 150);
            });
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
        TextView type = holder.itemView.findViewById(R.id.typeInFrag);
        TextView model = holder.itemView.findViewById(R.id.modelInFrag);
        type.setText(holder.car.getType());
        model.setText(holder.car.getModel());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

}
