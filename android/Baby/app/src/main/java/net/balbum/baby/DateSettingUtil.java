package net.balbum.baby;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by hyes on 2015. 12. 11..
 */
public class DateSettingUtil implements DatePickerDialog.OnDateSetListener {

    Context context;
    View editText;

    public DateSettingUtil (Context context, View view){
        this.context=context;
        this.editText =view;

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        ((EditText) editText).setText(year + "-" + month + "-" + day);
    }
}
