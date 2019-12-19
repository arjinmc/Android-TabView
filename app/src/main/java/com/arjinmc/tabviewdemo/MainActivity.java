package com.arjinmc.tabviewdemo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.arjinmc.tabview.TabItemView;
import com.arjinmc.tabview.TabView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabView tabView = findViewById(R.id.tabview);
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
