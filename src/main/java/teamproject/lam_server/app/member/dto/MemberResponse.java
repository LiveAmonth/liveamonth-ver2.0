package teamproject.lam_server.app.member.dto;

import lombok.Data;
import teamproject.lam_server.app.member.domain.Member;

@Data
public class MemberResponse {

    private String loginId;

    private String name;

    private String nickname;

    private String email;

    private String gender;

    private int age;

    public MemberResponse(Member member) {
        this.loginId = member.getLoginId();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.gender = member.getGender().getValue();
        this.age = member.calcAge();
    }

}