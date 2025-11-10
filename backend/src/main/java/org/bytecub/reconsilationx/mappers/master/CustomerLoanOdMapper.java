package org.bytecub.reconsilationx.mappers.master;

import org.bytecub.reconsilationx.dto.master.MCustomerLoanOdDto;
import org.bytecub.reconsilationx.model.master.MCustomerLoanOd;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerLoanOdMapper {

    MCustomerLoanOd toEntity(MCustomerLoanOdDto customerLoanOdDto);

    MCustomerLoanOdDto toDto(MCustomerLoanOd customerLoanOd);

    List<MCustomerLoanOdDto> listToDto(List<MCustomerLoanOd> customerLoanOds);
}
