package com.yeoksam_station_exit_1.meetharereserveadjust.webpush;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Base64.Decoder;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeoksam_station_exit_1.meetharereserveadjust.reserve.ReserveEntity;
import com.yeoksam_station_exit_1.meetharereserveadjust.reserve.ReserveRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WebPushService {

  @Value("${vapid.public.key}")
  private String publicKey;
  @Value("${vapid.private.key}")
  private String privateKey;
  @Value("${vapid.subject}")
  private String subject;

  private PushService pushService;
  private final ReserveRepository reserveRepository;

  private final Map<String, Subscription> socialIDToSubscription = new HashMap<>();

  @PostConstruct
  private void init() throws GeneralSecurityException {
    Security.addProvider(new BouncyCastleProvider());
    pushService = new PushService(publicKey, privateKey, subject);
  }

  @Scheduled(cron = "0 0/1 * * * *", zone = "Asia/Seoul")
  public void meetingAlarm() {
    LocalDate currentDate = LocalDate.now();
    List<ReserveEntity> reservations = reserveRepository.findByReserveTime(currentDate);

    NotificationPayload payload = NotificationPayload.builder()
        .title("오늘 예정된 약속이 있습니다.")
        .body("클릭해서 약속을 확인해보세요!")
        .build();

    String msg = "";
    try {

      ObjectMapper objectMapper = new ObjectMapper();
      msg = objectMapper.writeValueAsString(payload);
    } catch (Exception e) {
      e.printStackTrace();
    }

    for (ReserveEntity reservation : reservations) {
      List<String> members = reservation.getReserveMembers();
      for (String member : members) {
        Subscription subscription = socialIDToSubscription.get(member);
        sendNotification(subscription, msg);
      }

      MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
      params.add("roomCode", reservation.getRoomCode());
      HttpHeaders headers = new HttpHeaders();
      HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

      RestTemplate rt = new RestTemplate();

      ResponseEntity<String> response = rt.exchange(
          "http://meethare-user-manage.default.svc.cluster.local:8080/user-manage/room/tolivemap",
          HttpMethod.POST,
          entity,
          String.class);
    }

  }

  public void sendNotification(Subscription subscription, String messageJson) {

    try {

      HttpResponse response = pushService.send(new Notification(subscription, messageJson));
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode != 201) {
        System.out.println("Server error, status code:" + statusCode);
        InputStream content = response.getEntity().getContent();
        System.out.println("Content" + content);
      }
    } catch (GeneralSecurityException | IOException | JoseException | ExecutionException |

        InterruptedException e) {
      e.printStackTrace();
    }
  }

  public String parseSocialID(String jwt) {

    String[] temps = jwt.split("\\.");
    byte[] targetBytes = temps[1].getBytes();
    Decoder decoder = Base64.getDecoder();
    byte[] decodedBytes = decoder.decode(targetBytes);
    String payloadString = new String(decodedBytes);
    ObjectMapper objectMapper = new ObjectMapper();
    PayloadDTO payload = null;
    try {
      payload = objectMapper.readValue(payloadString, PayloadDTO.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return payload.getEmail();
  }

  public void subscribe(Subscription subscription, String jwt) {
    System.out.println("Subscribed to " + subscription.endpoint);

    String socialID = parseSocialID(jwt);
    /*
     * Note, in a real world app you'll want to persist these
     * in the backend. Also, you probably want to know which
     * subscription belongs to which user to send custom messages
     * for different users. In this demo, we'll just use
     * endpoint URL as key to store subscriptions in memory.
     */
    socialIDToSubscription.put(socialID, subscription);
  }

  public void unsubscribe(Subscription subscription, String jwt) {
    System.out.println("Unsubscribed " + subscription.endpoint + " auth:" + subscription.keys.auth);
    String socialID = parseSocialID(jwt);
    socialIDToSubscription.remove(socialID);
  }

  public void notifyAll(String title, String body) {
    try {
      log.info(title);
      log.info(body);
      NotificationPayload payload = NotificationPayload.builder()
          .title(title)
          .body(body)
          .build();
      ObjectMapper objectMapper = new ObjectMapper();
      String msg = objectMapper.writeValueAsString(payload);
      socialIDToSubscription.values().forEach(subscription -> {
        sendNotification(subscription, msg);
      });
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}