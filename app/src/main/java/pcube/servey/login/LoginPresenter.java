package pcube.servey.login;

public class LoginPresenter implements ILoginPresenter {
    ILognView lognView;

    public LoginPresenter(ILognView lognView) {
        this.lognView = lognView;
    }

    @Override
    public void doLogin(String email, String password)
    {
        if(email.equals(""))
        {
            lognView.showMessage("Pleas fill this");
        }
        else  if(password.equals(""))
        {
            lognView.showMessage("Pleas fill this");
        }
        else
        {
            lognView.showMessage("Success");
        }




    }
}
