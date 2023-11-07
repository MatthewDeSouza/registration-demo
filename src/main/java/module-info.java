module com.github.matthewdesouza.registrationdemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.matthewdesouza.registrationdemo to javafx.fxml;
    exports com.github.matthewdesouza.registrationdemo;
}