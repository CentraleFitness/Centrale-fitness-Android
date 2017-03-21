package com.fitness.centrale.centralefitness;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Objects;

/**
 * Created by remy on 21/03/17.
 */

public class DetailsDialog extends Dialog {



    String title;

    public DetailsDialog(Context context,
                         final String title,
                         final String prodDay,
                         final String prodWeek,
                         final String prodMonth,
                         final String prodBegin,
                         final String moneyDay,
                         final String moneyWeek,
                         final String moneyMonth,
                         final String moneyBegin) {
        super(context);
        this.title = title;
        setContentView(R.layout.dialog_details);
        setTitle(title);

        TextView titleView = (TextView) findViewById(R.id.DetailDialogTitle);
        TextView proDayView = (TextView) findViewById(R.id.DetailDialogProdDay);
        TextView prodWeekView = (TextView) findViewById(R.id.DetailDialogProdWeek);
        TextView prodMonthView = (TextView) findViewById(R.id.DetailDialogProdMonth);
        TextView prodBeginView = (TextView) findViewById(R.id.DetailDialogProdBegin);
        TextView moneyDayView = (TextView) findViewById(R.id.DetailDialogMoneyDay);
        TextView moneyWeekView = (TextView) findViewById(R.id.DetailDialogMoneyWeek);
        TextView moneyMonthView = (TextView) findViewById(R.id.DetailDialogMoneyMonth);
        TextView moneyBeginView = (TextView) findViewById(R.id.DetailDialogMoneyBegin);

        titleView.setText(title);
        proDayView.setText(prodDay + "W");
        prodWeekView.setText(prodWeek + "W");
        prodMonthView.setText(prodMonth + "W");
        prodBeginView.setText(prodBegin + "W");
        moneyDayView.setText(moneyDay+ "€");
        moneyWeekView.setText(moneyWeek + "€");
        moneyMonthView.setText(moneyMonth + "€");
        moneyBeginView.setText(moneyBegin + "€");



    }






}
