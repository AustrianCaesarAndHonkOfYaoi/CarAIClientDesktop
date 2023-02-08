package com.example.main.caraiclientdesktop.logic;

import com.example.main.caraiclientdesktop.controller.ControllerMain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class ImageHelper extends TimerTask {
    private ControllerMain cM;
    private ImageView iV;



    @Override
    public void run() {
        changeImageRequest();
    }

    private void changeImageRequest() {



    }


}
