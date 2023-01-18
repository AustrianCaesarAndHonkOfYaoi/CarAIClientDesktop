package com.example.main.caraiclientdesktop.logic;

import com.example.main.caraiclientdesktop.controller.ControllerMain;
import com.example.main.caraiclientdesktop.data.CPUData;
import com.example.main.caraiclientdesktop.data.GBMemoryData;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.TimerTask;

public class InformationHelper extends TimerTask {
    private final static HttpClient httpClient = HttpClient.newBuilder().build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private GBMemoryData memoryData;
    private CPUData cpuData;
    private ControllerMain cM;
    @Override
    public void run() {
        getData("http://localhost:8080/monitoring/memory/gb");
        getData("http://localhost:8080/monitoring/cpu");
       // setLabels();

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
    private void setLabels(){
        Label label;
        label=cM.getNameL();
        label.setText(cpuData.getName());

        label=cM.getCpuL();
        label.setText(""+cpuData.getCpuLoad());

        label=cM.getArchitectureL();
        label.setText(cpuData.getArchitecture());

        label=cM.getTemperatureL();
        label.setText(""+cpuData.getTemperature());

        label=cM.getModelL();
        label.setText(cpuData.getModel());

        label=cM.getFrequenzyL();
        label.setText(""+cpuData.getFrequency());

        label=cM.getVendorL();
        label.setText(cpuData.getVendor());

        label=cM.getTotalRAM();
        label.setText(""+memoryData.getTotal());



    }
    public void setcM(ControllerMain cM){
        this.cM = cM;
    }

}
