package org.bytecub.reconsilationx.aUsrMgt.service.impl.transaction;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.reconsilationx.aUsrMgt.dao.master.MUserDao;
import org.bytecub.reconsilationx.aUsrMgt.dao.transaction.TPasswordResetTokenDao;
import org.bytecub.reconsilationx.aUsrMgt.dao.transaction.TVerificationTokenDao;
import org.bytecub.reconsilationx.aUsrMgt.dto.transaction.TPasswordResetTokenDto;
import org.bytecub.reconsilationx.aUsrMgt.mappers.transaction.PasswordResetTokenMapper;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MUser;
import org.bytecub.reconsilationx.aUsrMgt.model.transaction.TPasswordResetToken;
import org.bytecub.reconsilationx.aUsrMgt.model.transaction.TVerificationToken;
import org.bytecub.reconsilationx.aUsrMgt.service.transaction.VerificationTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final TVerificationTokenDao verificationTokenDao;
    private final TPasswordResetTokenDao passwordResetTokenDao;
    private final PasswordResetTokenMapper passwordResetTokenMapper;
    private final MUserDao userDao;

    public VerificationTokenServiceImpl(TVerificationTokenDao verificationTokenDao, TPasswordResetTokenDao passwordResetTokenDao, PasswordResetTokenMapper passwordResetTokenMapper, MUserDao userDao) {
        this.verificationTokenDao = verificationTokenDao;
        this.passwordResetTokenDao = passwordResetTokenDao;
        this.passwordResetTokenMapper = passwordResetTokenMapper;
        this.userDao = userDao;
    }

    @Override
    public TVerificationToken createToken(MUser user) {
        Optional<TVerificationToken> existingToken = verificationTokenDao.findByUserUserId(user.getUserId());
        if (existingToken.isPresent()) {
            if (existingToken.get().getExpiryDate().isBefore(LocalDateTime.now())) {
                verificationTokenDao.delete(existingToken.get());
            } else {
                return existingToken.get();
            }
        }
        TVerificationToken token = new TVerificationToken(user);
        return verificationTokenDao.save(token);
    }

    @Override
    public Optional<TVerificationToken> getToken(String token) {
        return verificationTokenDao.findByVerificationToken(token);
    }

    @Override
    public String generateOTP() {
        return String.valueOf(100000 + new Random().nextInt(900000)); // Generates a 6-digit OTP
    }

    @Override
    public void saveOtp(String otp, Long userId) {
        TPasswordResetTokenDto token = new TPasswordResetTokenDto();
        token.setUserId(userId);
        token.setOtp(otp);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(10)); // OTP expires in 10 minutes
        cleanUpOtp(userId);
        passwordResetTokenDao.save(passwordResetTokenMapper.toEntity(token));
    }

    @Override
    public boolean validateOtp(String email, String otp) {
        Optional<TPasswordResetToken> tokenOpt = passwordResetTokenDao.findByUserEmail(email);
        if (tokenOpt.isEmpty()) return false;

        TPasswordResetToken token = tokenOpt.get();
        if (token.getExpiresAt().isBefore(LocalDateTime.now())) return false; // Expired
        return token.getOtp().equals(otp); // Check OTP match
    }

    @Override
    public void cleanUpOtp(Long userId) {
        passwordResetTokenDao.findByUserUserId(userId).ifPresent(passwordResetTokenDao::delete);
    }
}
