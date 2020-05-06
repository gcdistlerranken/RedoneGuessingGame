package com.google.distlergrace.guess02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameTotalsActivity extends AppCompatActivity
{

    TextView    textViewTotals;
    Button      buttonReturnHome;

    Integer     totalWows;
    Integer     totalAvgs;
    Integer     totalNovs;
    String      totals;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_totals);

        textViewTotals      = findViewById(R.id.textViewTotals);
        buttonReturnHome    = findViewById(R.id.buttonReturnHome);

        Intent totalsIntent = getIntent();
        Bundle extras  = totalsIntent.getExtras();

        if (extras != null)
        {
            if (extras.containsKey("totalWows"))
            {
                totalWows = extras.getInt("totalWows", 0);
            }
            if (extras.containsKey("totalAvgs"))
            {
                totalAvgs = extras.getInt("totalAvgs", 0);
            }
            if (extras.containsKey("totalNovs"))
            {
                totalNovs = extras.getInt("totalNovs", 0);
            }

            totals  = "\nTotal Wows: " + totalWows + "\n";
            totals += "\nTotal Avgs: " + totalAvgs + "\n";
            totals += "\nTotal Novs: " + totalNovs + "\n";

            textViewTotals.setText(totals);
        }

        buttonReturnHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}
