package teamproject.lam_server.domain.interaction.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InteractionRequest {

    @NotNull
    private Long from;

    @NotNull
    private Long to;
}
