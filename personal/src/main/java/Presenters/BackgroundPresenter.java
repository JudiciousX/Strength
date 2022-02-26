package Presenters;

import IView.IBackgroundView;
import Models.BackgroundModel;
import Models.IBackgroundModel;

public class BackgroundPresenter {
    private IBackgroundModel model;
    private IBackgroundView view;

    public BackgroundPresenter(IBackgroundView view) {
        this.view = view;
        model = new BackgroundModel();
    }

    public IBackgroundModel getModel() {
        return model;
    }
}
