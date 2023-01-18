package com.example.main.caraiclientdesktop.data;

import lombok.Data;

@Data
public class CPUData {
  private String name;
  private String vendor;
  private String model;
  private String architecture;
  private double cpuLoad;
  private double frequency;
  private double temperature;

}