package gdghackathon.team8.modoogym.service;

import gdghackathon.team8.modoogym.domain.fitness.Fitness;
import gdghackathon.team8.modoogym.domain.fitness.FitnessRepository;
import gdghackathon.team8.modoogym.domain.membership.Membership;
import gdghackathon.team8.modoogym.domain.membership.MembershipRepository;
import gdghackathon.team8.modoogym.domain.user.User;
import gdghackathon.team8.modoogym.domain.user.UserRepository;
import gdghackathon.team8.modoogym.dto.membership.MembershipCreateDto;
import gdghackathon.team8.modoogym.dto.membership.MembershipResponseDto;
import java.time.LocalDate;
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
public class MembershipService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final FitnessRepository fitnessRepository;

    /**
     * 회원권 1개 조회
     */
    public MembershipResponseDto getMembership(Long membershipId) {
        Membership membership = membershipRepository.findById(membershipId).get();
        return MembershipResponseDto.builder()
            .id(membership.getId())
            .price(membership.getPrice())
            .title(membership.getTitle())
            .description(membership.getDescription())
            .validation(membership.isValidation())
            .endDate(membership.getEndDate())
            .buyerId(membership.getBuyerId())
            .sellerId(membership.getSeller().getId())
            .fitnessId(membership.getFitness().getId())
            .build();
    }

    /**
     * Fitness에 등록된 회원권 모두 조회
     */
    public List<MembershipResponseDto> getMembershipsByFitness(Long fitnessId) {
        return fitnessRepository.findById(fitnessId).get()
            .getMembershipList().stream()
            .filter(membership -> membership.getBuyerId() == null) // 판매되지 않은 회원권 필터링
            .map(membership -> MembershipResponseDto.builder() // convert Membership to MembershipResponseDto
                .id(membership.getId())
                .price(membership.getPrice())
                .title(membership.getTitle())
                .description(membership.getDescription())
                .validation(membership.isValidation())
                .endDate(membership.getEndDate())
                .buyerId(membership.getBuyerId())
                .sellerId(membership.getSeller().getId())
                .fitnessId(membership.getFitness().getId())
                .build())
            .collect(Collectors.toList());
    }

    /**
     * 회원권 생성
     */
    @Transactional
    public boolean createMembership(MembershipCreateDto requestDto, String token) {
        if (!jwtService.isValidToken(token)) {
            return false;
        }

        Long sellerId = jwtService.getPayload(token).getUserId();
        User seller = userRepository.findById(sellerId).get();
        Fitness fitness = fitnessRepository.findById(requestDto.getFitnessId()).get();

        Membership membership = Membership.builder()
            .price(requestDto.getPrice())
            .title(requestDto.getTitle())
            .description(requestDto.getDescription())
            .validation(true)
            .endDate(requestDto.getEndDate())
            .buyerId(null)
            .seller(seller)
            .fitness(fitness)
            .build();

        if (!membership.isValidation()) {
            log.warn("아직 승인되지 않은 회원권입니다. (membershipId = {})", membership.getId());
        } else if (membership.getEndDate().isBefore(LocalDate.now())) {
            log.warn("사용 기한이 지난 회원권입니다. (membershipId = {})", membership.getId());
        } else {
            log.info("{}님의 회원권이 등록되었습니다! (membershipId = {})", seller.getEmail(), membership.getId());
            membershipRepository.save(membership); // 회원권 DB 에 저장
            fitness.getMembershipList().add(membership); // 해당 Fitness에 회원권 추가
            return true;
        }
        return false;
    }

    /**
     * 회원권 구매
     */
    @Transactional
    public boolean purchaseMembership(Long membershipId, String token) {
        if (!jwtService.isValidToken(token)) {
            return false;
        }

        Long buyerId = jwtService.getPayload(token).getUserId();
        User buyer = userRepository.findById(buyerId).get();

        Membership membership = membershipRepository.findById(membershipId).get();

        if (!membership.isValidation()) {
            log.warn("아직 승인되지 않은 회원권입니다. (membershipId = {})", membership.getId());
        } else if (membership.getBuyerId() != null) {
            log.warn("이미 판매된 회원권입니다. (membershipId = {})", membership.getId());
        } else if (membership.getEndDate().isBefore(LocalDate.now())) {
            log.warn("사용 기한이 지난 회원권입니다. (membershipId = {})", membership.getId());
        } else if (membership.getPrice() > buyer.getCash()) {
            log.warn("Cash가 부족하여 구매할 수 없습니다. (membershipId = {})", membership.getId());
        } else {
            log.info("{}님의 회원권 구매가 확정되었습니다! (membershipId = {})", buyer.getEmail(), membership.getId());
            membership.saleTo(buyerId); // 회원권 객체에 구매자 ID 저장
            membership.getSeller().addCash(membership.getPrice()); // 판매자 Cash 증가
            buyer.subtractCash(membership.getPrice()); // 구매자 Cash 감소
            return true;
        }
        return false;
    }
}
