package com.yeoksam_station_exit_1.meetharereserveadjust.reserve;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReserveMakeUpDto {

  private String roomCode;
  private Timestamp reserveTime;
  private String reservePlace;
}