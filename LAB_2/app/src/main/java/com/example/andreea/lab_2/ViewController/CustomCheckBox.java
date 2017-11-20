package com.example.andreea.lab_2.ViewController;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.CheckBox;
import com.example.andreea.lab_2.R;

/**
 * Created by Andreea on 11/15/2017.
 */

public class CustomCheckBox extends android.support.v7.widget.AppCompatCheckBox{

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked){
            this.setBackgroundResource(R.drawable.checkbox_backgound);
            this.setTextColor(Color.WHITE);
        }else {
            this.setBackgroundColor(Color.TRANSPARENT);
            this.setTextColor(Color.BLACK);
        }
        super.setChecked(checked);
    }
}
