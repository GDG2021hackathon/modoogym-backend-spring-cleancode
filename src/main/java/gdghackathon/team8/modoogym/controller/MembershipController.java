package gdghackathon.team8.modoogym.controller;

import gdghackathon.team8.modoogym.dto.membership.MembershipCreateDto;
import gdghackathon.team8.modoogym.dto.membership.MembershipResponseDto;
import gdghackathon.team8.modoogym.service.JwtService;
import gdghackathon.team8.modoogym.service.MembershipService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/memberships")
public class MembershipController {

    private final JwtService jwtService;
    private final MembershipService membershipService;

    @GetMapping("/{membershipId}")
    public ResponseEntity<MembershipResponseDto> getMembership(@PathVariable Long membershipId) {
        return new ResponseEntity<>(
            membershipService.getMembership(membershipId),
            HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<List<MembershipResponseDto>> getMembershipsByFitness(@RequestParam("fitness_id") Long fitnessId){
        return new ResponseEntity<>(
            membershipService.getMembershipsByFitness(fitnessId),
            HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Void> createMembership(@RequestBody MembershipCreateDto requestDto, @RequestHeader String token) {
        if (!jwtService.isValidToken(token)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (membershipService.createMembership(requestDto, token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{membershipId}/buy")
    public ResponseEntity<Void> purchaseMembership(@PathVariable Long membershipId, @RequestHeader String token) {
        if (!jwtService.isValidToken(token)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (membershipService.purchaseMembership(membershipId, token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
