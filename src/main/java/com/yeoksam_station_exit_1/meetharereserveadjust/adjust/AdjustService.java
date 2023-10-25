package com.yeoksam_station_exit_1.meetharereserveadjust.adjust;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdjustService {

  private final AdjustRepository adjustRepository;

  /**
   * <pre>
   * Adjust 생성
   * JPA Repository의 save Method를 사용하여 객체를 생성
   * Entity인 Model 객체에 @Id로 설정한 키 값은 Auto Increasement로 설정
   * 만약 새롭게 추가하려는 Adjust의 roomCode 값이 이미 존재하면 에러를 뿜으므로
   * 아래와 같이 추가하고자 하는 Adjust가 존재하는지 체크하는 로직을 추가
   * 
   * @param adjustMakeUpDto
   * @return
   */
  public void makeAdjust(AdjustMakeUpDto adjustMakeUpDto) throws Exception {

    if (adjustRepository.findByRoomCode(adjustMakeUpDto.getRoomCode()).isPresent()) {
      throw new Exception("해당 모임에 예약 정보가 이미 존재합니다.");
    }

    AdjustEntity adjust = AdjustEntity.builder()
        .roomCode(adjustMakeUpDto.getRoomCode())
        .adjustTime(adjustMakeUpDto.getAdjustTime())
        .adjustInfo(adjustMakeUpDto.getAdjustInfo())
        .build();

    adjustRepository.save(adjust);
  }

  /**
   * <pre>
   * Adjust 수정
   * JPA Repository의 save Method를 사용하여 객체를 갱신
   * 만약 새롭게 추가하려는 Adjust의 roomCode 값이 이미 존재하면 에러를 뿜으므로
   * 아래와 같이 추가하고자 하는 Adjust가 존재하는지 체크하는 로직을 추가
   *
   * @param updateData
   * @return
   */
  public String updateAdjust(AdjustUpdateDto updateData) {

    String updateResult;

    AdjustEntity existAdjust = getAdjust(updateData.getRoomCode());

    existAdjust.setAdjustTime(updateData.getAdjustTime());
    existAdjust.setAdjustInfo(updateData.getAdjustInfo());

    if (!ObjectUtils.isEmpty(existAdjust)) {

      adjustRepository.save(existAdjust);
      updateResult = "정산 정보가 변경되었습니다.";
    }

    else
      updateResult = "서버에 존재하지 않는 정산 정보입니다.";

    return updateResult;
  }

  /**
   * roomCode에 해당하는 Adjust 조회
   * JPA Repository의 findBy Method를 사용하여 특정 Reserve를 조회
   * find 메소드는 NULL 값일 수도 있으므로 Optional<T>를 반환하지만,
   * Optional 객체의 get() 메소드를 통해 Entity로 변환해서 반환함.
   * 
   * @param roomCode
   * @return
   */
  public AdjustEntity getAdjust(String roomCode) {

    return adjustRepository.findByRoomCode(roomCode).get();
  }

  /**
   * roomCode에 해당하는 Adjust 삭제
   * JPA Repository의 deleteBy Method를 사용하여 특정 User를 삭제
   * 
   * @param roomCode
   */
  public void deleteAdjust(String roomCode) {
    adjustRepository.deleteByRoomCode(roomCode);
  }
}
