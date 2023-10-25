package com.yeoksam_station_exit_1.meetharereserveadjust.adjust;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 테이블과 클래스명이 같을 경우 생략 가능
@Table(name = "Adjust", indexes = @Index(name = "idx_room_code", columnList = "room_code", unique = true))
@Entity
public class AdjustEntity {

  // @GeneratedValue(strategy = GenerationType.xxx) : Primary Key의 키 생성
  // 전략(Strategy)을 설정하고자 할 때 사용
  // GenerationType.IDENTITY : MySQL의 AUTO_INCREMENT 방식을 이용
  // GenerationType.AUTO(default) : JPA 구현체(Hibernate)가 생성 방식을 결정
  // GenerationType.SEQUENCE : DB의 SEQUENCE를 이용해서 키를 생성. @SequenceGenerator와 같이 사용
  // GenerationType.TABLE : 키 생성 전용 테이블을 생성해서 키 생성. @TableGenerator와 함께 사용

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "adjust_id")
  private Long adjustId;

  // Timestamp의 값을 현재 시간으로 자동 설정
  @Column(name = "reg_date", length = 200, nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp regDate;

  @Column(name = "room_code", nullable = false, updatable = false, unique = true)
  private String roomCode;

  @Column(name = "adjust_time", nullable = false)
  private Timestamp adjustTime;

  @Column(name = "adjust_info")
  private String adjustInfo;
}
