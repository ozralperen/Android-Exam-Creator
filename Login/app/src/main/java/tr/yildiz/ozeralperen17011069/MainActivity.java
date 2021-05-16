package tr.yildiz.ozeralperen17011069;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText emailET, passwordET;
    private Button signupButton;
    private static Button loginButton;
    private ArrayList<String[]> userList = new ArrayList<String[]>();
    private MainActivity instance;
    private int retryCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);

        setEmailET((EditText)findViewById(R.id.usermailET));
        setPasswordET((EditText)findViewById(R.id.userPasswordET));
        setLoginButton((Button)findViewById(R.id.loginButton));
        setSignupButton((Button)findViewById(R.id.singupButton));

        setInstance(this);

        retryCount = 0;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userIndex;
                if(getEmailET().getText().toString().trim().equals("") || getPasswordET().getText().toString().trim().equals(""))
                    Toast.makeText(getInstance(), "Mail adresi ve şifre giriniz", Toast.LENGTH_SHORT).show();
                else{
                    int userid;
                    userid = db.login(getEmailET().getText().toString(), getPasswordET().getText().toString());
                    if(userid == -1) {
                        Toast.makeText(getInstance(), "Yanlış mail adresi veya şifre", Toast.LENGTH_SHORT).show();
                        retryCount++;
                        if (retryCount == 3) {
                            loginButton.setAlpha(.5f);
                            loginButton.setClickable(false);
                            Log.d("step", "buton ayarları değişti");
                            Intent intentSignUp = new Intent(getApplicationContext(), SignUpScreen.class);
                            Log.d("step", "intent hazır");
                            startActivity(intentSignUp);
                            Log.d("step", "register ekranı");
                        }
                    }
                    else {
                        Log.d("mainde id", String.valueOf(userid));
                        Toast.makeText(getInstance(), "Başarıyla giriş yapıldı.", Toast.LENGTH_SHORT).show();
                        Intent intentLogin = new Intent(getApplicationContext(), MainScreen.class);
                        intentLogin.putExtra("UserID", userid);
                        startActivity(intentLogin);
                    }
                }

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignUp = new Intent(getApplicationContext(), SignUpScreen.class);
                startActivity(intentSignUp);
            }
        });
    }

    public static void makeLoginButtonClickable(){
        loginButton.setClickable(true);
        loginButton.setAlpha(1f);
    }

    public EditText getEmailET() {
        return emailET;
    }

    public void setEmailET(EditText emailET) {
        this.emailET = emailET;
    }

    public EditText getPasswordET() {
        return passwordET;
    }

    public void setPasswordET(EditText passwordET) {
        this.passwordET = passwordET;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(Button loginButton) {
        this.loginButton = loginButton;
    }

    public Button getSignupButton() {
        return signupButton;
    }

    public void setSignupButton(Button signupButton) {
        this.signupButton = signupButton;
    }

    public MainActivity getInstance() {
        return instance;
    }

    public void setInstance(MainActivity instance) {
        this.instance = instance;
    }
}