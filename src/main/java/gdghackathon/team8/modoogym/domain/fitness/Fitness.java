package gdghackathon.team8.modoogym.domain.fitness;

import gdghackathon.team8.modoogym.domain.category.Category;
import gdghackathon.team8.modoogym.domain.location.Location;
import gdghackathon.team8.modoogym.domain.membership.Membership;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fitness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name; // 이름

    private String navigation; // 길찾기 안내 URL

    private String image; // 이미지 URL

    private double score; // 평점

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "fitness")
    private List<Membership> membershipList = new ArrayList<>();

    @Builder
    public Fitness(String name, String navigation, String image, double score, Category category, Location location) {
        this.name = name;
        this.navigation = navigation;
        this.image = image;
        this.score = score;
        this.category = category;
        this.location = location;
    }

    public int getMembershipCount() { // 등록된 회원권 수
        return membershipList.size();
    }
}
