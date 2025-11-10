package org.bytecub.reconsilationx.aUsrMgt.service.impl.master;

import org.bytecub.reconsilationx.aUsrMgt.dao.master.MUserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final MUserDao userDao;

    public UserDetailsService(MUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByEmail(username).orElseThrow();
    }
}
