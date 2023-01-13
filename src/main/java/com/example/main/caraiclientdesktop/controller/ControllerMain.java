package com.example.main.caraiclientdesktop.controller;


import com.example.main.caraiclientdesktop.logic.ImageHelper;
import com.example.main.caraiclientdesktop.logic.InformationHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.main.caraiclientdesktop.ApplicationMain;
import com.example.main.caraiclientdesktop.data.CPUData;
import com.example.main.caraiclientdesktop.data.GBMemoryData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;
import java.util.Timer;


public class ControllerMain implements Initializable {
    @FXML
    private Label nameL;
    @FXML
    private Label modelL;
    @FXML
    private Label vendorL;
    @FXML
    private Label architectureL;
    @FXML
    private Label temperatureL;
    @FXML
    private Label frequenzyL;
    @FXML
    private Label cpuL;
    @FXML
    private Label totalRAM;
    @FXML
    private Slider velocitySlide;
    @FXML
    private ImageView currentImage;

    private final static HttpClient httpClient = HttpClient.newBuilder().build();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        velocitySlide.setMin(1);
        velocitySlide.setMax(3);
        velocitySlide.setBlockIncrement(0.5);

        Timer timer = new Timer();
        InformationHelper infoHelper = new InformationHelper();
        ImageHelper imgHelper = new ImageHelper();

        imgHelper.setcM(this);
        infoHelper.setcM(this);

       // timer.schedule(infoHelper, 3000);
       // timer.schedule(imgHelper, 3000);
    }

    public void forwards() {
        sendRequestToControl("http://localhost:8080/car/moveForward/2000/");

    }

    public void left() {

        sendRequestToControl("http://localhost:8080/car/steerLeft/1000/");

    }

    public void back() {

        sendRequestToControl("http://localhost:8080/car/moveBackwards/2000/");

    }

    public void right() {

        sendRequestToControl("http://localhost:8080/car/steerRight/1000/");
    }

    private void sendRequestToControl(String link) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(link))
                    .GET()
                    .build();

            //JSON FILE handlen
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());


        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void goToSetup() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("ControllerSetup.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setFullScreen(false);
            stage.setTitle("Car AI Setup");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ignored) {

        }
    }

    public void exitApp() {
        Platform.exit();
    }

    public void goToAbout() {
    }


    public Label getNameL() {
        return nameL;
    }

    public Label getModelL() {
        return modelL;
    }

    public Label getVendorL() {
        return vendorL;
    }

    public Label getArchitectureL() {
        return architectureL;
    }

    public Label getTemperatureL() {
        return temperatureL;
    }

    public Label getFrequenzyL() {
        return frequenzyL;
    }

    public Label getCpuL() {
        return cpuL;
    }

    public Label getTotalRAM() {
        return totalRAM;
    }

    public ImageView getCurrentImage() {
        return currentImage;
    }
}