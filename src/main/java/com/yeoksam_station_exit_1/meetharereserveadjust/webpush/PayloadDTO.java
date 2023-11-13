package com.yeoksam_station_exit_1.meetharereserveadjust.webpush;

import lombok.Data;

@Data
public class PayloadDTO {

  private String sub;
  private int exp;
  private String email;
}
