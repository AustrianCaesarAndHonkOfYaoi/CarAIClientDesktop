package com.example.main.caraiclientdesktop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.net.Inet4Address;
import java.net.InetAddress;

public class ControllerSetup {
    @FXML
    private TextField inputIP;
    public void connectToDevice() {
        checkIP();
    }
    private void checkIP(){
       InetAddressValidator validator = InetAddressValidator.getInstance();
       validator.isValidInet4Address(inputIP.getText());

    }
}
