package teamproject.lam_server.domain.interaction.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InteractionState implements EnumMapperType {
    LIKE("추천"),
    DISLIKE("비추천"),
    ;
    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}
