package com.example.projectx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartConfirmRecycleAdapter extends RecyclerView.Adapter<CartConfirmRecycleAdapter.CartViewHolder> {


    private ArrayList<CartModelClass> allOrder=new ArrayList<>();

    public static  class CartViewHolder extends RecyclerView.ViewHolder{

        public TextView itemname,unitprice,unit;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            itemname=itemView.findViewById(R.id.order_item_name);
            unitprice=itemView.findViewById(R.id.order_unit_price);
            unit=itemView.findViewById(R.id.order_units);
        }


    }

    public CartConfirmRecycleAdapter(ArrayList<CartModelClass>Riders){
        this.allOrder=Riders;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartconfrim_recycler_model,parent,false);
        CartViewHolder cartViewHolder=new CartViewHolder(v);

        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        final CartModelClass cartModelClass=allOrder.get(position);

        holder.itemname.setText(cartModelClass.getFoodName());
        holder.unitprice.setText(cartModelClass.getUnitPrice());
        holder.unit.setText(cartModelClass.getunit());
        if(position==0)CartConfirmOrder.totalpayable=0;
        CartConfirmOrder.totalpayable=(CartConfirmOrder.totalpayable+cartModelClass.getPayable());

        if(position+1==getItemCount()){
            CartConfirmOrder.totaltk.setText(String.valueOf(CartConfirmOrder.totalpayable-Integer.parseInt(CartConfirmOrder.cartConfirmOrderModelClass.getPromoAmount()))+ " BDT");
            CartConfirmOrder.lastpromo.setText( "Promo: -"+ String.valueOf(CartConfirmOrder.cartConfirmOrderModelClass.getPromoAmount())+" BDT"  );
        }
    }

    @Override
    public int getItemCount() {
        return this.allOrder.size();
    }
}
