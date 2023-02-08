package com.example.main.caraiclientdesktop.controller;



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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


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
    private ControllerMain currentMainController;
    private final static HttpClient httpClient = HttpClient.newBuilder().build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private GBMemoryData memoryData;
    private CPUData cpuData;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        velocitySlide.setMin(1);
        velocitySlide.setMax(3);
        velocitySlide.setBlockIncrement(0.5);

        setRunners();

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


    public void setCurrentMainController(ControllerMain currentMainController) {
        this.currentMainController = currentMainController;
    }

    public void setRunners() {

        Runnable imageRequest = () -> {
            try {
                URL url = new URL("http://localhost:8080/camera/getPicture");
                InputStream fis = url.openStream();
                Image image = new Image(fis);

                currentImage.setImage(image);
            } catch (FileNotFoundException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        };
        Runnable infos = () -> {
            getData("http://localhost:8080/monitoring/memory/gb");
            getData("http://localhost:8080/monitoring/cpu");
            setLabels();

        };
        ScheduledExecutorService imageExecutor = Executors
                .newSingleThreadScheduledExecutor();

        ScheduledExecutorService informationExecutor = Executors
                .newSingleThreadScheduledExecutor();
        imageExecutor.scheduleAtFixedRate(imageRequest, 0, 3000, TimeUnit.MILLISECONDS);
        informationExecutor.scheduleAtFixedRate(infos, 0, 3000, TimeUnit.MILLISECONDS);
    }

    private void getData(String link) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(link))
                    .GET()
                    .build();

            //JSON FILE handlen
            HttpResponse<String> json = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (link.contains("memory")) {
                memoryData = objectMapper.readValue(json.body(), GBMemoryData.class);

            } else {
                cpuData = objectMapper.readValue(json.body(), CPUData.class);

            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setLabels() {


        Platform.runLater(() -> {


            nameL.setText(cpuData.getName());


            cpuL.setText("" + cpuData.getCpuLoad());


            architectureL.setText(cpuData.getArchitecture());


            temperatureL.setText("" + cpuData.getTemperature());


            modelL.setText(cpuData.getModel());


            frequenzyL.setText("" + cpuData.getFrequency());


            vendorL.setText(cpuData.getVendor());


            totalRAM.setText("" + memoryData.getTotal());


        });


    }
}