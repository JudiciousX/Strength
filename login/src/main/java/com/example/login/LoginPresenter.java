package com.example.login;

public class LoginPresenter implements ILoginPresenter{
    private ILoginModel model;
    private ILoginView view;

    public LoginPresenter(ILoginView view) {
        this.view = view;
        model = new LoginModel();
    }

    @Override
    public boolean isSucceed(String id, String password) {
        if(model.result(id, password).equals("succeed")) {
            return true;
        }else {
            return false;
        }
    }

    public ILoginModel getModel() {
        return model;
    }
}
