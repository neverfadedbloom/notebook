package com.example.noteapp281;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.noteapp.R;
import com.example.noteapp281.bean.Note;
import com.example.noteapp281.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private EditText etTitle, etContent; // 定义两个EditText组件，用于输入笔记的标题和内容

    private NoteDbOpenHelper mNoteDbOpenHelper; // 定义一个NoteDbOpenHelper对象，用于操作数据库

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle = findViewById(R.id.et_title); // 通过id找到标题输入框
        etContent = findViewById(R.id.et_content); // 通过id找到内容输入框
        mNoteDbOpenHelper = new NoteDbOpenHelper(this); // 创建一个NoteDbOpenHelper对象，传入当前上下文

    }

    public void add(View view) { // 定义一个添加笔记的方法，参数为点击的视图
        String title = etTitle.getText().toString(); // 获取标题输入框的文本，并转换为字符串
        String content = etContent.getText().toString(); // 获取内容输入框的文本，并转换为字符串
        if (TextUtils.isEmpty(title)) { // 判断标题是否为空
            ToastUtil.toastShort(this, "标题不能为空！"); // 如果为空，弹出提示信息
            return; // 结束方法
        }

        Note note = new Note(); // 创建一个Note对象，用于封装笔记数据

        note.setTitle(title); // 设置笔记标题
        note.setContent(content); // 设置笔记内容
        note.setCreatedTime(getCurrentTimeFormat()); // 设置创建时间
        long row = mNoteDbOpenHelper.insertData(note); // 将笔记数据插入数据库，并返回插入的行数
        if (row != -1) { // 判断插入是否成功
            ToastUtil.toastShort(this, "添加成功！"); // 如果成功，弹出提示信息
            this.finish(); // 结束当前活动，返回上一个活动
        } else {
            ToastUtil.toastShort(this, "添加失败！"); // 如果失败，弹出提示信息
        }

    }

    private String getCurrentTimeFormat() { // 定义一个获取当前时间格式化字符串的方法
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY年MM月dd HH:mm:ss"); // 创建一个SimpleDateFormat对象，指定格式化模式
        Date date = new Date(); // 创建一个Date对象，表示当前时间
        return simpleDateFormat.format(date); // 返回当前时间的格式化字符串
    }
}
