package org.bytecub.reconsilationx.aUsrMgt.dao.transaction;

import org.bytecub.reconsilationx.aUsrMgt.model.transaction.TVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TVerificationTokenDao extends JpaRepository<TVerificationToken, Long> {
  Optional<TVerificationToken> findByVerificationToken(String token);

  Optional<TVerificationToken> findByUserUserId(Long userId);
}