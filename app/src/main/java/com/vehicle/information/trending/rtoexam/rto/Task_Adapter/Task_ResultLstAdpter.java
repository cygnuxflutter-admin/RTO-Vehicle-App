package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.ItemTouchHelper;

import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_MemsUtil;
import com.vehicle.information.trending.rtoexam.rto.R;
import java.util.ArrayList;
import java.util.HashMap;



public class Task_ResultLstAdpter extends BaseAdapter {
    private static final float BYTES_PER_PX = 4.0f;
    public static String KEY_CANS = "canswer";
    public static String KEY_Photo = "Pphoto";
    public static String KEY_QUES = "questions";
    public static String KEY_YANS = "yanswer";
    public static String key_image;
    ArrayList<Integer> Image;
    int[] images;
    LayoutInflater inflater;
    String nophoto;
    ArrayList<Integer> number;
    ArrayList<HashMap<String, Object>> originalValues;
    ArrayList<HashMap<String, Integer>> originalValues1;
    String photo;
    final Resources res;
    int resource;
    String str_language;
    ViewHolder viewHolder;

    @Override
    public long getItemId(int i) {
        return i;
    }


    public class ViewHolder {
        ImageView ivImage;
        TextView tvCans;
        TextView tvQS;
        TextView tvYouans;

        private ViewHolder() {
        }
    }

