package com.example.matchup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private List<Integer> images = new ArrayList<>(Arrays.asList(R.drawable.camel, R.drawable.coala, R.drawable.fox, R.drawable.lion, R.drawable.monkey, R.drawable.wolf, R.drawable.camel, R.drawable.coala, R.drawable.fox, R.drawable.lion, R.drawable.monkey, R.drawable.wolf));
    private ImageButton[] imageButtons = new ImageButton[12];

    private int cardBack = R.drawable.blackrec;
    private int clicked = 0;
    private boolean turnOver = false;
    private int lastClicked = -1;
    private int currentClicked = -1;
    private int matchedPairs = 0;
    private TextView congratsText;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        congratsText = findViewById(R.id.congratsText);
        congratsText.setVisibility(View.INVISIBLE);

        Button backButton = findViewById(R.id.backButton);
        Button restartButton = findViewById(R.id.restartButton);


//        imageButtons[0] = findViewById(R.id.imageButton1);
//        imageButtons[1] = findViewById(R.id.imageButton2);
//        imageButtons[2] = findViewById(R.id.imageButton3);
//        imageButtons[3] = findViewById(R.id.imageButton4);
//        imageButtons[4] = findViewById(R.id.imageButton5);
//        imageButtons[5] = findViewById(R.id.imageButton6);
//        imageButtons[6] = findViewById(R.id.imageButton7);
//        imageButtons[7] = findViewById(R.id.imageButton8);
//        imageButtons[8] = findViewById(R.id.imageButton9);
//        imageButtons[9] = findViewById(R.id.imageButton10);
//        imageButtons[10] = findViewById(R.id.imageButton11);
//        imageButtons[11] = findViewById(R.id.imageButton12);

        // initialize image buttons
        initializeImageButton();

        // shuffle the images
        Collections.shuffle(images);

        // set up the game board

//        for (int i = 0; i < 12; i++) {
//            imageButtons[i].setBackgroundResource(cardBack);
//            final int finalI = i;
//            imageButtons[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (!turnOver) {
//                        imageButtons[finalI].setImageResource(images.get(finalI));
//                        if (clicked == 0) {
//                            lastClicked = finalI;
//                        }
//                        else {
//                            currentClicked = finalI;
//                        }
//                        clicked++;
//                    }
//                    if (clicked == 2) {
//                        turnOver = true;
//                        if (imageButtons[currentClicked].getDrawable().getConstantState().equals(imageButtons[lastClicked].getDrawable().getConstantState())) {
//                            imageButtons[currentClicked].setClickable(false);
//                            imageButtons[lastClicked].setClickable(false);
//                            turnOver = false;
//                            clicked = 0;
//                            matchedPairs++;
//                            if (matchedPairs == 6) {
//                                congratsText.setText("Complete!");
//                                congratsText.setVisibility(View.VISIBLE);
//                            }
//                        }
//                        else {
//                            flipCardsBack(lastClicked, currentClicked);
//                        }
//                    }
//                }
//            });
//        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });
    }

    private void initializeImageButton() {
        for (int i = 0; i < imageButtons.length; i++) {
            String buttonID = "imageButton" + (i+1);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            imageButtons[i] = findViewById(resID);
        }
    }

    private void setupGameBoard() {
        for (int i = 0; i < imageButtons.length; i++) {
            imageButtons[i].setBackgroundResource(cardBack);
            final int finalI = i;
            imageButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!turnOver) {
                        handleCardClick(finalI);
                    }
                }
            });
        }
    }

    private void handleCardClick(int index) {
        imageButtons[index].setImageResource(images.get(index));
        if (clicked == 0) {
            lastClicked = index;
        } else {
            currentClicked = index;
        }
        clicked++;

        if (clicked == 2) {
            turnOver = true;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkMatch();
                }
            }, 1000);
        }
    }


    private void checkMatch() {
        if (imageButtons[currentClicked].getDrawable().getConstantState().equals(imageButtons[lastClicked].getDrawable().getConstantState())) {
            imageButtons[currentClicked].setClickable(false);
            imageButtons[lastClicked].setClickable(false);
            matchedPairs++;
            if (matchedPairs == 6) {  // 6 pairs for 12 cards
                congratsText.setText("Complete!");
                congratsText.setVisibility(View.VISIBLE);
            }
        } else {
            flipCardsBack(lastClicked, currentClicked);
        }
        clicked = 0;
        turnOver = false;
    }

    private void restartGame() {
        clicked = 0;
        turnOver = false;
        lastClicked = -1;
        currentClicked = -1;
        Collections.shuffle(images);
        for (int i = 0; i < 12; i++) {
            imageButtons[i].setImageResource(cardBack);
            imageButtons[i].setClickable(true);
        }
        congratsText.setText("");
        congratsText.setVisibility(View.INVISIBLE);
    }

    private void flipCardsBack(int lastClicked, int currentClicked) {
        imageButtons[lastClicked].setImageResource(cardBack);
        imageButtons[currentClicked].setImageResource(cardBack);
    }
}