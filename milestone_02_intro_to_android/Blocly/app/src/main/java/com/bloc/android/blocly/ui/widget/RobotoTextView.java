package com.bloc.android.blocly.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bloc.blocly.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wayne on 12/25/2014.
 */
public class RobotoTextView extends TextView {
    // a static map to track existing Typeface objects
    private static Map<String, Typeface> sTypefaces = new HashMap<String, Typeface>();

    public RobotoTextView(Context context) {
        super(context);
    }

    // these two methods make use of the extractFont(AttributeSet) method
    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        extractFont(attrs);
    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        extractFont(attrs);
    }

    void extractFont(AttributeSet attrs) {
    // check if RobotoTextView is currently being previewed inside of a WYSIWYG editor
        if (isInEditMode()) {
            return;
        }
        if (attrs == null) {
            return;
        }
    // extracts the only attributes which RobotoTextView cares for, namely robotoFont
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.Roboto, 0, 0);
    // pull an integer from typedArray corresponding to the robotoFont attribute
        int robotoFontIndex = typedArray.getInteger(R.styleable.Roboto_robotoFont, -1);
    // recycle the typedArray object as requested by the documentation
        typedArray.recycle();
    //  inflate our resource string-array into an actual array of Strings
        String[] stringArray = getResources().getStringArray(R.array.roboto_font_file_names);
        if (robotoFontIndex < 0 || robotoFontIndex >= stringArray.length) {
            return;
        }
        String robotoFont = stringArray[robotoFontIndex];
        Typeface robotoTypeface = null;
    // check for a cached Typeface for the given filename
        if (sTypefaces.containsKey(robotoFont)) {
            robotoTypeface = sTypefaces.get(robotoFont);
        } else {
    // if not cached, inflate a brand new Typeface object
            robotoTypeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/RobotoTTF/" + robotoFont);
            sTypefaces.put(robotoFont, robotoTypeface);
        }
    // set the Typeface, inflated or cached, as this instance's typeface
        setTypeface(robotoTypeface);
    }
}
