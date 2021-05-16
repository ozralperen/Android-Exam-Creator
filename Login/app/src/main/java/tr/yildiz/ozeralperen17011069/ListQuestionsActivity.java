package tr.yildiz.ozeralperen17011069;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListQuestionsActivity extends AppCompatActivity {

    private LinearLayout questionsRecyclerView;
    private List<Question> questionList = new ArrayList<Question>();
    private Context listQuestionsContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_questions);

        setQuestionsRecyclerView(findViewById(R.id.listQuestionsRV));

        DatabaseHandler db = new DatabaseHandler(this);
        questionList = db.getAllQuestions();

        LinearLayout newQuestionLinearLayout;
        LinearLayout questionDescLayout;
        TextView newQuestionDescription;
        RadioGroup newQuestionRadioGroup;
        RadioButton newAnswerRadioButton;
        LayoutParams textLayoutParams;
        ImageButton deleteQuestion;
        View whiteLine;

        for (Question question: questionList){
            whiteLine = new View(this);
            whiteLine.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10));
            whiteLine.setBackgroundColor(getResources().getColor(R.color.dark_background));
            questionsRecyclerView.addView(whiteLine);

            newQuestionLinearLayout = new LinearLayout(this);
            newQuestionLinearLayout.setOrientation(LinearLayout.VERTICAL);

            questionDescLayout = new LinearLayout(this);
            questionDescLayout.setOrientation(LinearLayout.HORIZONTAL);
            questionDescLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            deleteQuestion = new ImageButton(this);
            deleteQuestion.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_delete_24));
            deleteQuestion.setBackgroundColor(getResources().getColor(R.color.dark_background));
            deleteQuestion.setTag(question.getQuestionID());
            questionDescLayout.addView(deleteQuestion);

            deleteQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View parentview) {
                    AlertDialog.Builder deleteQuestionCheckADBuilder = new AlertDialog.Builder(listQuestionsContext, R.style.AlertDialogStyle);
                    View deleteQuestionCheckADLayout = getLayoutInflater().inflate(R.layout.delete_question_alertdialog_layout, null);
                    deleteQuestionCheckADBuilder.setView(deleteQuestionCheckADLayout);
                    AlertDialog deleteQuestionCheckAD = deleteQuestionCheckADBuilder.create();
                    Button cancel = (Button)deleteQuestionCheckADLayout.findViewById(R.id.deleteQuestionADCancelButton);
                    Button delete = (Button)deleteQuestionCheckADLayout.findViewById(R.id.deleteQuestionADOKButton);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            deleteQuestionCheckAD.cancel();
                        }
                    });
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            db.deleteQuestion(Integer.parseInt(parentview.getTag().toString()));
                        }
                    });
                    deleteQuestionCheckAD.show();

                }
            });

            newQuestionDescription = new TextView(this);
            newQuestionDescription.setText(question.getQuestion());
            newQuestionDescription.setTextColor(getResources().getColor(R.color.white));
            newQuestionDescription.setTextSize(18);

            questionDescLayout.addView(newQuestionDescription);
            newQuestionLinearLayout.addView(questionDescLayout);

            newQuestionRadioGroup = new RadioGroup(this);
            newQuestionRadioGroup.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            newQuestionRadioGroup.setOrientation(LinearLayout.VERTICAL);
            newQuestionRadioGroup.setVisibility(View.VISIBLE);

            newAnswerRadioButton = new RadioButton(this);
            newAnswerRadioButton.setText(question.getAnswer1());
            newAnswerRadioButton.setTextColor(getResources().getColor(R.color.white));

            newAnswerRadioButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            newAnswerRadioButton.setVisibility(View.VISIBLE);
            newQuestionRadioGroup.addView(newAnswerRadioButton);

            newAnswerRadioButton = new RadioButton(this);
            newAnswerRadioButton.setText(question.getAnswer2());
            newAnswerRadioButton.setTextColor(getResources().getColor(R.color.white));

            newAnswerRadioButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            newAnswerRadioButton.setVisibility(View.VISIBLE);
            newQuestionRadioGroup.addView(newAnswerRadioButton);

            if(question.getAnswer3().length() > 0){
                newAnswerRadioButton = new RadioButton(this);
                newAnswerRadioButton.setText(question.getAnswer3());
                newAnswerRadioButton.setTextColor(getResources().getColor(R.color.white));

                newAnswerRadioButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                newAnswerRadioButton.setVisibility(View.VISIBLE);
                newQuestionRadioGroup.addView(newAnswerRadioButton);

                if(question.getAnswer4().length() > 0) {
                    newAnswerRadioButton = new RadioButton(this);
                    newAnswerRadioButton.setText(question.getAnswer4());
                    newAnswerRadioButton.setTextColor(getResources().getColor(R.color.white));

                    newAnswerRadioButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    newAnswerRadioButton.setVisibility(View.VISIBLE);
                    newQuestionRadioGroup.addView(newAnswerRadioButton);

                    if (question.getAnswer5().length() > 0) {
                        newAnswerRadioButton = new RadioButton(this);
                        newAnswerRadioButton.setText(question.getAnswer5());
                        newAnswerRadioButton.setTextColor(getResources().getColor(R.color.white));

                        newAnswerRadioButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        newAnswerRadioButton.setVisibility(View.VISIBLE);
                        newQuestionRadioGroup.addView(newAnswerRadioButton);
                    }
                }
            }
            newQuestionLinearLayout.addView(newQuestionRadioGroup);

            newQuestionDescription = new TextView(this);
            newQuestionDescription.setText("Answer: " + question.getRightAnswer());
            newQuestionDescription.setTextColor(getResources().getColor(R.color.white));
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,0,10,0);
            newQuestionDescription.setLayoutParams(params);
            newQuestionLinearLayout.addView(newQuestionDescription);
            newQuestionLinearLayout.setTag(question.getQuestionID());
            newQuestionLinearLayout.setLongClickable(true);

            newQuestionLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    updateQuestionADOpener(Integer.parseInt(view.getTag().toString()), db);

                    return true;
                }
            });

            questionsRecyclerView.addView(newQuestionLinearLayout);

        }
    }

    public void updateQuestionADOpener(int id, DatabaseHandler db){
        AlertDialog.Builder updateQuestionADBuilder = new AlertDialog.Builder(listQuestionsContext, R.style.AlertDialogStyle);
        View updateQuestionADLayout = getLayoutInflater().inflate(R.layout.activity_add_questions, null);
        updateQuestionADBuilder.setView(updateQuestionADLayout);
        AlertDialog updateQuestionAD = updateQuestionADBuilder.create();


        EditText questionText, answer1, answer2, answer3, answer4, answer5, rightAnswer;
        Button updateButton;

        questionText = (EditText) updateQuestionADLayout.findViewById(R.id.addQuestionQuestionET);
        answer1 = (EditText) updateQuestionADLayout.findViewById(R.id.addQuestionAnswer2ET);
        answer2 = (EditText) updateQuestionADLayout.findViewById(R.id.addQuestionAnswer1ET);
        answer3 = (EditText) updateQuestionADLayout.findViewById(R.id.addQuestionAnswer3ET);
        answer4 = (EditText) updateQuestionADLayout.findViewById(R.id.addQuestionAnswer4ET);
        answer5 = (EditText) updateQuestionADLayout.findViewById(R.id.addQuestionAnswer5ET);
        rightAnswer = (EditText) updateQuestionADLayout.findViewById(R.id.addQuestionCorrectAnswer);
        updateButton = (Button) updateQuestionADLayout.findViewById(R.id.addQuestionsAddQuestionButton);

        Question question = db.getQuestion(id);

        questionText.setText(question.getQuestion());
        answer1.setText(question.getAnswer1());
        answer2.setText(question.getAnswer2());
        answer3.setText(question.getAnswer3());
        answer4.setText(question.getAnswer4());
        answer5.setText(question.getAnswer5());
        rightAnswer.setText(question.getRightAnswer());
        updateButton.setText("UPDATE QUESTION");

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question updatedQ = new Question(questionText.getText().toString(), answer1.getText().toString(), answer2.getText().toString(),
                        answer3.getText().toString(), answer4.getText().toString(), answer5.getText().toString(), rightAnswer.getText().toString());

                startActivity(getIntent());
                finish();
                overridePendingTransition(0, 0);
                updatedQ.setQuestionID(id);
                db.updateQuestion(updatedQ);
            }
        });

        updateQuestionAD.show();
    }

    public LinearLayout getQuestionsRecyclerView() {
        return questionsRecyclerView;
    }

    public void setQuestionsRecyclerView(LinearLayout questionsRecyclerView) {
        this.questionsRecyclerView = questionsRecyclerView;
    }
}