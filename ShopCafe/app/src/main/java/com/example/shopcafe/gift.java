package com.example.shopcafe;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shopcafe.database.APPDatabase;

import java.util.List;


public class gift extends Fragment{

    private List<OrderItem> listRewards;
    private List<Integer> listint;
    TextView points, Ndrink;

    RecyclerView revrewards, revLoyalty;
    RewardsAdapter rewardsAdapter;
    OrderViewModel orderViewModel;
    Button redeem;
    public gift() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        points = view.findViewById(R.id.score_points);
        redeem = view.findViewById(R.id.reddem_drinks);
        Ndrink = view.findViewById(R.id.ndrink);
        revLoyalty = view.findViewById(R.id.recycler_loyaltycard_rewards);
        revrewards = view.findViewById(R.id.recylerRewards);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        revrewards.setLayoutManager(linearLayoutManager);

        orderViewModel = new ViewModelProvider(gift.this).get(OrderViewModel.class);
        orderViewModel.getAllOrder().observe(getViewLifecycleOwner(), new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> list) {
                listRewards = list;
                rewardsAdapter = new RewardsAdapter(listRewards);
                revrewards.setAdapter(rewardsAdapter);
            }
        });

        int quantity = orderViewModel.SumOfQuantity();
        int point = quantity*12;
        int sumChangePoints = APPDatabase.getInstance(getActivity()).redeemDrinkDAO().getSumPoint();
        int current_point = point - sumChangePoints;
        points.setText(String.valueOf(current_point));

        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Redeem.class);
                startActivity(intent);
            }
        });

    }





    public static gift newInstance() {
        gift fragment = new gift();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gift, container, false);
    }
}