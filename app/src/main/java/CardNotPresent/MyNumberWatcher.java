package CardNotPresent;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;

public class MyNumberWatcher implements TextWatcher {
    private EditText editText;
    private int digit;

    //editText.addTextChangedListener(new MyNumberWatcher(editText));
    //String text = editText.getText().toString();
    //text = text.replaceAll("[,]","");

    public MyNumberWatcher(EditText editText) {
        this.editText = editText;

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        editText.removeTextChangedListener(this);

        String string = editText.getText().toString();

        String temp = DC.convertFNumToENum(string);
        String numberDate = temp.replaceAll("[^0-9]", "");

        if (numberDate.length() > 0) {
            DecimalFormat sdd = new DecimalFormat("#,###");
            Double doubleNumber = Double.parseDouble(numberDate);


            String format = sdd.format(doubleNumber);
            editText.setText(format);
            editText.setSelection(format.length());

        }
        editText.addTextChangedListener(this);
    }
}