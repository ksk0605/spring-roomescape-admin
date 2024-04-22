package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.CreateReservationTimeRequest;
import roomescape.domain.ReservationTime;
import roomescape.service.TimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> readTimes() {
        List<ReservationTime> reservationTimes = timeService.readAll();
        return ResponseEntity.ok(reservationTimes);
    }

    @PostMapping
    public ResponseEntity<ReservationTime> createTime(@RequestBody CreateReservationTimeRequest request) {
        ReservationTime reservationTime = timeService.createTime(request);
        return ResponseEntity.ok(reservationTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteTime(@PathVariable int id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}