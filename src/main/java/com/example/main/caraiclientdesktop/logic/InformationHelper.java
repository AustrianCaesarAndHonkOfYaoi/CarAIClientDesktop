package com.example.main.caraiclientdesktop.logic;

import com.example.main.caraiclientdesktop.controller.ControllerMain;
import com.example.main.caraiclientdesktop.data.CPUData;
import com.example.main.caraiclientdesktop.data.GBMemoryData;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.TimerTask;

public class InformationHelper extends TimerTask {

    private ControllerMain cM;

    public InformationHelper(ControllerMain cM) {
        this.cM = cM;
    }

    @Override
    public void run() {


    }



    public void setcM(ControllerMain cM) {
        this.cM = cM;
    }

}
