package com.yeoksam_station_exit_1.meetharereserveadjust.reserve;

import java.text.ParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("api/reserve")
@RestController
@Tag(name = "Reserve", description = "예약 관련 API 입니다.")
public class ReserveController {

  private final ReserveService reserveService;

  /**
   * Reserve 생성
   *
   * @return
   * @throws ParseException
   */
  @Operation(description = "예약 등록 메서드입니다.")
  @PostMapping()
  public ResponseEntity<String> createReserve(@RequestBody ReserveMakeUpDto reserveMakeUpDto) throws Exception {

    reserveService.makeReserve(reserveMakeUpDto);

    return ResponseEntity.ok("예약 정보를 등록했습니다.");
  }

  /**
   * Reserve 정보 수정
   *
   * @return
   * @throws ParseException
   */
  @Operation(description = "예약 정보 수정 메서드입니다.")
  @PutMapping()
  public ResponseEntity<String> updateReserve(@RequestBody ReserveUpdateDto updateData) throws ParseException {

    if (!ObjectUtils.isEmpty(updateData)) {

      String updateResult = reserveService.updateReserve(updateData);
      return new ResponseEntity<>(updateResult, HttpStatus.OK);

    } else {

      return new ResponseEntity<>("예약에 필요한 정보가 불충분합니다!", HttpStatus.NOT_FOUND);
    }
  }

  /**
   * roomCode에 해당하는 Reserve 조회
   *
   * @param roomCode
   * @return
   */
  @Operation(description = "특정 모임의 예약 정보 조회 메서드입니다.")
  @GetMapping("{roomCode}")
  public ResponseEntity<ReserveEntity> getReserve(@PathVariable("roomCode") String roomCode) {

    ReserveEntity reserveInfo = reserveService.getReserve(roomCode);

    return new ResponseEntity<>(reserveInfo, HttpStatus.OK);
  }

  /**
   * roomCode에 해당하는 Reserve 삭제
   *
   * @param roomCode
   * @return
   */
  @Operation(description = "특정 모임의 예약 정보 제거 메서드입니다.")
  @DeleteMapping("{roomCode}")
  public ResponseEntity<String> deleteReserve(@PathVariable("roomCode") String roomCode) {

    reserveService.deleteReserve(roomCode);

    return new ResponseEntity<>(roomCode, HttpStatus.OK);
  }
}