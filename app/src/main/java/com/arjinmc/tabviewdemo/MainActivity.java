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

    private TabView mTabView;
    private TabIndicatorView mTabIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabView = findViewById(R.id.tabview);
        TabItemView tabHome = mTabView.newItem();
        tabHome.setId(R.id.tab_home);
        tabHome.setTitle("Home");
        tabHome.setNumber(2);
        tabHome.setTitleTextSize(R.dimen.tabview_item_title_text_size);
        mTabView.addItem(tabHome);

        TabItemView tabComment = mTabView.newItem();
        tabComment.setId(R.id.tab_comment);
        tabComment.setTitle("Comment");
        tabComment.setMaxNumber(99);
        tabComment.setMaxNumberText("...");
        tabComment.setNumber(200);
        mTabView.addItem(tabComment);

        TabItemView tabNews = mTabView.newItem();
        tabNews.setId(R.id.tab_news);
        tabNews.setTitle("News");
        mTabView.addItem(tabNews);

        mTabView.setOnSelectedChangeListener(new TabView.OnSelectedChangeListener() {
            @Override
            public void onSelected(int position, TabItemView tabItemView) {
                Log.i("onSelected", print(tabItemView.getId()));
            }

            @Override
            public void onReleaseSelect(int position, TabItemView tabItemView) {
                Log.i("onReleaseSelect", print(tabItemView.getId()));
            }
        });

        mTabIndicatorView = findViewById(R.id.tabIndicatorview);
        //bing TabIndicatorView if you need.Once bind TabIndicatorView will auto change the selected position itself
        mTabView.bindIndicatorView(mTabIndicatorView);

        //set init index if you need
//        mTabView.setSelectedItemId(R.id.tab_news);
        //or
//        mTabView.setSelectedItemPosition(2);

        Button btnPreview = findViewById(R.id.btn_preview_position);
        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTabView.scrollToPreviewPosition();
            }
        });

        Button btnNext = findViewById(R.id.btn_next_position);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTabView.scrollToNextPosition();
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
