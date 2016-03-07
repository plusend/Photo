package com.plusend.photo.view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.plusend.photo.R;
import com.plusend.photo.db.PhotoDAO;
import com.plusend.photo.model.Photo;
import com.plusend.photo.utils.BitmapUtils;
import com.plusend.photo.utils.log.Logger;

import java.util.Calendar;
import java.util.Date;

public class InsertActivity extends AppCompatActivity {
    private static final String TAG = "InsertActivity";

    private ImageView imageView;
    private TextView date;
    private EditText editText;
    private PhotoDAO photoDAO;
    private Photo photo;
    long result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        photoDAO = new PhotoDAO(InsertActivity.this);
        photo = new Photo();

        imageView = (ImageView) findViewById(R.id.pic);
        imageView.setDrawingCacheEnabled(true);
        date = (TextView) findViewById(R.id.date);
        editText = (EditText) findViewById(R.id.note);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                final DatePickerDialog datePicker = new DatePickerDialog(InsertActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        int month = monthOfYear + 1;
                        date.setText(year + "年" + month + "月" + dayOfMonth + "日");
                        calendar.set(year, monthOfYear, dayOfMonth);
                        Date date = calendar.getTime();
                        photo.setDate(date);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String note = editText.getText().toString();
                photo.setNote(note);
                photo.setPic(BitmapUtils.getMagicDrawingCache(imageView));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        result = photoDAO.insert(photo);
                    }
                }).start();

                if (result != -1) {
                    Snackbar.make(view, "记录照片成功", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    InsertActivity.this.finish();
                } else
                    Snackbar.make(view, "记录照片失败,请重试", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });

        pickPhoto();
    }

    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Logger.d(TAG, "Uri:" + uri);
            Glide.with(this).load(uri).into(imageView);
            Logger.d(TAG, "width:" + imageView.getWidth() + " height:" + imageView.getHeight());
        } else {
            this.finish();
        }
    }

}
