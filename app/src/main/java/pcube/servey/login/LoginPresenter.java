package pcube.servey.login;

public class LoginPresenter implements ILoginPresenter {
    ILognView lognView;

    public LoginPresenter(ILognView lognView) {
        this.lognView = lognView;
    }

    @Override
    public void doLogin(String email, String password)
    {

        lognView.showMessage("");

    }
}
