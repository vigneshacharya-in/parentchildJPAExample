package in.vigachar.parentchildjpa.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import in.vigachar.parentchildjpa.Utils;
import in.vigachar.parentchildjpa.dtos.PostDTO;
import in.vigachar.parentchildjpa.dtos.UserDTO;
import in.vigachar.parentchildjpa.entity.Post;
import in.vigachar.parentchildjpa.entity.User;
import in.vigachar.parentchildjpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(mapUserToUserDTO).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        return userRepository.findById(id).map(mapUserToUserDTO).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private Function<User, UserDTO> mapUserToUserDTO = user -> UserDTO.builder()
            .name(user.getName())
            .email(user.getEmail())
            .posts(postEntityToDTOTransformer(user.getPosts()))
            .build();

    public static Set<PostDTO> postEntityToDTOTransformer(Set<Post> posts) {
        Set<PostDTO> postsDTO = new HashSet<>();
        for (Post post : posts) {
            postsDTO.add(PostDTO.builder()
                    .content(post.getContent())
                    .title(Utils.getTitle(post.getMetadata()))
                    .likes(Utils.getlikes(post.getMetadata())).build());
        }
        return postsDTO;
    }
}
