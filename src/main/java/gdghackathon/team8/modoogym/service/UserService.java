package gdghackathon.team8.modoogym.service;

import gdghackathon.team8.modoogym.domain.membership.Membership;
import gdghackathon.team8.modoogym.domain.membership.MembershipRepository;
import gdghackathon.team8.modoogym.domain.user.User;
import gdghackathon.team8.modoogym.domain.user.UserRepository;
import gdghackathon.team8.modoogym.dto.membership.MembershipResponseDto;
import gdghackathon.team8.modoogym.dto.user.UserMembershipListResponseDto;
import gdghackathon.team8.modoogym.dto.user.UserResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;

    /**
     * Email 로 ID 를 반환한다. 신규 email 이라면, 회원가입도 진행된다.
     */
    @Transactional
    public Long getIdByEmail(String email) {
        User findUser = userRepository.findUserByEmail(email);
        if (findUser != null) { // 기존 회원
            return findUser.getId();
        } else { // 신규 회원 -> 자동 회원가입
            User newUser = User.builder()
                .nickname("신규회원")
                .email(email)
                .cash(0)
                .build();
            User savedUser = userRepository.save(newUser);
            return savedUser.getId();
        }
    }

    /**
     * 사용자 정보 조회: 토큰의 정보로 UserResponseDto 객체 반환
     */
    public UserResponseDto getUserResponseDtoByToken(String token) {
        User user = getUserByToken(token);
        return UserResponseDto.builder()
            .id(user.getId())
            .nickname(user.getNickname())
            .email(user.getEmail())
            .cash(user.getCash())
            .build();
    }

    /**
     * 등록(판매) 내역 조회
     */
    @Transactional
    public UserMembershipListResponseDto getSales(String token) {
        User user = getUserByToken(token);
        List<Membership> membershipList = user.getMembershipList();
        return getUserMembershipListResponseDto(membershipList);
    }

    /**
     * 구매 내역 조회
     */
    @Transactional
    public UserMembershipListResponseDto getPurchases(String token) {
        User user = getUserByToken(token);
        List<Membership> userBuyMembershipList = membershipRepository.findAll().stream()
            .filter(membership -> membership.getBuyerId().equals(user.getId()))
            .collect(Collectors.toList());
        return getUserMembershipListResponseDto(userBuyMembershipList);
    }

    private UserMembershipListResponseDto getUserMembershipListResponseDto(List<Membership> userBuyMembershipList) {
        List<MembershipResponseDto> responseDtoList = userBuyMembershipList.stream()
            .map(membership -> MembershipResponseDto.builder()
                .id(membership.getId())
                .price(membership.getPrice())
                .title(membership.getTitle())
                .description(membership.getDescription())
                .validation(membership.isValidation())
                .endDate(membership.getEndDate())
                .buyerId(membership.getBuyerId())
                .sellerId(membership.getSeller().getId())
                .fitnessId(membership.getFitness().getId())
                .build()
            ).collect(Collectors.toList());

        return new UserMembershipListResponseDto(responseDtoList);
    }

    private User getUserByToken(String token) {
        Long userId = jwtService.getPayload(token).getUserId();
        return userRepository.findById(userId).get();
    }

    /**
     * 하트 누른 회원권들 조회
     */
}
