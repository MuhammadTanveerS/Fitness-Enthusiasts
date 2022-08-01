package com.example.fitnessenthusiasts.activities.Common.Workout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.Settings;
import com.example.fitnessenthusiasts.activities.HelperClasses.Exercises;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.ExercisesModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class ExerciseTimer extends AppCompatActivity {

    ArrayList<ExercisesModel> exercisesModel;
    DatabaseReference databaseReference;
    String workOutName;
    LottieAnimationView exAnim;
    CountDownTimer timer,timer2,timer3;
    TextView exName,restTV;
    CircularProgressIndicator progressIndicator;
    TextView progress;
    int count=0;
    long t = 10000,rT = 10000;
    TextToSpeech t1;
    float timeP=0;
    MediaPlayer mp,whistle;
    boolean ctFlag=true, exFlag=true;
    MaterialButton toggle;
    LinearLayout restLayout,mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_exercise_timer);

        mainLayout = findViewById(R.id.mainLayout);
        restLayout = findViewById(R.id.restLayout);
        mainLayout.setVisibility(View.VISIBLE);
        restLayout.setVisibility(View.GONE);

        exercisesModel=new ArrayList<>();
        exName = findViewById(R.id.excName);
        exAnim = findViewById(R.id.excAnim);
        progress = findViewById(R.id.progress);
        progressIndicator = findViewById(R.id.workoutProgressIndicator);
        toggle = findViewById(R.id.toggleP);

        mp = MediaPlayer.create(this,R.raw.countdown);
        whistle = MediaPlayer.create(this,R.raw.whistle);

        restTV = findViewById(R.id.restTV);

        fetchExercises();

    }


    private void fetchExercises(){
        workOutName = (String) getIntent().getExtras().get("Workout");
        databaseReference = FirebaseDatabase.getInstance(getString(R.string.db_instance)).getReference("Workouts").child(workOutName).child("Exercises");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    for(DataSnapshot snapshot1:snapshot.getChildren()){

                        ExercisesModel data = snapshot1.getValue(ExercisesModel.class);
                        exercisesModel.add(data);

                    }
                }
                update(count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    private void update(int i) {

        mainLayout.setVisibility(View.VISIBLE);
        restLayout.setVisibility(View.GONE);

        if(exFlag){
            String str = exercisesModel.get(i).getTime();
            String substring = str.substring(Math.max(str.length() - 2, 0));
            int a = Integer.parseInt(substring);
            exFlag=false;
            exName.setText(exercisesModel.get(count).getName());
            String toSpeak = "Ready to go the Next" + a +" SECONDS . ."+exercisesModel.get(count).getName();
            setUpProgressBar(toSpeak);
        }else{

            String str = exercisesModel.get(i).getTime();
            String substring = str.substring(Math.max(str.length() - 2, 0));

            int a = Integer.parseInt(substring);
            t = a*1000;
            progress.setTextSize(36);

            if(count<exercisesModel.size()){
                t1.speak(exercisesModel.get(count).getName(),TextToSpeech.QUEUE_FLUSH,null);
                exAnim.setAnimation(Exercises.ex(exercisesModel.get(i).getName()));
                exName.setText(exercisesModel.get(count).getName());
                exAnim.playAnimation();

                timer = new CountDownTimer(t,1000) {
                    @Override
                    public void onTick(long l) {
                        t=l;
                        setUpExc((int)t/1000);
                        if(t<=3000){
                            mp.start();
                        }
                    }
                    @Override
                    public void onFinish() {
                        if(count==(exercisesModel.size()-1)){
//                            startActivity(new Intent(ExerciseTimer.this, Settings.class));
                            Toast.makeText(ExerciseTimer.this, "WORKOUT COMPLETED", Toast.LENGTH_SHORT).show();
                            //USE THIS LATER
                        }else{
                            count+=1;
                            rest();
                        }

                    }
                }.start();
            }
        }

    }

    private void setUpExc(int time){
        progressIndicator.setVisibility(View.INVISIBLE);
        if(time >9){
            progress.setText("00:"+time);
        }else{
            progress.setText("00:0"+time);
        }
    }

    private void setUpProgressBar(String toSpeak){

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            long pTime = 16000;

            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                    t1.setSpeechRate(0.7f);
                    t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);

                    timer2 = new CountDownTimer(pTime,1000) {
                        @Override
                        public void onTick(long l) {
                            t=l;
                            progress.setText(t/1000+"");
                            if(ctFlag && t<=4000){
                                ctFlag=false;
                                mp.start();
                            }

                            timeP+=6.3;
//                            Log.e("",timeP+"");
                            progressIndicator.setProgress((int)timeP);
                        }

                        @Override
                        public void onFinish() {
                            exFlag=false;
                            whistle.start();
                            TextView rTV = findViewById(R.id.readyTV);
                            rTV.setVisibility(View.GONE);
                            toggle.setVisibility(View.VISIBLE);
                            update(0);

                        }
                    }.start();
                }
            }

        });
    }


    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mp.stop();
        whistle.stop();
    }


    public void toggleP(View view) {
        toggle.setIcon(getResources().getDrawable(R.drawable.ic_play));
        toggle.setText("Resume");


    }

    private void rest(){

        mainLayout.setVisibility(View.GONE);
        restLayout.setVisibility(View.VISIBLE);
        setUpRest();

        timer3 = new CountDownTimer(rT,1000) {
            @Override
            public void onTick(long l) {
                rT-=1000;
                if(rT>9000){
                    restTV.setText("00:"+rT/1000);
                }else{
                    restTV.setText("00:0"+rT/1000);
                }

            }

            @Override
            public void onFinish() {
                rT=10000;
                update(count);
            }
        }.start();
    }

    private void setUpRest() {
        LottieAnimationView restAnim = findViewById(R.id.restAnim);
        TextView restExcName = findViewById(R.id.restExcName);
        TextView restExcTime = findViewById(R.id.restExcTime);

        restAnim.setAnimation(Exercises.ex(exercisesModel.get(count).getName()));
        restAnim.playAnimation();
        restExcName.setText(exercisesModel.get(count).getName());
        restExcTime.setText(exercisesModel.get(count).getTime());
    }

    public void addTwentysec(View view) {
        rT+=20000;
        timer3.cancel();
        rest();
    }

    public void skipRest(View view) {
        timer3.cancel();
        rT=10000;
        update(count);
    }
}