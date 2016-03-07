package com.plusend.photo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.plusend.photo.R;
import com.plusend.photo.db.PhotoDAO;
import com.plusend.photo.db.handler.PhotoCursorHandler;
import com.plusend.photo.model.Photo;
import com.plusend.photo.utils.log.Logger;
import com.plusend.photo.view.adapter.RvAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class LineActivity extends AppCompatActivity {
    private static final String TAG = "LineActivity";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RvAdapter rvAdapter;
    private List<Photo> photos = new ArrayList<>();

    private MyHandler UiHandler = new MyHandler(LineActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PhotoDAO photoDAO = new PhotoDAO(LineActivity.this);
                        List<Photo> photosDb = photoDAO.query(new String[]{"_id", "pic", "note", "date"},
                                null, null, new PhotoCursorHandler());
                        Logger.d(TAG, "photosDb:" + photosDb);
                        Message msg = Message.obtain();
                        msg.what = 1;
                        msg.obj = photosDb;
                        UiHandler.sendMessage(msg);
                    }
                }).start();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        rvAdapter = new RvAdapter(photos);
        rvAdapter.setItemClickListener(new RvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Logger.d(TAG, "onItemClick");
                Intent intent = new Intent(LineActivity.this, DetailActivity.class);
                intent.putExtra("photo", photos.get(position).getId());
                startActivity(intent);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(rvAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LineActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });

    }

    public static class MyHandler extends Handler {
        private WeakReference<LineActivity> activity;

        public MyHandler(LineActivity lineActivity) {
            super();
            this.activity = new WeakReference<>(lineActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LineActivity line = activity.get();
            if (line == null)
                return;
            switch (msg.what) {
                case 1:
                    line.photos.clear();
                    line.photos.addAll((List<Photo>) msg.obj);
                    line.rvAdapter.notifyDataSetChanged();
                    line.swipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_line, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
