## Converting vector drawable to Bitmap
The library contains the util class to allow you to convert almost any drawable to a Bitmap. For example:
```java
DisplayMetrics metrics = context.getResources().getDisplayMetrics();
Bitmap bitmap = BitmapUtil.toBitmap(sourceDrawable, metrics, 60f, 120f);
```
That snippet will convert `sourceDrawable` to a `Bitmap` with size depending on device screen density.

You can also set size only for one side then the other side will match aspect ratio if the `Drawable`'s `getIntrinsicWidth()` and `getIntrinsicHeight()` return values greater than zero. For example:
```java
DisplayMetrics metrics = context.getResources().getDisplayMetrics();
Bitmap bitmap = BitmapUtil.toBitmap(sourceDrawable, metrics, 60f, 0);
```
Result `Drawable` will have 60dp width and the height is determined by its aspect ratio.

For cases when you are not sure that `Drawable`'s `getIntrinsicWidth()` and `getIntrinsicHeight()` return values greater than zero you can provide fallback values that will be used if method fails to acquire
the aspect ratio of source drawable. For example:
```java
GradientDrawable gradientDrawable = new GradientDrawable();
gradientDrawable.setColor(Color.BLUE);
DisplayMetrics metrics = context.getResources().getDisplayMetrics();
Bitmap bitmap = BitmapUtil.toBitmap(gradientDrawable, metrics, 60f, 0, 60f, 60f);
```

In that case resultant `Bitmap` will have 60dp x 60dp size, because `GradientDrawable#getIntrinsicHeight()` returns `-1`.
