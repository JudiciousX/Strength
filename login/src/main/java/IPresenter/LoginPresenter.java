package IPresenter;

import android.content.Context;
import android.os.Handler;

import IModel.ILoginModel;
import IModel.LoginModel;
import IView.ILoginView;

public class LoginPresenter  {
    private ILoginModel model;
    private ILoginView view;

    public LoginPresenter(ILoginView view) {
        this.view = view;
        model = new LoginModel();
    }

    public ILoginModel getModel() {
        return model;
    }
}
