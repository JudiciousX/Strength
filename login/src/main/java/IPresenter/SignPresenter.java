package IPresenter;

import IModel.ISignModel;
import IModel.SignInModel;
import IView.ISignView;

public class SignPresenter {
    private ISignModel model;
    private ISignView view;

    public SignPresenter(ISignView view) {
        this.view = view;
        model = new SignInModel();
    }

    public ISignModel getModel() {
        return model;
    }
}
