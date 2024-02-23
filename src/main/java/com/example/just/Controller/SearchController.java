package com.example.just.Controller;

import com.example.just.Dao.Member;
import com.example.just.Dao.Post;
import com.example.just.Document.PostDocument;
import com.example.just.Document.PostDocument.PostDocumentBuilder;
import com.example.just.Service.SearchService;
import com.example.just.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@Tag(name = "search", description = "검색 api")
public class SearchController {
    @Autowired
    SearchService searchService;

    @Autowired
    JwtProvider jwtProvider;

    @GetMapping("/get/search/post")
    @Operation(summary = "게시글 내용 검색", description = "해당 keyword를 content에 포함하는 게시글 검색\n태그검색구현시 추후 변경 가능")
    public ResponseEntity getPosts(@RequestParam String keyword) {
        return searchService.searchPostContent(keyword);
    }
}