package com.yeoksam_station_exit_1.meetharereserveadjust.reserve;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReserveService {

  private final ReserveRepository reserveRepository;

  /**
   * <pre>
   * Reserve 생성
   * JPA Repository의 save Method를 사용하여 객체를 생성
   * Entity인 Model 객체에 @Id로 설정한 키 값은 Auto Increasement로 설정
   * 만약 새롭게 추가하려는 Reserve의 roomCode 값이 이미 존재하면 에러를 뿜으므로
   * 아래와 같이 추가하고자 하는 Reserve가 존재하는지 체크하는 로직을 추가
   * 
   * @param reserveMakeUpDto
   * @return
   */
  public void makeReserve(ReserveMakeUpDto reserveMakeUpDto) throws Exception {

    if (reserveRepository.findByRoomCode(reserveMakeUpDto.getRoomCode()).isPresent()) {
      throw new Exception("해당 모임에 예약 정보가 이미 존재합니다.");
    }

    ReserveEntity reserve = ReserveEntity.builder()
        .roomCode(reserveMakeUpDto.getRoomCode())
        .reserveTime(reserveMakeUpDto.getReserveTime())
        .reservePlace(reserveMakeUpDto.getReservePlace())
        .build();

    reserveRepository.save(reserve);
  }

  /**
   * <pre>
   * Reserve 수정
   * JPA Repository의 save Method를 사용하여 객체를 갱신
   * 만약 새롭게 추가하려는 Reserve의 roomCode 값이 이미 존재하면 에러를 뿜으므로
   * 아래와 같이 추가하고자 하는 Reserve가 존재하는지 체크하는 로직을 추가
   *
   * @param updateData
   * @return
   */
  public String updateReserve(ReserveUpdateDto updateData) {

    String updateResult;

    ReserveEntity existReserve = getReserve(updateData.getRoomCode());

    existReserve.setReserveTime(updateData.getReserveTime());
    existReserve.setReservePlace(updateData.getReservePlace());

    if (!ObjectUtils.isEmpty(existReserve)) {

      reserveRepository.save(existReserve);
      updateResult = "예약 정보가 변경되었습니다.";
    }

    else
      updateResult = "서버에 존재하지 않는 예약 정보입니다.";

    return updateResult;
  }

  /**
   * roomCode에 해당하는 Reserve 조회
   * JPA Repository의 findBy Method를 사용하여 특정 Reserve를 조회
   * find 메소드는 NULL 값일 수도 있으므로 Optional<T>를 반환하지만,
   * Optional 객체의 get() 메소드를 통해 Entity로 변환해서 반환함.
   * 
   * @param roomCode
   * @return
   */
  public ReserveEntity getReserve(String roomCode) {

    return reserveRepository.findByRoomCode(roomCode).get();
  }

  /**
   * roomCode에 해당하는 Reserve 삭제
   * JPA Repository의 deleteBy Method를 사용하여 특정 User를 삭제
   * 
   * @param roomCode
   */
  public void deleteReserve(String roomCode) {
    reserveRepository.deleteByRoomCode(roomCode);
  }
}