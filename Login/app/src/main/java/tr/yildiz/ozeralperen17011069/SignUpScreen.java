package tr.yildiz.ozeralperen17011069;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpScreen extends AppCompatActivity {

    private EditText nameET, surnameET, emailET, phoneET, bdayET, passwordET, passwordRepeatET;
    private Button signUpButton, cancelButton, savePPButton;
    private ImageButton profilePictureIB;
    private DatePickerDialog.OnDateSetListener bDayDateSetListener;
    private Context signUpContext;
    private RadioGroup profilePicturesRG;
    private Integer profilePictureID;

    private static final Pattern mailPattern = Pattern.compile("^(.+)@(.+)$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        signUpContext = this.getApplicationContext();

        DatabaseHandler db = new DatabaseHandler(this);

        setProfilePictureIB(findViewById(R.id.SignUpProfilePictureIB));

        setNameET(findViewById(R.id.SignUpNameET));
        setSurnameET(findViewById(R.id.SignUpSurnameET));
        setEmailET(findViewById(R.id.SignUpMailET));
        setPhoneET(findViewById(R.id.SignUpPhoneET));
        setBdayET(findViewById(R.id.SignUpBDayET));
        setPasswordET(findViewById(R.id.SignUpPasswordET));
        setPasswordRepeatET(findViewById(R.id.SignUpPasswordRepeatET));

        setSignUpButton(findViewById(R.id.SignUpSignUpButton));

        profilePictureIB.setImageResource(R.drawable.male1);

        AlertDialog.Builder profilePictureBuilder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        View profilePictureAlertDialog = getLayoutInflater().inflate(R.layout.profile_picture_picker_ad, null);
        profilePictureBuilder.setView(profilePictureAlertDialog);
        AlertDialog profilePictureAD = profilePictureBuilder.create();
        setSavePPButton(profilePictureAlertDialog.findViewById(R.id.SelectProfilePictureButton));
        setCancelButton(profilePictureAlertDialog.findViewById(R.id.CancelProfilePictureButton));
        setProfilePicturesRG(profilePictureAlertDialog.findViewById(R.id.ProfilePictureOptionsRB));

        profilePictureIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilePictureAD.show();
            }
        });

        savePPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer selectedProfilePicture = getProfilePicturesRG().getCheckedRadioButtonId();
                if(selectedProfilePicture != null){
                    if(selectedProfilePicture == R.id.ProfilePictureAlternative1)
                        profilePictureIB.setImageResource(R.drawable.female1);
                    else if(selectedProfilePicture == R.id.ProfilePictureAlternative2)
                        profilePictureIB.setImageResource(R.drawable.female2);
                    else if(selectedProfilePicture == R.id.ProfilePictureAlternative3)
                        profilePictureIB.setImageResource(R.drawable.male1);
                    else
                        profilePictureIB.setImageResource(R.drawable.male2);
                    setProfilePictureID(selectedProfilePicture);
                    profilePictureAD.cancel();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilePictureAD.cancel();
            }
        });

        bdayET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(bdayET.isFocused()){
                    bdayET.clearFocus();
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(SignUpScreen.this, R.style.Theme_AppCompat_Light_Dialog_MinWidth, bDayDateSetListener, year,month,day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                    dialog.show();
                }
            }
        });

        bDayDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                bdayET.setText(i2 + "/" + (i1+1) + "/" + i);
            }
        };

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date bday, minDate, maxDate;
                maxDate = new Date();
                bday = null;
                minDate = null;
                Boolean userExists = db.userMailExists(getEmailET().getText().toString());
                try {
                    bday = new SimpleDateFormat("dd/MM/yyyy").parse(getBdayET().getText().toString());
                    minDate = new SimpleDateFormat("dd/MM/yyyy").parse("1/1/1921");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if((getNameET().getText().toString().length() < 2)){
                    Toast.makeText(signUpContext, "Name should be longer than one character.", Toast.LENGTH_LONG).show();
                }
                else if((getSurnameET().getText().toString().length() < 2)){
                    Toast.makeText(signUpContext, "Surname should be longer than one character.", Toast.LENGTH_LONG).show();
                }
                else if (userExists){
                    Toast.makeText(signUpContext, "User with this mail already exists.", Toast.LENGTH_LONG).show();
                }
                else if (!mailPattern.matcher(getEmailET().getText().toString()).find()){
                    Toast.makeText(signUpContext, "Please enter an email with a valid format.", Toast.LENGTH_LONG).show();
                }
                else if (!getPhoneET().getText().toString().matches("[0-9]+") || getPhoneET().getText().toString().length() != 10){
                    Toast.makeText(signUpContext, "Please enter a phone number that contains only numeric characters and total of 10 numbers.", Toast.LENGTH_LONG).show();
                }
                else if (bday == null || minDate == null || bday.before(minDate) || bday.after(maxDate)){
                    Toast.makeText(signUpContext, "Please enter a valid birth date.", Toast.LENGTH_LONG).show();
                }
                else if(passwordET.getText().toString().length() < 4){
                    Toast.makeText(signUpContext, "Please enter a password with at least 4 characters", Toast.LENGTH_LONG).show();
                }
                else if(!passwordET.getText().toString().equals(passwordRepeatET.getText().toString())){
                    Toast.makeText(signUpContext, "Please make sure passwords match.", Toast.LENGTH_LONG).show();
                }
                else{
                    Log.d("step", "saving user");
                    Log.d("name", getNameET().getText().toString());
                    Log.d("surname", getSurnameET().getText().toString());
                    Log.d("mail", getEmailET().getText().toString());
                    Log.d("phone", getPhoneET().getText().toString());
                    Log.d("bday", getBdayET().getText().toString());
                    Log.d("pass", getPasswordET().getText().toString());
                    db.addUser(new User(getNameET().getText().toString(), getSurnameET().getText().toString(), getEmailET().getText().toString(),
                            getPhoneET().getText().toString(), getBdayET().getText().toString(), getPasswordET().getText().toString()));
                    MainActivity.makeLoginButtonClickable();
                    finish();
                }
            }
        });
    }

    public EditText getNameET() {
        return nameET;
    }

    public void setNameET(EditText nameET) {
        this.nameET = nameET;
    }

    public EditText getSurnameET() {
        return surnameET;
    }

    public void setSurnameET(EditText surnameET) {
        this.surnameET = surnameET;
    }

    public EditText getEmailET() {
        return emailET;
    }

    public void setEmailET(EditText emailET) {
        this.emailET = emailET;
    }

    public EditText getPhoneET() {
        return phoneET;
    }

    public void setPhoneET(EditText phoneET) {
        this.phoneET = phoneET;
    }

    public EditText getBdayET() {
        return bdayET;
    }

    public void setBdayET(EditText bdayET) {
        this.bdayET = bdayET;
    }

    public EditText getPasswordET() {
        return passwordET;
    }

    public void setPasswordET(EditText passwordET) {
        this.passwordET = passwordET;
    }

    public EditText getPasswordRepeatET() {
        return passwordRepeatET;
    }

    public void setPasswordRepeatET(EditText passwordRepeatET) {
        this.passwordRepeatET = passwordRepeatET;
    }

    public Button getSignUpButton() {
        return signUpButton;
    }

    public void setSignUpButton(Button signUpButton) {
        this.signUpButton = signUpButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    public Button getSavePPButton() {
        return savePPButton;
    }

    public void setSavePPButton(Button savePPButton) {
        this.savePPButton = savePPButton;
    }

    public RadioGroup getProfilePicturesRG() {
        return profilePicturesRG;
    }

    public void setProfilePicturesRG(RadioGroup profilePicturesRG) {
        this.profilePicturesRG = profilePicturesRG;
    }

    public ImageButton getProfilePictureIB() {
        return profilePictureIB;
    }

    public void setProfilePictureIB(ImageButton profilePictureIB) {
        this.profilePictureIB = profilePictureIB;
    }

    public Integer getProfilePictureID() {
        return profilePictureID;
    }

    public void setProfilePictureID(Integer profilePictureID) {
        this.profilePictureID = profilePictureID;
    }
}