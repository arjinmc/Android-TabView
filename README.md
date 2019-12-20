# Android-TabView
Ajustable Indicator TabView.

<img src="https://github.com/arjinmc/Android-TabView/blob/master/images/device-2019-12-20-140730.png" width="30%" height="30%">

## How to use

### TabView
Add it in your layout.xml
```
<com.arjinmc.tabview.TabView
    android:id="@+id/tabview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:TabView_tabNumberPaddingHorizontal="7dp"
    app:TabView_tabNumberPaddingVertical="4dp"
    app:TabView_tabNumberTextSize="@dimen/tabview_item_number_text_size"
    app:TabView_tabTitleTextColor="@drawable/front_tab_text"
    app:TabView_tabTitleTextSize="14sp"
    app:TabView_tabItemPaddingHorizontal="10dp"
    app:TabView_tabItemPaddingVertical="2dp"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
```
Add your items like this
```
mTabView = findViewById(R.id.tabview);
TabItemView tabHome = mTabView.newItem();
tabHome.setId(R.id.tab_home);
tabHome.setTitle("Home");
tabHome.setNumber(2);
tabHome.setTitleTextSize(R.dimen.tabview_item_title_text_size);
mTabView.addItem(tabHome);
```
Get the selected change item info.
```
mTabView.setOnSelectedChangeListener(new TabView.OnSelectedChangeListener() {
        @Override
        public void onSelected(int itemId) {
            Log.i("onSelected", print(itemId));
        }

        @Override
        public void onReleaseSelect(int itemId) {
            Log.i("onReleaseSelect", print(itemId));
        }
    });
```

#### Attributes of TabView

Attribute | Note
---- | ---
TabView_tabItemPaddingHorizontal | The item padding left and right.
TabView_tabItemPaddingVertical | The item padding top and bottom.
TabView_tabTitleTextColor | The item title color.
TabView_tabTitleTextSize  | The item title text size.
TabView_tabNumberTextColor | The item number text color.
TabView_tabNumberTextSize | The item number text size.
TabView_tabNumberPaddingHorizontal | The item number padding left and right.
TabView_tabNumberPaddingVertical | The item number padding top and bottom.
TabView_tabNumberBackground | The item number background.
TabView_tabNumberMarginLeft | The item number margin left.
TabView_tabNumberMarginTop | The item number margin top.
TabView_tabNumberSize | The item number layout size (width=height)

#### Methods of TabView
You can use setXXX to change attributes. Here is other methods.

Method | Note
---- | ---
setSelectedItemId(itemId) | Set selected item by id without animation.
setSelectedItemPosition(position) | Set selected position by id without animation.
scrollToPreviewPosition() | Scroll to preview position.
scrollToNextPosition() | Scroll to next position.
scrollToItem(itemId) | Scroll to position of item which is itemId.
scrollToPosition(position) | Scroll to position of item.
getSelectedItemId() | Return current selected item id.
getSelectedItemPosition() | Return current selected item position.
bindIndicatorView(TabIndicatorView) | Bind a TabIndicatorView for auto change selected item.
        
### TabIndicatorView
If you want a IndicatorView,just add it.
```
<com.arjinmc.tabview.TabIndicatorView
    android:id="@+id/tabIndicatorview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:TabIndicatorView_color="@color/colorPrimary"
    app:TabIndicatorView_itemCount="3"
    app:TabIndicatorView_paddingHorizontal="6dp"
    app:TabIndicatorView_thickness="4dp"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintTop_toBottomOf="@id/tabview" />
```
Remember bind it to TabView,so that it will auto change selected position when TabView selected item is changed. Like:
```
mTabView.bindIndicatorView(mTabIndicatorView);
```

#### Attributes of TabIndicatorView

Attribute | Note
---- | ---
TabIndicatorView_color | The color of TabIndicatorView.
TabIndicatorView_itemCount | The item count of TabView.
TabIndicatorView_thickness | The height of TabIndicatorView.
TabIndicatorView_paddingHorizontal | The padding left and padding right of TabIndicatorView.

You also can use setXXX to set the attributes.

## License
```
   Copyright 2019 arjinmc

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
