package com.yeoksam_station_exit_1.meetharereserveadjust.adjust;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AdjustMakeUpDto {

  private String roomCode;
  private Timestamp adjustTime;
  private String adjustInfo;
}