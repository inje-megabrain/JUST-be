package com.example.just.Response;

import com.example.just.Dao.Member;
import com.example.just.Dao.Notification;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
public class ResponseNotification {

    private Long not_id;

    private Long not_object_id;

    private String not_type;

    private Date not_datetime;

    private Boolean not_is_read; //알림 읽음 여부

    private Long receiver_id;

    private Long sender_id;

    public ResponseNotification(Notification notification){
        not_id = notification.getNotId();
        not_object_id = notification.getNotObjectId();
        not_type = notification.getNotType();
        not_datetime = notification.getNotDatetime();
        not_is_read = notification.getNotIsRead();
        receiver_id = notification.getReceiver().getId();
        sender_id = notification.getSenderId();
    }
}
