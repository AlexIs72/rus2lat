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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity /*implements View.OnFocusChangeListener*/ {

    private EditText mInputText;
    private EditText mTranslatedText;
    private ListView m_historyList;
    private Button mCopyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
                "Костя", "Игорь", "Анна", "Денис", "Андрей" };

        m_historyList = findViewById(R.id.historyList);

// создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);

        // присваиваем адаптер списку
        m_historyList.setAdapter(adapter);

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
        copyToClipboard(getApplicationContext(), mTranslatedText.getText().toString());
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

    private boolean copyToClipboard(Context context, String text) {
        try {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                        .getSystemService(context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                        .getSystemService(context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData
                        .newPlainText(
                                /*context.getResources().getString(
                                        R.string.message)*/"rus2lat", text);
                clipboard.setPrimaryClip(clip);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
