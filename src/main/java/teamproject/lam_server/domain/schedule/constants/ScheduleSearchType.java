package teamproject.lam_server.domain.schedule.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ScheduleSearchType implements EnumMapperType {
    MOVIE_TITLE("영화 제목"),
    THEATER_NAME("극장 이름"),
    SCREEN_FORMAT("상영관 포멧"),
    SCREEN_START_TIME("시작 날짜"),
    SCREEN_END_TIME("끝 날짜");
    private String value;

    @Override
    public String getCode() {
        return name();
    }
}
