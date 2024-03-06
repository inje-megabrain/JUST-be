package com.example.just.Dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hash_tag_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "tag_count")
    private Long tagCount;

    @ManyToMany(mappedBy = "hashTags", cascade = CascadeType.ALL)
    private List<Post> posts;

    public void setTags(List<String> tagNames) {
        this.name = tagNames.get(0);
    }

    public HashTag(String name) {
        this.name = name;
    }


}