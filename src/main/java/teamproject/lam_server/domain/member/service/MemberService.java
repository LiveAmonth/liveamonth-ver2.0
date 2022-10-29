package teamproject.lam_server.domain.member.service;

import teamproject.lam_server.domain.member.dto.request.FindIdRequest;
import teamproject.lam_server.domain.member.dto.request.FindPasswordRequest;
import teamproject.lam_server.domain.member.dto.request.ModifyMemberRequest;
import teamproject.lam_server.domain.member.dto.request.SignUpRequest;
import teamproject.lam_server.domain.member.dto.response.DuplicateCheckResponse;
import teamproject.lam_server.domain.member.dto.response.FindIdResponse;
import teamproject.lam_server.domain.member.dto.response.MemberProfileResponse;
import teamproject.lam_server.domain.member.dto.response.SimpleProfileResponse;
import teamproject.lam_server.global.dto.PostIdResponse;

public interface MemberService {

    /**
     * 회원 가입
     */
    PostIdResponse signUp(SignUpRequest request);

    /**
     * 이메일 중복 체크
     */
    DuplicateCheckResponse checkDuplicateEmail(String email);

    /**
     * 회원 아이디 중복 체크
     */
    DuplicateCheckResponse checkDuplicateLoginId(String LoginId);

    /**
     * 닉네임 중복 체크
     */
    DuplicateCheckResponse checkDuplicateNickname(String nickname);

    /**
     * 회원 아이디 찾기
     */
    FindIdResponse findLoginId(FindIdRequest request);

    /**
     * 회원 비밀번호 찾기
     */
    void findPassword(FindPasswordRequest request);

    /**
     * 회원 정보  수정
     */
    void modify(Long id, ModifyMemberRequest request);

    /**
     * 회원 탈퇴
     */
    void dropUser(Long id);

    /**
     * 회원 DB 삭제
     */
    void delete(Long id);

    /**
     * 회원 정보 조회
     */
    MemberProfileResponse getMember(String accessToken);

    /**
     * 메인 화면 프로필
     */
    SimpleProfileResponse getSimpleProfile(String accessToken);
}
