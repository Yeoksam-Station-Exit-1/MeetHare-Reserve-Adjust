package com.yeoksam_station_exit_1.meetharereserveadjust.adjust;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdjustRepository extends JpaRepository<AdjustEntity, Long> {

  Optional<AdjustEntity> findByAdjustOwnerAndRoomCode(String adjustOwner, String roomCode);

  void deleteByAdjustOwnerAndRoomCode(String adjustOwner, String roomCode);
}