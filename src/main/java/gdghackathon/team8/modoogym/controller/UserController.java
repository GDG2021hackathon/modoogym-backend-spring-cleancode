package gdghackathon.team8.modoogym.controller;

import gdghackathon.team8.modoogym.dto.user.UserMembershipListResponseDto;
import gdghackathon.team8.modoogym.dto.user.UserResponseDto;
import gdghackathon.team8.modoogym.service.JwtService;
import gdghackathon.team8.modoogym.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/my")
public class UserController {

    private final JwtService jwtService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDto> getUser(@RequestHeader String token) {
        if (!jwtService.isValidToken(token)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.getUserResponseDtoByToken(token), HttpStatus.OK);
    }

    @GetMapping("/sales")
    public ResponseEntity<UserMembershipListResponseDto> getSales(@RequestHeader String token) {
        if (!jwtService.isValidToken(token)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.getSales(token), HttpStatus.OK);
    }

    @GetMapping("/purchases")
    public ResponseEntity<UserMembershipListResponseDto> getPurchases(@RequestHeader String token) {
        if (!jwtService.isValidToken(token)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.getPurchases(token), HttpStatus.OK);
    }
}
