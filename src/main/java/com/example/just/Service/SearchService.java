package com.example.just.Service;

import com.example.just.Dao.Blame;
<<<<<<< HEAD
import com.example.just.Document.PostDocument;
import com.example.just.Repository.BlameRepository;
import com.example.just.Repository.MemberRepository;
import com.example.just.Repository.PostContentESRespository;
import com.example.just.Repository.PostRepository;
import com.example.just.jwt.JwtProvider;
=======
import com.example.just.Document.HashTagDocument;
import com.example.just.Document.PostDocument;
import com.example.just.Repository.BlameRepository;
import com.example.just.Repository.HashTagESRepository;
import com.example.just.Repository.MemberRepository;
import com.example.just.Repository.PostContentESRespository;
import com.example.just.Repository.PostRepository;
import com.example.just.Response.ResponseMessage;
import com.example.just.Response.ResponseSearchDto;
import com.example.just.jwt.JwtProvider;
import java.util.ArrayList;
import java.util.Collections;
>>>>>>> aea347125278b8318ff91f76045a9a2d7fb0c828
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
<<<<<<< HEAD
=======
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
>>>>>>> aea347125278b8318ff91f76045a9a2d7fb0c828
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    PostContentESRespository postContentESRespository;

    @Autowired
<<<<<<< HEAD
=======
    HashTagESRepository hashTagESRepository;

    @Autowired
>>>>>>> aea347125278b8318ff91f76045a9a2d7fb0c828
    PostRepository postRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    BlameRepository blameRepository;

    @Autowired
    MemberRepository memberRepository;

    public ResponseEntity searchPostContent(HttpServletRequest request,String keyword,int page){
        String token = jwtProvider.getAccessToken(request);
<<<<<<< HEAD
=======
        if(token == null){
            return new ResponseEntity(new ResponseMessage("로그인 후 검색가능합니다."),null, HttpStatus.BAD_REQUEST);
        }
>>>>>>> aea347125278b8318ff91f76045a9a2d7fb0c828
        Long id = Long.valueOf(jwtProvider.getIdFromToken(token)); //토큰
        List<Blame> blames = blameRepository.findByBlameMemberId(id);
        //유저가 신고한 게시글 id들
        List<Long> postIds = blames.stream()
                .map(Blame::getTargetPostId)
                .collect(Collectors.toList());
        //유저가 신고한 회원 id들
        List<Long> memberIds = blames.stream()
                .map(Blame::getTargetMemberId)
                .collect(Collectors.toList());

<<<<<<< HEAD
        List<PostDocument> searchList = postContentESRespository.findByPostContent_ContentContains(keyword);
=======
        List<PostDocument> searchList = postContentESRespository.findByPostContentContaining(keyword);
>>>>>>> aea347125278b8318ff91f76045a9a2d7fb0c828

        List<PostDocument> filterList = searchList.stream()
                .filter(postDocument -> !postIds.contains(postDocument.getId()))
                .filter(postDocument -> !memberIds.contains(postDocument.getMemberId()))
                .collect(Collectors.toList());
<<<<<<< HEAD
        PageRequest pageRequest = PageRequest.of(page,5);
        filterList.sort(Comparator.comparing(PostDocument::getPostCreateTime).reversed());
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()),filterList.size());
        Page<PostDocument> postPage = new PageImpl<>(filterList.subList(start,end), pageRequest, filterList.size());
=======

        List<ResponseSearchDto> result = filterList.stream()
                .map(postDocument -> new ResponseSearchDto(postDocument,id))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page,10);
        result.sort(Comparator.comparing(ResponseSearchDto::getPost_create_time).reversed());
        int start = (int) pageRequest.getOffset();
        if (start >= result.size()) {
            return new ResponseEntity(new ResponseMessage("페이지를 초과하엿습니다."),null,HttpStatus.BAD_REQUEST);
        }
        int end = Math.min((start + pageRequest.getPageSize()),result.size());
        Page<ResponseSearchDto> postPage = new PageImpl<>(result.subList(start,end), pageRequest, result.size());
        return ResponseEntity.ok(postPage);
    }

    public ResponseEntity getAutoTag(String str,int page){
        List<HashTagDocument> hashTagDocuments = new ArrayList<HashTagDocument>();
        if(str.equals("") || str.equals(null)){
            hashTagDocuments = hashTagESRepository.findAll(Sort.by(Direction.DESC,"tagCount"));
        }else {
            hashTagDocuments = hashTagESRepository.findByNameContaining(str,Sort.by(Direction.DESC,"tagCount"));
        }
        if(hashTagDocuments.equals(null)) {
            return new ResponseEntity(new ResponseMessage("태그가 존재하지 않습니다."), null, HttpStatus.BAD_REQUEST);
        }
        PageRequest pageRequest = PageRequest.of(page,10);
        int start = (int) pageRequest.getOffset();
        if (start >= hashTagDocuments.size()) {
            return new ResponseEntity(new ResponseMessage("페이지를 초과하엿습니다."),null,HttpStatus.BAD_REQUEST);
        }
        int end = Math.min((start + pageRequest.getPageSize()),hashTagDocuments.size());
        Page<HashTagDocument> postPage = new PageImpl<>(hashTagDocuments.subList(start,end), pageRequest, hashTagDocuments.size());
        return ResponseEntity.ok(postPage);
    }

    public ResponseEntity searchTagPost(HttpServletRequest request,String tag,int page){
        String token = jwtProvider.getAccessToken(request);
        if(token == null){
            return new ResponseEntity(new ResponseMessage("로그인 후 검색가능합니다."),null, HttpStatus.BAD_REQUEST);
        }
        Long id = Long.valueOf(jwtProvider.getIdFromToken(token)); //토큰
        List<Blame> blames = blameRepository.findByBlameMemberId(id);
        //유저가 신고한 게시글 id들
        List<Long> postIds = blames.stream()
                .map(Blame::getTargetPostId)
                .collect(Collectors.toList());
        //유저가 신고한 회원 id들
        List<Long> memberIds = blames.stream()
                .map(Blame::getTargetMemberId)
                .collect(Collectors.toList());

        List<PostDocument> searchList = postContentESRespository.findByHashTagIn(tag);

        List<PostDocument> filterList = searchList.stream()
                .filter(postDocument -> !postIds.contains(postDocument.getId()))
                .filter(postDocument -> !memberIds.contains(postDocument.getMemberId()))
                .collect(Collectors.toList());

        List<ResponseSearchDto> result = filterList.stream()
                .map(postDocument -> new ResponseSearchDto(postDocument,id))
                .collect(Collectors.toList());

        PageRequest pageRequest = PageRequest.of(page,10);
        result.sort(Comparator.comparing(ResponseSearchDto::getPost_create_time).reversed());//최신순 조회
        int start = (int) pageRequest.getOffset();
        if (start >= result.size()) {
            return new ResponseEntity(new ResponseMessage("페이지를 초과하엿습니다."),null,HttpStatus.BAD_REQUEST);
        }
        int end = Math.min((start + pageRequest.getPageSize()),result.size());
        Page<ResponseSearchDto> postPage = new PageImpl<>(result.subList(start,end), pageRequest, result.size());
>>>>>>> aea347125278b8318ff91f76045a9a2d7fb0c828
        return ResponseEntity.ok(postPage);
    }

}
