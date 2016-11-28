package presenter;

/**
 * Created by www.scclaptop.com on 11/19/2016.
 */
public interface LoginView {
    void showValidationError();
    void loginSucces();
    void loginError();
    void beforeLogin();
}
