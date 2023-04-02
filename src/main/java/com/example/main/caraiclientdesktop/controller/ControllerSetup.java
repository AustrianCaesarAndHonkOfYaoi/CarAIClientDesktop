package com.example.main.caraiclientdesktop.controller;

import com.example.main.caraiclientdesktop.logic.CommonCommands;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;

public class ControllerSetup implements Initializable {
    @FXML
    private TextField inputIP;
    @FXML
    private CheckBox checkBoxManual;


    private final static HttpClient httpClient = HttpClient.newBuilder().build();
    private final static CommonCommands commands = new CommonCommands();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getCurrentStateOfFailsafe();
        addListener();
    }

    public void connectToDevice() {
        checkIP();
    }

    public void checkIP() {
        InetAddressValidator validator = InetAddressValidator.getInstance();
        boolean t = validator.isValidInet4Address(inputIP.getText());
        if (t) {
            commands.writeToFile("src\\assets\\address.txt", inputIP.getText());
        }

    }

    private void getCurrentStateOfFailsafe() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/failsafe/getCurrentFailsafe"))
                    .GET()
                    .build();

            //JSON FILE handlen
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            String result = "null";

            for (int i = response.body().length() - 1; i > 0; i--) {
                if (response.body().charAt(i) == ' ') {
                    result = response.body().substring(i + 1);
                    break;
                }
            }

            System.out.println(result);
            String savedContent = commands.readFile("src\\assets\\savedFailStatus.txt");
            if (savedContent.equals("false") && result.equals("true")) {
                disable();
                checkBoxManual.setSelected(false);

            }
            if (savedContent.equals("true") && result.equals("false")) {
                checkBoxManual.setSelected(true);

                enable();
            }
            if (savedContent.equals("true") && result.equals("true")) {
                checkBoxManual.setSelected(true);
            }


        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



    private void disable() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/failsafe/disableManualFailsafe"))
                    .GET()
                    .build();

            //JSON FILE handlen
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void enable() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/failsafe/enableManualFailsafe"))
                    .GET()
                    .build();

            //JSON FILE handlen
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void addListener() {
        checkBoxManual.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!checkBoxManual.isSelected()) {
                disable();
            } else {
                enable();
            }
            commands.writeToFile("src\\assets\\savedFailStatus.txt","" + checkBoxManual.isSelected());
        });
    }

    public CheckBox getCheckBoxManual() {
        return checkBoxManual;
    }

    public TextField getInputIP() {
        return inputIP;
    }
}
