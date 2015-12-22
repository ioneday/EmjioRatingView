# EmjioRatingView
Rate library for Android，Using Emoji icon to interactive, with:  :) = Positive. :| = Neutral. :( = Negative

The Android implementation of [Rating Version A](https://dribbble.com/shots/2211556-Rating-Version-A), designed by [Hoang Nguyen](https://dribbble.com/Hoanguyen).


![](https://raw.githubusercontent.com/ioneday/EmjioRatingView/master/screenshot/ratingview.gif)

## Quick start

1) Add your `layout.xml`


```java
<com.yongchun.emjioratingview.RatingView
        android:id="@+id/rating_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="center" />
```

2) set `OnRateChangeListener`

```java
RatingView view = (RatingView) findViewById(R.id.rating_view);
view.setOnRateChangeListener(new RatingView.OnRateChangeListener() {
    @Override
    public void onChanged(float ratio) {
        DecimalFormat df = new DecimalFormat("0.0");
        score.setText(df.format(ratio*5));
    }
});
```

###License
>The MIT License (MIT)

>Copyright (c) 2015 Dee

>Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

>The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

