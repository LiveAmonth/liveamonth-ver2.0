package teamproject.lam_server.domain.member.exception.messages;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserExceptionMessages {
    USER_NOT_FOUND_EXCEPTION_MESSAGES(" user not found"),
    ALREADY_DROP_USER_EXCEPTION_MESSAGE(" user already dropped"),
    NORMAL_USER_OR_NOT_FOUND_EXCEPTION_MESSAGES(" user not dropped yet or not found"),
    EXISTS_EXCEPTION_MESSAGES(" already exits"),
    VALID_REFRESH_TOKEN_MESSAGE(" refresh token is not valid"),
    CORRESPOND_REFRESH_TOKEN_MESSAGE(" refresh token is not correspond");
    private String message;

    public String getMessage(Long id) {
        return "[ID]" + id + message;
    }
    public String getMessage() {return message;}
    public String getMessage(String userId) {
        return "[request]" + userId + message;
    }
    public String getExistMessage(String param) {
        return "[param: "+param+"]"+EXISTS_EXCEPTION_MESSAGES;
    }
}