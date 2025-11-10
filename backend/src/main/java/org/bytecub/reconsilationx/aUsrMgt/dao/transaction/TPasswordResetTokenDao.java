package org.bytecub.reconsilationx.aUsrMgt.dao.transaction;

import org.bytecub.reconsilationx.aUsrMgt.model.transaction.TPasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TPasswordResetTokenDao extends JpaRepository<TPasswordResetToken, Long>, JpaSpecificationExecutor<TPasswordResetToken> {
  void deleteByUserUserId(Long userId);

  Optional<TPasswordResetToken> findByUserEmail(String email);

  void deleteByUserEmail(String email);

  Optional<TPasswordResetToken> findByUserUserId(Long userId);
}