package com.yeoksam_station_exit_1.meetharereserveadjust.reserve;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<ReserveEntity, Long> {

  Optional<ReserveEntity> findByRoomCode(String roomCode);

  List<ReserveEntity> findByReserveTime(LocalDate localDate);

  void deleteByRoomCode(String roomCode);
}
