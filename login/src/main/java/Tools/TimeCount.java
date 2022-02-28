package Tools;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.login.R;

public class TimeCount extends CountDownTimer {
    private TextView send;
    public TimeCount(long millisInFuture, long countDownInterval, TextView send) {
        super(millisInFuture, countDownInterval);
        this.send = send;
    }

    @Override
    public void onTick(long l) {
        send.setBackgroundResource(R.drawable.shape2);
        send.setClickable(false);
        send.setText("(" + l / 1000 + ") 秒后可重发");
    }

    @Override
    public void onFinish() {
        send.setText("重新获取验证码");
        send.setClickable(true);
        send.setBackgroundResource(R.drawable.shape1);
    }
}
