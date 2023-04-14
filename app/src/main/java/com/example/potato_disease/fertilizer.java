package com.example.potato_disease;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.net.MailTo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class fertilizer extends AppCompatActivity {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    EditText plot_size,N,P,K;
    Button minus_button;
    Button plus_btn;
    Button cal_btn;
    TextView dap1,mop1,urea12,mop2,ssp1,urea22;
    ScrollView scrollview;
    TextView textView22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer);

        plot_size=findViewById(R.id.plot_size);
        minus_button=findViewById(R.id.minus_button);
        plus_btn=findViewById(R.id.plus_btn);
        cal_btn=findViewById(R.id.cal_btn);

        mop2=findViewById(R.id.textView81);
        ssp1=findViewById(R.id.ssp1);
        urea22=findViewById(R.id.textView41);


        scrollview=findViewById(R.id.scrollview);
        textView22=findViewById(R.id.textView22);


        EditText edittexnumber1 = findViewById(R.id.editTextNumber);
        EditText edittexnumber2 = findViewById(R.id.editTextNumber2);
        EditText edittexnumber3 = findViewById(R.id.editTextNumber3);


        RadioButton radioButton1 = findViewById(R.id.radio_button_1);
        RadioButton radioButton2 = findViewById(R.id.radio_button_2);

        minus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    try {
                        String size = plot_size.getText().toString();

                        Double area = Double.valueOf(Float.parseFloat(size));

                        if (area >= 0.5) {
                            area -= 0.5;
                            plot_size.setText(String.valueOf(area));
                        }
                    }
                    catch (Exception e)
                    {

                    }


            }
        });
        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String size = plot_size.getText().toString();
                    Double area = Double.valueOf(Float.parseFloat(size));

                    if (area >= 0) {
                        area += 0.5;
                        plot_size.setText(String.valueOf(area));
                    }
                }
                catch (Exception e)
                {

                }
            }
        });

        cal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    String size=plot_size.getText().toString();

                    Double area= Double.valueOf(Float.parseFloat(size));
                    String numberString1 = edittexnumber1.getText().toString(); // Retrieve the text value of the EditText view as a String
                    int N = Integer.parseInt(numberString1);
                    String numberString2 = edittexnumber2.getText().toString(); // Retrieve the text value of the EditText view as a String
                    int P = Integer.parseInt(numberString2);
                    String numberString3 = edittexnumber3.getText().toString(); // Retrieve the text value of the EditText view as a String
                    int K = Integer.parseInt(numberString3);

                    if(area>0 && area<1000)
                    {

                        if (!radioButton2.isChecked()){
                            area = area*0.404;
                        }
                        if (N == 0 && P == 0 && K == 0) {
                         Toast.makeText(fertilizer.this, "Invalid Nutrient quantities", Toast.LENGTH_SHORT).show();
                        }
                        else if (Math.abs(N - P) > 200 ||Math.abs(N - K) > 200 || Math.abs(P - K) > 200){
                            Toast.makeText(fertilizer.this, "Invalid Nutrient quantities", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            calculateFertilizer(area, N, P, K);
                            scrollview.setVisibility(view.VISIBLE);
                            textView22.setVisibility(view.VISIBLE);
                        }
                    }
                    else
                        Toast.makeText(fertilizer.this, "Invalid data", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(fertilizer.this, "Invalid data", Toast.LENGTH_SHORT).show();
                }



            }
        });


    }
    private void calculateFertilizer(double area,int N,int P,int K) {

        double urea=N*2.17*area;
        double ssp=P*6.25*area;
        double mop=K*1.67*area;
        Log.d("N",String.valueOf(N));
        Log.d("P",String.valueOf(P));
        Log.d("K",String.valueOf(K));

        mop2.setText(String.valueOf(  df.format(mop))+"KG");
        ssp1.setText(String.valueOf( df.format(ssp))+"KG");
        urea22.setText(String.valueOf(df.format(urea))+"KG");


    }

}