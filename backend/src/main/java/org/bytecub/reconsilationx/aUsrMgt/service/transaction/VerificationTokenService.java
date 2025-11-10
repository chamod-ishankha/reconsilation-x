package org.bytecub.reconsilationx.aUsrMgt.service.transaction;

import org.bytecub.reconsilationx.aUsrMgt.model.master.MUser;
import org.bytecub.reconsilationx.aUsrMgt.model.transaction.TVerificationToken;

import java.util.Optional;

public interface VerificationTokenService {

    TVerificationToken createToken(MUser user);

    Optional<TVerificationToken> getToken(String token);

    String generateOTP();

    void saveOtp(String otp, Long userId);

    boolean validateOtp(String email, String otp);

    void cleanUpOtp(Long userId);
}
