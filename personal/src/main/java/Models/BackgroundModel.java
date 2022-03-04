package Models;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

public class BackgroundModel implements IBackgroundModel{
    @Override
    public Intent photo() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        return intent;

    }

    @Override
    public Intent camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
