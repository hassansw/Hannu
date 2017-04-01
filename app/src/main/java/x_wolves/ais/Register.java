package x_wolves.ais;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;
import x_wolves.ais.Models.DataFamily;

public class Register extends AppCompatActivity {

    //For Animation Effects
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.cv_add)
    CardView cvAdd;

    //Initialize User Data Fields
    EditText etName;
    EditText etEmail;
    EditText etContact;
    EditText etPass;
    EditText etConfPass;
    EditText etAddress;
    EditText etSubsId;
    EditText etNumFamily;

    //Variables
    String urlData;
    String subsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator2);
        pulsator.start();
        ButterKnife.inject(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });

        //Fetching All User information
        etName = (EditText) findViewById(R.id.et_username);
        etEmail = (EditText) findViewById(R.id.et_email);
        etContact = (EditText) findViewById(R.id.et_contact);
        etPass = (EditText) findViewById(R.id.et_password);
        etConfPass = (EditText) findViewById(R.id.et_repeatpassword);
        etAddress = (EditText) findViewById(R.id.et_address);
        etSubsId = (EditText) findViewById(R.id.et_subsId);
        etNumFamily = (EditText) findViewById(R.id.et_numFamily);


        //Preparing the request URL
        urlData = "http://mmssatc.pk/main/test/sign_up.php?name="+etName.getText().toString()+"&email="+etEmail.getText().toString()+"&pass="+etPass.getText().toString()+"&contact="+etContact.getText().toString()+"&subsId="+etSubsId.getText().toString()+"$address="+etAddress.getText().toString()+"&numFam="+etNumFamily.getText().toString();
        subsID = etSubsId.getText().toString();

        //EventListener for the SignUp button
        Button btnSignUp = (Button) findViewById(R.id.bt_register);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valResult = validateUserData(); // for validating user data such as passwords
                if (valResult.equals("Success")){
                    signUp();
                }
            }
        });
    }

    protected String validateUserData() {
        if ( etPass.getText().toString() != etConfPass.getText().toString() ) {
            Toast.makeText( Register.this, "Passwords do not match, try again", Toast.LENGTH_SHORT).show();
            return "PassNotMatch";
        } else if ( etPass.getText().toString().length() < 8) {
            Toast.makeText( Register.this, "The password length should be atleast 8 characters", Toast.LENGTH_SHORT).show();
            return "ShortLength";
        } else {
            return "Success";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {}

            @Override
            public void onTransitionPause(Transition transition) {}

            @Override
            public void onTransitionResume(Transition transition) {}


        });
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                Register.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    protected void signUp() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(urlData).build();

                try {
                    Response response = client.newCall(request).execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        jsonObject.getString("fam_name");
                        if (jsonObject.getString("status").equals("success")) {
                            Toast.makeText( Register.this, "Account has been created successfully", Toast.LENGTH_SHORT ).show();
                        } else if ( jsonObject.getString("status").equals("error") ) {
                            Toast.makeText(Register.this, "Account was not created, please contact the provider", Toast.LENGTH_SHORT).show();
                        } else if ( jsonObject.getString("status").equals("exists") ){
                            Toast.makeText(Register.this, "The account with the id :"+ subsID +", Already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch (IOException | JSONException e) {
                    Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        };

        task.execute();
    }
}