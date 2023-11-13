package com.yeoksam_station_exit_1.meetharereserveadjust.webpush;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationPayload {

  private String title;
  private String body;
}
