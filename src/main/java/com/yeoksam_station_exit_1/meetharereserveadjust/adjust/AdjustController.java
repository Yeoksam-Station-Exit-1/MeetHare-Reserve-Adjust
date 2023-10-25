package com.yeoksam_station_exit_1.meetharereserveadjust.adjust;

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
@RequestMapping("api/adjust")
@RestController
@Tag(name = "Adjust", description = "정산 관련 API 입니다.")
public class AdjustController {

  private final AdjustService adjustService;

  /**
   * Adjust 생성
   *
   * @return
   * @throws ParseException
   */
  @Operation(description = "정산 등록 메서드입니다.")
  @PostMapping()
  public ResponseEntity<String> createAdjust(@RequestBody AdjustMakeUpDto adjustMakeUpDto) throws Exception {

    adjustService.makeAdjust(adjustMakeUpDto);

    return ResponseEntity.ok("정산 정보를 등록했습니다.");
  }

  /**
   * Adjust 정보 수정
   *
   * @return
   * @throws ParseException
   */
  @Operation(description = "정산 정보 수정 메서드입니다.")
  @PutMapping()
  public ResponseEntity<String> updateAdjust(@RequestBody AdjustUpdateDto updateData) throws ParseException {

    if (!ObjectUtils.isEmpty(updateData)) {

      String updateResult = adjustService.updateAdjust(updateData);
      return new ResponseEntity<>(updateResult, HttpStatus.OK);

    } else {

      return new ResponseEntity<>("정산에 필요한 정보가 불충분합니다!", HttpStatus.NOT_FOUND);
    }
  }

  /**
   * roomCode에 해당하는 Adjust 조회
   *
   * @param roomCode
   * @return
   */
  @Operation(description = "특정 모임의 정산 정보 조회 메서드입니다.")
  @GetMapping("{roomCode}")
  public ResponseEntity<AdjustEntity> getAdjust(@PathVariable("roomCode") String roomCode) {

    AdjustEntity adjustInfo = adjustService.getAdjust(roomCode);

    return new ResponseEntity<>(adjustInfo, HttpStatus.OK);
  }

  /**
   * roomCode에 해당하는 Adjust 삭제
   *
   * @param roomCode
   * @return
   */
  @Operation(description = "특정 모임의 정산 정보 제거 메서드입니다.")
  @DeleteMapping("{roomCode}")
  public ResponseEntity<String> deleteAdjust(@PathVariable("roomCode") String roomCode) {

    adjustService.deleteAdjust(roomCode);

    return new ResponseEntity<>(roomCode, HttpStatus.OK);
  }
}