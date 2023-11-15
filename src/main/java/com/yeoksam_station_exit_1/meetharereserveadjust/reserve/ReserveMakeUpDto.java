package com.yeoksam_station_exit_1.meetharereserveadjust.reserve;

import java.util.List;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReserveMakeUpDto {

  private String roomCode;
  private LocalDate reserveTime;
  private List<String> reserveMembers;
}