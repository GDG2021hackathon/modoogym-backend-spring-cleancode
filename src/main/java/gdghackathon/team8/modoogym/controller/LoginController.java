package gdghackathon.team8.modoogym.controller;

import gdghackathon.team8.modoogym.dto.login.LoginDto;
import gdghackathon.team8.modoogym.dto.login.PayloadDto;
import gdghackathon.team8.modoogym.service.JwtService;
import gdghackathon.team8.modoogym.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {

    @Value("${naver.client-id}")
    String CLIENT_ID;

    @Value("${naver.client-secret}")
    String CLIENT_SECRET;

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/auth-login")
    public ResponseEntity<Void> getJwt(@RequestBody LoginDto loginDto, @RequestParam String email) {
        String code = loginDto.getCode();
        String state = loginDto.getState();

        // === 변경될 부분 - start ===
        log.info("code = {}", code);
        log.info("state = {}", state);
        log.info("clientId = {}", CLIENT_ID);
        log.info("clientSecret = {}", CLIENT_SECRET);

        log.info("API 요청 후) Access Token 얻기 성공!");
        log.info("API 요청 후) 회원 정보 얻기 성공!");

//        String email = "jinseong.dev@gmail.com";
        Long id = userService.getIdByEmail(email);
        // === 변경될 부분 - end ===

        PayloadDto payload = PayloadDto.builder()
            .email(email)
            .userId(id)
            .build();

        String token = jwtService.createToken(payload);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("token", token);

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    // 토큰 유효성 테스트를 위한 임시 API
    @GetMapping("/validation")
    public ResponseEntity<String> tokenValidation(@RequestParam String token) {
        log.info("token = {}", token);
        if (jwtService.isValidToken(token)) {
            return new ResponseEntity<>("성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("실패", HttpStatus.BAD_REQUEST);
        }
    }
}
