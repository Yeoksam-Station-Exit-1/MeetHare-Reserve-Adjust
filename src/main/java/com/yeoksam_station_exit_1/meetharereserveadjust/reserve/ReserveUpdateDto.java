package com.yeoksam_station_exit_1.meetharereserveadjust.reserve;

import java.util.List;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class ReserveUpdateDto {

  private String roomCode;
  private LocalDate reserveTime;
  private List<String> reserveMembers;

  public boolean isReserveUpdateEmpty() {

    return Stream.of(roomCode, reserveTime, reserveMembers)
        .anyMatch(Objects::isNull);
  }
}