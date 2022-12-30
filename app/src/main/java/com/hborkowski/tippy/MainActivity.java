package com.hborkowski.tippy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private Integer initial_tip = 15;
    private EditText etBaseAmount;
    private SeekBar seekBarTip;
    private TextView tvTipPercentLabel;
    private TextView tvTipAmount;
    private TextView tvTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBaseAmount = findViewById(R.id.etBaseAmount);
        seekBarTip = findViewById(R.id.seekBarTip);
        tvTipPercentLabel = findViewById(R.id.tvTipPercentLabel);
        tvTipAmount = findViewById(R.id.tvTipAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);


        seekBarTip.setProgress(initial_tip);
        tvTipPercentLabel.setText(Integer.toString(initial_tip) + "%");
        seekBarTip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i(TAG, "onProgress Changed " + i);
                tvTipPercentLabel.setText(Integer.toString(i) + "%");
                computeTipAndTotal();

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