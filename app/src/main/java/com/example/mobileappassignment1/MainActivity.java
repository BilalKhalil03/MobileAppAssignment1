package com.example.mobileappassignment1;

import android.os.Bundle;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText mortgageAmount, interestRate, amortizationPeriod;
    Spinner paymentFrequency;
    TextView resultText;
    Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //UI stuff for mortgage calculator
        mortgageAmount = findViewById(R.id.mortgageAmount);
        interestRate = findViewById(R.id.interestRate);
        amortizationPeriod = findViewById(R.id.amortizationPeriod);
        paymentFrequency = findViewById(R.id.paymentFrequency);
        resultText = findViewById(R.id.resultText);
        calculateButton = findViewById(R.id.calculateButton);

        // Onclicklistener for the calculate button
        calculateButton.setOnClickListener(v -> calculateMortgage());
    }
        //Calculation stuff
    private void calculateMortgage() {
        double principal = Double.parseDouble(mortgageAmount.getText().toString());
        double annualInterestRate = Double.parseDouble(interestRate.getText().toString()) / 100;
        int years = Integer.parseInt(amortizationPeriod.getText().toString());

        int frequency;
        switch (paymentFrequency.getSelectedItemPosition()) {
            case 0: frequency = 52; break;  // Weekly
            case 1: frequency = 26; break;  // Bi-Weekly
            default: frequency = 12; break; // Monthly
        }

        double monthlyInterestRate = annualInterestRate / frequency;
        int totalPayments = years * frequency;

        double payment = (monthlyInterestRate * principal) /
                (1 - Math.pow(1 + monthlyInterestRate, -totalPayments));

        resultText.setText(String.format("Your payment is: $%.2f", payment));
    }
}
