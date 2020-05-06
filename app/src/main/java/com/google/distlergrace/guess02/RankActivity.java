package com.google.distlergrace.guess02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class RankActivity extends AppCompatActivity {
    //Constants
    final int WOWGUESSMAX = 5;
    final int AVGGUESSMAX = 10;

    ImageView imageViewRank;
    Button buttonReturnHome;

    Integer attempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        imageViewRank = findViewById(R.id.imageViewRank);
        buttonReturnHome = findViewById(R.id.buttonReturnHome);

        attempts = 0;

        //Get The Intent In The Target Activity
        Intent rankIntent = getIntent();

        //Get The Attached Bundle From The Intent
        Bundle extras = rankIntent.getExtras();

        //Extract The Stored Data From The Bundle
        if (extras != null)
        {
            if (extras.containsKey("attempts"))
            {
                attempts = extras.getInt("attempts", 0);

                if (attempts <= WOWGUESSMAX)
                {
                    imageViewRank.setImageResource(R.drawable.wow);
                }
                else if (attempts <= AVGGUESSMAX)
                {
                    imageViewRank.setImageResource(R.drawable.average);
                }
                else if (attempts > AVGGUESSMAX)
                {
                    imageViewRank.setImageResource(R.drawable.novice);
                }
            }
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
