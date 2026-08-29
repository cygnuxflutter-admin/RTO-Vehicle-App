package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
    private static final int TOTAL_EXAM_QUESTIONS = 15;
    private static final int PASSING_SCORE = 9;

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
        super.onCreate(bundle);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_quizes);

        ((ImageView) findViewById(R.id.iv_back)).setOnClickListener(view -> onBackPressed());

        this.str_language = getIntent().getStringExtra("language");
        if (this.str_language == null || this.str_language.isEmpty()) {
            this.str_language = "english";
        }
        Log.d("R_Quiz", "Language is = " + this.str_language);

        this.images = this.images_english;

        this.tvTimer = findViewById(R.id.tvTimer);
        this.tvPositive = findViewById(R.id.tvPositive);
        this.tvNegative = findViewById(R.id.tvNegative);
        this.ivImage = findViewById(R.id.ivImage);
        this.tvQuestion = findViewById(R.id.tvQuestion);
        this.radioButton3 = findViewById(R.id.radioButton3);
        this.radioButton = findViewById(R.id.radioButton);
        this.radioButton2 = findViewById(R.id.radioButton2);
        this.btnNext = findViewById(R.id.btnNext);

        if (this.str_language.equalsIgnoreCase("gujarati")) {
            this.btnNext.setText("આગળ");
        } else if (this.str_language.equalsIgnoreCase("hindi")) {
            this.btnNext.setText("आगे");
        } else {
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

        this.btnNext.setOnClickListener(view -> {
            Task_QuizActivity.this.timer.cancel();
            Task_QuizActivity.this.tvTimer.setText("");
            Task_QuizActivity m_rtoQuizes = Task_QuizActivity.this;
            m_rtoQuizes.rgChoice = (RadioGroup) m_rtoQuizes.findViewById(R.id.rgChoice);
            RadioButton selectedRb = (RadioButton) m_rtoQuizes.findViewById(m_rtoQuizes.rgChoice.getCheckedRadioButtonId());

            if (selectedRb != null) {
                Task_QuizActivity.this.myAnsList.add("" + selectedRb.getText());
                if (Task_QuizActivity.this.currentquestion.getAnswer().equals(selectedRb.getText())) {
                    Task_QuizActivity.this.Score++;
                    Task_QuizActivity.this.positive++;
                    Task_QuizActivity.this.tvPositive.setText("" + Task_QuizActivity.this.positive);
                } else {
                    Task_QuizActivity.this.negative++;
                    Task_QuizActivity.this.tvNegative.setText("" + Task_QuizActivity.this.negative);
                }
            } else {
                if (Task_QuizActivity.this.str_language.equalsIgnoreCase("gujarati")) {
                    Task_QuizActivity.this.myAnsList.add("તમે આ પ્રશ્નનો જવાબ આપ્યો નથી");
                } else if (Task_QuizActivity.this.str_language.equalsIgnoreCase("hindi")) {
                    Task_QuizActivity.this.myAnsList.add("आपने इस प्रश्न का उत्तर नहीं दिया है");
                } else {
                    Task_QuizActivity.this.myAnsList.add("You have not answered this question");
                }
                Task_QuizActivity.this.negative++;
                Task_QuizActivity.this.tvNegative.setText("" + Task_QuizActivity.this.negative);
            }

            // Check if exam is finished (Score >= 9 OR reached 15 questions OR questions exhausted)
            if (Task_QuizActivity.this.Score >= PASSING_SCORE || Task_QuizActivity.this.f16k >= TOTAL_EXAM_QUESTIONS || Task_QuizActivity.this.questionId >= Task_QuizActivity.this.quesList.size()) {
                finishQuiz();
            } else {
                Task_QuizActivity.this.currentquestion = Task_QuizActivity.this.quesList.get(Task_QuizActivity.this.questionId);
                Task_QuizActivity.this.setQuestionsView();
                Task_QuizActivity.this.curr();
                Task_QuizActivity.this.timer.start();
            }
            Task_QuizActivity.this.rgChoice.clearCheck();
        });
    }

    private void finishQuiz() {
        this.timer.cancel();
        Intent intent = new Intent(Task_QuizActivity.this, Task_ResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("score", this.Score);
        bundle.putStringArrayList("questionnumbers", this.QuestionNumbers);
        bundle.putStringArrayList("myanswer", this.myAnsList);
        bundle.putStringArrayList("Correct", this.Correctans);
        bundle.putIntegerArrayList("Image", this.image);
        bundle.putIntegerArrayList("Numbers", this.numbers);
        bundle.putStringArrayList("photo", this.Photo);
        bundle.putString("language", this.str_language);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public class CounterClass extends CountDownTimer {
        public CounterClass(long j, long j2) {
            super(j, j2);
        }

        @Override
        public void onTick(long j) {
            TextView textView = Task_QuizActivity.this.tvTimer;
            textView.setText(String.format("%02ds", Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j)))));
        }

        @Override
        public void onFinish() {
            // If already at 15th question, show Exam Over dialog and finish
            if (Task_QuizActivity.this.f16k >= TOTAL_EXAM_QUESTIONS || Task_QuizActivity.this.questionId >= Task_QuizActivity.this.quesList.size() || Task_QuizActivity.this.Score >= PASSING_SCORE) {
                final Dialog dialog2 = new Dialog(Task_QuizActivity.this);
                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog2.setContentView(R.layout.task_dialog_exam_over);
                if (dialog2.getWindow() != null) {
                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                dialog2.setCanceledOnTouchOutside(false);
                dialog2.setCancelable(false);
                dialog2.show();

                TextView textView3 = dialog2.findViewById(R.id.tv_header);
                TextView textView4 = dialog2.findViewById(R.id.tv_sub_text);
                Button button2 = dialog2.findViewById(R.id.btn_dia_next);

                if (Task_QuizActivity.this.str_language.equalsIgnoreCase("gujarati")) {
                    textView3.setText("પરીક્ષા પૂર્ણ થઈ!");
                    textView4.setText("તમે તમામ 15 પ્રશ્નો પૂર્ણ કરી લીધા છે.\nપરિણામ જોવા માટે નીચે ક્લિક કરો.");
                    button2.setText("પરિણામ જુઓ");
                } else if (Task_QuizActivity.this.str_language.equalsIgnoreCase("hindi")) {
                    textView3.setText("परीक्षा समाप्त!");
                    textView4.setText("आपने सभी 15 प्रश्न पूरे कर लिए हैं।\nपरिणाम देखने के लिए नीचे क्लिक करें।");
                    button2.setText("परिणाम देखें");
                } else {
                    textView3.setText("Exam Completed!");
                    textView4.setText("You have finished all 15 questions.\nTap below to view your test result.");
                    button2.setText("View Result");
                }

                button2.setOnClickListener(view -> {
                    String str;
                    if (Task_QuizActivity.this.str_language.equalsIgnoreCase("gujarati")) {
                        str = "તમે આ પ્રશ્નનો જવાબ આપ્યો નથી";
                    } else if (Task_QuizActivity.this.str_language.equalsIgnoreCase("hindi")) {
                        str = "आपने इस प्रश्न का उत्तर नहीं दिया है";
                    } else {
                        str = "You have not answered this question";
                    }
                    Task_QuizActivity.this.myAnsList.add(str);
                    dialog2.cancel();
                    finishQuiz();
                });
            } else {
                // Show Time Over dialog and proceed to next question
                final Dialog dialog = new Dialog(Task_QuizActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.task_dialog_exam_time_over);
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();

                TextView textView = dialog.findViewById(R.id.tv_header);
                TextView textView2 = dialog.findViewById(R.id.tv_sub_text);
                Button button = dialog.findViewById(R.id.btn_dia_next);

                if (Task_QuizActivity.this.str_language.equalsIgnoreCase("gujarati")) {
                    textView.setText("સમય પુરો!");
                    textView2.setText("આ પ્રશ્ન માટે 30 સેકન્ડ પૂર્ણ થઈ ગયા છે.\nઆગળનો પ્રશ્ન જોવા માટે નીચે ક્લિક કરો.");
                    button.setText("આગળનો પ્રશ્ન");
                } else if (Task_QuizActivity.this.str_language.equalsIgnoreCase("hindi")) {
                    textView.setText("समय समाप्त!");
                    textView2.setText("इस प्रश्न के लिए 30 सेकंड समाप्त हो गए हैं।\nअगला प्रश्न देखने के लिए नीचे क्लिक करें।");
                    button.setText("अगला सवाल");
                } else {
                    textView.setText("Time Over!");
                    textView2.setText("30 seconds have passed for this question.\nTap below to proceed to the next question.");
                    button.setText("Next Question");
                }

                button.setOnClickListener(view -> {
                    String str;
                    if (Task_QuizActivity.this.str_language.equalsIgnoreCase("gujarati")) {
                        str = "તમે આ પ્રશ્નનો જવાબ આપ્યો નથી";
                    } else if (Task_QuizActivity.this.str_language.equalsIgnoreCase("hindi")) {
                        str = "आपने इस प्रश्न का उत्तर नहीं दिया है";
                    } else {
                        str = "You have not answered this question";
                    }

                    Task_QuizActivity.this.myAnsList.add(str);
                    Task_QuizActivity.this.tvTimer.setText("");
                    Task_QuizActivity.this.negative++;
                    Task_QuizActivity.this.tvNegative.setText("" + Task_QuizActivity.this.negative);

                    if (Task_QuizActivity.this.Score >= PASSING_SCORE || Task_QuizActivity.this.f16k >= TOTAL_EXAM_QUESTIONS || Task_QuizActivity.this.questionId >= Task_QuizActivity.this.quesList.size()) {
                        dialog.cancel();
                        finishQuiz();
                    } else {
                        CounterClass.this.start();
                        Task_QuizActivity.this.currentquestion = Task_QuizActivity.this.quesList.get(Task_QuizActivity.this.questionId);
                        Task_QuizActivity.this.curr();
                        Task_QuizActivity.this.setQuestionsView();
                        dialog.cancel();
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
        RadioGroup radioGroup = findViewById(R.id.rgChoice);
        this.rgChoice = radioGroup;
        radioGroup.setOnCheckedChangeListener((radioGroup2, i) -> {
            if (Task_QuizActivity.this.radioButton.isChecked() ||
                Task_QuizActivity.this.radioButton2.isChecked() ||
                Task_QuizActivity.this.radioButton3.isChecked()) {
                Task_QuizActivity.this.btnNext.setEnabled(true);
            }
        });
    }

    public void setQuestionsView() {
        this.radioButton2.setChecked(false);
        this.radioButton.setChecked(false);
        this.radioButton3.setChecked(false);
        this.f16k++;
        this.answeredquestno = this.questionId + 1;

        TextView tvQuestionBadge = findViewById(R.id.tv_question_badge);
        TextView tvOptionsTitle = findViewById(R.id.tv_options_title);
        TextView tvHeaderTitle = findViewById(R.id.tv_header_title);

        if (this.str_language.equalsIgnoreCase("gujarati")) {
            this.btnNext.setText("આગળ");
            if (tvHeaderTitle != null) tvHeaderTitle.setText("RTO પરીક્ષા");
            if (tvQuestionBadge != null) tvQuestionBadge.setText("પ્રશ્ન " + this.f16k + " / " + TOTAL_EXAM_QUESTIONS);
            if (tvOptionsTitle != null) tvOptionsTitle.setText("યોગ્ય વિકલ્પ પસંદ કરો:");
        } else if (this.str_language.equalsIgnoreCase("hindi")) {
            this.btnNext.setText("आगे");
            if (tvHeaderTitle != null) tvHeaderTitle.setText("RTO परीक्षा");
            if (tvQuestionBadge != null) tvQuestionBadge.setText("प्रश्न " + this.f16k + " / " + TOTAL_EXAM_QUESTIONS);
            if (tvOptionsTitle != null) tvOptionsTitle.setText("सही विकल्प चुनें:");
        } else {
            this.btnNext.setText("Next");
            if (tvHeaderTitle != null) tvHeaderTitle.setText("RTO Exam");
            if (tvQuestionBadge != null) tvQuestionBadge.setText("QUESTION " + this.f16k + " / " + TOTAL_EXAM_QUESTIONS);
            if (tvOptionsTitle != null) tvOptionsTitle.setText("Select one option:");
        }

        this.tvQuestion.setText(this.currentquestion.getQuestion());
        this.radioButton.setText(this.currentquestion.getOption1());
        this.radioButton2.setText(this.currentquestion.getOption2());
        this.radioButton3.setText(this.currentquestion.getOption3());

        this.QuestionNumbers.add("" + this.currentquestion.getQuestion());
        this.Correctans.add("" + this.currentquestion.getAnswer());
        this.Photo.add("" + this.currentquestion.getPhoto());
        int id = this.currentquestion.getId();
        this.curr1 = id;
        this.image.add(Integer.valueOf(id));
        this.numbers.add(Integer.valueOf(this.f16k));

        if (this.photo.equals(this.currentquestion.getPhoto())) {
            loadImage();
        } else {
            View cvImageWrapper = findViewById(R.id.cv_image_wrapper);
            if (cvImageWrapper != null) cvImageWrapper.setVisibility(View.GONE);
            this.ivImage.setVisibility(View.GONE);
        }
        this.questionId++;
    }

    private void loadImage() {
        View cvImageWrapper = findViewById(R.id.cv_image_wrapper);
        try {
            int qId = currentquestion.getId();
            int resId = 0;
            if (images_english != null && qId >= 0 && qId < images_english.length) {
                resId = images_english[qId];
            }
            if (resId > 1000) {
                if (readBitmapInfo() > Task_MemsUtil.megabytesFree()) {
                    subSampleImage(32);
                } else {
                    this.ivImage.setImageResource(resId);
                }
                this.ivImage.setVisibility(View.VISIBLE);
                if (cvImageWrapper != null) cvImageWrapper.setVisibility(View.VISIBLE);
            } else {
                this.ivImage.setVisibility(View.GONE);
                if (cvImageWrapper != null) cvImageWrapper.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            this.ivImage.setVisibility(View.GONE);
            if (cvImageWrapper != null) cvImageWrapper.setVisibility(View.GONE);
        }
    }

    private float readBitmapInfo() {
        Resources resources = getResources();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, this.images[this.currentquestion.getId()], options);
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