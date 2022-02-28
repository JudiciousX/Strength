package Tool;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class LoopViewGestureListener extends GestureDetector.SimpleOnGestureListener {
    final LoopView loopView;

    LoopViewGestureListener(LoopView loopview) {
        super();
        loopView = loopview;
    }

    public final boolean onDown(MotionEvent motionevent) {
        if (loopView.mTimer != null) {
            loopView.mTimer.cancel();
        }
        return true;
    }

    public final boolean onFling(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1) {
        loopView.b(f1);
        return true;
    }
}
