package tr.yildiz.ozeralperen17011069;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainScreen extends AppCompatActivity {

    private Button usernameButton, newQuestionButton, listQuestionsButton, examSettingsButton, createExamButton;
    private int userid;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Intent loginInfo = getIntent();
        userid = loginInfo.getIntExtra("UserID", -1);

        setUsernameButton(findViewById(R.id.user_info_button));
        setNewQuestionButton(findViewById(R.id.add_new_question_to_db));
        setListQuestionsButton(findViewById(R.id.list_questions_main_menu));
        setExamSettingsButton(findViewById(R.id.exam_setting_main_menu));
        setCreateExamButton(findViewById(R.id.create_new_exam_to_db));

        DatabaseHandler db = new DatabaseHandler(this);
        setUser(db.getUser(userid));

        usernameButton.setText(user.getName() + " " + user.getSurname());

        newQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddQuestion = new Intent(getApplicationContext(), addQuestionsActivity.class);
                startActivity(intentAddQuestion);
            }
        });

        listQuestionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentListQuestions = new Intent(getApplicationContext(), ListQuestionsActivity.class);
                startActivity(intentListQuestions);
            }
        });

        examSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent examSettings = new Intent(getApplicationContext(), ExamSettingsActivity.class);
                startActivity(examSettings);
            }
        });

        createExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createExam = new Intent(getApplicationContext(), CreateExamActivity.class);
                startActivity(createExam);
            }
        });
    }


    public Button getUsernameButton() {
        return usernameButton;
    }

    public void setUsernameButton(Button usernameButton) {
        this.usernameButton = usernameButton;
    }

    public Button getNewQuestionButton() {
        return newQuestionButton;
    }

    public void setNewQuestionButton(Button newQuestionButton) {
        this.newQuestionButton = newQuestionButton;
    }

    public Button getListQuestionsButton() {
        return listQuestionsButton;
    }

    public void setListQuestionsButton(Button listQuestionsButton) {
        this.listQuestionsButton = listQuestionsButton;
    }

    public Button getExamSettingsButton() {
        return examSettingsButton;
    }

    public void setExamSettingsButton(Button examSettingsButton) {
        this.examSettingsButton = examSettingsButton;
    }

    public Button getCreateExamButton() {
        return createExamButton;
    }

    public void setCreateExamButton(Button createExamButton) {
        this.createExamButton = createExamButton;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}