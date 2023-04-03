package com.example.just.Service;


import com.example.just.Dao.Member;
import com.example.just.Dao.Post;


import com.example.just.Dao.QPost;
import com.example.just.Dto.PostDto;
import com.example.just.Impl.MySliceImpl;
import com.example.just.Mapper.PostMapper;
import com.example.just.Repository.MemberRepository;
import com.example.just.Repository.PostRepository;


import com.example.just.jwt.JwtProvider;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;


import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.just.Dao.QPost.post;


@Service
public class PostService {


    private final EntityManager em;

    private final JPAQueryFactory query;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private JwtProvider jwtProvider;

    public PostService(EntityManager em, JPAQueryFactory query) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }


    public Post write(Long member_id, PostDto postDto) {    //글 작성

        Optional<Member> optionalMember = memberRepository.findById(member_id);
        if (!optionalMember.isPresent()) {  //아이디 없을시 예외처리
            throw new NoSuchElementException("DB에 존재하지 않는 ID : " + member_id);
        }
        Member member = optionalMember.get();   //존재한다면 객체 생성

        Long k = Long.valueOf((int) (Math.random() * 3));

        Post post = new Post(postDto.getPost_content(), postDto.getPost_tag(),
                k, postDto.getSecret(), postDto.getEmoticon(), postDto.getPost_category(), 0L,
                member, 0);

        postRepository.save(post);

        return post;
    }


    //글 삭제
    @Transactional
    public String deletePost(Long post_id) {
        Optional<Post> optionalPost = postRepository.findById(post_id);
        if (!optionalPost.isPresent()) {  //아이디 없을시 예외처리
            throw new NoSuchElementException("post_id의 값이 DB에 존재하지 않습니다:" + post_id);
        }
        Post post = optionalPost.get();
        post.setMember(null);
        try {
            postRepository.delete(post);
        } catch (Exception e) {
            throw new NoSuchElementException("post_id의 값이 DB에 존재하지 않습니다: " + post_id);
        }
        return String.valueOf(post_id) + "번 게시글 삭제 완료";
    }

    //글 수정
    public Post putPost(Long post_id, Long member_id, PostDto postDto) {
        Optional<Post> optionalPost = postRepository.findById(post_id);
        if (!optionalPost.isPresent()) {  //아이디 없을시 예외처리
            throw new NoSuchElementException("post_id의 값이 DB에 존재하지 않습니다:" + post_id);
        }
        Optional<Member> optionalMember = memberRepository.findById(member_id);
        if (!optionalMember.isPresent()) {  //아이디 없을시 예외처리
            throw new NoSuchElementException("DB에 존재하지 않는 ID : " + member_id);
        }
        postDto.setPost_create_time(new Timestamp(System.currentTimeMillis()));
        Member member = optionalMember.get();   //존재한다면 객체 생성
        postDto.setMember(member);
        Post post = postMapper.toEntity(postDto);
        postRepository.save(post);
        return post;
    }

    public Slice<Post> searchByCursor(String cursor, Long limit) { //글 조


        QPost post = QPost.post;
        Set<Long> viewedPostIds = new HashSet<>();

        // 이전에 본 글들의 ID를 가져옵니다.
        if (cursor != null) {
            String[] viewedPostIdsArray = cursor.split(",");
            viewedPostIds = new HashSet<>();
            for (String viewedPostId : viewedPostIdsArray) {
                viewedPostIds.add(Long.parseLong(viewedPostId));
            }
        }

        // 중복된 글을 제외하고 랜덤으로 limit+1개의 글을 가져옵니다.
        List<Post> results = query.selectFrom(post)
                .where(post.post_id.notIn(viewedPostIds),
                        post.post_create_time.isNotNull())
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(limit + 1)
                .fetch();

        // 가져온 글들의 ID를 저장합니다.
        Set<Long> resultPostIds = results.stream().map(Post::getPost_id).collect(Collectors.toSet());
        viewedPostIds.addAll(resultPostIds);

        // hasNext와 nextCursor를 계산합니다.
        boolean hasNext = results.size() > limit;
        String nextCursor = null;
        if (!viewedPostIds.isEmpty()) {
            nextCursor = String.join(",", viewedPostIds.stream().map(Object::toString).collect(Collectors.toList()));
        }

        // limit+1개의 글 중에서 limit개의 글만 남기고 제거합니다.
        if (hasNext) {
            results.remove(limit);
        }

        // Slice 객체를 생성해서 반환합니다.
        return new MySliceImpl<>(results, PageRequest.of(0, Math.toIntExact(limit)), hasNext, nextCursor);

    }
    /*
    public Slice<Post> searchByMyPost(Long limit, Long member_id) {


        List<Post> results = query.selectFrom(post)
                .where(
                        post.member.id.eq(member_id)
                )
                .orderBy(post.post_id.desc())
                .limit(limit + 1)
                .fetch();





        // hasNext와 nextCursor를 계산합니다.
        boolean hasNext = results.size() > limit;


        // limit+1개의 글 중에서 limit개의 글만 남기고 제거합니다.
        if (hasNext) {
            results.remove(limit);
        }


        return new SliceImpl<>(results, limit, hasNext);
    }

     */




    @Transactional
    public Post postLikes(Long post_id, Long member_id) {    //글 좋아요


        Optional<Post> optionalPost = postRepository.findById(post_id);
        if (!optionalPost.isPresent()) {  //아이디 없을시 예외처리
            throw new NoSuchElementException("post_id의 값이 DB에 존재하지 않습니다:" + post_id);
        }
        Post post = optionalPost.get();

        Optional<Member> optionalMember = memberRepository.findById(member_id);
        if (!optionalMember.isPresent()) {  //아이디 없을시 예외처리
            throw new NoSuchElementException("DB에 존재하지 않는 ID : " + member_id);
        }
        Member member = optionalMember.get(); //존재한다면 객체 생성

        if (post.getLikedMembers().contains(member)) {
            post.removeLike(member);
        } else {
            post.addLike(member);
        }

        Post savePost = postRepository.save(post);
        return savePost;
    }


}