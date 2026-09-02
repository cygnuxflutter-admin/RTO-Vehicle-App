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

    int[] images_english = {1, 2, R.drawable.ic_symbol_1, 4, R.drawable.ic_symbol_70, 6, R.drawable.ic_symbol_3, 8, R.drawable.ic_symbol_81, 10, R.drawable.ic_symbol_8, 12, R.drawable.ic_symbol_6, 14, R.drawable.ic_symbol_7, 16, R.drawable.ic_symbol_72, 18, R.drawable.ic_symbol_9, 20, R.drawable.ic_symbol_10, 22, R.drawable.ic_symbol_71, 24, R.drawable.ic_symbol_83, 26, R.drawable.ic_symbol_79, 28, R.drawable.ic_symbol_11, 30, R.drawable.ic_symbol_12, 32, R.drawable.ic_symbol_84, 34, R.drawable.ic_symbol_14, 36, R.drawable.ic_symbol_15, 38, R.drawable.ic_symbol_4, 40, R.drawable.ic_symbol_75, 42, R.drawable.ic_symbol_17, 44, R.drawable.ic_symbol_85, 46, R.drawable.ic_symbol_69, 48, R.drawable.ic_symbol_86, 50, R.drawable.ic_symbol_21, 52, R.drawable.ic_symbol_22, 54, 55, R.drawable.ic_symbol_24, 57, R.drawable.ic_symbol_25, 59, R.drawable.ic_symbol_26, 61, R.drawable.ic_symbol_87, 63, R.drawable.ic_symbol_88, 65, R.drawable.ic_symbol_19, 67, R.drawable.ic_symbol_20, 69, R.drawable.ic_symbol_16, 71, R.drawable.ic_symbol_2, 73, R.drawable.ic_symbol_5, 75, R.drawable.ic_symbol_29, 77, R.drawable.ic_symbol_30, 79, R.drawable.ic_symbol_31, R.drawable.ic_symbol_32, 82, R.drawable.ic_symbol_33, 84, R.drawable.ic_symbol_89, R.drawable.ic_symbol_18, 87, R.drawable.ic_symbol_90, 89, R.drawable.ic_symbol_37, 91, R.drawable.ic_symbol_38, 93, R.drawable.ic_symbol_78, 95, R.drawable.ic_symbol_40, 97, R.drawable.ic_symbol_39, 99, R.drawable.ic_symbol_34, 108, R.drawable.ic_symbol_27, 110, R.drawable.ic_symbol_41, 112, R.drawable.ic_symbol_42, 114, R.drawable.ic_symbol_43, 117, R.drawable.ic_symbol_44, 119, R.drawable.ic_symbol_45, 121, R.drawable.ic_symbol_46, 123, R.drawable.ic_symbol_47, 125, R.drawable.ic_symbol_56, R.drawable.ic_symbol_49, 120, R.drawable.ic_symbol_50, 122, R.drawable.ic_symbol_51, 124, R.drawable.ic_symbol_52, 126, R.drawable.ic_symbol_53, 128, R.drawable.ic_symbol_54, 130, R.drawable.ic_symbol_55, R.drawable.ic_symbol_80, 133, R.drawable.ic_symbol_57, 133, R.drawable.ic_symbol_58, 137, R.drawable.ic_symbol_60, 139, R.drawable.ic_symbol_59, 141, R.drawable.ic_symbol_63, 143, R.drawable.ic_symbol_64, 145, R.drawable.ic_symbol_65, 147, R.drawable.ic_symbol_66, 149, R.drawable._symbol_91, 151, R.drawable.ic_symbol_92, 153, R.drawable.ic_symbol_93, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, R.drawable.ic_symbol_74, R.drawable.ic_symbol_94, 202, 203, R.drawable.ic_symbol_95, R.drawable.ic_symbol_96, R.drawable.ic_symbol_97, R.drawable.ic_symbol_98, R.drawable.ic_symbol_99, R.drawable.ic_symbol_100, 210, 211, 212, R.drawable.ic_symbol_76, R.drawable.ic_symbol_77, R.drawable.ic_symbol_123, R.drawable.ic_symbol_101, R.drawable.ic_symbol_102, R.drawable.ic_symbol_103, R.drawable.ic_symbol_104, R.drawable.ic_symbol_105, R.drawable.ic_symbol_106, R.drawable.ic_symbol_107, R.drawable.ic_symbol_61, R.drawable.ic_symbol_108, R.drawable.ic_symbol_48, R.drawable.ic_symbol_109, 227, 228, 229, 231, 232, 233, 232, 233, 234, 235, 236, 237, 238, 239, R.drawable.ic_symbol_110, R.drawable.ic_symbol_111, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, R.drawable.ic_symbol_112, R.drawable.ic_symbol_113, R.drawable.ic_symbol_114, R.drawable.ic_symbol_115, 304, 305, 306, 307, 308, 309, 310, R.drawable.ic_symbol_116, R.drawable.ic_symbol_117, R.drawable.ic_symbol_118, 314, 315, R.drawable.ic_symbol_119, R.drawable.ic_symbol_120, R.drawable.ic_symbol_121, R.drawable.ic_symbol_122, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 397, 398, 399, 400, 301, 302, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425, 426, 427};
    int[] images_gujarati = {0, R.drawable.ic_symbol_2, R.drawable.ic_symbol_1, R.drawable.ic_symbol_48, R.drawable.ic_symbol_42, R.drawable.ic_symbol_43, R.drawable.ic_symbol_40, R.drawable.ic_symbol_39, R.drawable.ic_symbol_10, R.drawable.ic_symbol_38, R.drawable.ic_symbol_12, R.drawable.ic_symbol_11, R.drawable.ic_symbol_13, R.drawable.ic_symbol_18, R.drawable.ic_symbol_77, R.drawable.ic_symbol_32, R.drawable.ic_symbol_33, R.drawable.ic_symbol_31, R.drawable.ic_symbol_30, R.drawable.ic_symbol_72, R.drawable.ic_symbol_8, R.drawable.ic_symbol_23, R.drawable.ic_symbol_22, R.drawable.ic_symbol_16, R.drawable.ic_symbol_15, R.drawable.ic_symbol_27, R.drawable.ic_symbol_17, R.drawable.ic_symbol_7, R.drawable.ic_symbol_35, R.drawable.ic_symbol_46, R.drawable.ic_symbol_37, R.drawable.ic_symbol_34, R.drawable.ic_symbol_80, R.drawable.ic_symbol_81, R.drawable.ic_symbol_49, R.drawable.ic_symbol_50, R.drawable.ic_symbol_70, R.drawable.ic_symbol_19, R.drawable.ic_symbol_71, R.drawable.ic_symbol_73, R.drawable.ic_symbol_134, R.drawable.ic_symbol_75, R.drawable.ic_symbol_51, R.drawable.ic_symbol_14, R.drawable.ic_symbol_3, R.drawable.ic_symbol_4, R.drawable.ic_symbol_5, R.drawable.ic_symbol_29, R.drawable.ic_symbol_9, R.drawable.ic_symbol_6, R.drawable.ic_symbol_24, R.drawable.ic_symbol_79, R.drawable.ic_symbol_78, R.drawable.ic_symbol_45, R.drawable.ic_symbol_20, R.drawable.ic_symbol_74, R.drawable.ic_symbol_47, R.drawable.ic_symbol_55, R.drawable.ic_symbol_54, R.drawable.ic_symbol_41, R.drawable.ic_symbol_21, R.drawable.ic_symbol_69, R.drawable.ic_symbol_53, R.drawable.ic_symbol_56, R.drawable.ic_symbol_52, R.drawable.ic_symbol_44, R.drawable.ic_symbol_57, R.drawable.ic_symbol_61, R.drawable.ic_symbol_60, R.drawable.ic_symbol_59, R.drawable.ic_symbol_58, R.drawable.ic_symbol_45, R.drawable.ic_symbol_26, R.drawable.ic_symbol_25, R.drawable.ic_symbol_66, R.drawable.ic_symbol_67, R.drawable.ic_symbol_65, R.drawable.ic_symbol_63, R.drawable.ic_symbol_64, R.drawable.ic_symbol_204, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 43, 39, 46, 47, 48, 49, 70, 91, 92, 93, 94, 96, 100, 101, 77, 115, 114, 56, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 133, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200};
    int[] images_hindi = {1, 2, R.drawable.ic_symbol_1, 4, R.drawable.ic_symbol_70, 6, R.drawable.ic_symbol_3, 8, R.drawable.ic_symbol_81, 10, R.drawable.ic_symbol_8, 12, R.drawable.ic_symbol_6, 14, R.drawable.ic_symbol_7, 16, R.drawable.ic_symbol_72, 18, R.drawable.ic_symbol_9, 20, R.drawable.ic_symbol_10, 22, R.drawable.ic_symbol_71, 24, R.drawable.ic_symbol_83, 26, R.drawable.ic_symbol_79, 28, R.drawable.ic_symbol_11, 30, R.drawable.ic_symbol_12, 32, R.drawable.ic_symbol_84, 34, R.drawable.ic_symbol_14, 36, R.drawable.ic_symbol_15, 38, R.drawable.ic_symbol_4, 40, R.drawable.ic_symbol_75, 42, R.drawable.ic_symbol_17, 44, R.drawable.ic_symbol_85, 46, R.drawable.ic_symbol_69, 48, R.drawable.ic_symbol_86, 50, R.drawable.ic_symbol_21, 52, R.drawable.ic_symbol_22, 54, 55, R.drawable.ic_symbol_24, 57, R.drawable.ic_symbol_25, 59, R.drawable.ic_symbol_26, 61, R.drawable.ic_symbol_87, 63, R.drawable.ic_symbol_88, 65, R.drawable.ic_symbol_19, 67, R.drawable.ic_symbol_20, 69, R.drawable.ic_symbol_16, 71, R.drawable.ic_symbol_2, 73, R.drawable.ic_symbol_5, 75, R.drawable.ic_symbol_29, 77, R.drawable.ic_symbol_30, 79, R.drawable.ic_symbol_31, R.drawable.ic_symbol_32, 82, R.drawable.ic_symbol_33, 84, R.drawable.ic_symbol_89, R.drawable.ic_symbol_18, 87, R.drawable.ic_symbol_90, 89, R.drawable.ic_symbol_37, 91, R.drawable.ic_symbol_38, 93, R.drawable.ic_symbol_78, 95, R.drawable.ic_symbol_40, 97, R.drawable.ic_symbol_39, 99, R.drawable.ic_symbol_34, 101, R.drawable.ic_symbol_27, 103, R.drawable.ic_symbol_41, 105, R.drawable.ic_symbol_42, 107, R.drawable.ic_symbol_43, 109, R.drawable.ic_symbol_44, 111, R.drawable.ic_symbol_45, 113, R.drawable.ic_symbol_46, 115, R.drawable.ic_symbol_47, 117, R.drawable.ic_symbol_56, R.drawable.ic_symbol_49, 120, R.drawable.ic_symbol_50, 122, R.drawable.ic_symbol_51, 124, R.drawable.ic_symbol_52, 126, R.drawable.ic_symbol_53, 128, R.drawable.ic_symbol_54, 130, R.drawable.ic_symbol_55, R.drawable.ic_symbol_80, 133, R.drawable.ic_symbol_57, 133, R.drawable.ic_symbol_58, 137, R.drawable.ic_symbol_60, 139, R.drawable.ic_symbol_59, 141, R.drawable.ic_symbol_63, 143, R.drawable.ic_symbol_64, 145, R.drawable.ic_symbol_65, 147, R.drawable.ic_symbol_66, 149, R.drawable._symbol_91, 151, R.drawable.ic_symbol_92, 153, R.drawable.ic_symbol_93, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 1, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, R.drawable.ic_symbol_74, R.drawable.ic_symbol_94, 202, 203, R.drawable.ic_symbol_95, R.drawable.ic_symbol_96, R.drawable.ic_symbol_97, R.drawable.ic_symbol_98, R.drawable.ic_symbol_99, R.drawable.ic_symbol_100, 210, 211, 212, R.drawable.ic_symbol_76, R.drawable.ic_symbol_77, R.drawable.ic_symbol_123, R.drawable.ic_symbol_101, R.drawable.ic_symbol_102, R.drawable.ic_symbol_103, R.drawable.ic_symbol_104, R.drawable.ic_symbol_105, R.drawable.ic_symbol_106, R.drawable.ic_symbol_107, R.drawable.ic_symbol_61, R.drawable.ic_symbol_108, R.drawable.ic_symbol_48, R.drawable.ic_symbol_109, 227, 228, 229, 231, 232, 233, 232, 233, 234, 235, 236, 237, 238, 239, R.drawable.ic_symbol_110, R.drawable.ic_symbol_111, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, R.drawable.ic_symbol_112, R.drawable.ic_symbol_113, R.drawable.ic_symbol_114, R.drawable.ic_symbol_115, 304, 305, 306, 307, 308, 309, 310, R.drawable.ic_symbol_116, R.drawable.ic_symbol_117, R.drawable.ic_symbol_118, 314, 315, R.drawable.ic_symbol_119, R.drawable.ic_symbol_120, R.drawable.ic_symbol_121, R.drawable.ic_symbol_122, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 397, 398, 399, 400, 301, 302, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425, 426, 427};
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

        if (this.str_language.equalsIgnoreCase("gujarati")) {
            this.images = this.images_gujarati;
        } else if (this.str_language.equalsIgnoreCase("hindi")) {
            this.images = this.images_hindi;
        } else {
            this.images = this.images_english;
        }

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
            if (this.images != null && qId >= 0 && qId < this.images.length) {
                resId = this.images[qId];
            }
            if (resId > 1000) {
                this.ivImage.setImageResource(resId);
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

    @Override
    public void onBackPressed() {
        showExitConfirmationDialog();
    }

    private void showExitConfirmationDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.task_dialog_exit_exam);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        TextView tvTitle = dialog.findViewById(R.id.tv_exit_title);
        TextView tvMessage = dialog.findViewById(R.id.tv_exit_message);
        Button btnExit = dialog.findViewById(R.id.btn_confirm_exit);
        Button btnContinue = dialog.findViewById(R.id.btn_cancel_exit);

        if (this.str_language != null && this.str_language.equalsIgnoreCase("gujarati")) {
            tvTitle.setText("પરીક્ષા છોડવી છે?");
            tvMessage.setText("જો તમે અત્યારે બહાર જશો, તો તમારી ચાલુ પરીક્ષા રદ થઈ જશે અને પ્રગતિ સેવ થશે નહીં.");
            btnExit.setText("બહાર નીકળો");
            btnContinue.setText("ચાલુ રાખો");
        } else if (this.str_language != null && this.str_language.equalsIgnoreCase("hindi")) {
            tvTitle.setText("परीक्षा छोड़ना चाहते हैं?");
            tvMessage.setText("यदि आप अभी बाहर निकलते हैं, तो आपकी वर्तमान परीक्षा रद्द हो जाएगी और प्रगति सेव नहीं होगी।");
            btnExit.setText("बाहर निकलें");
            btnContinue.setText("जारी रखें");
        } else {
            tvTitle.setText("Exit Exam?");
            tvMessage.setText("Are you sure you want to quit? Your current test progress will be lost.");
            btnExit.setText("Exit");
            btnContinue.setText("Continue");
        }

        btnExit.setOnClickListener(v -> {
            dialog.dismiss();
            this.timer.cancel();
            finish();
        });

        btnContinue.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}