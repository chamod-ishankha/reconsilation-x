package org.bytecub.reconsilationx.aUsrMgt.mappers.transaction;

import org.bytecub.reconsilationx.aUsrMgt.dto.transaction.TPasswordResetTokenDto;
import org.bytecub.reconsilationx.aUsrMgt.mappers.master.UserMapper;
import org.bytecub.reconsilationx.aUsrMgt.model.transaction.TPasswordResetToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PasswordResetTokenMapper {
    @Mappings({
            @Mapping(target = "user.userId", source = "userId"),
    })
    TPasswordResetToken toEntity(TPasswordResetTokenDto TPasswordResetTokenDto);

    @Mappings({
            @Mapping(source = "user.userId", target = "userId"),
    })
    TPasswordResetTokenDto toDto(TPasswordResetToken TPasswordResetToken);
}