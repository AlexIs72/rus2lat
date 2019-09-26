package com.alexis.rus2lat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity /*implements View.OnFocusChangeListener*/ {

    private EditText mInputText;
    private EditText mTranslatedText;
    private Button mCopyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCopyButton = findViewById(R.id.copyBtn);
        mCopyButton.requestFocus();

        mInputText = findViewById(R.id.inputText);
        mInputText.requestFocus();

        mTranslatedText = findViewById(R.id.translatedText);

        mInputText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(v.hasFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        mInputText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here

                // yourEditText...
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = mInputText.getText().toString();
                mTranslatedText.setText(translateString(text));
            }
        });


    }

    public void onCopyBtn(View v)
    {
        copyDone();
    }

    private String translateString(String srcText)
    {
        String rusText = "йцукенгшщзхъфывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ";
        String latText = "qwertyuiop[]asdfghjkl;'zxcvbnm,.QWERTYUIOP{}ASDFGHJKL:\"ZXCVBNM<>";
        String dstText = "";
        char[] srcBytes = srcText.toCharArray();

        for(int i=0; i<srcText.length(); i++)
        {
            int index = rusText.indexOf(srcBytes[i]);
            if(index == -1)
            {
                dstText += srcBytes[i];
            }
            else
            {
                dstText += latText.charAt(index);
            }
        }

        return dstText;
    }

    private void copyDone()
    {
        Toast toast = Toast.makeText(getApplicationContext(),"Скопировано в буфер обмена", Toast.LENGTH_SHORT);
        toast.show();
    }
}
