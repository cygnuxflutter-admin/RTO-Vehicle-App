package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_DataBase.Task_DBHandler;
import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_MemsUtil;
import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_QueConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;


public class Task_QuizActivity extends AppCompatActivity {
    private static final float BYTES_PER_PX = 4.0f;
    private static final String FORMAT = "%02d";
    Button btnNext;
    int curr;
    int curr1;
    Task_QueConstructor currentquestion;
    int[] images;
    ImageView ivImage;
    ArrayList<String> myAnsList;
    ArrayList<Task_QueConstructor> quesList;
    RadioButton radioButton;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioGroup rgChoice;
    String str_language;
    TextView tvNegative;
    TextView tvPositive;
    TextView tvQuestion;
    TextView tvTimer;
    ArrayList<String> Correctans = new ArrayList<>();
    ArrayList<String> Photo = new ArrayList<>();
    ArrayList<String> QuestionNumbers = new ArrayList<>();
    int Score = 0;
    int answeredquestno = 0;
    final ArrayList<Integer> array = new ArrayList<>();
    int f16k = 0;
    ArrayList<Integer> image = new ArrayList<>();

    int[] images_english = {0, 1, 2, 3, 4, 5, R.drawable.sign_6, 7, 8, 9, 10, 11, R.drawable.sign_12, 13, 14, 15, 16, 17, 18, 19, 20, 21, R.drawable.sign_22, R.drawable.sign_23, 24, 25, R.drawable.sign_26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, R.drawable.sign_74, R.drawable.sign_75, 76, 77, 78, R.drawable.sign_79, 80, 81, 82, 83, 84, 85, R.drawable.sign_86, 87, 88, 89, R.drawable.sign_90, 91, R.drawable.sign_92, 93, 94, 95, 96, 97, 98, 99, 100, R.drawable.sign_31, 102, R.drawable.sign_33, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, R.drawable.sign_119, 190, 191, R.drawable.sign_122, 193, R.drawable.sign_124, 195, 196, 197, 198, 199, R.drawable.sign_130};
    int negative = 0;
    String nophoto = "nophoto";
    ArrayList<Integer> numbers = new ArrayList<>();
    String photo = "photo";
    int positive = 0;
    int questionId = 0;
    final CounterClass timer = new CounterClass(31000, 1000);

