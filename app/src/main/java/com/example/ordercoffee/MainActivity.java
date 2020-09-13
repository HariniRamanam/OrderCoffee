package com.example.ordercoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    TextView total_txt,total_num;
    View desc;
    ArrayList<Coffee> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding content (to ArrayList) which needs to be displayed in Recycler View
        /* Any number of rows can be added to Recycler View by the following steps:
        * Add image that needs to be displayed into drawable (\app\src\main\res\drawable) folder
        * Add data to "data" ArrayList by setting parameters to meaningful values, else app crashes */
        data.add(new Coffee(R.drawable.esp,"Espresso","Coffee Powder + Little Water","A shot of pure intense coffee flavour.",175));
        data.add(new Coffee(R.drawable.americano,"Americano","Coffee Powder + Water","Rich espresso with hot water",185));
        data.add(new Coffee(R.drawable.flatwhite,"Flat White","Espresso + Milk","Extremely steamed milk poured over shots of espresso",250));
        data.add(new Coffee(R.drawable.mach,"Caramel Macchiato","Espresso + Milk Foam","Rich espresso, steamed milk and sweet vanilla syrup topped with foam and caramel drizzle",250));
        data.add(new Coffee(R.drawable.latte,"Latte","Milk Foam (x ml) + \nEspresso (x ml) + \nMilk(2x ml)","Rich espresso, steamed milk and dollop of foam",200));
        data.add(new Coffee(R.drawable.cap,"Cappuccino","Milk Foam(x ml) + \nEspresso(x ml) + \nMilk(x ml) ","Rich espresso, steamed milk and deep layer of foam",200));
        data.add(new Coffee(R.drawable.mocha,"Mocha","Chocolate Syrup + Milk Foam + Milk + Espresso","Coffee with rich mocha sauce blended with milk and topped with whipped cream",270));

        RVAdapter rva = new RVAdapter(data);
        rv = findViewById(R.id.rview);
        //rv.setNestedScrollingEnabled(false);
        desc = findViewById(R.id.desc);

        //For animation of expanding and shrinking the description
        ((SimpleItemAnimator)rv.getItemAnimator()).setSupportsChangeAnimations(true);
        rv.setAdapter(rva);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        total_num = findViewById(R.id.tot_num);
        total_txt = findViewById(R.id.tot_txt);
    }

    public void bill(View view) {
        /*Pack your data into a bundle
        The objects in bundle must be serializable
        putSerializable(key,data) maps "data" to "key"
        This "key" is later used (in another activity here, Bill) to retrieve "data"
        */
        Bundle cdata_bundle = new Bundle();
        cdata_bundle.putSerializable("key_to_cdata",data);
        /*
        This bundle needs to be transferred to another activity
        Activity is created using Intent
        We use putExtras() to send any extra data to the created Intent
        The extra data here is the bundle
         */
        Intent bill_intent = new Intent(this,Bill.class);
        bill_intent.putExtras(cdata_bundle);
        startActivity(bill_intent);
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolder> {
        ArrayList<Coffee> cdata;
        RVAdapter(ArrayList<Coffee> cdata) {
            this.cdata = cdata;
        }

        @NonNull
        @Override
        public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //Initializes your ViewHolder class
            //Create a LayoutInflater object to inflate your XML file
            //created for each row of RecyclerView
            /*LayoutInflater li = LayoutInflater.from(MainActivity.this);
            View v = li.inflate(R.layout.one_coffee,parent,false);
            return new RVViewHolder(v);*/
            View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.one_coffee,parent,false);
            return new RVViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
            Coffee c = cdata.get(position);
            boolean exp;
            int n = c.getNo_coffee();
            //Toggling expansion
            exp = c.isExpanded();
            if(!exp)
            {
                holder.coffee.setTextSize(23);
                holder.desc.setVisibility(View.GONE);
            }
            else
            {
                holder.coffee.setTextSize(20);
                holder.desc.setVisibility(View.VISIBLE);
            }
            //For disabling and enabling "-" button
            if(n > 0 && n <= 99) {
                holder.dec.setEnabled(true);
            }
            else {
                holder.dec.setEnabled(false);
            }

            holder.iv.setImageResource(c.getImgId());
            holder.coffee.setText(c.getName());
            holder.sub1.setText(c.getSubtext1());
            holder.sub2.setText(c.getSubtext2());
            holder.price.setText("₹" + c.getPriceof());
            holder.num.setText("" + c.getNo_coffee());
        }

        @Override
        public int getItemCount() {
            return cdata.size();
        }

        class RVViewHolder extends RecyclerView.ViewHolder {

            ImageView iv;
            TextView coffee,sub1,sub2,price,num;
            Button dec,inc;
            View desc,listen;
            RVViewHolder(@NonNull View itemView) {
                super(itemView);

                iv = itemView.findViewById(R.id.img);
                coffee = itemView.findViewById(R.id.cname);
                sub1 = itemView.findViewById(R.id.desc1);
                sub2 = itemView.findViewById(R.id.desc2);
                price = itemView.findViewById(R.id.price);
                dec = itemView.findViewById(R.id.decrement);
                inc = itemView.findViewById(R.id.increment);
                num = itemView.findViewById(R.id.nc);
                desc = itemView.findViewById(R.id.desc);
                listen = itemView.findViewById(R.id.listenr);

            //Creating listeners for all click events
                listen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean exp = cdata.get(getAdapterPosition()).isExpanded();
                        cdata.get(getAdapterPosition()).setExpansion(!exp);
                        notifyItemChanged(getAdapterPosition());
                    }
                });

                inc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Coffee c = cdata.get(getAdapterPosition());
                        int n = c.getNo_coffee();
                        if(n >= 0 && n < 99) {
                            if(n == 0)
                                dec.setEnabled(true);
                            n++;
                            c.setNo_coffee(n);
                            num.setText(String.valueOf(n));

                            Coffee.total_no_coffee++;
                            if(Coffee.total_no_coffee == 1)
                            {
                                View u = findViewById(R.id.up);
                                View d = findViewById(R.id.down);
                                LinearLayout.LayoutParams up = new LinearLayout.LayoutParams(u.getLayoutParams().width, u.getLayoutParams().height,9.0f);
                                u.setLayoutParams(up);
                                LinearLayout.LayoutParams down = new LinearLayout.LayoutParams(d.getLayoutParams().width, d.getLayoutParams().height,1.0f);
                                d.setLayoutParams(down);
                                total_txt.setText("1 cup of coffee");
                            }
                            else
                                total_txt.setText(Coffee.total_no_coffee + " cups of coffee");

                            Coffee.total_cost += c.getPriceof();
                            total_num.setText("Total: ₹" + Coffee.total_cost);
                        }
                        else
                            Toast.makeText(MainActivity.this,"Still not asleep? Try another type of coffee. This one's out of limit.",Toast.LENGTH_SHORT).show();
                    }
                });

                dec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Coffee c = cdata.get(getAdapterPosition());
                        Coffee.total_no_coffee--;
                        if(Coffee.total_no_coffee == 0)
                        {
                            View u = findViewById(R.id.up);
                            View d = findViewById(R.id.down);
                            LinearLayout.LayoutParams upp = new LinearLayout.LayoutParams(u.getLayoutParams().width, u.getLayoutParams().height,10.0f);
                            u.setLayoutParams(upp);
                            LinearLayout.LayoutParams downn = new LinearLayout.LayoutParams(d.getLayoutParams().width, d.getLayoutParams().height,0.0f);
                            d.setLayoutParams(downn);
                        }
                        else if(Coffee.total_no_coffee == 1)
                            total_txt.setText("1 cup of coffee");
                        else
                            total_txt.setText(Coffee.total_no_coffee + " cups of coffee");

                        Coffee.total_cost -= c.getPriceof();
                        total_num.setText("Total: ₹" + Coffee.total_cost);

                        int n = c.getNo_coffee();
                        n--;
                        if(n == 0 || n < 0) {
                            dec.setEnabled(false);
                            c.setNo_coffee(n);
                            num.setText(String.valueOf(n));
                        }
                        else {
                            num.setText(String.valueOf(n));
                            c.setNo_coffee(n);
                        }
                    }
                });
            }
        }
    }
}