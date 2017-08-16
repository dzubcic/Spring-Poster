package com.springposter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springposter.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	
	@Query(nativeQuery = true, value = "select * from post order by id desc offset ?1 limit 3")
	List<Post> findPosts(int offset);

	Post findByimageName(String name);
	
}
