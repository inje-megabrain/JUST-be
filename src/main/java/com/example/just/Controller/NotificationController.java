package com.example.just.Controller;

import com.example.just.Service.NotificationService;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @ApiOperation(value = "알림", notes = "<big>알림 통신 연결</big>헤더에 access_token으로 토큰을 넣어주고 통신연결을 필수로 해야함.\n" +
            "not_type : comment,like로 댓글알람인지 좋아요알람인지 판단 \n" +
            "not_post_id : 알림의 target 게시글 id \n" +
            "not_datetime : 알람이온 시간(일단 필요해서 넣었음)\n" +
            "receiver : 수신자(게시글 작성자인 본인을 말함)id\n" +
            "senderId : 송신자id")
    @GetMapping(value = "/noti", produces = "text/event-stream")
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter subscribe(HttpServletRequest request, @RequestHeader(value = "Last_Event-ID",required = false,defaultValue = "") String lastEventId){

        return notificationService.subscribe(request,lastEventId);

    }
}
