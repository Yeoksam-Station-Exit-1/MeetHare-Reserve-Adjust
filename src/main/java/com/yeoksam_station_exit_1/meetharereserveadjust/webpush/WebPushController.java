package com.yeoksam_station_exit_1.meetharereserveadjust.webpush;

import java.text.ParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.Subscription;

@RequiredArgsConstructor
@RequestMapping("webpush")
@RestController
@Tag(name = "Web-Push", description = "Web-Push 관련 API 입니다.")
public class WebPushController {

  private final WebPushService webPushService;

  /**
   * Subscribe 생성
   *
   * @return
   * @throws ParseException
   */
  @Operation(description = "알람 구독 메서드입니다.")
  @PostMapping()
  public ResponseEntity<String> createSubscription(@RequestBody Subscription subscription,
      @RequestHeader("Authorization") String jwt) throws Exception {

    webPushService.subscribe(subscription, jwt);

    return ResponseEntity.ok("알람을 구독했습니다.");
  }

  /**
   * 모든 유저에게 푸쉬 알람 보내기
   *
   * @return
   * @throws JsonProcessingException
   */
  @Operation(description = "모든 유저에게 푸쉬 알람 보내기 메서드입니다.")
  @GetMapping()
  public ResponseEntity<String> sendNotificationToAll() throws Exception {

    webPushService.notifyAll("테스트", "테스트 입니다.");

    return new ResponseEntity<>("모든 유저에게 푸쉬 알림 발송 완료", HttpStatus.OK);

  }

  /**
   * 알람 구독 취소
   *
   * @return
   * @throws ParseException
   */
  @Operation(description = "알람 구독 취소 메서드입니다.")
  @DeleteMapping()
  public ResponseEntity<String> cancelSubscription(@RequestBody Subscription subscription,
      @RequestHeader("Authorization") String jwt) throws Exception {

    webPushService.unsubscribe(subscription, jwt);

    return new ResponseEntity<>("알람을 구독 취소했습니다.", HttpStatus.OK);
  }
}
