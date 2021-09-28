package com.example.triple_c;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
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

            TextView carDeleteButton =itemView.findViewById(R.id.carDeleteButton);

            carDeleteButton.setOnClickListener((v)->{
                Amplify.API.mutate(ModelMutation.delete(car),
                        result -> {
                            Log.i("MyAmplifyApp", "Todo with id: " + result.getData().getId());
                        },
                        error -> {
                            Log.e("MyAmplifyApp", "Create failed", error);
                        }
                );

            });

            itemView.setOnClickListener(view -> {
                itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                sharedPreferencesEditor.putString("carId", car.getId());
                Toast.makeText(itemView.getContext(),  car.getType()+ "-" +car.getModel()+" Selected Successfully!", Toast.LENGTH_LONG).show();
                sharedPreferencesEditor.apply();
                itemView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundColor(Color.parseColor("#042C5A"));
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
