package roomescape.mapper;

import org.springframework.stereotype.Component;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationTimeResponse;

@Component
public class ReservationTimeMapper {

    public CreateReservationTimeResponse toCreateReservationTimeResponse(ReservationTime reservationTime) {
        return new CreateReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );
    }
}