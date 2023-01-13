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
      //  changeImageRequest();
    }

  /*  private void changeImageRequest() {

        DateTimeFormatter date = DateTimeFormatter.ISO_INSTANT;
        String name = date.format(Instant.now());
        Image img = new Image();
        img.setSrc(new StreamResource(name, () -> {
            try {
                URL url = new URL("http://localhost:8080/camera/getPicture");
                InputStream fis = url.openStream();
                return fis;
            } catch (FileNotFoundException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
        ));
        cM.getCurrentImage().setImage(img);
    }*/

    public void setcM(ControllerMain cM) {
        this.cM = cM;
    }
}
