package com.bloc.blocnotes;

/**
 * Created by Wayne on 9/18/2014.
 */
public interface iCustomStyle {
    public void onStyleChange(CustomStyleDialogFragment dialog, int styleId);
    public void onFontChange(CustomStyleDialogFragment dialog, String fontName);
    public void onThemeChange(CustomStyleDialogFragment dialog, int themeId);
}

