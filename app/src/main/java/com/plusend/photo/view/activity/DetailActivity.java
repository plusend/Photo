package com.plusend.photo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.plusend.photo.R;
import com.plusend.photo.db.PhotoDAO;
import com.plusend.photo.db.handler.PhotoCursorHandler;
import com.plusend.photo.model.Photo;
import com.plusend.photo.utils.log.Logger;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    private ImageView imageView;
    private TextView note;
    private TextView date;

    private MyHandler UiHandler = new MyHandler(DetailActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Logger.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = (ImageView) findViewById(R.id.image);
        note = (TextView) findViewById(R.id.text);
        date = (TextView) findViewById(R.id.date);

        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        final int photoId = intent.getIntExtra("photo", -1);
        if (photoId == -1)
            return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                PhotoDAO photoDAO = new PhotoDAO(DetailActivity.this);
                List<Photo> photosDb = photoDAO.query(new String[]{"_id", "pic", "note", "date"},
                        "_id" + "=?", new String[]{String.valueOf(photoId)}, new PhotoCursorHandler());
                Logger.d(TAG, "photosDb:" + photosDb);
                if (photosDb == null || photosDb.size() == 0)
                    return;
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = photosDb;
                UiHandler.sendMessage(msg);
            }
        }).start();

    }

    public static class MyHandler extends Handler {
        private WeakReference<DetailActivity> activity;

        public MyHandler(DetailActivity detailActivity) {
            super();
            this.activity = new WeakReference<>(detailActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DetailActivity detailActivity = activity.get();
            if (detailActivity == null)
                return;
            switch (msg.what) {
                case 1:
                    List<Photo> photos = (List<Photo>) msg.obj;
                    Photo photo = photos.get(0);
                    detailActivity.imageView.setImageBitmap(photo.getPic());
                    detailActivity.note.setText(photo.getNote());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(photo.getDate());
                    detailActivity.date.setText(calendar.get(Calendar.YEAR) + "年"
                            + (calendar.get(Calendar.MONTH) + 1)
                            + "月" + calendar.get(Calendar.DATE) + "日");
                    break;
            }
        }
    }
}
