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
    private ArrayList<Object> arrayList;
    Context context;
    LayoutInflater inflater;
    private boolean isFromFirst = true;
    String str_language;

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public Task_QuestionListAdapter(Context context, ArrayList<Task_QueConstructor> arrayList, String str) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ArrayList<Object> arrayList2 = new ArrayList<>();
        this.arrayList = arrayList2;
        arrayList2.addAll(arrayList);
        this.str_language = str;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.inflater.inflate(R.layout.task_add_questions, (ViewGroup) null);
        }
        TextView textView = (TextView) view.findViewById(R.id.tv_question);
        TextView textView2 = (TextView) view.findViewById(R.id.tv_question_data);
        TextView textView3 = (TextView) view.findViewById(R.id.tv_answer);
        TextView textView4 = (TextView) view.findViewById(R.id.tv_answer_data);
        Task_QueConstructor m_rtoTaskQueConstructor = (Task_QueConstructor) this.arrayList.get(i);
        if (this.str_language.equals("gujarati")) {
            textView.setText("પ્રશ્ન :");
            textView3.setText("જવાબ :");
        } else if (this.str_language.equals("hindi")) {
            textView.setText("सवाल :");
            textView3.setText("उत्तर :");
        } else if (this.str_language.equals("english")) {
            textView.setText("Question :");
            textView3.setText("Answer :");
        }
        textView2.setText("" + m_rtoTaskQueConstructor.getQuestion());
        textView4.setText("" + m_rtoTaskQueConstructor.getAnswer());
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return !(this.arrayList.get(i) instanceof Task_QueConstructor) ? 1 : 0;
    }
}
