package IPresenter;

import IModel.ForgetModel;
import IModel.IForgetModel;
import IModel.ISignModel;
import IModel.SignInModel;
import IView.IForgetView;
import IView.ISignView;

public class SignPresenter {
    private ISignModel model;
    private ISignView view;
    private IForgetView view2;
    private IForgetModel model2;

    public SignPresenter(ISignView view) {
        this.view = view;
        model = new SignInModel();
        model2 = new ForgetModel();
    }


    public ISignModel getModel() {
        return model;
    }

    public IForgetModel getModel2() {
        return model2;
    }
}
