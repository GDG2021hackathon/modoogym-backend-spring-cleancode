package gdghackathon.team8.modoogym.domain.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String city; // 지역: 대단위 (ex. 서울)

    @Column(length = 50)
    private String region; // 지역: 소단위 (ex. 강남, 신촌, 잠실)

    @Builder
    public Location(String city, String region) {
        this.city = city;
        this.region = region;
    }
}
