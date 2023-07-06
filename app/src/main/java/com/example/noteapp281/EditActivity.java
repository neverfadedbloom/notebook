package com.example.noteapp281;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.noteapp.R;
import com.example.noteapp281.bean.Note;
import com.example.noteapp281.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    private Note note; // 定义一个Note对象，用于存储要编辑的笔记数据
    private EditText etTitle,etContent; // 定义两个EditText组件，用于输入笔记的标题和内容

    private NoteDbOpenHelper mNoteDbOpenHelper; // 定义一个NoteDbOpenHelper对象，用于操作数据库
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etTitle = findViewById(R.id.et_title); // 通过id找到标题输入框
        etContent = findViewById(R.id.et_content); // 通过id找到内容输入框

        initData(); // 调用initData方法，初始化数据

    }

    private void initData() { // 定义一个初始化数据的方法
        Intent intent = getIntent(); // 获取启动当前活动的Intent对象
        note = (Note) intent.getSerializableExtra("note"); // 从Intent对象中获取传递的Note对象，并赋值给note变量
        if (note != null) { // 判断note是否为空
            etTitle.setText(note.getTitle()); // 如果不为空，将note的标题设置给标题输入框
            etContent.setText(note.getContent()); // 将note的内容设置给内容输入框
        }
        mNoteDbOpenHelper = new NoteDbOpenHelper(this); // 创建一个NoteDbOpenHelper对象，传入当前上下文
    }

    public void save(View view) { // 定义一个保存笔记的方法，参数为点击的视图
        String title = etTitle.getText().toString(); // 获取标题输入框的文本，并转换为字符串
        String content = etContent.getText().toString(); // 获取内容输入框的文本，并转换为字符串
        if (TextUtils.isEmpty(title)) { // 判断标题是否为空
            ToastUtil.toastShort(this, "标题不能为空！"); // 如果为空，弹出提示信息
            return; // 结束方法
        }

        note.setTitle(title); // 设置note的标题为输入的标题
        note.setContent(content); // 设置note的内容为输入的内容
        note.setCreatedTime(getCurrentTimeFormat()); // 设置note的创建时间为当前时间格式化字符串
        long rowId = mNoteDbOpenHelper.updateData(note); // 调用数据库操作类的updateData方法，更新数据库中对应的笔记记录，并返回更新的行数
        if (rowId != -1) { // 判断更新是否成功
            ToastUtil.toastShort(this, "修改成功！"); // 如果成功，弹出提示信息
            this.finish(); // 结束当前活动，返回上一个活动
        }else{
            ToastUtil.toastShort(this, "修改失败！"); // 如果失败，弹出提示信息
        }

    }

    private String getCurrentTimeFormat() { // 定义一个获取当前时间格式化字符串的方法
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss"); // 创建一个SimpleDateFormat对象，指定格式化模式
        Date date = new Date(); // 创建一个Date对象，表示当前时间
        return sdf.format(date); // 返回当前时间的格式化字符串
    }
}