package com.google.distlergrace.guess02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    //Program Constants
    final int       MINNUMBER      = 1;
    final int       MAXNUMBER      = 100;
    final String    NOGUESS        = "NO GUESS ATTEMPTED!";
    final String    OORGUESS       = "GUESS INPUTTED OUT OF RANGE!\nGuess Must Be Between " +
                                      MINNUMBER + " and " + MAXNUMBER;
    final String    GUESSLOW       = "Guess Too Low. Try Again.";
    final String    GUESSHIGH      = "Guess Too High. Try Again.";
    final String    GUESSCORRECT   = "Congratulations!\nGuess Correct! You Did It!";
    final int       WOWGUESSMAX    = 5;
    final int       AVGGUESSMAX    = 10;

    //Program Widget Variables
    EditText    editTextGuessInput;
    Button      buttonCalculate;
    Button      buttonNewGame;
    Button      buttonRank;
    Button      buttonTotals;
    TextView    textViewResults;
    Toast       t;

    //Program Non-Widget Variables
    int randomNumber;
    int guess;
    int attempts;
    int numberOfGames;
    boolean keepGoing;
    boolean activeGame;
    int totalWows;
    int totalAvgs;
    int totalNovs;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Widget References
        editTextGuessInput  = findViewById(R.id.editTextGuessInput);
        buttonCalculate     = findViewById(R.id.buttonCalculate);
        buttonNewGame       = findViewById(R.id.buttonNewGame);
        buttonRank          = findViewById(R.id.buttonRank);
        buttonTotals        = findViewById(R.id.buttonTotals);
        textViewResults     = findViewById(R.id.textViewResults);

        //Initialize Global Variables
        attempts        = 0;
        numberOfGames   = 0;
        keepGoing       = true;
        activeGame      = false;
        buttonRank.setEnabled(false);
        totalWows       = 0;
        totalAvgs       = 0;
        totalNovs       = 0;

        buttonCalculate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                playTheGame();
            }
        });

        buttonNewGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clearAll();
            }
        });

        buttonRank.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Create A Bundle Object
                Bundle extras = new Bundle();

                //Add 'attempts' To Bundle
                extras.putInt("attempts", attempts);

                //Create & Initialize Intent
                Intent rankIntent =  new Intent(getApplicationContext(), RankActivity.class);

                //Attach Bundle To Intent
                rankIntent.putExtras(extras);

                //Start The Activity
                startActivity(rankIntent);
            }
        });

        buttonTotals.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Create A Bundle Object
                Bundle extras = new Bundle();
                extras.putInt("totalWows", totalWows);
                extras.putInt("totalAvgs", totalAvgs);
                extras.putInt("totalNovs", totalNovs);

                //Create & Initialize An Intent
                Intent totalsIntent = new Intent(getApplicationContext(),
                        GameTotalsActivity.class);

                //Attach The Bundle To The Intent Object
                totalsIntent.putExtras(extras);

                //Start The Activity
                startActivity(totalsIntent);
            }
        });

    }

    private void playTheGame()
    {
        //If 'activeGame' Flag Is 'false', A New Game Is To Be Started
        // Should Be Either: Start Of Program Or Current Random Number Was Correctly Guessed
        if (!activeGame)
        {
            //  Set 'attempts' To 0
            //  Generate A Random Number Between 1 - 100
            //  Set 'activeGame' To 'true'
            //  Set 'keepGoing' To 'true'
            attempts    = 0;
            generateRandomNumber();
            activeGame  = true;
            keepGoing   = true;
            buttonRank.setEnabled(false);
        }

        //Check For No Input
        keepGoing = checkForNoInput();

        if (keepGoing)
        {
            //  If User Gets To This Point, A Guess Has Been Made
            //  So, Set Guess Variable
            guess = Integer.valueOf(editTextGuessInput.getText().toString());

            //  Check For Guess Out Of Range
            keepGoing = checkForOutOfRangeInput();
        }

        if(keepGoing)
        {
            //  If User Gets To This Point, A Valid Guess Was Made
            //  Increment 'attempts' Counter
            ++attempts;

            // Check Guess For Too High, Too Low, Or Correct
            checkGuessStatus();
        }
    }

    private void generateRandomNumber()
    {
        Random rand = new Random();
        randomNumber = rand.nextInt((MAXNUMBER - MINNUMBER) + 1) + MINNUMBER;
    }

    private boolean checkForNoInput()
    {
        if (editTextGuessInput.getText().toString().isEmpty())
        {
            t = Toast.makeText(getApplicationContext(), NOGUESS, Toast.LENGTH_LONG);
            t.show();

            return false;
        }

        return true;
    }

    private boolean checkForOutOfRangeInput()
    {
        if ((guess < MINNUMBER) || (guess > MAXNUMBER))
        {
            textViewResults.setText("");

            t = Toast.makeText(getApplicationContext(), OORGUESS, Toast.LENGTH_LONG);
            t.show();

            return false;
        }

        return true;
    }

    private void checkGuessStatus()
    {
        if (guess < randomNumber)
        {
            textViewResults.setText(GUESSLOW);
            eraseInput();
        }
        else if (guess > randomNumber)
        {
            textViewResults.setText(GUESSHIGH);
            eraseInput();
        }
        else
        {
            textViewResults.setText("You Guessed The Secret Number (" + randomNumber +
                                    " In " + attempts + " Guesses");
            incrementAppropriateCounter();
            activeGame = false;
            keepGoing = false;
            buttonRank.setEnabled(true);
            newGame();
        }
    }

    private void eraseInput()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                buttonRank.setEnabled(false);
                clearAll();
            }
        }, 5000);   // 5 Second Wait
    }

    private void clearAll()
    {
        editTextGuessInput.setText("");
        textViewResults.setText("");
        editTextGuessInput.requestFocus();
    }

    private void incrementAppropriateCounter()
    {
        if (attempts <= WOWGUESSMAX)
        {
            ++totalWows;
        }
        else if (attempts <= AVGGUESSMAX)
        {
            ++totalAvgs;
        }
        else
        {
            ++totalNovs;
        }
    }

    private void newGame()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                playTheGame();
            }
        }, 10000);   // 10 Second Wait
    }
}
