package com.yeoksam_station_exit_1.meetharereserveadjust.adjust;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AdjustMakeUpDto {

  private String adjustOwner;
  private String roomCode;
  private int adjustAmount;
  private String adjustInfo;
}