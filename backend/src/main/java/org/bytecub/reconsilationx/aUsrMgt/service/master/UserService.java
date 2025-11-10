package org.bytecub.reconsilationx.aUsrMgt.service.master;

import org.bytecub.reconsilationx.aUsrMgt.dto.common.*;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MUserDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.ApiResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseDto companyRegistration(CRegistrationRequest request);

    AuthenticationDto login(LoginRequest loginRequest);

    MUserDto register(RegisterRequest registerRequest);

    MUserDto getUserById(Long userId);

    ApiResponseDto<List<MUserDto>> getUsers(int page, Integer perPage, String search, String sort, String direction);

    MUserDto updateUser(Long userId, MUserDto userDto);

    ResponseDto deleteUser(Long userId);

    String updateVerifyStatus(String token);

    ResponseEntity<ResponseDto> forgotPassword(String email, Long companyId);

    ResponseEntity<ResponseDto> verifyOtp(OtpVerificationDto otpVerificationDto);

    ResponseEntity<ResponseDto> resetPassword(ResetPasswordDto resetPasswordDto);

    ResponseEntity<ResponseDto> changePassword(PasswordChangeDto passwordChangeDto);

    MUserDto getUserDetailsByToken(String token);
}
