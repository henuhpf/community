package code.community.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {
    private String code;
    private String client_id;
    private String redirect_uri;
    private String client_secret;
}
