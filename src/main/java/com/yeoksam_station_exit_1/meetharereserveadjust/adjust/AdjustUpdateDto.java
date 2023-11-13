package com.yeoksam_station_exit_1.meetharereserveadjust.adjust;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class AdjustUpdateDto {

  private String roomCode;
  private String adjustOwner;
  private int adjustAmount;
  private String adjustInfo;

  public boolean isAdjustUpdateEmpty() {

    return Stream.of(roomCode, adjustAmount, adjustInfo)
        .anyMatch(Objects::isNull);
  }
}