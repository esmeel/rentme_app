package com.rentme.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentme.model.Notification;
import com.rentme.model.NotificationType;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
  List<Notification> findByReceiverIdOrderByCreatedAtDesc(Long receiverId);



  void deleteByTypeAndRelatedId(NotificationType type, Long relatedId);


  List<Notification> findByReceiverId(Long receiverId);

  Optional<Notification> findFirstByTypeAndRelatedIdOrderByCreatedAtDesc(NotificationType type,
      Long relatedId);

  List<Notification> findByTypeAndRelatedId(NotificationType type, Long relatedId);

}
