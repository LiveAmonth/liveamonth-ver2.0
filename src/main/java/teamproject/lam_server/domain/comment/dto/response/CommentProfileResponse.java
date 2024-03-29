package teamproject.lam_server.domain.comment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static teamproject.lam_server.constants.AttrConstants.IMAGEBB_URL;

@Getter
@NoArgsConstructor
public class CommentProfileResponse {

    private String nickname;
    private String image;

    public CommentProfileResponse(String nickname, String image) {
        this.nickname = nickname;
        this.image = IMAGEBB_URL + image;
    }
}
