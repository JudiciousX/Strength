package Models;

import android.content.Intent;
import android.provider.MediaStore;

public class BackgroundModel implements IBackgroundModel{
    @Override
    public Intent photo() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        return intent;
    }

    @Override
    public Intent camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("111", "00");
        return intent;
    }

    @Override
    public void gain() {

    }

    @Override
    public void head() {

    }

    @Override
    public void Background() {

    }


}
