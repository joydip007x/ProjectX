package com.example.projectx;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class CartConfirmOrder extends AppCompatActivity {

    static ArrayList<CartModelClass> allOrder;
    private DatabaseReference db,db2;
    private RecyclerView recyclerView;
    static  RecyclerView.Adapter Adapter;
    private RecyclerView.LayoutManager layoutManager;
    String postUID;
    int grouppos;
    static CartConfirmOrderModelClass cartConfirmOrderModelClass;
    static  Integer  totalpayable=new Integer(0);
    static  int selectedRider;

    ArrayList<String> listavailableRider=new ArrayList<>();

    Button acceptb,rejectb;
    TextView name,phone,address,promocode ;
    static  TextView totaltk,lastpromo,promoamount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_confirmorder);
        postUID= getIntent().getExtras().getString("postUID");
        grouppos= Integer.parseInt(        Objects.requireNonNull(getIntent().getExtras().getString("gPos")));

        acceptb=findViewById(R.id.cart_accept);
        rejectb=findViewById(R.id.cart_reject);

        if(grouppos==1){

             acceptb.setText("Update Rider");
             rejectb.setText("Order Completed");
        }
        else if(grouppos==2){

             acceptb.setText("DELETE HISTORY");
             rejectb.setText("GO BACK");
        }

        name=findViewById(R.id.cart_name);
        address=findViewById(R.id.cart_address);
        phone=findViewById(R.id.cart_number);
        promoamount=findViewById(R.id.cart_promotk);
        promocode=findViewById(R.id.cart_promo_name);
        lastpromo=findViewById(R.id.cart_finalpromo);
        totaltk=findViewById(R.id.cart_total);

        recyclerView=findViewById(R.id.cart_recycle);


        db= FirebaseDatabase.getInstance().getReference("Restaurant").child(UtilitiesX.UID).child("Orders").child(postUID).child("Foodlist");
        db2=FirebaseDatabase.getInstance().getReference("Restaurant").child(UtilitiesX.UID).child("Orders").child(postUID);

        readHashMapData(db2, new OnGetDataListener() {
            @Override
            public void onSuccess(String reg) {

                name.setText(cartConfirmOrderModelClass.getName());
                address.setText(cartConfirmOrderModelClass.getAddress());
                phone.setText(cartConfirmOrderModelClass.getNumber());
                promoamount.setText(cartConfirmOrderModelClass.getPromoAmount());
                promocode.setText(cartConfirmOrderModelClass.getPromocode());
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });

        acceptb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listavailableRider.clear();
                 if(grouppos==2){

                     BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(CartConfirmOrder.this)
                             .setTitle("Delete?")
                             .setMessage("Are you sure want to delete this from Order Histoy ?")
                             .setCancelable(true)
                             .setPositiveButton("Delete", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                                 @Override
                                 public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {


                                     DatabaseReference dref=FirebaseDatabase.getInstance().getReference("Restaurant")
                                             .child(UtilitiesX.UID).child("Orders").child(postUID);

                                     dref.addListenerForSingleValueEvent(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot snapshot) {

                                             OrderReceiver or=new OrderReceiver((HashMap) snapshot.getValue());

                                             Orders.L2.remove(or.titleDisplay());
                                             FirebaseDatabase.getInstance().getReference("Restaurant")
                                                     .child(UtilitiesX.UID).child("Orders").child(postUID).removeValue();
                                             Orders.orderAdapter.notifyDataSetChanged();
                                             CartConfirmOrder.super.onBackPressed();
                                         }

                                         @Override
                                         public void onCancelled(@NonNull DatabaseError error) {

                                         }
                                     });

                                     dialogInterface.dismiss();

                                 }
                             })
                             .setNegativeButton("Cancel", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {

                                 @Override
                                 public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                                     dialogInterface.dismiss();
                                 }
                             })
                             .build();
                     mBottomSheetDialog.show();



                     return;
                 }
                if(grouppos==1){

                    DatabaseReference drf=FirebaseDatabase.getInstance().getReference("Restaurant").child(UtilitiesX.UID).child("Orders")
                            .child(postUID);
                    drf.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            Toasty.info(CartConfirmOrder.this, "Current Rider : "+((HashMap)snapshot.getValue()).get("RIDER").toString() ,Toast.LENGTH_LONG, true).show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                readRiderData(  FirebaseDatabase.getInstance().
                        getReference("Restaurant").child(UtilitiesX.UID).child("Rider"), new OnGetDataListener() {
                    @Override
                    public void onSuccess(String reg) {


                        final String[] colors = listavailableRider.toArray(new String[0]);
                        final MaterialAlertDialogBuilder materialAlertDialogBuilder=new MaterialAlertDialogBuilder(CartConfirmOrder.this);
                        int checkeditem =0;

                        materialAlertDialogBuilder.setTitle( "Pick A Rider")
                               .setSingleChoiceItems(colors, checkeditem, new DialogInterface.OnClickListener() {

                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                         selectedRider=which;
                                   }
                               })
                                .setPositiveButton("Select", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        String Y=colors[selectedRider];
                                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Restaurant").child(UtilitiesX.UID).child("Rider");

                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                             ///   System.out.println("LAAL "+snapshot.toString());
                                                for( DataSnapshot d:snapshot.getChildren()){

                                                     HashMap M= (HashMap) d.getValue();
                                                     String x=M.get("Rider").toString().concat(" : ").concat(M.get("Phone").toString());
                                                     final String y=colors[selectedRider];
                                                     final String riderUID=M.get("riderUID").toString();

                                                     if(x.equals(y)){

                                                         M.replace("STATUS","locked");
                                                         FirebaseDatabase.getInstance().getReference("Restaurant").child(UtilitiesX.UID)
                                                                 .child("Rider").child(riderUID).setValue(M);

                                                         DatabaseReference db=FirebaseDatabase.getInstance().getReference("Restaurant")
                                                                 .child(UtilitiesX.UID).child("Orders").child(postUID);

                                                         db.addListenerForSingleValueEvent(new ValueEventListener() {
                                                             @Override
                                                             public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                 HashMap H= (HashMap) snapshot.getValue();
                                                                 OrderReceiver or=new OrderReceiver((HashMap) snapshot.getValue());
                                                                 String titleDis=or.titleDisplay();
                                                                 final String prevRider=or.getRiderUID();
                                                                 H.replace("RIDER",y);
                                                              if(grouppos==0)  {
                                                                  H.put("RIDERUID",riderUID);
                                                                  H.replace("STATUS","Received");
                                                              }
                                                              if(grouppos==1) {
                                                                  H.replace("RIDERUID",riderUID);
                                                                  DatabaseReference db3=FirebaseDatabase.getInstance().
                                                                          getReference("Restaurant")
                                                                          .child(UtilitiesX.UID).child("Rider").child(prevRider);

                                                                  db3.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                      @Override
                                                                      public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                          HashMap H2= (HashMap) snapshot.getValue();
                                                                          H2.replace("STATUS","unlocked");
                                                                          FirebaseDatabase.getInstance().getReference("Restaurant").child(UtilitiesX.UID)
                                                                                  .child("Rider").child(prevRider).setValue(H2);
                                                                      }
                                                                      @Override
                                                                      public void onCancelled(@NonNull DatabaseError error) {

                                                                      }
                                                                  });
                                                              }


                                                                 FirebaseDatabase.getInstance().getReference("Restaurant")
                                                                         .child(UtilitiesX.UID).child("Orders").child(postUID).setValue(H);

                                                                 if(grouppos==0) {
                                                                     Orders.L0.remove(titleDis);
                                                                     Orders.L1.add(titleDis);
                                                                     Orders.orderAdapter.notifyDataSetChanged();
                                                                     Toasty.info(CartConfirmOrder.this, "Received this Order", Toast.LENGTH_LONG, true).show();
                                                                 }
                                                                 if(grouppos==1){
                                                                     Toasty.info(CartConfirmOrder.this, "Rider Updated", Toast.LENGTH_LONG, true).show();
                                                                 }
                                                                 CartConfirmOrder.super.onBackPressed();
                                                             }

                                                             @Override
                                                             public void onCancelled(@NonNull DatabaseError error) {

                                                             }
                                                         });

                                                         break;
                                                     }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                })
                                .show();


                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });

        rejectb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(grouppos==0){

                      BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(CartConfirmOrder.this)
                             .setTitle("Delete?")
                             .setMessage("Are you sure want to delete this file?")
                             .setCancelable(true)
                             .setPositiveButton("Delete", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                                 @Override
                                 public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {


                                      DatabaseReference dref=FirebaseDatabase.getInstance().getReference("Restaurant")
                                              .child(UtilitiesX.UID).child("Orders").child(postUID);

                                      dref.addListenerForSingleValueEvent(new ValueEventListener() {
                                          @Override
                                          public void onDataChange(@NonNull DataSnapshot snapshot) {

                                              OrderReceiver or=new OrderReceiver((HashMap) snapshot.getValue());
                                              Orders.L0.remove(or.titleDisplay());
                                              Orders.orderAdapter.notifyDataSetChanged();
                                              Toasty.warning(CartConfirmOrder.this, "Deleted this Order", Toast.LENGTH_LONG, true).show();
                                              FirebaseDatabase.getInstance().getReference("Restaurant")
                                                      .child(UtilitiesX.UID).child("Orders").child(postUID).removeValue();
                                              CartConfirmOrder.super.onBackPressed();

                                          }
                                          @Override
                                          public void onCancelled(@NonNull DatabaseError error) {

                                          }
                                      });

                                      dialogInterface.dismiss();

                                 }
                             })
                             .setNegativeButton("Cancel", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {

                                 @Override
                                 public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                                     dialogInterface.dismiss();
                                 }
                             })
                             .build();
                     mBottomSheetDialog.show();
                     return;
                 }
                 else if(grouppos==1){

                     BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(CartConfirmOrder.this)
                             .setTitle("Delete?")
                             .setMessage("Are you sure want to delete this Order?")
                             .setCancelable(true)
                             .setPositiveButton("Delete", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                                 @Override
                                 public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                                     DatabaseReference db4=FirebaseDatabase.getInstance().getReference("Restaurant").child(UtilitiesX.UID).child("Orders").child(postUID);
                                     db4.addListenerForSingleValueEvent(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot snapshot) {

                                             OrderReceiver or=new OrderReceiver((HashMap) snapshot.getValue());

                                             final String rider=or.getRiderUID();
                                             String display=or.titleDisplay();
                                             Orders.L1.remove(display);
                                             Orders.L2.add(display);
                                             HashMap H2=or.serve;
                                             H2.replace("STATUS","Completed");
                                             FirebaseDatabase.getInstance().getReference("Restaurant").child(UtilitiesX.UID).child("Orders").child(postUID).setValue(H2);
                                             DatabaseReference db5=FirebaseDatabase.getInstance().getReference("Restaurant").child(UtilitiesX.UID).child("Rider").child(rider);

                                             db5.addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                     HashMap h= (HashMap) snapshot.getValue();
                                                     h.replace("STATUS","unlocked");
                                                     FirebaseDatabase.getInstance().getReference("Restaurant").child(UtilitiesX.UID)
                                                             .child("Rider").child(rider).setValue(h);

                                                     Orders.orderAdapter.notifyDataSetChanged();
                                                     CartConfirmOrder.super.onBackPressed();
                                                 }

                                                 @Override
                                                 public void onCancelled(@NonNull DatabaseError error) {

                                                 }
                                             });
                                         }

                                         @Override
                                         public void onCancelled(@NonNull DatabaseError error) {

                                         }
                                     });
                                     dialogInterface.dismiss();

                                 }
                             })
                             .setNegativeButton("Cancel", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {

                                 @Override
                                 public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                                     dialogInterface.dismiss();
                                 }
                             })
                             .build();
                     mBottomSheetDialog.show();

                     return;
                 }
                 else{
                      CartConfirmOrder.super.onBackPressed();
                 }

            }
        });
        readData(db, new OnGetDataListener() {

            @Override
            public void onSuccess(String reg) {

                InitRecycleView();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });



    }

    private void readRiderData(DatabaseReference child, final OnGetDataListener onGetDataListener) {

        onGetDataListener.onStart();

        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    HashMap M = new HashMap();
                    M = (HashMap) dataSnapshot.getValue();
                    if(!Objects.requireNonNull(M.get("STATUS")).toString().equals("unlocked")) continue;
                    listavailableRider.add(M.get("Rider").toString().concat(" : ").concat(M.get("Phone").toString()));
                }
             ///   System.out.println("LAAL5 "+ listavailableRider.toString());
                onGetDataListener.onSuccess(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void readHashMapData(DatabaseReference ref, final OnGetDataListener onGetDataListener) {

         onGetDataListener.onStart();

         ref.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {

                 cartConfirmOrderModelClass= new CartConfirmOrderModelClass((HashMap) snapshot.getValue());
                 onGetDataListener.onSuccess(null);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }

         });


    }

    private void InitRecycleView() {

        // recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        Adapter=new CartConfirmRecycleAdapter(allOrder);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(Adapter);

      ///  System.out.println( " LAAL3  "+totalpayable);
        ///totaltk.setText(String.valueOf(totalpayable));
    }

    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                allOrder = new ArrayList<>();

                for(DataSnapshot dataSnapshot :snapshot.getChildren() ){

                    CartModelClass cmc=new CartModelClass((HashMap) dataSnapshot.getValue());
                    allOrder.add(cmc);
                }
                listener.onSuccess(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

}
//{
//        //                        AlertDialog.Builder builder = new AlertDialog.Builder(CartConfirmOrder.this,R.style.MaterialAlertDialog_MaterialComponents_Title_Icon_CenterStacked);
////                        builder.setTitle("Pick a Rider");
////                        builder.setItems(colors, new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////
////                                System.out.println(" LAAL4 "+ colors[which]);
////                            }
////                        });
////                        builder.show();
//        }