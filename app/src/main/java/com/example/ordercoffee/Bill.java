package com.example.ordercoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Bill extends AppCompatActivity {
    TextView t1,t2,t3,t,tt,gt;
    Button n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t = findViewById(R.id.total1);
        tt = findViewById(R.id.taxtotal);
        gt = findViewById(R.id.total2);
        n = findViewById(R.id.neww);

        //getIntent() returns the intent that started this activity
        Intent main_intent = getIntent();
        Bundle b = main_intent.getExtras();
        ArrayList<Coffee> bill_data = null;
        if (b != null) {
            bill_data = (ArrayList<Coffee>) b.getSerializable("key_to_cdata");
        }
        generate_bill(bill_data);
    }

    private void generate_bill(ArrayList<Coffee> bill_data) {
        Coffee temp;
        String set_t;
        for(int i=0;i<bill_data.size();i++) {
            temp = bill_data.get(i);
            if(temp.getNo_coffee()>0) {
                t1.append(temp.getName()+"\n");
                t2.append(temp.getNo_coffee()+"\n");
                t3.append("₹"+(temp.getPriceof()*temp.getNo_coffee())+"\n");
            }
        }
        float tax = (float) (Coffee.total_cost*0.1);
        float grand = Coffee.total_cost + tax;
        set_t = "₹" + Coffee.total_cost;
        t.setText(set_t);
        set_t = "₹" + tax;
        tt.setText(set_t);
        set_t = "₹" + grand;
        gt.setText(set_t);
    }

    public void new_order(View view) {
        System.exit(0);
    }
    /*public void exit_app(View view) {

    }*/
}
