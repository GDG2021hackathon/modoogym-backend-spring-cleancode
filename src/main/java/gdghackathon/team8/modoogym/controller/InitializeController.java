package gdghackathon.team8.modoogym.controller;

import gdghackathon.team8.modoogym.domain.category.Category;
import gdghackathon.team8.modoogym.domain.category.CategoryRepository;
import gdghackathon.team8.modoogym.domain.fitness.Fitness;
import gdghackathon.team8.modoogym.domain.fitness.FitnessRepository;
import gdghackathon.team8.modoogym.domain.location.Location;
import gdghackathon.team8.modoogym.domain.location.LocationRepository;
import gdghackathon.team8.modoogym.domain.membership.Membership;
import gdghackathon.team8.modoogym.domain.membership.MembershipRepository;
import gdghackathon.team8.modoogym.domain.user.User;
import gdghackathon.team8.modoogym.domain.user.UserRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class InitializeController {

    /**
     * /main/resources/data-mysql.sql 이 동작하지 않아서, 대신 테스트용 초기 데이터 INSERT 를 해주는 welcome page
     */
    private final FitnessRepository fitnessRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;

    @ResponseBody
    @GetMapping("/")
    public ResponseEntity<String> initialize() {
        Location location1 = Location.builder()
            .city("서울").region("강남").build();
        Location location2 = Location.builder()
            .city("경기").region("판교").build();
        Location location3 = Location.builder()
            .city("부산").region("해운대").build();
        locationRepository.save(location1);
        locationRepository.save(location2);
        locationRepository.save(location3);

        Category category1 = Category.builder()
            .name("헬스").build();
        Category category2 = Category.builder()
            .name("요가").build();
        Category category3 = Category.builder()
            .name("필라테스").build();
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        Fitness fitness1 = Fitness.builder()
            .name("애플필라테스").image("imageUrl").navigation("naviUrl").location(location3).category(category3).build();
        Fitness fitness2 = Fitness.builder()
            .name("바나나요가").image("imageUrl").navigation("naviUrl").location(location2).category(category2).build();
        Fitness fitness3 = Fitness.builder()
            .name("당근휘트니스").image("imageUrl").navigation("naviUrl").location(location1).category(category1).build();
        fitnessRepository.save(fitness1);
        fitnessRepository.save(fitness2);
        fitnessRepository.save(fitness3);

        User user1 = User.builder()
            .nickname("javascript").email("javascript@gmail.com").cash(50000).build();
        User user2 = User.builder()
            .nickname("spring").email("spring@gmail.com").cash(34000).build();
        User user3 = User.builder()
            .nickname("cplusplus").email("cplusplus@gmail.com").cash(11000).build();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        Membership membership1 = Membership.builder()
            .price(12000).description("설명").validation(true).endDate(LocalDate.of(2021, 11, 21))
            .isSoldOut(false).fitness(fitness1).seller(user1).buyerId(null).title("제목제목").build();
        Membership membership2 = Membership.builder()
            .price(12000).description("설명").validation(true).endDate(LocalDate.of(2021, 11, 21))
            .isSoldOut(false).fitness(fitness2).seller(user2).buyerId(null).title("제목제목").build();
        Membership membership3 = Membership.builder()
            .price(12000).description("설명").validation(true).endDate(LocalDate.of(2021, 11, 21))
            .isSoldOut(false).fitness(fitness3).seller(user3).buyerId(null).title("제목제목").build();
        Membership membership4 = Membership.builder()
            .price(10000).description("설명").validation(true).endDate(LocalDate.of(2021, 8, 18))
            .isSoldOut(false).fitness(fitness2).seller(user2).buyerId(null).title("제목제목").build();
        Membership membership5 = Membership.builder()
            .price(8000).description("설명").validation(true).endDate(LocalDate.of(1988, 8, 18))
            .isSoldOut(false).fitness(fitness3).seller(user3).buyerId(null).title("제목제목").build();
        membershipRepository.save(membership1);
        membershipRepository.save(membership2);
        membershipRepository.save(membership3);
        membershipRepository.save(membership4);
        membershipRepository.save(membership5);

        return ResponseEntity.ok("초기값 설정 완료");
    }
}
