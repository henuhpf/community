package code.community.dto;

import lombok.Data;

@Data
public class GiteeUser {
    private Long id;
    private String name;
    private String bio;
    private String avatarUrl;
}
