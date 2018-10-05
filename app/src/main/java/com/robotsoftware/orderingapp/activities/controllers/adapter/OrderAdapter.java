package com.robotsoftware.orderingapp.activities.controllers.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.robotsoftware.orderingapp.R;
import com.robotsoftware.orderingapp.activities.models.UserOrder;
import com.robotsoftware.orderingapp.database.DatabaseSQLite;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.HolderView> {

    private ArrayList<UserOrder> userOrder = new ArrayList<>();
    private DatabaseSQLite dbSQL;
    private UserOrder order;
    private Context mContext;

    /**
     * declaration constructor
     *
     * @param userOrder
     */
    public OrderAdapter(ArrayList<UserOrder> userOrder, Context context) {
        this.userOrder = userOrder;
        this.mContext = context;
    }

    /**
     * this for create view adapter and inflate to main
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public OrderAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate layout adapter
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_card_view, parent, false);

        return new OrderAdapter.HolderView(view);
    }

    /**
     * this for, do action resources from adapter
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final OrderAdapter.HolderView holder, int position) {

        //initiate
        order = userOrder.get(position);

        holder.mName.setText(order.getName());
        holder.mOrder.setText(order.getChecked());
        holder.mQuantity.setText(order.getQuantity());
        holder.mPrice.setText(order.getPrice());
    }

    /**
     * get result from count array
     *
     * @return
     */
    @Override
    public int getItemCount() {

        return (userOrder != null) ? userOrder.size() : 0;
    }

    /**
     * this inner class
     */
    public class HolderView extends RecyclerView.ViewHolder {

        //declaration fields attribute
        private TextView mName, mOrder, mQuantity, mPrice;
        private ImageButton mImgDelete, mImgSend;

        /**
         * declare constructor
         *
         * @param itemView
         */
        public HolderView(View itemView) {
            super(itemView);

            //Text View
            mName = itemView.findViewById(R.id.displayName);
            mOrder = itemView.findViewById(R.id.displayOrder);
            mQuantity = itemView.findViewById(R.id.displayQuantity);
            mPrice = itemView.findViewById(R.id.displayPrice);

            //Button
            mImgDelete = itemView.findViewById(R.id.imageBtnDelete);
            mImgSend = itemView.findViewById(R.id.imageBtnSend);

            //action for btn delete
            mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbSQL = new DatabaseSQLite(mContext);
                    String name = userOrder.get(getAdapterPosition()).getName();
                    dbSQL.deleteOrder(name);

                    userOrder.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    notifyItemRemoved(getAdapterPosition());

                    Log.d("OrderAdapter", "Deleting ..." + name);
                }
            });

            //action for btn send go to email app
            mImgSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //getting the value
                    String name = userOrder.get(getAdapterPosition()).getName();
                    String order = userOrder.get(getAdapterPosition()).getChecked();
                    String quantity = userOrder.get(getAdapterPosition()).getQuantity();
                    String price = userOrder.get(getAdapterPosition()).getPrice();

                    //setup message
                    String emailMessage = "[ORDER]\n" +
                            "Name" + name + "\n" +
                            "Order" + order + "\n" +
                            "Quantity" + quantity + "\n" +
                            "Price" + price;

                    //Intent to gmail for send email
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "fajaragungpramana12@gmail.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MyOrder");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, emailMessage);
                    emailIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(Intent.createChooser(emailIntent, "Select Application"));

                    Log.d("OrderAdapter", "Send order via email:Success");
                }
            });
        }
    }
}
