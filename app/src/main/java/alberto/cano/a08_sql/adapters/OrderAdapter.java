package alberto.cano.a08_sql.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import alberto.cano.a08_sql.R;
import alberto.cano.a08_sql.models.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OderVH> {
    private Context context;
    private int resource;
    private List<Order> objects;

    public OrderAdapter(Context context, int resource, List<Order> objects) {
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public OrderAdapter.OderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orderView = LayoutInflater.from(context).inflate(resource, null);
        orderView.setLayoutParams(new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        return new OderVH(orderView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OderVH holder, int position) {
        Order order = objects.get(position);

        holder.lbMenu.setText(order.getMenu());
        holder.lbTotal.setText(String.valueOf(order.getTotal()));

        holder.imEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmEdit(holder.getAdapterPosition()).show();
            }
        });

        holder.imDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete(holder.getAdapterPosition()).show();
            }
        });
    }

    private AlertDialog confirmDelete(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("SURE DELETE??");
        builder.setCancelable(false);

        builder.setNegativeButton("CANCEL", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                objects.remove(position);
                notifyItemRemoved(position);
            }
        });
        return builder.create();
    }

    private AlertDialog confirmEdit(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("EDIT ORDER!!!!");
        builder.setCancelable(false);

        View orderView = LayoutInflater.from(context).inflate(R.layout.order_view_model, null);
        EditText txtMenu = orderView.findViewById(R.id.txtMenuOrderViewModel);
        EditText txtQuantity = orderView.findViewById(R.id.txtQuantityOrderViewModel);
        EditText txtPrice = orderView.findViewById(R.id.txtPriceOrderViewModel);
        CheckBox chLarge = orderView.findViewById(R.id.chLargeOrderViewModel);

        Order order = objects.get(position);
        txtMenu.setText(order.getMenu());
        txtQuantity.setText(String.valueOf(order.getQuantity()));
        txtPrice.setText(String.valueOf(order.getPrice()));
        chLarge.setChecked(order.isLarge());

        builder.setNegativeButton("CANCEL", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (txtMenu.getText().toString().isEmpty() || txtQuantity.getText().toString().isEmpty() || txtPrice.getText().toString().isEmpty()){
                    Toast.makeText(context, "MISSING DATA", Toast.LENGTH_SHORT).show();
                } else {
                    objects.set(position, new Order(
                            txtMenu.getText().toString(),
                            chLarge.isChecked(),
                            Integer.parseInt(txtQuantity.getText().toString()),
                            Float.parseFloat(txtPrice.getText().toString())
                    ));
                    notifyItemChanged(position);
                }
            }

        });
        return builder.create();
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class OderVH extends RecyclerView.ViewHolder{
        TextView lbMenu, lbTotal;
        ImageButton imEdit, imDelete;

        public OderVH(@NonNull View itemView) {
            super(itemView);
            lbMenu = itemView.findViewById(R.id.lbMenuOrderViewHolder);
            lbTotal = itemView.findViewById(R.id.lbTotalOrderViewHolder);
            imEdit = itemView.findViewById(R.id.imEditOrderViewHolder);
            imDelete = itemView.findViewById(R.id.imDeleteOrderViewHolder);
        }
    }
}
