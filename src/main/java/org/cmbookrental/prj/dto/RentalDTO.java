package org.cmbookrental.prj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 대여 DTO 클래스
 */
@Getter
@Setter
@AllArgsConstructor
public class RentalDTO {
    //대여 ID (중복 허용 안함) : 만화책을 빌린 고객의 ID
    private int rentalID;
    //대여일
    private LocalDate rentalDate;
    //대여 만화책 정보
    private ComicBookDTO comicBookDTO;

    @Override
    public int hashCode() {
        return rentalID;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RentalDTO){
            RentalDTO target = (RentalDTO) obj;
            return target.rentalDate.isEqual(rentalDate) && target.comicBookDTO.equals(comicBookDTO);
        } else return false;
    }
}
