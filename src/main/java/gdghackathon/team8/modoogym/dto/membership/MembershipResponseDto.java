package gdghackathon.team8.modoogym.dto.membership;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MembershipResponseDto {

    private Long id;
    private int price; // 판매 가격
    private String title; // 회원권 제목
    private String description; // 부가 설명
    private boolean validation; // 검증 여부
    private LocalDate endDate; // 사용 마감일
    private Long buyerId; // 구매자 ID (null 여부로 판매 여부 판단)
    private Long sellerId; // 판매자 ID
    private Long fitnessId; // Fitness ID

    @Builder
    public MembershipResponseDto(Long id, int price, String title, String description, boolean validation, LocalDate endDate,
        Long buyerId, Long sellerId, Long fitnessId) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.description = description;
        this.validation = validation;
        this.endDate = endDate;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.fitnessId = fitnessId;
    }
}
