package com.example.demo.post;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public List<PostResponse> getPosts() {
       List<Post> posts = postRepository.findAll();
       return posts.stream().map(post -> postMapper.toPostResponse(post)).collect(Collectors.toList());
    }

    // Método interno 
    private Post getPostByIdOrThrow(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Post no encontrado con ID: " + id));
    }

    // Método público que devuelve DTO al cliente
    public PostResponse getPostResponseById(Long id) {
        Post post = getPostByIdOrThrow(id);
        return postMapper.toPostResponse(post);
    }

    public List<PostResponse> getPostsByAuthor(String author) {
        List<Post> posts = postRepository.findByAuthor_Username(author);
        return posts.stream().map(post -> postMapper.toPostResponse(post)).collect(Collectors.toList());
    }

    public List<PostResponse> getPostsByAuthorId(Long id){
        List<Post> posts =postRepository.findByAuthor_Id(id);
        return posts.stream().map(post -> postMapper.toPostResponse(post)).collect(Collectors.toList());
    }

    public List<PostResponse> getPostsByCategory(String category) {
        List<Post> posts =postRepository.findByCategory(category);
        return posts.stream().map(post -> postMapper.toPostResponse(post)).collect(Collectors.toList());
    }

    @Transactional
    public PostResponse createPost(Post post) {
        if (post == null || post.getTitle() == null) {
            throw new IllegalArgumentException("Title y Author son obligatorios");
        }
        User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setAuthor(author);
        
        return postMapper.toPostResponse(postRepository.save(post));
    }

    @Transactional
    public PostResponse updatePost(Long id, Post postDetails) {
        if (postDetails == null) {
            throw new IllegalArgumentException("Los datos del Post no pueden ser nulos");
        }
        Post post = getPostByIdOrThrow(id); // Reutiliza la lógica de búsqueda + excepción

        User autor = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if(!post.isOwnedBy(autor)){
            throw new AccessDeniedException("No autorizado");
        }
        
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        post.setCategory(postDetails.getCategory());
        
        return postMapper.toPostResponse(postRepository.save(post));
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = getPostByIdOrThrow(id); // Valida que exista antes de borrar
        User autor = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if(!post.isOwnedBy(autor) && autor.getRole().name() != "ADMIN"){
            throw new AccessDeniedException("No autorizado");
        }
        postRepository.delete(post);
    }
}
