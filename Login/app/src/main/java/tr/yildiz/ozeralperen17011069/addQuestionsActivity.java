package tr.yildiz.ozeralperen17011069;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class addQuestionsActivity extends AppCompatActivity {

    private EditText questionET, answer1ET, answer2ET, answer3ET, answer4ET, answer5ET, correctAnswerET;
    private ImageButton attachmentIB;
    private Button addQuestionButton;
    private Context context = this;
    private String attahmentPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        DatabaseHandler db = new DatabaseHandler(this);

        setQuestionET(findViewById(R.id.addQuestionQuestionET));
        setAnswer1ET(findViewById(R.id.addQuestionAnswer1ET));
        setAnswer2ET(findViewById(R.id.addQuestionAnswer2ET));
        setAnswer3ET(findViewById(R.id.addQuestionAnswer3ET));
        setAnswer4ET(findViewById(R.id.addQuestionAnswer4ET));
        setAnswer5ET(findViewById(R.id.addQuestionAnswer5ET));
        setCorrectAnswerET(findViewById(R.id.addQuestionCorrectAnswer));
        setAttachmentIB(findViewById(R.id.addQuestionAttachmentIB));
        setAddQuestionButton(findViewById(R.id.addQuestionsAddQuestionButton));

        attachmentIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getQuestionET().getText().toString().length() < 2 || getAnswer1ET().getText().toString().length() < 2 || getAnswer2ET().getText().toString().length() < 2 )
                    Toast.makeText(context, "Please make sure that there is a question with at least two answers with at least 2 characters each.", Toast.LENGTH_LONG).show();
                else if(getCorrectAnswerET().getText().toString().toUpperCase().equals("A") || getCorrectAnswerET().getText().toString().toUpperCase().equals("B") ||
                        getCorrectAnswerET().getText().toString().toUpperCase().equals("C") || getCorrectAnswerET().getText().toString().toUpperCase().equals("D") ||
                        getCorrectAnswerET().getText().toString().toUpperCase().equals("E")){
                    db.addQuestion(new Question(getQuestionET().getText().toString(), getAnswer1ET().getText().toString(), getAnswer1ET().getText().toString(),
                            getAnswer3ET().getText().toString(), getAnswer4ET().getText().toString(), getAnswer5ET().getText().toString(),
                            getCorrectAnswerET().getText().toString()));
                    Toast.makeText(context, "Question added to database", Toast.LENGTH_LONG).show();
                    questionET.setText("");
                    answer1ET.setText("");
                    answer2ET.setText("");
                    answer3ET.setText("");
                    answer4ET.setText("");
                    answer5ET.setText("");
                    correctAnswerET.setText("");
                    attahmentPath = "";
                }
                else
                    Toast.makeText(context, "Please make sure that there is an answer with answer A,B,C,D or E.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public EditText getQuestionET() {
        return questionET;
    }

    public void setQuestionET(EditText questionET) {
        this.questionET = questionET;
    }

    public EditText getAnswer1ET() {
        return answer1ET;
    }

    public void setAnswer1ET(EditText answer1ET) {
        this.answer1ET = answer1ET;
    }

    public EditText getAnswer2ET() {
        return answer2ET;
    }

    public void setAnswer2ET(EditText answer2ET) {
        this.answer2ET = answer2ET;
    }

    public EditText getAnswer3ET() {
        return answer3ET;
    }

    public void setAnswer3ET(EditText answer3ET) {
        this.answer3ET = answer3ET;
    }

    public EditText getAnswer4ET() {
        return answer4ET;
    }

    public void setAnswer4ET(EditText answer4ET) {
        this.answer4ET = answer4ET;
    }

    public EditText getAnswer5ET() {
        return answer5ET;
    }

    public void setAnswer5ET(EditText answer5ET) {
        this.answer5ET = answer5ET;
    }

    public EditText getCorrectAnswerET() {
        return correctAnswerET;
    }

    public void setCorrectAnswerET(EditText correctAnswerET) {
        this.correctAnswerET = correctAnswerET;
    }

    public ImageButton getAttachmentIB() {
        return attachmentIB;
    }

    public void setAttachmentIB(ImageButton attachmentIB) {
        this.attachmentIB = attachmentIB;
    }

    public Button getAddQuestionButton() {
        return addQuestionButton;
    }

    public void setAddQuestionButton(Button addQuestionButton) {
        this.addQuestionButton = addQuestionButton;
    }
}