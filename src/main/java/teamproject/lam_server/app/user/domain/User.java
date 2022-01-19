package teamproject.lam_server.app.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import teamproject.lam_server.constants.CategoryConstants.GenderTypes;
import teamproject.lam_server.app.schedule.domain.Schedule;
import teamproject.lam_server.app.review.domain.Review;
import teamproject.lam_server.app.review.domain.ReviewReply;
import teamproject.lam_server.app.user.converter.GenderTypeConverter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static teamproject.lam_server.constants.SessionConstants.*;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private String email;

    @Convert(converter = GenderTypeConverter.class)
    @Column(name = "gender")
    private GenderTypes genderType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date birth;

    private String image;
    /**
     * 아래의 리스트들이 사실상 필요없다.
     * 어떤 회원이 작성한 게시글들의 관점으로 보는 것이 아니라
     * 게시글을 작성할 때 회원 정보가 필요한 것.
     * 사람의 입장이 아니라 시스템의 입장에서 생각해보자
     * -> 비즈니스 로직 어떤 회원이 작성한 게시글을 찾을 때
     *    user.getReviews()가 아니라 reviewRepository.findByUser(user) 로 찾자!!
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Schedule> schedules = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ReviewReply> reviewReplies = new ArrayList<>();

    @Builder
    public User(String loginId, String password, String name, String nickname, String email, GenderTypes genderTypes, Date birth) {
        Assert.notNull(loginId, "loginId must not be empty");
        Assert.notNull(password, "password must not be empty");
        Assert.notNull(name, "name must not be empty");
        Assert.notNull(nickname, "nickname must not be empty");
        Assert.notNull(email, "email must not be empty");
        Assert.notNull(genderTypes, "gender must not be null");
        Assert.notNull(birth, "birth must not be empty");
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.genderType = genderTypes;
        this.birth = birth;
    }

    // => 비즈니스 로직
    public int calcAge() {
        Calendar current = Calendar.getInstance();
        int currentYear = current.get(Calendar.YEAR);
        int age = currentYear - this.birth.toLocalDate().getYear() + 1;
        return age;
    }

    public String getProfileImgPath() {
        if (this.image == null) return S3_BUCKET_PATH + PROFILE_IMAGE_DIR + DEFAULT_IMAGE_NAME;
        else return S3_BUCKET_PATH + PROFILE_IMAGE_DIR + this.image;
    }
}