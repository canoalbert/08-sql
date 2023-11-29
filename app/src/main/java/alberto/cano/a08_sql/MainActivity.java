package alberto.cano.a08_sql;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import alberto.cano.a08_sql.adapters.OrderAdapter;
import alberto.cano.a08_sql.databinding.ActivityMainBinding;
import alberto.cano.a08_sql.models.Order;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Order> listOrder;


    private OrderAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listOrder = new ArrayList<>();

        adapter = new OrderAdapter(MainActivity.this, R.layout.order_view_holder, listOrder);

        binding.contentMain.container.setAdapter(adapter);

        binding.contentMain.container.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrder().show();
            }
        });
    }

    private AlertDialog createOrder(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("CREATE ORDER");
        builder.setCancelable(false);

        View orderView = LayoutInflater.from(this).inflate(R.layout.order_view_model, null);
        EditText txtMenu = orderView.findViewById(R.id.txtMenuOrderViewModel);
        CheckBox chLarge = orderView.findViewById(R.id.chLargeOrderViewModel);
        EditText txtQuantity = orderView.findViewById(R.id.txtQuantityOrderViewModel);
        EditText txtPrice = orderView.findViewById(R.id.txtPriceOrderViewModel);

        builder.setView(orderView);

        builder.setNegativeButton("CANCEL", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!txtMenu.getText().toString().isEmpty() && !txtQuantity.getText().toString().isEmpty() && !txtPrice.getText().toString().isEmpty()){
                    Order order = new Order(
                            txtMenu.getText().toString(),
                            chLarge.isChecked(),
                            Integer.parseInt(txtQuantity.getText().toString()),
                            Float.parseFloat(txtPrice.getText().toString())
                    );
                    listOrder.add(order);
                    adapter.notifyItemInserted(listOrder.size()-1);
                }else {
                    Toast.makeText(MainActivity.this, "MISSING DATA", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return  builder.create();
    }


}