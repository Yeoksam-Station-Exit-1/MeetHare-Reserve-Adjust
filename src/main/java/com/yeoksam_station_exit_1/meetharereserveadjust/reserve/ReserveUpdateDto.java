package com.yeoksam_station_exit_1.meetharereserveadjust.reserve;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class ReserveUpdateDto {

  private String roomCode;
  private Timestamp reserveTime;
  private String reservePlace;

  public boolean isReserveUpdateEmpty() {

    return Stream.of(roomCode, reserveTime, reservePlace)
        .anyMatch(Objects::isNull);
  }
}