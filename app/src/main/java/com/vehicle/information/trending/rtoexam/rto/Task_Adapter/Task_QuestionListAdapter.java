package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_QueConstructor;
import com.vehicle.information.trending.rtoexam.rto.R;
import java.util.ArrayList;

public class Task_QuestionListAdapter extends BaseAdapter {
    private final ArrayList<Object> arrayList;
    private final Context context;
    private final LayoutInflater inflater;
    private final String str_language;

    public Task_QuestionListAdapter(Context context, ArrayList<Task_QueConstructor> arrayList, String str) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<>();
        if (arrayList != null) {
            this.arrayList.addAll(arrayList);
        }
        this.str_language = str != null ? str : "english";
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.inflater.inflate(R.layout.task_add_questions, viewGroup, false);
        }

        TextView textView = view.findViewById(R.id.tv_question);
        TextView textView2 = view.findViewById(R.id.tv_question_data);
        TextView textView3 = view.findViewById(R.id.tv_answer);
        TextView textView4 = view.findViewById(R.id.tv_answer_data);

        Object obj = this.arrayList.get(i);
        if (obj instanceof Task_QueConstructor) {
            Task_QueConstructor q = (Task_QueConstructor) obj;

            if ("gujarati".equalsIgnoreCase(this.str_language)) {
                textView.setText("પ્રશ્ન");
                textView3.setText("જવાબ");
            } else if ("hindi".equalsIgnoreCase(this.str_language)) {
                textView.setText("प्रश्न");
                textView3.setText("उत्तर");
            } else {
                textView.setText("Que");
                textView3.setText("Ans");
            }

            textView2.setText(q.getQuestion());
            textView4.setText(q.getAnswer());
        }

        return view;
    }
}