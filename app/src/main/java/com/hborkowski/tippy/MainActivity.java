package com.hborkowski.tippy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private Integer initialTip = 15;
    private EditText etBaseAmount;
    private SeekBar seekBarTip;
    private TextView tvTipPercentLabel;
    private TextView tvTipAmount;
    private TextView tvTotalAmount;
    private TextView tvTipDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBaseAmount = findViewById(R.id.etBaseAmount);
        seekBarTip = findViewById(R.id.seekBarTip);
        tvTipPercentLabel = findViewById(R.id.tvTipPercentLabel);
        tvTipAmount = findViewById(R.id.tvTipAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvTipDescription = findViewById(R.id.tvTipDescription);


        seekBarTip.setProgress(initialTip);
        tvTipPercentLabel.setText(Integer.toString(initialTip) + "%");
        updateTipDescription(initialTip);
        seekBarTip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i(TAG, "onProgress Changed " + i);
                tvTipPercentLabel.setText(Integer.toString(i) + "%");
                computeTipAndTotal();
                updateTipDescription(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        etBaseAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                computeTipAndTotal();
            }
        });
    }

    private void updateTipDescription(int tipPercent) {

        switch(tipPercent) {
            case 0: case 1: case 2: case 3:
            case 4: case 5: case 6:
            case 7: case 8: case 9:
                tvTipDescription.setText("Damn, they suck!");
                break;

            case 10: case 11: case 12:
            case 13: case 14:
                tvTipDescription.setText("They were alright");
                break;

            case 15: case 16: case 17:
            case 18: case 19:
                tvTipDescription.setText("That was good service");
                break;

            case 20: case 21: case 22:
            case 23: case 24:
                tvTipDescription.setText("Damn, I hope I get them again next time!");
                break;

            case 25: case 26: case 27:
            case 28: case 29: case 30:
                tvTipDescription.setText("If I wasn't married, I'd have their child");
                break;
        }

        //Update the color based on the tip percentage
        int color;
        float tip = tipPercent;
        ArgbEvaluator evaluator = new ArgbEvaluator();
        color =  (Integer) evaluator.evaluate(
                tip / seekBarTip.getMax(),
                ContextCompat.getColor(this, R.color.color_worst_tip),
                ContextCompat.getColor(this, R.color.color_best_tip));

        tvTipDescription.setTextColor(color);

    }

    private void computeTipAndTotal() {
        double baseValue;
        int tipPercent;
        double tipAmount;
        double total;

        if(etBaseAmount.getText().toString().isEmpty()) {
            tvTipAmount.setText("");
            tvTotalAmount.setText("");
            return;
        }
        // 1. Get Value of the base and tip percent
        baseValue = Double.parseDouble(etBaseAmount.getText().toString());
        tipPercent = seekBarTip.getProgress();
        // 2. Compute the tip and the total126.26
        tipAmount = baseValue * tipPercent / 100;
        total = baseValue + tipAmount;
        // 3. Update the UI
        tvTotalAmount.setText(String.format("%.2f", total));
        tvTipAmount.setText(String.format("%.2f", tipAmount));
    }
}