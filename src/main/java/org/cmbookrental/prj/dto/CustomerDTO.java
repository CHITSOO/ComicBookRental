package org.cmbookrental.prj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 고객 DTO 클래스
 */
@Getter
@Setter
@AllArgsConstructor
public class CustomerDTO {
    //고객 ID (중복 허용 안함)
    private int customerID;
    //고객명
    private String name;

    @Override
    public int hashCode() {
        return customerID;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CustomerDTO){
            CustomerDTO target = (CustomerDTO) obj;
            return target.name.equals(name);
        } else return false;
    }

    @Override
    public String toString() {
        return "고객 ID : " + customerID + ", 고객명 : " + name;
    }
}
