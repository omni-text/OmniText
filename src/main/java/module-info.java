module com.raa.omnitext {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.raa.omnitext to javafx.fxml;
    exports com.raa.omnitext;
}