    public Task_ResultLstAdpter(Context context, int i, ArrayList<HashMap<String, Object>> arrayList, ArrayList<HashMap<String, Integer>> arrayList2, ArrayList<Integer> arrayList3, ArrayList<Integer> arrayList4, String str) {
        this.Image = new ArrayList<>();
        int[] iArr = {1, 2, R.drawable.ic_symbol_1, 4, R.drawable.ic_symbol_70, 6, R.drawable.ic_symbol_3, 8, R.drawable.ic_symbol_81, 10, R.drawable.ic_symbol_8, 12, R.drawable.ic_symbol_6, 14, R.drawable.ic_symbol_7, 16, R.drawable.ic_symbol_72, 18, R.drawable.ic_symbol_9, 20, R.drawable.ic_symbol_10, 22, R.drawable.ic_symbol_71, 24, R.drawable.ic_symbol_83, 26, R.drawable.ic_symbol_79, 28, R.drawable.ic_symbol_11, 30, R.drawable.ic_symbol_12, 32, R.drawable.ic_symbol_84, 34, R.drawable.ic_symbol_14, 36, R.drawable.ic_symbol_15, 38, R.drawable.ic_symbol_4, 40, R.drawable.ic_symbol_75, 42, R.drawable.ic_symbol_17, 44, R.drawable.ic_symbol_85, 46, R.drawable.ic_symbol_69, 48, R.drawable.ic_symbol_86, 50, R.drawable.ic_symbol_21, 52, R.drawable.ic_symbol_22, 54, 55, R.drawable.ic_symbol_24, 57, R.drawable.ic_symbol_25, 59, R.drawable.ic_symbol_26, 61, R.drawable.ic_symbol_87, 63, R.drawable.ic_symbol_88, 65, R.drawable.ic_symbol_19, 67, R.drawable.ic_symbol_20, 69, R.drawable.ic_symbol_16, 71, R.drawable.ic_symbol_2, 73, R.drawable.ic_symbol_5, 75, R.drawable.ic_symbol_29, 77, R.drawable.ic_symbol_30, 79, R.drawable.ic_symbol_31, R.drawable.ic_symbol_32, 82, R.drawable.ic_symbol_33, 84, R.drawable.ic_symbol_89, R.drawable.ic_symbol_18, 87, R.drawable.ic_symbol_90, 89, R.drawable.ic_symbol_37, 91, R.drawable.ic_symbol_38, 93, R.drawable.ic_symbol_78, 95, R.drawable.ic_symbol_40, 97, R.drawable.ic_symbol_39, 99, R.drawable.ic_symbol_34, 108, R.drawable.ic_symbol_27, 110, R.drawable.ic_symbol_41, 112, R.drawable.ic_symbol_42, 114, R.drawable.ic_symbol_43, 117, R.drawable.ic_symbol_44, 119, R.drawable.ic_symbol_45, 121, R.drawable.ic_symbol_46, 123, R.drawable.ic_symbol_47, 125, R.drawable.ic_symbol_56, R.drawable.ic_symbol_49, 120, R.drawable.ic_symbol_50, 122, R.drawable.ic_symbol_51, 124, R.drawable.ic_symbol_52, 126, R.drawable.ic_symbol_53, 128, R.drawable.ic_symbol_54, 130, R.drawable.ic_symbol_55, R.drawable.ic_symbol_80, 133, R.drawable.ic_symbol_57, 133, R.drawable.ic_symbol_58, 137, R.drawable.ic_symbol_60, 139, R.drawable.ic_symbol_59, 141, R.drawable.ic_symbol_63, 143, R.drawable.ic_symbol_64, 145, R.drawable.ic_symbol_65, 147, R.drawable.ic_symbol_66, 149, R.drawable._symbol_91, 151, R.drawable.ic_symbol_92, 153, R.drawable.ic_symbol_93, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, R.drawable.ic_symbol_74, R.drawable.ic_symbol_94, 202, 203, R.drawable.ic_symbol_95, R.drawable.ic_symbol_96, R.drawable.ic_symbol_97, R.drawable.ic_symbol_98, R.drawable.ic_symbol_99, R.drawable.ic_symbol_100, 210, 211, 212, R.drawable.ic_symbol_76, R.drawable.ic_symbol_77, R.drawable.ic_symbol_123, R.drawable.ic_symbol_101, R.drawable.ic_symbol_102, R.drawable.ic_symbol_103, R.drawable.ic_symbol_104, R.drawable.ic_symbol_105, R.drawable.ic_symbol_106, R.drawable.ic_symbol_107, R.drawable.ic_symbol_61, R.drawable.ic_symbol_108, R.drawable.ic_symbol_48, R.drawable.ic_symbol_109, 227, 228, 229, 231, 232, 233, 232, 233, 234, 235, 236, 237, 238, 239, R.drawable.ic_symbol_110, R.drawable.ic_symbol_111, 242, 243, 244, 245, 246, 247, 248, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, R.drawable.ic_symbol_112, R.drawable.ic_symbol_113, R.drawable.ic_symbol_114, R.drawable.ic_symbol_115, 304, 305, 306, 307, 308, 309, 310, R.drawable.ic_symbol_116, R.drawable.ic_symbol_117, R.drawable.ic_symbol_118, 314, 315, R.drawable.ic_symbol_119, R.drawable.ic_symbol_120, R.drawable.ic_symbol_121, R.drawable.ic_symbol_122, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 397, 398, 399, 400, 301, 302, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425, 426, 427};
        int[] iArr2 = {0, R.drawable.ic_symbol_2, R.drawable.ic_symbol_1, R.drawable.ic_symbol_48, R.drawable.ic_symbol_42, R.drawable.ic_symbol_43, R.drawable.ic_symbol_40, R.drawable.ic_symbol_39, R.drawable.ic_symbol_10, R.drawable.ic_symbol_38, R.drawable.ic_symbol_12, R.drawable.ic_symbol_11, R.drawable.ic_symbol_13, R.drawable.ic_symbol_18, R.drawable.ic_symbol_77, R.drawable.ic_symbol_32, R.drawable.ic_symbol_33, R.drawable.ic_symbol_31, R.drawable.ic_symbol_30, R.drawable.ic_symbol_72, R.drawable.ic_symbol_8, R.drawable.ic_symbol_23, R.drawable.ic_symbol_22, R.drawable.ic_symbol_16, R.drawable.ic_symbol_15, R.drawable.ic_symbol_27, R.drawable.ic_symbol_17, R.drawable.ic_symbol_7, R.drawable.ic_symbol_35, R.drawable.ic_symbol_46, R.drawable.ic_symbol_37, R.drawable.ic_symbol_34, R.drawable.ic_symbol_80, R.drawable.ic_symbol_81, R.drawable.ic_symbol_49, R.drawable.ic_symbol_50, R.drawable.ic_symbol_70, R.drawable.ic_symbol_19, R.drawable.ic_symbol_71, R.drawable.ic_symbol_73, R.drawable.ic_symbol_134, R.drawable.ic_symbol_75, R.drawable.ic_symbol_51, R.drawable.ic_symbol_14, R.drawable.ic_symbol_3, R.drawable.ic_symbol_4, R.drawable.ic_symbol_5, R.drawable.ic_symbol_29, R.drawable.ic_symbol_9, R.drawable.ic_symbol_6, R.drawable.ic_symbol_24, R.drawable.ic_symbol_79, R.drawable.ic_symbol_78, R.drawable.ic_symbol_45, R.drawable.ic_symbol_20, R.drawable.ic_symbol_74, R.drawable.ic_symbol_47, R.drawable.ic_symbol_55, R.drawable.ic_symbol_54, R.drawable.ic_symbol_41, R.drawable.ic_symbol_21, R.drawable.ic_symbol_69, R.drawable.ic_symbol_53, R.drawable.ic_symbol_56, R.drawable.ic_symbol_52, R.drawable.ic_symbol_44, R.drawable.ic_symbol_57, R.drawable.ic_symbol_61, R.drawable.ic_symbol_60, R.drawable.ic_symbol_59, R.drawable.ic_symbol_58, R.drawable.ic_symbol_45, R.drawable.ic_symbol_26, R.drawable.ic_symbol_25, R.drawable.ic_symbol_66, R.drawable.ic_symbol_67, R.drawable.ic_symbol_65, R.drawable.ic_symbol_63, R.drawable.ic_symbol_64, R.drawable.ic_symbol_204, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 43, 39, 46, 47, 48, 49, 70, 91, 92, 93, 94, 96, 100, 101, 77, 115, 114, 56, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 133, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION};
        int[] iArr3 = {1, 2, R.drawable.ic_symbol_1, 4, R.drawable.ic_symbol_70, 6, R.drawable.ic_symbol_3, 8, R.drawable.ic_symbol_81, 10, R.drawable.ic_symbol_8, 12, R.drawable.ic_symbol_6, 14, R.drawable.ic_symbol_7, 16, R.drawable.ic_symbol_72, 18, R.drawable.ic_symbol_9, 20, R.drawable.ic_symbol_10, 22, R.drawable.ic_symbol_71, 24, R.drawable.ic_symbol_83, 26, R.drawable.ic_symbol_79, 28, R.drawable.ic_symbol_11, 30, R.drawable.ic_symbol_12, 32, R.drawable.ic_symbol_84, 34, R.drawable.ic_symbol_14, 36, R.drawable.ic_symbol_15, 38, R.drawable.ic_symbol_4, 40, R.drawable.ic_symbol_75, 42, R.drawable.ic_symbol_17, 44, R.drawable.ic_symbol_85, 46, R.drawable.ic_symbol_69, 48, R.drawable.ic_symbol_86, 50, R.drawable.ic_symbol_21, 52, R.drawable.ic_symbol_22, 54, 55, R.drawable.ic_symbol_24, 57, R.drawable.ic_symbol_25, 59, R.drawable.ic_symbol_26, 61, R.drawable.ic_symbol_87, 63, R.drawable.ic_symbol_88, 65, R.drawable.ic_symbol_19, 67, R.drawable.ic_symbol_20, 69, R.drawable.ic_symbol_16, 71, R.drawable.ic_symbol_2, 73, R.drawable.ic_symbol_5, 75, R.drawable.ic_symbol_29, 77, R.drawable.ic_symbol_30, 79, R.drawable.ic_symbol_31, R.drawable.ic_symbol_32, 82, R.drawable.ic_symbol_33, 84, R.drawable.ic_symbol_89, R.drawable.ic_symbol_18, 87, R.drawable.ic_symbol_90, 89, R.drawable.ic_symbol_37, 91, R.drawable.ic_symbol_38, 93, R.drawable.ic_symbol_78, 95, R.drawable.ic_symbol_40, 97, R.drawable.ic_symbol_39, 99, R.drawable.ic_symbol_34, 101, R.drawable.ic_symbol_27, 103, R.drawable.ic_symbol_41, 105, R.drawable.ic_symbol_42, 107, R.drawable.ic_symbol_43, 109, R.drawable.ic_symbol_44, 111, R.drawable.ic_symbol_45, 113, R.drawable.ic_symbol_46, 115, R.drawable.ic_symbol_47, 117, R.drawable.ic_symbol_56, R.drawable.ic_symbol_49, 120, R.drawable.ic_symbol_50, 122, R.drawable.ic_symbol_51, 124, R.drawable.ic_symbol_52, 126, R.drawable.ic_symbol_53, 128, R.drawable.ic_symbol_54, 130, R.drawable.ic_symbol_55, R.drawable.ic_symbol_80, 133, R.drawable.ic_symbol_57, 133, R.drawable.ic_symbol_58, 137, R.drawable.ic_symbol_60, 139, R.drawable.ic_symbol_59, 141, R.drawable.ic_symbol_63, 143, R.drawable.ic_symbol_64, 145, R.drawable.ic_symbol_65, 147, R.drawable.ic_symbol_66, 149, R.drawable._symbol_91, 151, R.drawable.ic_symbol_92, 153, R.drawable.ic_symbol_93, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 1, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, R.drawable.ic_symbol_74, R.drawable.ic_symbol_94, 202, 203, R.drawable.ic_symbol_95, R.drawable.ic_symbol_96, R.drawable.ic_symbol_97, R.drawable.ic_symbol_98, R.drawable.ic_symbol_99, R.drawable.ic_symbol_100, 210, 211, 212, R.drawable.ic_symbol_76, R.drawable.ic_symbol_77, R.drawable.ic_symbol_123, R.drawable.ic_symbol_101, R.drawable.ic_symbol_102, R.drawable.ic_symbol_103, R.drawable.ic_symbol_104, R.drawable.ic_symbol_105, R.drawable.ic_symbol_106, R.drawable.ic_symbol_107, R.drawable.ic_symbol_61, R.drawable.ic_symbol_108, R.drawable.ic_symbol_48, R.drawable.ic_symbol_109, 227, 228, 229, 231, 232, 233, 232, 233, 234, 235, 236, 237, 238, 239, R.drawable.ic_symbol_110, R.drawable.ic_symbol_111, 242, 243, 244, 245, 246, 247, 248, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, R.drawable.ic_symbol_112, R.drawable.ic_symbol_113, R.drawable.ic_symbol_114, R.drawable.ic_symbol_115, 304, 305, 306, 307, 308, 309, 310, R.drawable.ic_symbol_116, R.drawable.ic_symbol_117, R.drawable.ic_symbol_118, 314, 315, R.drawable.ic_symbol_119, R.drawable.ic_symbol_120, R.drawable.ic_symbol_121, R.drawable.ic_symbol_122, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 397, 398, 399, 400, 301, 302, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425, 426, 427};
        this.str_language = str;
        if (str.equals("gujarati")) {
            this.images = iArr2;
        } else if (str.equals("hindi")) {
            this.images = iArr3;
        } else if (str.equals("english")) {
            this.images = iArr;
        }
        this.nophoto = "nophoto";
        this.number = new ArrayList<>();
        this.originalValues = new ArrayList<>();
        this.originalValues1 = new ArrayList<>();
        this.photo = "photo";
        this.res = Resources.getSystem();
        this.resource = i;
        this.originalValues = arrayList;
        this.originalValues1 = arrayList2;
        this.Image = arrayList3;
        this.number = arrayList4;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.originalValues.size();
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.inflater.inflate(R.layout.task_view_ans_custom, (ViewGroup) null);
            ViewHolder viewHolder = new ViewHolder();
            this.viewHolder = viewHolder;
            viewHolder.tvQS = (TextView) view.findViewById(R.id.tvQuestion2);
            this.viewHolder.tvYouans = (TextView) view.findViewById(R.id.yourAnswer);
            this.viewHolder.tvCans = (TextView) view.findViewById(R.id.RightAnwer);
            this.viewHolder.ivImage = (ImageView) view.findViewById(R.id.ivImage);
            view.setTag(this.viewHolder);
        } else {
            this.viewHolder = (ViewHolder) view.getTag();
        }
        if (!this.str_language.equals("gujarati") && !this.str_language.equals("hindi")) {
            this.str_language.equals("english");
        }
        StringBuilder sb = new StringBuilder();
        if (this.str_language.equals("gujarati")) {
            sb.append("પ્રશ્ન.");
        } else if (this.str_language.equals("hindi")) {
            sb.append("सवाल");
        } else if (this.str_language.equals("english")) {
            sb.append("Que.");
        }
        sb.append(this.number.get(i));
        sb.append(" ");
        sb.append(this.originalValues.get(i).get(KEY_QUES).toString());
        this.viewHolder.tvQS.setText(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        if (this.str_language.equals("gujarati")) {
            sb2.append("સાચો જવાબ: ");
        } else if (this.str_language.equals("hindi")) {
            sb2.append("सही जवाब: ");
        } else if (this.str_language.equals("english")) {
            sb2.append("True Ans.: ");
        }
        sb2.append(this.originalValues.get(i).get(KEY_CANS).toString());
        this.viewHolder.tvCans.setText(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        if (this.str_language.equals("gujarati")) {
            sb3.append("તમારો જવાબ: ");
        } else if (this.str_language.equals("hindi")) {
            sb3.append("आपका जवाब: ");
        } else if (this.str_language.equals("english")) {
            sb3.append("Your ans.: ");
        }
        sb3.append(this.originalValues.get(i).get(KEY_YANS).toString());
        this.viewHolder.tvYouans.setText(sb3.toString());
        if (this.originalValues.get(i).get(KEY_CANS).toString().equals(this.originalValues.get(i).get(KEY_YANS).toString())) {
            this.viewHolder.tvYouans.setTextColor(Color.parseColor("#ff99cc00"));
        } else {
            this.viewHolder.tvYouans.setTextColor(Color.parseColor("#ffff4444"));
        }
        if (this.photo.equals(this.originalValues.get(i).get(KEY_Photo).toString())) {
            this.viewHolder.ivImage.setVisibility(View.VISIBLE);
            loadImage(i);
        } else if (this.nophoto.equals(this.originalValues.get(i).get(KEY_Photo).toString())) {
            this.viewHolder.ivImage.setVisibility(View.GONE);
        }
        return view;
    }

    private void loadImage(int i) {
        if (readBitmapInfo(i) > Task_MemsUtil.megabytesFree()) {
            subSampleImage(32, i);
        } else {
            this.viewHolder.ivImage.setImageResource(this.images[this.Image.get(i).intValue()]);
        }
    }

    private float readBitmapInfo(int i) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(this.res, this.images[this.Image.get(i).intValue()], options);
        String str = options.outMimeType;
        return ((options.outWidth * options.outHeight) * BYTES_PER_PX) / 1048576.0f;
    }

    private void subSampleImage(int i, int i2) {
        if (i < 1 || i > 32) {
            return;
        }
        Resources system = Resources.getSystem();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = i;
        this.viewHolder.ivImage.setImageBitmap(BitmapFactory.decodeResource(system, this.images[this.Image.get(i2).intValue()], options));
    }
}
