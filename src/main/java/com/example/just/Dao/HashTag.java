package com.example.just.Dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
<<<<<<< HEAD

import javax.persistence.*;

=======
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Builder.Default;
>>>>>>> 6f71b57bb52f06fce8868fecb52ba4134688351c
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hash_tag_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "tag_count")
    private Long tagCount;

<<<<<<< HEAD
    @ManyToMany(mappedBy = "hashTags", cascade = CascadeType.ALL)
    private List<Post> posts;

    public void setTags(List<String> tagNames) {
        this.name = tagNames.get(0);
=======
    @OneToMany(mappedBy = "hashTag", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<HashTagMap> hashTagMaps = new ArrayList<>();


    public HashTag() {
>>>>>>> 6f71b57bb52f06fce8868fecb52ba4134688351c
    }

    public HashTag(String name) {
        this.name = name;
    }

<<<<<<< HEAD

=======
    public void addHashTagMap(HashTagMap hashTagMap) {
        this.hashTagMaps.add(hashTagMap);
    }
>>>>>>> 6f71b57bb52f06fce8868fecb52ba4134688351c
}