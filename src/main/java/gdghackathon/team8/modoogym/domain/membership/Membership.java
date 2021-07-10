package gdghackathon.team8.modoogym.domain.membership;

import gdghackathon.team8.modoogym.domain.fitness.Fitness;
import gdghackathon.team8.modoogym.domain.user.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String title; // 회원권 제목

    private int price; // 판매 가격

    private String description; // 부가 설명

    private boolean validation; // 검증 여부

    private LocalDate endDate; // 사용 마감일

    private Long buyerId; // 구매자 ID (null 여부로 판매 여부 판단)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller; // 판매자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fitness_id")
    private Fitness fitness; // 소속 Fitness

    @ManyToMany(mappedBy = "memberships") // user_favorites
    private List<User> users = new ArrayList<>();

    @Builder
    public Membership(int price, String title, String description, boolean validation, LocalDate endDate, boolean isSoldOut,
        User seller, Long buyerId, Fitness fitness) {
        this.price = price;
        this.title = title;
        this.description = description;
        this.validation = validation;
        this.endDate = endDate;
        this.seller = seller;
        this.buyerId = buyerId;
        this.fitness = fitness;
    }

    public void saleTo(Long buyerId) {
        this.buyerId = buyerId;
    }
}