package me.zsr.rss.common;

import android.content.ActivityNotFoundException;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.MotionEvent;
import android.widget.TextView;

public class LinkMovementMethodEx extends LinkMovementMethod {
    private static LinkMovementMethodEx sInstance;
    private OnPicClickListener mPicClickListener;

    public static LinkMovementMethodEx getInstance() {
        if (sInstance == null) {
            sInstance = new LinkMovementMethodEx();
        }

        return sInstance;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {

        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ImageSpan[] images = buffer.getSpans(off, off, ImageSpan.class);

            if (images.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    if (mPicClickListener != null) {
                        mPicClickListener.onPicClick(images[0].getSource());
                    }
                }
                return true;
            }
        }

        try {
            return super.onTouchEvent(widget, buffer, event);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public interface OnPicClickListener {
        void onPicClick(String url);
    }

    public void setOnPicClickListener(OnPicClickListener listener) {
        mPicClickListener = listener;
    }
}
