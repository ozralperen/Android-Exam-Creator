package tr.yildiz.ozeralperen17011069;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateExamActivity extends AppCompatActivity {

    private EditText examTimeET, scoreET;
    private RatingBar difficultyRB;
    private List<Question> questionList = new ArrayList<Question>();
    private List<Integer> checkBoxIDs = new ArrayList<Integer>();
    private List<String> questionTexts = new ArrayList<String>();
    private LinearLayout createExamRV;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exam);

        setExamTimeET(findViewById(R.id.createExamTimeET));
        setScoreET(findViewById(R.id.createExamPointsET));
        setDifficultyRB(findViewById(R.id.createExamDifficultyTB));
        setCreateExamRV(findViewById(R.id.createExamRV));

        SharedPreferences sharedPreferences = this.getSharedPreferences("prefs", MODE_PRIVATE);
        examTimeET.setText(sharedPreferences.getString("Time", "0"));
        scoreET.setText(sharedPreferences.getString("Score", "0"));
        difficultyRB.setRating(sharedPreferences.getInt("Difficulty", 5));

        DatabaseHandler db = new DatabaseHandler(this);
        questionList = db.getAllQuestions();

        List<String> choices = new ArrayList<String>();
        choices.add(0, "A");
        choices.add(1, "B");
        choices.add(2, "C");
        choices.add(3, "D");
        choices.add(4, "E");

        LinearLayout newQuestionLinearLayout;
        LinearLayout questionDescLayout;
        TextView newQuestionDescription, answerTV;
        CheckBox checkBox;
        View whiteLine;
        List<String> answers;
        List<String> answersAdded;
        Button saveButton;
        int randindex;
        int questionIndex = 0;
        String questionText;

        for (Question question: questionList) {
            questionText = new String("");

            answers = new ArrayList<String>();
            answersAdded = new ArrayList<String>();

            whiteLine = new View(this);
            whiteLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10));
            whiteLine.setBackgroundColor(getResources().getColor(R.color.dark_background));
            createExamRV.addView(whiteLine);

            newQuestionLinearLayout = new LinearLayout(this);
            newQuestionLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            checkBox = new CheckBox(context);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                checkBox.setId(View.generateViewId());
            }
            else{
                checkBox.setId(ViewCompat.generateViewId());
            }
            newQuestionLinearLayout.addView(checkBox);
            checkBoxIDs.add(checkBox.getId());

            questionDescLayout = new LinearLayout(this);
            questionDescLayout.setOrientation(LinearLayout.VERTICAL);
            questionDescLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            newQuestionDescription = new TextView(this);
            newQuestionDescription.setText(question.getQuestion());
            newQuestionDescription.setTextColor(getResources().getColor(R.color.white));
            questionDescLayout.addView(newQuestionDescription);
            questionText += question.getQuestion();

            answers.add(question.getAnswer1());
            answers.add(question.getAnswer2());
            if(!question.getAnswer3().equals("")){
                answers.add(question.getAnswer3());
                if(!question.getAnswer4().equals("")){
                    answers.add(question.getAnswer4());
                    if(!question.getAnswer5().equals(""))
                        answers.add(question.getAnswer5());
                }
            }

            switch (question.getRightAnswer().toUpperCase()){
                case "A":
                    answersAdded.add(answers.get(0));
                    answers.remove(0);
                    break;
                case "B":
                    answersAdded.add(answers.get(1));
                    answers.remove(1);
                    break;
                case "C":
                    answersAdded.add(answers.get(2));
                    answers.remove(2);
                    break;
                case "D":
                    answersAdded.add(answers.get(3));
                    answers.remove(3);
                    break;
                case "E":
                    answersAdded.add(answers.get(4));
                    answers.remove(4);
                    break;
            }


            for(int i = 0; i<difficultyRB.getRating()-1; i++){
                randindex = (int)(Math.random() * answers.size());
                answersAdded.add(answers.get(randindex));
            }

            for(int i = 0; i<difficultyRB.getRating(); i++){
                randindex = (int)(Math.random() * answersAdded.size());
                answerTV = new TextView(getApplicationContext());
                answerTV.setText(answersAdded.get(randindex));
                questionText += "\n" + choices.get(i) + " - " + answersAdded.get(randindex);
                answerTV.setTextColor(getResources().getColor(R.color.white));
                answersAdded.remove(randindex);
                questionDescLayout.addView(answerTV);
            }

            newQuestionLinearLayout.addView(questionDescLayout);
            createExamRV.addView(newQuestionLinearLayout);

            questionTexts.add(questionIndex, questionText);
            questionIndex++;
        }

        saveButton = new Button(context);
        saveButton.setText("Save Exam");
        saveButton.setTextColor(getResources().getColor(R.color.white));
        saveButton.setBackgroundColor(getResources().getColor(R.color.accept_green));



        difficultyRB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0, 0);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String examString = new String("");
                //try {
                    /*Log.d("step", "try'a girdi");
                    File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                    Log.d("step", "file opened");
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    File gpxfile = new File(root, "exam");

                    if (!gpxfile.exists()){
                        try {
                            gpxfile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("step", "gpx opened");*/


                    examString += "\nTime: " + examTimeET.getText().toString() + " minutes";
                    examString += "\nEach question is " + scoreET.getText().toString() + " points.";
                    examString += "\nGood luck.";

                    for (int i = 0; i < checkBoxIDs.size(); i++) {
                        CheckBox cb = findViewById(checkBoxIDs.get(i));
                        if (cb.isChecked()) {
                            examString += "\n" + questionTexts.get(i);
                        }
                    }

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT ,examString);
                    shareIntent.setType("text/plain");
                    startActivity(Intent.createChooser(shareIntent, "Share exam with"));

                /*try {
                    FileWriter writer = new FileWriter(gpxfile);
                    Log.d("step", "filewriter hazır");

                    writer.append(examString);
                    writer.flush();
                    writer.close();

                    Log.d("step", "text yazıldı");

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/*");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + gpxfile.getAbsolutePath()));
                    startActivity(Intent.createChooser(sharingIntent, "Share exam with"));

                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                }catch(IOException e){
                    Log.d("step", "patladık");
                    e.printStackTrace();
                }*/
            }
        });

        createExamRV.addView(saveButton);

    }


    public EditText getExamTimeET() {
        return examTimeET;
    }

    public void setExamTimeET(EditText examTimeET) {
        this.examTimeET = examTimeET;
    }

    public EditText getScoreET() {
        return scoreET;
    }

    public void setScoreET(EditText scoreET) {
        this.scoreET = scoreET;
    }

    public RatingBar getDifficultyRB() {
        return difficultyRB;
    }

    public void setDifficultyRB(RatingBar difficultyRB) {
        this.difficultyRB = difficultyRB;
    }

    public LinearLayout getCreateExamRV() {
        return createExamRV;
    }

    public void setCreateExamRV(LinearLayout createExamRV) {
        this.createExamRV = createExamRV;
    }
}