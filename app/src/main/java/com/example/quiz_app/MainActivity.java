package com.example.quiz_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView questionTextView;
    TextView totalQuestionTextView;
    Button ansA,ansB,ansC,ansD;
    Button btn_submit;
    int score=0;
    int totalQuestion=QuestionAnswer.questions.length;
    int currentQuestionIndex=0;
    String selectedAnswer;
    String Message;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestionTextView=findViewById(R.id.total_question);
        questionTextView=findViewById(R.id.question);

        ansA=findViewById(R.id.ans_a);
        ansB=findViewById(R.id.ans_b);
        ansC=findViewById(R.id.ans_c);
        ansD=findViewById(R.id.ans_d);

        btn_submit=findViewById(R.id.submit);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        totalQuestionTextView.setText("Total Question: "+totalQuestion);

        loadNewQuestions();

    }
    private void loadNewQuestions(){
        resetButtonColors();
        if(currentQuestionIndex==totalQuestion){
            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionAnswer.questions[currentQuestionIndex]);

        ansA.setText(QuestionAnswer.Choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.Choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.Choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.Choices[currentQuestionIndex][3]);

        selectedAnswer="";
    }

    private void finishQuiz(){
        String passStatus;
        if(score>= totalQuestion*0.6){
            passStatus="Passed...!";
            Message="Congrats ! You have Passed";
        }
        else{
            passStatus="Failed !";
            Message="Sorry ! You have Failed";
        }
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Your Score :"+score+"/"+totalQuestion)
                .setPositiveButton("Restart",((dialog, i) -> restartQuiz() ))
                .setNegativeButton("Cancel",((dialog, i) -> {
                        new AlertDialog.Builder( this)
                                .setTitle(passStatus)
                                .setMessage(Message)
                                .setPositiveButton("Ok",((innerDialog, j) -> finish()))
                                .setCancelable(false)
                                .show();
                }))
                .setCancelable(false).show();
    }

    private void restartQuiz(){
        score=0;
        currentQuestionIndex=0;
        loadNewQuestions();
    }

    private void resetButtonColors() {
        ansA.setBackgroundColor(Color.parseColor("#5D708A"));
        ansB.setBackgroundColor(Color.parseColor("#5D708A"));
        ansC.setBackgroundColor(Color.parseColor("#5D708A"));
        ansD.setBackgroundColor(Color.parseColor("#5D708A"));
    }


    @Override
    public void onClick(View v) {


        Button clickedButton=(Button) v;

        if(clickedButton.getId()==R.id.submit){
            if(!selectedAnswer.isEmpty()){
                if(selectedAnswer.equals(QuestionAnswer.CorrectAnswer[currentQuestionIndex])){
                    score++;
                }
                currentQuestionIndex++;
                loadNewQuestions();
            }
            else{
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            }
        }else{
            selectedAnswer=clickedButton.getText().toString();
//            clickedButton.setBackgroundColor(Color.GREEN);
        }
    }
}