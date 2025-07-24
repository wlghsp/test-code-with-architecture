package com.example.demo.post.infrastructure;

import java.util.Optional;

public interface PostRepository {

    Optional<PostEntity> findById(long id);

    PostEntity save(PostEntity postEntity);
}
