package com.example.main.caraiclientdesktop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ControllerSetup implements Initializable {
    @FXML
    private TextField inputIP;
    @FXML
    private CheckBox checkBoxManual;


    private final static HttpClient httpClient = HttpClient.newBuilder().build();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getCurrentStateOfFailsafe();
        addListener();
    }

    public void connectToDevice() {
        checkIP();
    }

    private void checkIP() {
        InetAddressValidator validator = InetAddressValidator.getInstance();
        boolean t = validator.isValidInet4Address(inputIP.getText());
        System.out.println(t);


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
            String savedContent= readFile();
            if(savedContent.equals("false")&&result.equals("true")){
               disable();
               checkBoxManual.setSelected(false);

            }
            if(savedContent.equals("true")&&result.equals("false")){
                checkBoxManual.setSelected(true);

                enable();
            }
            if(savedContent.equals("true")&&result.equals("true")){
                checkBoxManual.setSelected(true);
            }


        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String readFile() {
        FileInputStream inputStream = null;
        Scanner sc = null;
        String content = "";
        try {

            inputStream = new FileInputStream("src\\assets\\savedFailStatus.txt");
            sc = new Scanner(inputStream, StandardCharsets.UTF_8);


            while (sc.hasNextLine()) {
               content = sc.nextLine();
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sc != null) {
                sc.close();
            }
        }
        return content;
    }
    private void disable()   {
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
    private void enable()   {
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
    private void addListener(){
        checkBoxManual.selectedProperty().addListener((observable, oldValue, newValue) -> {
           if(!checkBoxManual.isSelected()){
               disable();
               writeToFile();


           }else{
               enable();
               writeToFile();

           }

        });
    }
    public void writeToFile() {
        try {
            File fout = new File("src\\assets\\savedFailStatus.txt");
            FileOutputStream fos = new FileOutputStream(fout);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(""+checkBoxManual.isSelected());
            bw.close();
        } catch (IOException ignored) {

        }
    }
}
