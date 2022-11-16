package com.bhlieberman.codefellowship.repositories;

import com.bhlieberman.codefellowship.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findPostById(Long id);

    Optional<List<Post>> findPostsById(Long id);

}
