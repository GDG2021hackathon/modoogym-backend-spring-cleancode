package gdghackathon.team8.modoogym.dto.membership;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MembershipCreateDto {

    private int price; // 판매 가격
    private String title; // 회원권 제목
    private String description; // 부가 설명
    private LocalDate endDate; // 사용 마감일
    private Long sellerId; // 판매자 ID
    private Long fitnessId; // Fitness ID
}
