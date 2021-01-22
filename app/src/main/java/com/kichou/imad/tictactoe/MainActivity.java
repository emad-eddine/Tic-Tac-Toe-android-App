package com.kichou.imad.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons = new Button[3][3];
    private Boolean player1Turn = true;
    private int gameCount = 0 ;
    private int player1Points = 0;
    private int player2Points = 0;
    private TextView player1PointsText ;
    private TextView player2PointsText ;
    private Button resetbtn ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1PointsText = findViewById(R.id.player_1_points);
        player2PointsText = findViewById(R.id.player_2_points) ;
        resetbtn = findViewById(R.id.reset_btn) ;

        for(int i = 0 ; i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                String btn_id = "btn_" + i + j ;
                int btnResId = getResources().getIdentifier(btn_id,"id",getPackageName());
                buttons[i][j] = findViewById(btnResId);
                buttons[i][j].setOnClickListener(this);
            }
        }

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if(!((Button) v ).getText().toString().equals(""))
            return;
        else
        {
            if(player1Turn)
                ((Button) v ).setText("X");
            else
                ((Button) v ).setText("O");

            gameCount++;
            if(checkWinner())
            {
                if(player1Turn)
                    player1Win();
                else
                    player2Win();

            }
            else if(gameCount == 9)
                     draw();
                else
                    player1Turn = !player1Turn;
        }

    }

    private Boolean checkWinner()
    {
//        {0,0},{0,1},{0,2}
//        {1,0},{1,1},{1,2}
//        {2,0},{2,1},{2,2}

        String[][] field = new String[3][3];
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        // check rows
        for(int i=0;i<3;i++)
        {
            if(field[i][0].equals(field[i][1])
                        && field[i][0].equals(field[i][2])
                        && !field[i][0].equals(""))
                return true;

        }
        // check columns
        for(int i=0;i<3;i++)
        {
            if(field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals(""))
                return true;
        }
        if(field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals(""))
            return true;
        if(field[2][0].equals(field[1][1])
                && field[2][0].equals(field[0][2])
                && !field[2][0].equals(""))
            return true;

        return false;

    }

    public void player1Win()
    {

        player1Points = player1Points +1;
        Toast.makeText(this,"Player 1 wins ",Toast.LENGTH_SHORT).show();
        updatePlayersPoint();
        reset();
    }

    public void player2Win()
    {

        player2Points = player2Points + 1;
        Toast.makeText(this,"Player 2 wins ",Toast.LENGTH_SHORT).show();
        updatePlayersPoint();
        reset();
    }
    public void draw()
    {
        Toast.makeText(this,"DRAW !! ",Toast.LENGTH_SHORT).show();
        reset();
    }

    public void reset()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                 buttons[i][j].setText("");
            }
        }
        gameCount = 0;
        player1Turn = true;

    }
    public void updatePlayersPoint()
    {
        player1PointsText.setText("Player 1 :" + player1Points);
        player2PointsText.setText("Player 2 :" + player2Points);
    }
    public void resetGame()
    {
        reset();
        player1Points = 0;
        player2Points = 0;
        updatePlayersPoint();
    }

    // keep game going when phone get rotated


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount",gameCount);
        outState.putInt("player1Points",player1Points);
        outState.putInt("player2Points",player2Points);
        outState.putBoolean("playerTurn",player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        gameCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("playerTurn");
    }
}