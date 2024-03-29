package teamproject.lam_server.domain.city.dto.response.api;

import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;

import static teamproject.lam_server.constants.AttrConstants.IMAGEBB_URL;

@Getter
public class CityGridDataResponse {
    private final CityName name;
    private final String image;
    private final float averageDegree;
    private final int transportScore;

    public CityGridDataResponse(CityName name, String image, float averageDegree, int transportScore) {
        this.name = name;
        this.image = IMAGEBB_URL + image;
        this.averageDegree = averageDegree;
        this.transportScore = transportScore;
    }
}
