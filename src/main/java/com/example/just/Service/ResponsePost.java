package com.example.just.Service;

import com.example.just.Dao.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePost {
    private Long post_id;
    private boolean value;

    public ResponsePost(Long post_id, boolean b) {
        this.post_id =post_id;
        this.value =b;
    }
}
