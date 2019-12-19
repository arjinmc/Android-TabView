package com.arjinmc.tabviewdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.arjinmc.tabview.TabIndicatorView;
import com.arjinmc.tabview.TabItemView;
import com.arjinmc.tabview.TabView;

public class MainActivity extends AppCompatActivity {

    private int mCurrentPosition;
    private TabIndicatorView tabIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TabView tabView = findViewById(R.id.tabview);
        TabItemView tabHome = tabView.newItem();
        tabHome.setId(R.id.tab_home);
        tabHome.setTitle("Home");
        tabHome.setNumber(2);
        tabHome.setTitleTextSize(R.dimen.tabview_item_title_text_size);
        tabView.addItem(tabHome);

        TabItemView tabComment = tabView.newItem();
        tabComment.setId(R.id.tab_comment);
        tabComment.setTitle("Comment");
        tabComment.setMaxNumber(99);
        tabComment.setMaxNumberText("...");
        tabComment.setNumber(200);
        tabView.addItem(tabComment);

        TabItemView tabNews = tabView.newItem();
        tabNews.setId(R.id.tab_news);
        tabNews.setTitle("News");
        tabView.addItem(tabNews);

        tabView.setOnSelectedChangeListener(new TabView.OnSelectedChangeListener() {
            @Override
            public void onSelected(int itemId) {
                Log.i("onSelected", print(itemId));
            }

            @Override
            public void onReleaseSelect(int itemId) {
                Log.i("onReleaseSelect", print(itemId));
            }
        });

        tabIndicatorView = findViewById(R.id.tabIndicatorview);

        mCurrentPosition = 0;
        Button btnNext = findViewById(R.id.btn_next_position);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPosition++;
                if (mCurrentPosition >= tabView.getChildCount()) {
                    mCurrentPosition = 0;
                }
                tabIndicatorView.setSelectPosition(mCurrentPosition);
            }
        });
    }

    private String print(int itemId) {
        switch (itemId) {
            case R.id.tab_home:
                return "Home";
            case R.id.tab_comment:
                return "Comment";
            case R.id.tab_news:
                return "News";
            default:
                break;
        }
        return "";
    }
}
