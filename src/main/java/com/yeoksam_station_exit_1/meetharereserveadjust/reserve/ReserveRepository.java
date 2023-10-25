package com.yeoksam_station_exit_1.meetharereserveadjust.reserve;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<ReserveEntity, String> {

  Optional<ReserveEntity> findByRoomCode(String roomCode);

  void deleteByRoomCode(String roomCode);
}
