package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtDisplay;

    private double accumulator = 0.0;
    private String pendingOperator = null; // "+", "−", "×", "÷"
    private boolean userIsTypingNumber = false;
    private boolean lastInputWasEquals = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDisplay = findViewById(R.id.txtDisplay);

        // Pririsu visus buttons
        int[] ids = new int[]{
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnDot, R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv,
                R.id.btnEquals, R.id.btnClear, R.id.btnBackspace, R.id.btnSign, R.id.btnSqrt
        };
        for (int id : ids) {
            View v = findViewById(id);
            if (v != null) v.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (!(v instanceof Button)) return;
        Button b = (Button) v;
        String label = b.getText().toString();
        int id = v.getId();

        if (id == R.id.btn0 || id == R.id.btn1 || id == R.id.btn2 || id == R.id.btn3 || id == R.id.btn4
                || id == R.id.btn5 || id == R.id.btn6 || id == R.id.btn7 || id == R.id.btn8 || id == R.id.btn9) {
            appendDigit(label);

        } else if (id == R.id.btnDot) {
            appendDot();

            // Operatoriai
        } else if (id == R.id.btnAdd || id == R.id.btnSub || id == R.id.btnMul || id == R.id.btnDiv) {
            applyOperator(label);

        } else if (id == R.id.btnEquals) {
            computeResult();

            // Papildomos funkcijos
        } else if (id == R.id.btnClear) {
            clearAll();

        } else if (id == R.id.btnBackspace) {
            backspace();

        } else if (id == R.id.btnSign) {
            toggleSign();

        } else if (id == R.id.btnSqrt) {
            sqrtCurrent();
        }
    }

    // Ivedimas

    private void appendDigit(String d) {
        if (lastInputWasEquals) {
            setDisplayText("0");
            lastInputWasEquals = false;
        }

        String current = getDisplayText();
        if (!userIsTypingNumber || current.equals(getString(R.string.zero)) || current.equals("Error")) {
            setDisplayText(d);
        } else {
            setDisplayText(current + d);
        }
        userIsTypingNumber = true;
    }

    private void appendDot() {
        if (lastInputWasEquals) {
            setDisplayText("0");
            lastInputWasEquals = false;
        }

        String current = getDisplayText();
        if (!current.contains(".")) {
            if (!userIsTypingNumber) {
                setDisplayText("0.");
            } else {
                setDisplayText(current + ".");
            }
            userIsTypingNumber = true;
        }
    }

    // Operacijos

    private void applyOperator(String op) {
        try {
            double currentValue = Double.parseDouble(getDisplayText());

            if (pendingOperator == null) {
                accumulator = currentValue;
            } else {
                accumulator = perform(accumulator, currentValue, pendingOperator);
                setDisplayText(format(accumulator));
            }

            pendingOperator = op;
            userIsTypingNumber = false;
            lastInputWasEquals = false;

        } catch (NumberFormatException | ArithmeticException ex) {
            showError();
        }
    }

    private void computeResult() {
        if (pendingOperator == null) {
            lastInputWasEquals = true;
            return;
        }
        try {
            double currentValue = Double.parseDouble(getDisplayText());
            accumulator = perform(accumulator, currentValue, pendingOperator);
            setDisplayText(format(accumulator));

            pendingOperator = null;
            userIsTypingNumber = false;
            lastInputWasEquals = true;
        } catch (NumberFormatException | ArithmeticException ex) {
            showError();
        }
    }

    private double perform(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "−": return a - b; // U+2212
            case "×": return a * b; // U+00D7
            case "÷":
                if (b == 0.0) throw new ArithmeticException("Div by zero");
                return a / b; // U+00F7
        }
        return b;
    }

    private void clearAll() {
        accumulator = 0.0;
        pendingOperator = null;
        userIsTypingNumber = false;
        lastInputWasEquals = false;
        setDisplayText(getString(R.string.zero));
    }

    private void backspace() {
        if (!userIsTypingNumber) return;
        String cur = getDisplayText();
        if (cur.length() > 1) {
            cur = cur.substring(0, cur.length() - 1);
            if (cur.equals("-")) cur = "0";
        } else {
            cur = "0";
        }
        setDisplayText(cur);
    }

    private void toggleSign() {
        String cur = getDisplayText();
        if (cur.equals("0") || cur.equals(getString(R.string.zero)) || cur.equals("Error")) return;
        if (cur.startsWith("-")) {
            setDisplayText(cur.substring(1));
        } else {
            setDisplayText("-" + cur);
        }
    }

    private void sqrtCurrent() {
        try {
            double x = Double.parseDouble(getDisplayText());
            if (x < 0) {
                showError();
                return;
            }
            double r = Math.sqrt(x);
            setDisplayText(format(r));
            userIsTypingNumber = false;
            lastInputWasEquals = true;
        } catch (NumberFormatException ex) {
            showError();
        }
    }


    private String getDisplayText() {
        return txtDisplay.getText().toString();
    }

    private void setDisplayText(String s) {
        txtDisplay.setText(s);
    }

    private void showError() {
        setDisplayText(getString(R.string.msg_invalid_input));
        accumulator = 0.0;
        pendingOperator = null;
        userIsTypingNumber = false;
        lastInputWasEquals = false;
    }

    private String format(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            return String.valueOf(value);
        }
    }
}
