package com.yeoksam_station_exit_1.meetharereserveadjust.adjust;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdjustRepository extends JpaRepository<AdjustEntity, String> {

  Optional<AdjustEntity> findByRoomCode(String roomCode);

  void deleteByRoomCode(String roomCode);
}