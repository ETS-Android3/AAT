package ch.bailu.aat.util.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import ch.bailu.aat_lib.app.AppColor;

public class UiThemeDark implements UiTheme {


    private final int hl_color;


    public UiThemeDark(int hl_color) {
        this.hl_color = hl_color;
    }


    @Override
    public int getBackgroundColor() {
        return Color.BLACK;

    }

    @Override
    public int getHighlightColor () {
        return hl_color;
    }

    @Override
    public int getGraphBackgroundColor() {
        return 0;
    }

    @Override
    public int getGraphLineColor() {
        return Color.DKGRAY;
    }

    @Override
    public int getGraphTextColor() {
        return Color.LTGRAY;
    }


    @Override
    public void list(ListView l) {
        int height = l.getDividerHeight();
        l.setDivider(new ColorDrawable(hl_color));
        l.setDividerHeight(height);
        l.setSelector(android.R.color.transparent);
    }

    @Override
    public void background(View v) {
        v.setBackgroundColor(Color.BLACK);
    }

    @Override
    public void button(View v) {
        v.setBackgroundDrawable(AppTheme.getButtonDrawable(0, hl_color));
    }


    @Override
    public void topic(TextView v) {
        v.setTextColor(hl_color);
        v.setTextSize(HEADER_TEXT_SIZE);
    }

    @Override
    public void header(TextView v) {
        v.setTextColor(Color.WHITE);
        v.setTextSize(HEADER_TEXT_SIZE);
    }

    @Override
    public void content(TextView v) {
        v.setTextColor(Color.LTGRAY);
        v.setLinkTextColor(hl_color);
    }

    @Override
    public void toolTip(TextView v) {
        v.setTextColor(AppColor.HL_BLUE);
    }


    @Override
    public void backgroundAlt(View v) {
        v.setBackgroundColor(Color.DKGRAY);
    }


    @Override
    public void contentAlt(TextView v) {
        v.setTextColor(Color.WHITE);
    }
}
