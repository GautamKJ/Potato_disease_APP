package com.example.potato_disease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class fertilizer extends AppCompatActivity {
EditText plot_size;
Button minus_button;
Button plus_btn;
Button cal_btn;
TextView dap1,mop1,urea12,mop2,ssp1,urea22;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer);

        plot_size=findViewById(R.id.plot_size);
        minus_button=findViewById(R.id.minus_button);
        plus_btn=findViewById(R.id.plus_btn);
        cal_btn=findViewById(R.id.cal_btn);
        dap1=findViewById(R.id.dap1);
        mop1=findViewById(R.id.mop1);
        urea12=findViewById(R.id.urea12);
        mop2=findViewById(R.id.mop2);
        ssp1=findViewById(R.id.ssp1);
        urea22=findViewById(R.id.urea22);


        minus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double size=plot_size.getTextSize();
                if(size>0) {
                    size -= 0.5;
                    plot_size.setText(String.valueOf(size));
                }

            }
        });

        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double size=plot_size.getTextSize();
                if(size>0 && size<1000) {
                    size += 0.5;
                    if(size<1000)
                    plot_size.setText(String.valueOf(size));
                }
            }
        });

        cal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double size=plot_size.getTextSize();
                if(size>0 && size<1000)
                {
                        calculateFertilizer(size,100,50,50);
                }

            }
        });


    }
    private void calculateFertilizer(double area,int N,int P,int K) {

        double urea=(N/46)*100*area;
        double ssp=(P/16)*100*area;
        double mop=(K/60)*100*area;

        mop2.setText(String.valueOf(mop)+"KG");
        ssp1.setText(String.valueOf(ssp)+"KG");
        urea22.setText(String.valueOf(urea)+"");

    }

}