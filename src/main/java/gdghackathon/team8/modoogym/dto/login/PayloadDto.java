package gdghackathon.team8.modoogym.dto.login;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PayloadDto {

    private String email;
    private Long userId;

    @Builder
    public PayloadDto(String email, Long userId) {
        this.email = email;
        this.userId = userId;
    }
}
