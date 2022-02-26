package Models;

import android.content.Intent;

public interface IBackgroundModel {
    //从相册中选择
    Intent photo();
    //拍照
    Intent camera();
    //获取用户资料
    void gain();
    //修改用户头像
    void head();
    //修改背景
    void Background();
}