    @Override
    public void onCreate(Bundle bundle) {
        int c = 0;
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_quizes);
        ((ImageView) findViewById(R.id.iv_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task_QuizActivity.this.onBackPressed();
            }
        });
        this.str_language = getIntent().getStringExtra("language");
        Log.d("R_Quiz", "Language is = " + this.str_language);
        String str = this.str_language;
        str.hashCode();
        int hashCode = str.hashCode();

        if (str.equals("english")) {
            c = 0;
        }
        //  c = CharCompanionObject.MAX_VALUE;
        else if (str.equals("gujarati")) {
            c = 1;
        } else if (str.equals("hindi")) {
            c = 2;
        }


        if (c == 0) {
            this.images = this.images_english;
        } else if (c == 1) {
            this.images = this.images_english;
        } else if (c == 2) {
            this.images = this.images_english;
        }
        this.tvTimer = (TextView) findViewById(R.id.tvTimer);
        this.tvPositive = (TextView) findViewById(R.id.tvPositive);
        this.tvNegative = (TextView) findViewById(R.id.tvNegative);
        this.ivImage = (ImageView) findViewById(R.id.ivImage);
        this.tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        this.radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        this.radioButton = (RadioButton) findViewById(R.id.radioButton);
        this.radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        this.btnNext = (Button) findViewById(R.id.btnNext);
        if (this.str_language.equals("gujarati")) {
            this.btnNext.setText("આગળ");
        } else if (this.str_language.equals("hindi")) {
            this.btnNext.setText("आगे");
        } else if (this.str_language.equals("english")) {
            this.btnNext.setText("Next");
        }
        this.myAnsList = new ArrayList<>();
        ArrayList<Task_QueConstructor> allQuestions2 = new Task_DBHandler(this).getAllQuestions2();
        this.quesList = allQuestions2;
        Collections.shuffle(allQuestions2);
        this.currentquestion = this.quesList.get(this.questionId);
        setQuestionsView();
        curr();
        this.timer.start();
        ButtonEnable();
        this.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task_QuizActivity.this.timer.cancel();
                Task_QuizActivity.this.tvTimer.setText("");
                Task_QuizActivity m_rtoQuizes = Task_QuizActivity.this;
                m_rtoQuizes.rgChoice = (RadioGroup) m_rtoQuizes.findViewById(R.id.rgChoice);
                Task_QuizActivity m_rtoQuizes2 = Task_QuizActivity.this;
                RadioButton radioButton = (RadioButton) m_rtoQuizes2.findViewById(m_rtoQuizes2.rgChoice.getCheckedRadioButtonId());
                if (radioButton != null) {
                    Task_QuizActivity.this.myAnsList.add("" + ((Object) radioButton.getText()));
                    if (Task_QuizActivity.this.currentquestion.getAnswer().equals(radioButton.getText())) {
                        Task_QuizActivity.this.Score++;
                        Task_QuizActivity.this.positive++;
                        Task_QuizActivity.this.tvPositive.setText("" + Task_QuizActivity.this.positive);
                    } else {
                        Task_QuizActivity.this.negative++;
                        Task_QuizActivity.this.tvNegative.setText("" + Task_QuizActivity.this.negative);
                    }
                    if (Task_QuizActivity.this.Score == 9) {
                        Task_QuizActivity.this.timer.cancel();
                        Intent intent = new Intent(Task_QuizActivity.this, Task_ResultActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("score", Task_QuizActivity.this.Score);
                        bundle2.putStringArrayList("questionnumbers", Task_QuizActivity.this.QuestionNumbers);
                        bundle2.putStringArrayList("myanswer", Task_QuizActivity.this.myAnsList);
                        bundle2.putStringArrayList("Correct", Task_QuizActivity.this.Correctans);
                        bundle2.putIntegerArrayList("Image", Task_QuizActivity.this.image);
                        bundle2.putStringArrayList("photo", Task_QuizActivity.this.Photo);
                        bundle2.putIntegerArrayList("Numbers", Task_QuizActivity.this.numbers);
                        bundle2.putString("language", Task_QuizActivity.this.str_language);
                        intent.putExtras(bundle2);
                        Task_QuizActivity.this.startActivity(intent);
                        Task_QuizActivity.this.finish();
                    } else if (Task_QuizActivity.this.array.size() != 50) {
                        Task_QuizActivity m_rtoQuizes3 = Task_QuizActivity.this;
                        m_rtoQuizes3.currentquestion = m_rtoQuizes3.quesList.get(Task_QuizActivity.this.questionId);
                        Task_QuizActivity.this.setQuestionsView();
                        Task_QuizActivity.this.curr();
                        Task_QuizActivity.this.timer.start();
                    } else {
                        Task_QuizActivity.this.timer.cancel();
                        Intent intent2 = new Intent(Task_QuizActivity.this, Task_ResultActivity.class);
                        Bundle bundle3 = new Bundle();
                        bundle3.putStringArrayList("questionnumbers", Task_QuizActivity.this.QuestionNumbers);
                        bundle3.putStringArrayList("myanswer", Task_QuizActivity.this.myAnsList);
                        bundle3.putStringArrayList("Correct", Task_QuizActivity.this.Correctans);
                        bundle3.putIntegerArrayList("Image", Task_QuizActivity.this.image);
                        bundle3.putStringArrayList("photo", Task_QuizActivity.this.Photo);
                        bundle3.putIntegerArrayList("Numbers", Task_QuizActivity.this.numbers);
                        bundle3.putInt("score", Task_QuizActivity.this.Score);
                        bundle3.putString("language", Task_QuizActivity.this.str_language);
                        intent2.putExtras(bundle3);
                        Task_QuizActivity.this.startActivity(intent2);
                        Task_QuizActivity.this.finish();
                    }
                    Task_QuizActivity.this.rgChoice.clearCheck();
                } else if (radioButton == null) {
                    Task_QuizActivity.this.timer.cancel();
                    Task_QuizActivity.this.tvTimer.setText("");
                    if (Task_QuizActivity.this.str_language.equals("gujarati")) {
                        Task_QuizActivity.this.myAnsList.add("તમે આ પ્રશ્નનો જવાબ આપ્યો નથી");
                    } else if (Task_QuizActivity.this.str_language.equals("hindi")) {
                        Task_QuizActivity.this.myAnsList.add("आपने इस प्रश्न का उत्तर नहीं दिया है");
                    } else if (Task_QuizActivity.this.str_language.equals("english")) {
                        Task_QuizActivity.this.myAnsList.add("You have not answered this question");
                    }
                    Task_QuizActivity.this.negative++;
                    Task_QuizActivity.this.tvNegative.setText("" + Task_QuizActivity.this.negative);
                    if (Task_QuizActivity.this.Score == 9) {
                        Task_QuizActivity.this.timer.cancel();
                        Intent intent3 = new Intent(Task_QuizActivity.this, Task_ResultActivity.class);
                        Bundle bundle4 = new Bundle();
                        bundle4.putInt("score", Task_QuizActivity.this.Score);
                        bundle4.putStringArrayList("questionnumbers", Task_QuizActivity.this.QuestionNumbers);
                        bundle4.putStringArrayList("myanswer", Task_QuizActivity.this.myAnsList);
                        bundle4.putStringArrayList("Correct", Task_QuizActivity.this.Correctans);
                        bundle4.putIntegerArrayList("Image", Task_QuizActivity.this.image);
                        bundle4.putIntegerArrayList("Numbers", Task_QuizActivity.this.numbers);
                        bundle4.putStringArrayList("photo", Task_QuizActivity.this.Photo);
                        bundle4.putString("language", Task_QuizActivity.this.str_language);
                        intent3.putExtras(bundle4);
                        Task_QuizActivity.this.startActivity(intent3);
                        Task_QuizActivity.this.finish();
                    } else if (Task_QuizActivity.this.array.size() != 50) {
                        Task_QuizActivity m_rtoQuizes4 = Task_QuizActivity.this;
                        m_rtoQuizes4.currentquestion = m_rtoQuizes4.quesList.get(Task_QuizActivity.this.questionId);
                        Task_QuizActivity.this.setQuestionsView();
                        Task_QuizActivity.this.curr();
                        Task_QuizActivity.this.timer.start();
                    } else {
                        Task_QuizActivity.this.timer.cancel();
                        Intent intent4 = new Intent(Task_QuizActivity.this, Task_ResultActivity.class);
                        Bundle bundle5 = new Bundle();
                        bundle5.putStringArrayList("questionnumbers", Task_QuizActivity.this.QuestionNumbers);
                        bundle5.putStringArrayList("myanswer", Task_QuizActivity.this.myAnsList);
                        bundle5.putStringArrayList("Correct", Task_QuizActivity.this.Correctans);
                        bundle5.putIntegerArrayList("Image", Task_QuizActivity.this.image);
                        bundle5.putStringArrayList("photo", Task_QuizActivity.this.Photo);
                        bundle5.putInt("score", Task_QuizActivity.this.Score);
                        bundle5.putIntegerArrayList("Numbers", Task_QuizActivity.this.numbers);
                        bundle5.putString("language", Task_QuizActivity.this.str_language);
                        intent4.putExtras(bundle5);
                        Task_QuizActivity.this.startActivity(intent4);
                        Task_QuizActivity.this.finish();
                    }
                }
                Task_QuizActivity.this.rgChoice.clearCheck();
            }
        });
    }


    public class CounterClass extends CountDownTimer {
        public CounterClass(long j, long j2) {
            super(j, j2);
        }

        @Override
        public void onTick(long j) {
            TextView textView = Task_QuizActivity.this.tvTimer;
            textView.setText("" + String.format("%02d", Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j)))));
        }

        @Override
        public void onFinish() {
            if (Task_QuizActivity.this.array.size() != 50) {
                final Dialog dialog = new Dialog(Task_QuizActivity.this);
                dialog.requestWindowFeature(1);
                dialog.setContentView(R.layout.task_dialog_exam_time_over);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
                TextView textView = (TextView) dialog.findViewById(R.id.tv_header);
                TextView textView2 = (TextView) dialog.findViewById(R.id.tv_sub_text);
                Button button = (Button) dialog.findViewById(R.id.btn_dia_next);
                if (Task_QuizActivity.this.str_language.equals("gujarati")) {
                    textView.setText("સમય પુરો!!!");
                    textView2.setText(Task_QuizActivity.this.getResources().getString(R.string.str_30_sec_over_txt_gujarati));
                    button.setText("આગળ");
                } else if (Task_QuizActivity.this.str_language.equals("hindi")) {
                    textView.setText("समय समाप्त!!!");
                    textView2.setText(Task_QuizActivity.this.getResources().getString(R.string.str_30_sec_over_txt_hindi));
                    button.setText("अगला सवाल");
                } else if (Task_QuizActivity.this.str_language.equals("english")) {
                    textView.setText("Time Over!!!");
                    textView2.setText(Task_QuizActivity.this.getResources().getString(R.string.str_30_sec_over_txt_english));
                    button.setText("Next Question");
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str;
                        Task_QuizActivity.this.rgChoice = (RadioGroup) Task_QuizActivity.this.findViewById(R.id.rgChoice);
                        RadioButton radioButton = (RadioButton) Task_QuizActivity.this.findViewById(Task_QuizActivity.this.rgChoice.getCheckedRadioButtonId());
                        if (Task_QuizActivity.this.str_language.equals("gujarati")) {
                            str = "તમે આ પ્રશ્નનો જવાબ આપ્યો નથી";
                        } else if (Task_QuizActivity.this.str_language.equals("hindi")) {
                            str = "आपने इस प्रश्न का उत्तर नहीं दिया है";
                        } else {
                            str = Task_QuizActivity.this.str_language.equals("english") ? "You have not answered this question" : "";
                        }
                        if (radioButton != null) {
                            Task_QuizActivity.this.myAnsList.add(str);
                            Task_QuizActivity.this.tvTimer.setText("");
                            Task_QuizActivity.this.negative++;
                            Task_QuizActivity.this.tvNegative.setText("" + Task_QuizActivity.this.negative);
                            CounterClass.this.start();
                            Task_QuizActivity.this.currentquestion = Task_QuizActivity.this.quesList.get(Task_QuizActivity.this.questionId);
                            Task_QuizActivity.this.curr();
                            Task_QuizActivity.this.setQuestionsView();
                            dialog.cancel();
                            return;
                        }
                        Task_QuizActivity.this.myAnsList.add(str);
                        Task_QuizActivity.this.tvTimer.setText("");
                        Task_QuizActivity.this.negative++;
                        Task_QuizActivity.this.tvNegative.setText("" + Task_QuizActivity.this.negative);
                        CounterClass.this.start();
                        Task_QuizActivity.this.currentquestion = Task_QuizActivity.this.quesList.get(Task_QuizActivity.this.questionId);
                        Task_QuizActivity.this.curr();
                        Task_QuizActivity.this.setQuestionsView();
                        dialog.cancel();
                    }
                });
                Task_QuizActivity.this.rgChoice.clearCheck();
            } else {
                Task_QuizActivity.this.array.size();
                final Dialog dialog2 = new Dialog(Task_QuizActivity.this);
                dialog2.requestWindowFeature(1);
                dialog2.setContentView(R.layout.task_dialog_exam_over);
                dialog2.setCanceledOnTouchOutside(false);
                dialog2.setCancelable(false);
                dialog2.show();
                TextView textView3 = (TextView) dialog2.findViewById(R.id.tv_header);
                TextView textView4 = (TextView) dialog2.findViewById(R.id.tv_sub_text);
                Button button2 = (Button) dialog2.findViewById(R.id.btn_dia_next);
                if (Task_QuizActivity.this.str_language.equals("gujarati")) {
                    textView3.setText("15 પ્રશ્નો સમાપ્ત થાય છે!!!");
                    textView4.setText(Task_QuizActivity.this.getResources().getString(R.string.str_exam_over_txt_gujarati));
                    button2.setText("");
                } else if (Task_QuizActivity.this.str_language.equals("hindi")) {
                    textView3.setText("15 सवाल खत्म हो चुके हो चुके है!!!");
                    textView4.setText(Task_QuizActivity.this.getResources().getString(R.string.str_exam_over_txt_hindi));
                    button2.setText("अगला सवाल");
                } else if (Task_QuizActivity.this.str_language.equals("english")) {
                    textView3.setText("15 questions are Over!!!");
                    textView4.setText(Task_QuizActivity.this.getResources().getString(R.string.str_exam_over_txt_english));
                    button2.setText("Next Question");
                }
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str;
                        Task_QuizActivity.this.rgChoice = (RadioGroup) Task_QuizActivity.this.findViewById(R.id.rgChoice);
                        RadioButton radioButton = (RadioButton) Task_QuizActivity.this.findViewById(Task_QuizActivity.this.rgChoice.getCheckedRadioButtonId());
                        if (Task_QuizActivity.this.str_language.equals("gujarati")) {
                            str = "તમે આ પ્રશ્નનો જવાબ આપ્યો નથી";
                        } else if (Task_QuizActivity.this.str_language.equals("hindi")) {
                            str = "आपने इस प्रश्न का उत्तर नहीं दिया है";
                        } else {
                            str = Task_QuizActivity.this.str_language.equals("english") ? "You have not answered this question" : "";
                        }
                        if (radioButton != null) {
                            Task_QuizActivity.this.timer.cancel();
                            Task_QuizActivity.this.myAnsList.add(str);
                            Intent intent = new Intent(Task_QuizActivity.this, Task_ResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("score", Task_QuizActivity.this.Score);
                            bundle.putStringArrayList("myanswer", Task_QuizActivity.this.myAnsList);
                            bundle.putStringArrayList("questionnumbers", Task_QuizActivity.this.QuestionNumbers);
                            bundle.putStringArrayList("Correct", Task_QuizActivity.this.Correctans);
                            bundle.putIntegerArrayList("Image", Task_QuizActivity.this.image);
                            bundle.putIntegerArrayList("Numbers", Task_QuizActivity.this.numbers);
                            bundle.putStringArrayList("photo", Task_QuizActivity.this.Photo);
                            bundle.putString("language", Task_QuizActivity.this.str_language);
                            intent.putExtras(bundle);
                            Task_QuizActivity.this.startActivity(intent);
                            Task_QuizActivity.this.finish();
                            dialog2.cancel();
                            return;
                        }
                        Task_QuizActivity.this.timer.cancel();
                        Task_QuizActivity.this.myAnsList.add(str);
                        Intent intent2 = new Intent(Task_QuizActivity.this, Task_ResultActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("score", Task_QuizActivity.this.Score);
                        bundle2.putStringArrayList("myanswer", Task_QuizActivity.this.myAnsList);
                        bundle2.putStringArrayList("questionnumbers", Task_QuizActivity.this.QuestionNumbers);
                        bundle2.putStringArrayList("Correct", Task_QuizActivity.this.Correctans);
                        bundle2.putIntegerArrayList("Image", Task_QuizActivity.this.image);
                        bundle2.putIntegerArrayList("Numbers", Task_QuizActivity.this.numbers);
                        bundle2.putStringArrayList("photo", Task_QuizActivity.this.Photo);
                        bundle2.putString("language", Task_QuizActivity.this.str_language);
                        intent2.putExtras(bundle2);
                        Task_QuizActivity.this.startActivity(intent2);
                        Task_QuizActivity.this.finish();
                        dialog2.cancel();
                    }
                });
            }
            Task_QuizActivity.this.rgChoice.clearCheck();
        }
    }

    public void curr() {
        int id = this.currentquestion.getId();
        this.curr = id;
        this.array.add(Integer.valueOf(id));
    }

    public void ButtonEnable() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgChoice);
        this.rgChoice = radioGroup;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup2, int i) {
                if (Task_QuizActivity.this.radioButton.isChecked()) {
                    Task_QuizActivity.this.btnNext.setEnabled(true);
                } else if (Task_QuizActivity.this.radioButton2.isChecked()) {
                    Task_QuizActivity.this.btnNext.setEnabled(true);
                } else if (Task_QuizActivity.this.radioButton3.isChecked()) {
                    Task_QuizActivity.this.btnNext.setEnabled(true);
                }
            }
        });
    }

    public void setQuestionsView() {
        this.radioButton2.setChecked(false);
        this.radioButton.setChecked(false);
        this.radioButton3.setChecked(false);
        this.f16k++;
        this.answeredquestno = this.questionId + 1;
        StringBuilder sb = new StringBuilder();
        if (this.str_language.equals("gujarati")) {
            this.btnNext.setText("આગળ");
        } else if (this.str_language.equals("hindi")) {
            this.btnNext.setText("आगे");
        } else if (this.str_language.equals("english")) {
            this.btnNext.setText("Next");
        }
        sb.append("");
        sb.append(this.f16k);
        sb.append(" ");
        sb.append(this.currentquestion.getQuestion());
        this.tvQuestion.setText(sb.toString());
        this.radioButton3.setText(this.currentquestion.getOption3());
        this.radioButton.setText(this.currentquestion.getOption1());
        this.radioButton2.setText(this.currentquestion.getOption2());
        this.QuestionNumbers.add("" + this.currentquestion.getQuestion());
        this.Correctans.add("" + this.currentquestion.getAnswer());
        this.Photo.add("" + this.currentquestion.getPhoto());
        int id = this.currentquestion.getId();
        this.curr1 = id;
        this.image.add(Integer.valueOf(id));
        this.numbers.add(Integer.valueOf(this.f16k));
        if (this.photo.equals(this.currentquestion.getPhoto())) {
            this.ivImage.setVisibility(View.VISIBLE);
            loadImage();
        } else if (this.nophoto.equals(this.currentquestion.getPhoto())) {
            this.ivImage.setVisibility(View.GONE);
        }
        this.questionId++;
    }

    private void loadImage() {
        if (readBitmapInfo() > Task_MemsUtil.megabytesFree()) {
            subSampleImage(32);
        } else {
            try {
                this.ivImage.setImageResource(images_english[currentquestion.getId()]);
            } catch (Exception e) {
                Log.e("TAG", "loadImage: " + e.getMessage());
                Log.e("TAG", "loadImage: ID" + currentquestion.getId());
            }

        }
    }

    private float readBitmapInfo() {
        Resources resources = getResources();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, this.images[this.currentquestion.getId()], options);
        String str = options.outMimeType;
        return ((options.outWidth * options.outHeight) * BYTES_PER_PX) / 1048576.0f;
    }

    private void subSampleImage(int i) {
        if (i < 1 || i > 32) {
            return;
        }
        Resources resources = getResources();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = i;
        this.ivImage.setImageBitmap(BitmapFactory.decodeResource(resources, this.images[this.currentquestion.getId()], options));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.timer.cancel();
    }
}
