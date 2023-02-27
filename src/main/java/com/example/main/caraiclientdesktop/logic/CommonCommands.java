package com.example.main.caraiclientdesktop.logic;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CommonCommands {

    public String readFile(String source) {
        FileInputStream inputStream = null;
        Scanner sc = null;
        String content = "";
        try {

            inputStream = new FileInputStream(source);
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
    public void writeToFile(String source,String content) {
        try {
            File fout = new File(source);
            FileOutputStream fos = new FileOutputStream(fout);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(content);
            bw.close();
        } catch (IOException ignored) {

        }
    }
}
