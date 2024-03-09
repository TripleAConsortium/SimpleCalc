package com.example.simplecalc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.displayEditText);
        display.setShowSoftInputOnFocus(false);

        Button button0 = findViewById(R.id.button0);
        button0.setOnClickListener(v -> updateDisplay("0"));
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(v -> updateDisplay("1"));
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> updateDisplay("2"));
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(v -> updateDisplay("3"));
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(v -> updateDisplay("4"));
        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(v -> updateDisplay("5"));
        Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(v -> updateDisplay("6"));
        Button button7 = findViewById(R.id.button7);
        button7.setOnClickListener(v -> updateDisplay("7"));
        Button button8 = findViewById(R.id.button8);
        button8.setOnClickListener(v -> updateDisplay("8"));
        Button button9 = findViewById(R.id.button9);
        button9.setOnClickListener(v -> updateDisplay("9"));

        Button buttonPoint = findViewById(R.id.buttonPoint);
        buttonPoint.setOnClickListener(v -> updateDisplay("."));

        Button buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(v -> display.setText(""));

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(v -> updateDisplay("+"));

        Button buttonSubtract = findViewById(R.id.buttonSubtract);
        buttonSubtract.setOnClickListener(v -> updateDisplay("-"));

        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonMultiply.setOnClickListener(v -> updateDisplay("*"));

        Button buttonDivide = findViewById(R.id.buttonDivide);
        buttonDivide.setOnClickListener(v -> updateDisplay("/"));

        Button buttonExponent = findViewById(R.id.buttonExponent);
        buttonExponent.setOnClickListener(v -> updateDisplay("^"));

        Button buttonParentheses = findViewById(R.id.buttonParentheses);
        buttonParentheses.setOnClickListener(v -> setParenthesis());

        Button buttonPlusMinus = findViewById(R.id.buttonPlusMinus);
        buttonPlusMinus.setOnClickListener(v -> updateDisplay("+/-"));

        Button buttonEquals = findViewById(R.id.buttonEquals);
        buttonEquals.setOnClickListener(v -> evalExpression());

        ImageButton backspace = findViewById(R.id.buttonBackspace);
        backspace.setOnClickListener(v -> {
            int position = display.getSelectionStart();
            if (position > 0) {
                StringBuilder sb = new StringBuilder(display.getText().toString());
                sb.deleteCharAt(position - 1);
                display.setText(sb.toString());
                display.setSelection(position - 1);
            }
        });
    }

    private void evalExpression() {
        String expression = display.getText().toString().trim();
        if (expression.isEmpty()) {
            Toast.makeText(this, "Please enter an expression", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidExpression(expression)) {
            Toast.makeText(this, "Invalid expression", Toast.LENGTH_SHORT).show();
            return;
        }
        if (expression.contains("/") && expression.endsWith("/0")) {
            Toast.makeText(this, "Division by zero is not allowed", Toast.LENGTH_SHORT).show();
            return;
        }
        Expression e = new Expression(expression);
        String result = String.valueOf(e.calculate());
        display.setText(result);
    }

    private boolean isValidExpression(String expression) {
        String validCharsRegex = "[0-9+\\-*/^(). ]*";
        return expression.matches(validCharsRegex);
    }

    private void updateDisplay(String text) {
        int position = display.getSelectionStart();
        StringBuilder sb = new StringBuilder(display.getText().toString());
        sb.insert(position, text);
        display.setText(sb.toString());
        display.setSelection(position + text.length());
    }

    private void setParenthesis() {
        int currentPosition = display.getSelectionStart();
        int endSelection = display.getSelectionEnd();
        if (currentPosition == endSelection) {
            updateDisplay("()");
        } else {
            StringBuilder sb = new StringBuilder(display.getText().toString());
            sb.insert(currentPosition, "(");
            sb.insert(endSelection, ")");
            display.setText(sb.toString());
            display.setSelection(currentPosition + 2);
        }

    }
}
