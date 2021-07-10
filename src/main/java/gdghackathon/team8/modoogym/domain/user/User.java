package gdghackathon.team8.modoogym.domain.user;

import gdghackathon.team8.modoogym.domain.membership.Membership;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname; // 별명

    private String email; // 이메일

    private int cash; // 잔액 (default: 0)

    @OneToMany(mappedBy = "seller")
    private List<Membership> membershipList = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_favorites",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "membership_id"))
    private List<Membership> memberships = new ArrayList<>();

    @Builder
    public User(String nickname, String email, int cash) {
        this.nickname = nickname;
        this.email = email;
        this.cash = cash;
    }

    public void addCash(int price) {
        cash += price;
    }

    public void subtractCash(int price) {
        cash += price;
    }
}
