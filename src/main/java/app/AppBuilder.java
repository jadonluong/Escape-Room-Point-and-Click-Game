package app;

import application.use_cases.User.Login.LoginInteractor;
import application.use_cases.User.Login.LoginUserDataAccessInterface;
import domain.entities.User.CommonUserFactory;
import domain.entities.User.CommonUserFactoryClass;
import interface_adapter.User.Login.LoginController;
import interface_adapter.User.Login.LoginPresenter;
import interface_adapter.User.Login.LoginViewModel;
import interface_adapter.User.Signup.SignupController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewManager;
import view.mainmenu.MainMenuView;

public class AppBuilder extends Application {

    @Override
    public void start(Stage primaryStage) {

        ViewManager viewManager = new ViewManager(primaryStage);

        /*
         * Login ViewModel
         * Stores UI state (errors, login status, etc.)
         */
        LoginViewModel loginViewModel = new LoginViewModel();


        /*
         * Login Presenter
         * Updates the ViewModel after the use case finishes
         */
        LoginPresenter loginPresenter =
                new LoginPresenter(loginViewModel);


        /*
         * Login Interactor
         * TODO:
         * Replace null with your team's actual DAO implementation.
         */
        LoginUserDataAccessInterface loginDAO = null;


        CommonUserFactory userFactory = new CommonUserFactoryClass();


        LoginInteractor loginInteractor =
                new LoginInteractor(
                        loginDAO,
                        loginPresenter,
                        userFactory
                );


        /*
         * Login Controller
         * Passed into the View layer.
         */
        LoginController loginController =
                new LoginController(loginInteractor);


        /*
         * Main Menu View
         * Receives controllers/viewmodels, NOT interactors.
         */
        MainMenuView mainMenu =
                new MainMenuView(
                        viewManager,
                        loginController,
                        loginViewModel,
                        //signup controller
                );


        primaryStage.setTitle("Escapists");

        viewManager.show(mainMenu);
    }
}
