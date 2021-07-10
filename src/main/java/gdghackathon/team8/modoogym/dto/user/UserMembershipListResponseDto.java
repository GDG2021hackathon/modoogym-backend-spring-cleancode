package gdghackathon.team8.modoogym.dto.user;

import gdghackathon.team8.modoogym.dto.membership.MembershipResponseDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserMembershipListResponseDto {

    private List<MembershipResponseDto> membershipResponseDtoList;
}
