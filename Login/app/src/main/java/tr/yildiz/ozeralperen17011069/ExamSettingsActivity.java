package tr.yildiz.ozeralperen17011069;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class ExamSettingsActivity extends AppCompatActivity {

    private EditText examTimeET, examScorepQET;
    private RatingBar examDifficultyRB;
    private Button saveButton;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_settings);

        setExamTimeET(findViewById(R.id.examSettingsTimeET));
        setExamScorepQET(findViewById(R.id.examSettingScoresPerQuestionET));
        setExamDifficultyRB(findViewById(R.id.examSettingsDifficultyRB));
        setSaveButton(findViewById(R.id.examSettingsSaveButton));

        SharedPreferences examSettingsSP = this.getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = examSettingsSP.edit();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getExamTimeET().getText().toString().length() < 1 || getExamScorepQET().getText().toString().length() < 1)
                    Toast.makeText(context, "Please fill in the exam time and score.", Toast.LENGTH_LONG).show();
                else if(examDifficultyRB.getNumStars() < 2)
                    Toast.makeText(context, "Please select a difficulty of at least 2.", Toast.LENGTH_LONG).show();
                else if(getExamTimeET().getText().toString().equals(new String("0")))
                    Toast.makeText(context, "Please enter a valid exam time (more than 0).", Toast.LENGTH_LONG).show();
                else if(Integer.parseInt(getExamScorepQET().getText().toString()) < 1 || Integer.parseInt(getExamScorepQET().getText().toString()) > 100)
                    Toast.makeText(context, "Please enter a valid question score (more than 0 and less than 100).", Toast.LENGTH_LONG).show();
                else{
                    editor.putString("Time", getExamTimeET().getText().toString());
                    editor.putString("Score", getExamScorepQET().getText().toString());
                    editor.putInt("Difficulty", (int)examDifficultyRB.getRating());
                    editor.commit();
                    finish();
                }


            }
        });


    }

    public EditText getExamTimeET() {
        return examTimeET;
    }

    public void setExamTimeET(EditText examTimeET) {
        this.examTimeET = examTimeET;
    }

    public EditText getExamScorepQET() {
        return examScorepQET;
    }

    public void setExamScorepQET(EditText examScorepQET) {
        this.examScorepQET = examScorepQET;
    }

    public RatingBar getExamDifficultyRB() {
        return examDifficultyRB;
    }

    public void setExamDifficultyRB(RatingBar examDifficultyRB) {
        this.examDifficultyRB = examDifficultyRB;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }
}