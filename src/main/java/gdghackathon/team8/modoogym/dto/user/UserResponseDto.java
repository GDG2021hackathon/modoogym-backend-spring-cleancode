package gdghackathon.team8.modoogym.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponseDto {

    private Long id;
    private String nickname; // 별명
    private String email; // 이메일
    private int cash; // 잔액 (default: 0)

    @Builder
    public UserResponseDto(Long id, String nickname, String email, int cash) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.cash = cash;
    }
}
