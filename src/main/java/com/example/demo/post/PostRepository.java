package com.example.demo.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAuthor_Id(Long authorId);
    List<Post> findByAuthor_Username(String username);
    List<Post> findByCategory(String category);
}
