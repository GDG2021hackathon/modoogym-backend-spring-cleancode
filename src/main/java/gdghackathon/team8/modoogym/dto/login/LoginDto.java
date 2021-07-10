package gdghackathon.team8.modoogym.dto.login;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginDto {

    private String code;
    private String state;

    @Builder
    public LoginDto(String code, String state) {
        this.code = code;
        this.state = state;
    }
}
