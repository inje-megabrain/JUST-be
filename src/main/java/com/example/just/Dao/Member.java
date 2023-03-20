  package com.example.just.Dao;

  import com.fasterxml.jackson.annotation.JsonIgnore;
  import lombok.*;
  import org.hibernate.annotations.CreationTimestamp;
  import org.springframework.stereotype.Service;

  import javax.persistence.*;
  import java.sql.Timestamp;
  import java.util.ArrayList;
  import java.util.List;

  @Entity
  @Builder
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public class Member {
      @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      private String email;

      @Enumerated(EnumType.STRING)
      @Setter
      private Role role;

      @CreationTimestamp
      private Timestamp createTime;

      private String provider;


    @Builder.Default //안 써도 되는데 경고떠서 그냥 부침
    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();
    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "post_like",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> likedPosts = new ArrayList<>();


      public void addLikedPost(Post post) {
          if (!likedPosts.contains(post)) {
              likedPosts.add(post);
              post.addLike(this);
          }
      }

      public void removeLikedPost(Post post) {
          if (likedPosts.contains(post)) {
              likedPosts.remove(post);
              post.removeLike(this);
          }
      }



      @OneToMany(mappedBy = "member")   //알림
      private List<Notification> notifications;
      public void updateMember(final Post post) {
          posts.add(post);
      }
  }

