package org.bytecub.reconsilationx.aUsrMgt.service.impl.master;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.reconsilationx.aUsrMgt.dao.master.MCompanyBranchDao;
import org.bytecub.reconsilationx.aUsrMgt.dao.master.MCompanyDao;
import org.bytecub.reconsilationx.aUsrMgt.dao.master.MUserDao;
import org.bytecub.reconsilationx.aUsrMgt.dao.reference.RStatusDao;
import org.bytecub.reconsilationx.aUsrMgt.dao.transaction.TLoginHistoryDao;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MCompanyBranchDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.common.*;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MCompanyDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MUserDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.ApiResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.PaginationDto;
import org.bytecub.reconsilationx.aUsrMgt.error.BadRequestAlertException;
import org.bytecub.reconsilationx.aUsrMgt.mappers.master.CompanyBranchMapper;
import org.bytecub.reconsilationx.aUsrMgt.mappers.master.CompanyMapper;
import org.bytecub.reconsilationx.aUsrMgt.mappers.master.UserMapper;
import org.bytecub.reconsilationx.aUsrMgt.mappers.reference.StatusMapper;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MCompany;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MCompanyBranch;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MUser;
import org.bytecub.reconsilationx.aUsrMgt.model.transaction.TLoginHistory;
import org.bytecub.reconsilationx.aUsrMgt.model.transaction.TVerificationToken;
import org.bytecub.reconsilationx.aUsrMgt.service.master.UserService;
import org.bytecub.reconsilationx.aUsrMgt.service.reference.ReferenceService;
import org.bytecub.reconsilationx.aUsrMgt.service.transaction.VerificationTokenService;
import org.bytecub.reconsilationx.aUsrMgt.utils.JWTUtils;
import org.bytecub.reconsilationx.aUsrMgt.utils.RequestContextHolderUtil;
import org.bytecub.reconsilationx.aUsrMgt.utils.Search.FilterCriteria;
import org.bytecub.reconsilationx.aUsrMgt.utils.Search.FilterUtility;
import org.bytecub.reconsilationx.aUsrMgt.utils.Services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.bytecub.reconsilationx.aUsrMgt.constants.Common.*;
import static org.bytecub.reconsilationx.aUsrMgt.constants.EmailTemplates.*;
import static org.bytecub.reconsilationx.aUsrMgt.constants.Roles.SUPER_ADMIN;
import static org.bytecub.reconsilationx.aUsrMgt.constants.Status.NEW;
import static org.bytecub.reconsilationx.aUsrMgt.utils.JWTUtils.EXPIRATION_TIME;
import static org.bytecub.reconsilationx.aUsrMgt.utils.Utils.uniqKeyGenerator;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final MUserDao userDao;
    private final UserDetailsService userDetailsService;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final StatusMapper statusMapper;
    private final VerificationTokenService verificationTokenService;
    private final FilterUtility<MUser, Long> userFilterUtility;
    private final MCompanyDao companyDao;
    private final CompanyMapper companyMapper;
    private final RStatusDao statusDao;
    private final ReferenceService referenceService;
    private final TLoginHistoryDao loginHistoryDao;
    private final CompanyBranchMapper companyBranchMapper;
    private final MCompanyBranchDao companyBranchDao;

    @Value("${app.base-url}")
    private String appBaseUrl;

    public UserServiceImpl(MUserDao userDao, UserDetailsService userDetailsService, JWTUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserMapper userMapper, EmailService emailService, StatusMapper statusMapper, VerificationTokenService verificationTokenService, MCompanyDao companyDao, CompanyMapper companyMapper, RStatusDao statusDao, ReferenceService referenceService, TLoginHistoryDao loginHistoryDao, CompanyBranchMapper companyBranchMapper, MCompanyBranchDao companyBranchDao) {
        this.userDao = userDao;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.emailService = emailService;
        this.statusMapper = statusMapper;
        this.verificationTokenService = verificationTokenService;
        this.userFilterUtility = new FilterUtility<>(userDao);
        this.companyDao = companyDao;
        this.companyMapper = companyMapper;
        this.statusDao = statusDao;
        this.referenceService = referenceService;
        this.loginHistoryDao = loginHistoryDao;
        this.companyBranchMapper = companyBranchMapper;
        this.companyBranchDao = companyBranchDao;
    }

    @Override
    public ResponseDto companyRegistration(CRegistrationRequest request) {
        log.info("Inside user service company registration method");
        try {
            // register the company
            MCompanyDto companyDto = new MCompanyDto();
            companyDto.setName(request.getCompanyName());
            companyDto.setEmail(request.getCompanyEmail());
            companyDto.setContactNo(request.getCompanyContactNo());
            companyDto.setAddress(request.getCompanyAddress());
            companyDto.setWebsite(request.getCompanyWebsite());
            companyDto.setCompanyDescription(request.getCompanyDescription());
            companyDto.setIsActive(true);
            companyDto.setStatusId(statusDao.findByStatusName(NEW).get().getStatusId());
            companyDto = companyMapper.toDto(companyDao.save(companyMapper.toEntity(companyDto)));

            // regiter company default branch
            MCompanyBranchDto companyBranchDto = new MCompanyBranchDto();
            companyBranchDto.setBranchCode(COMPANY_DEFAULT_BRANCH_CODE);
            companyBranchDto.setBranchName(COMPANY_DEFAULT_BRANCH_NAME);
            companyBranchDto.setBranchAddress(COMPANY_DEFAULT_ADDRESS);
            companyBranchDto.setCompanyId(companyDto.getCompanyId());
            companyBranchDto.setIsActive(true);
            companyBranchDto.setStatusId(statusDao.findByStatusName(NEW).get().getStatusId());
            companyBranchDto = companyBranchMapper.toDto(companyBranchDao.save(companyBranchMapper.toEntity(companyBranchDto)));

            // register the company super admin user
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setFirstName(request.getAdminFirstName());
            registerRequest.setLastName(request.getAdminLastName());
            registerRequest.setEmail(request.getAdminEmail());
            registerRequest.setPhone(request.getAdminPhone());
            registerRequest.setPassword(request.getAdminPassword());
            registerRequest.setCompanyId(companyDto.getCompanyId());
            registerRequest.setBranchId(companyBranchDto.getBranchId());
            MUserDto companyUser = register(registerRequest);

            // assign the super admin role to user
            referenceService.getRoleService().assignRoleToUser(
                    companyUser.getUserId(),
                    referenceService.getRoleService().getRoleByRoleName(SUPER_ADMIN).getRoleId());

            return new ResponseDto(companyDto.getCompanyId(), "Your company is registered and super admin account has been created. Please verify your admin email!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "Company registration failed");
        }
    }

    @Override
    public AuthenticationDto login(LoginRequest loginRequest) {
        log.info("Inside user service login method");
        AuthenticationDto authenticationDto = new AuthenticationDto();

        // Fetch user before authentication for lockout logic
        MCompany company = companyDao.findByEmailAndIsActive(loginRequest.getCompanyEmail(), true)
                .orElseThrow(() -> new BadRequestAlertException("Company not found", "User", "Login failed"));
        MUser user = userDao.findByEmailAndCompanyEmail(loginRequest.getEmail(), loginRequest.getCompanyEmail())
                .orElseThrow(() -> new BadRequestAlertException("Invalid credentials", "User", "Login failed"));

        if (Boolean.TRUE.equals(user.getIsLocked())) {
            throw new BadRequestAlertException("Your account is locked due to multiple failed login attempts. Please contact admin.", "User", "Account locked");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getPassword()));

            // Successful login – reset failed attempts
            user.setFailedAttempts(0);
            user.setIsLocked(false);
            userDao.save(user); // save updated flags

            // Check email verification
            if (!user.getIsVerified()) {
                log.error("User is not verified! Please follow verification link sent to your email");

                String newVerificationLink = appBaseUrl + "/auth/verify?token=" +
                        verificationTokenService.createToken(user).getVerificationToken();

                String content = EXPIRED_LINK_EMAIL_CONTENT;
                content = content.replace("{{firstName}}", user.getFirstName());
                content = content.replace("{{newVerificationLink}}", newVerificationLink);
                content = content.replace("{{companyName}}", user.getCompany().getName());

                String subject = EXPIRED_LINK_EMAIL_SUBJECT;
                subject = subject.replace("{{companyName}}", user.getCompany().getName());

                emailService.sendEmail(user.getEmail(), subject, content);

                throw new BadRequestAlertException("User is not verified! Please follow verification link sent to your email", "User", "User login failed");
            }

            // Generate JWT and refresh token
            String jwt = jwtUtils.generateToken(user);
            String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            // Save login history
            TLoginHistory loginHistory = new TLoginHistory();
            loginHistory.setUser(user);
            loginHistory.setIpAddress(RequestContextHolderUtil.getClientIpAddress()); // implement this util
            loginHistory.setDevice(RequestContextHolderUtil.getUserAgent());          // implement this util
            loginHistory.setLoginTime(LocalDateTime.now());
            loginHistoryDao.save(loginHistory);

            // Build response
            authenticationDto.setUserId(user.getUserId());
            authenticationDto.setFirstName(user.getFirstName());
            authenticationDto.setLastName(user.getLastName());
            authenticationDto.setEmail(user.getEmail());
            authenticationDto.setPhone(user.getPhone());
            authenticationDto.setWhrStatus(statusMapper.toDto(user.getStatus()));
            authenticationDto.setUniqKey(user.getUniqKey());
            authenticationDto.setDob(user.getDob());
            authenticationDto.setCompanyDto(companyMapper.toDto(user.getCompany()));

            TokenDto tokenDto = new TokenDto();
            tokenDto.setTokenType(TOKEN_TYPE);
            tokenDto.setToken(jwt);
            tokenDto.setRefreshToken(refreshToken);
            tokenDto.setExpiresIn(EXPIRATION_TIME);
            authenticationDto.setTokenDto(tokenDto);

            return authenticationDto;

        } catch (AuthenticationException e) {
            log.warn("Login failed for user: {}", loginRequest.getEmail());

            if (user.getIsVerified()) {
                // Increase failed attempts
                int attempts = user.getFailedAttempts() != null ? user.getFailedAttempts() + 1 : 1;
                user.setFailedAttempts(attempts);

                System.out.println("Failed login attempt for user: " + loginRequest.getEmail() + ", attempts: " + attempts);

                // Lock user if exceeded max attempts
                if (attempts >= company.getMaxFailedLoginAttempts()) {
                    user.setIsLocked(true);
                }

                userDao.saveAndFlush(user);
            }

            throw new BadRequestAlertException(e.getMessage(), "User", "Login failed");
        } catch (Exception e) {
            log.error("Unexpected error during login for user: {}", loginRequest.getEmail(), e);
            throw new BadRequestAlertException(e.getMessage(), "User", "Login failed");
        }
    }


    @Override
    @Transactional
    public MUserDto register(RegisterRequest registerRequest) {
        log.info("Inside user service register method");
        MUserDto userDto = new MUserDto();
        try {
            // validation
            Optional<MCompany> companyOp = companyDao.findByCompanyIdAndIsActive(registerRequest.getCompanyId(), true);
            if (companyOp.isEmpty()) {
                throw new BadRequestAlertException("Company not found", "User", "User registration failed");
            }
            Optional<MCompanyBranch> branchOp = companyBranchDao.findByBranchIdAndIsActive(registerRequest.getBranchId(), true);
            if (branchOp.isEmpty())
                throw new BadRequestAlertException("Branch not found", "User", "User registration failed");
            if (userDao.existsByEmailAndCompanyCompanyIdAndBranchBranchIdAndIsActive(registerRequest.getEmail(), registerRequest.getCompanyId(), registerRequest.getBranchId(), true)) {
                throw new BadRequestAlertException("Email already exists", "User", "User registration failed");
            }

            userDto.setFirstName(registerRequest.getFirstName());
            userDto.setLastName(registerRequest.getLastName());
            userDto.setEmail(registerRequest.getEmail());
            userDto.setPhone(registerRequest.getPhone());
            userDto.setStatusId(statusDao.findByStatusName(NEW).get().getStatusId());
            userDto.setUniqKey(uniqKeyGenerator());
            userDto.setIsActive(true);
            userDto.setCompanyId(registerRequest.getCompanyId());
            userDto.setBranchId(registerRequest.getBranchId());
            userDto.setIsVerified(!companyOp.get().getEnableEmailVerification());
            userDto.setDob(registerRequest.getDob());

            MUser user = userMapper.toEntity(userDto);
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

            userDto = userMapper.toDto(userDao.save(user));

            MCompany company = companyDao.findById(userDto.getCompanyId()).get();

            // create email verification link
            String verificationLink = appBaseUrl + "/auth/verify?token=" + verificationTokenService.createToken(user).getVerificationToken();
            // send verification & registration email
            String content = USER_CREATION_EMAIL_CONTENT;
            content = content.replace("{{firstName}}", userDto.getFirstName());
            content = content.replace("{{username}}", userDto.getEmail());
            content = content.replace("{{userId}}", userDto.getUniqKey());
            content = content.replace("{{verificationLink}}", verificationLink);
            content = content.replace("{{companyName}}", company.getName());

            String subject = USER_CREATION_EMAIL_SUBJECT;
            subject = subject.replace("{{companyName}}", company.getName());

            emailService.sendEmail(userDto.getEmail(), subject, content);

            return userDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "User registration failed");
        }
    }

    @Override
    public MUserDto getUserById(Long userId) {
        log.info("Inside user service get user by id method");
        try {
            Optional<MUser> userOp = userDao.findById(userId);
            if (userOp.isEmpty()) {
                throw new BadRequestAlertException("User not found", "User", "User not found");
            } else {
                return userMapper.toDto(userOp.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public ApiResponseDto<List<MUserDto>> getUsers(int page, Integer perPage, String search, String sort, String direction) {
        log.info("Inside user service get all users method");
        try {
            List<FilterCriteria<MUser>> filterCriteriaList = new ArrayList<>();
            if (search != null && !search.isEmpty()) {
                filterCriteriaList.add((root1, criteriaBuilder) ->
                        (root, query, cb) -> cb.like(
                                cb.lower(root.get("firstName").as(String.class)),
                                "%" + search.toLowerCase() + "%"
                        )
                );
                filterCriteriaList.add((root1, criteriaBuilder) ->
                        (root, query, cb) -> cb.like(
                                cb.lower(root.get("lastName").as(String.class)),
                                "%" + search.toLowerCase() + "%"
                        )
                );
            }

            filterCriteriaList.add((root1, criteriaBuilder) ->
                    (root, query, cb) -> cb.equal(root.get("isActive"), true)
            );

            Page<MUser> pageable = userFilterUtility.filterRecords(page, perPage, sort, direction, filterCriteriaList);

            ApiResponseDto<List<MUserDto>> response = new ApiResponseDto<>();
            PaginationDto pagination = new PaginationDto();
            pagination.setTo((int) pageable.getTotalElements());
            response.setPagination(pagination);
            response.setResult(userMapper.listToDto(pageable.getContent()));

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public MUserDto updateUser(Long userId, MUserDto userDto) {
        log.info("Inside user service update user method");
        try {
            Optional<MUser> userOp = userDao.findById(userId);
            if (userDto.getUserId() == null) {
                throw new BadRequestAlertException("User id is required", "User", "User update failed");
            } else if (!userId.equals(userDto.getUserId())) {
                throw new BadRequestAlertException("User id mismatch", "User", "User update failed");
            } else if (userOp.isEmpty()) {
                throw new BadRequestAlertException("User not found", "User", "User not found");
            } else if (userDto.getPhone() == null || userDto.getPhone().isEmpty()) {
                throw new BadRequestAlertException("Phone number is required", "User", "User update failed");
            } else if (userDto.getFirstName() == null || userDto.getFirstName().isEmpty()) {
                throw new BadRequestAlertException("First name is required", "User", "User update failed");
            } else if (userDto.getLastName() == null || userDto.getLastName().isEmpty()) {
                throw new BadRequestAlertException("Last name is required", "User", "User update failed");
            } else {
                MUser user = userOp.get();
                user.setFirstName(userDto.getFirstName());
                user.setLastName(userDto.getLastName());
                user.setPhone(userDto.getPhone());
                user.setDob(userDto.getDob());
                return userMapper.toDto(userDao.save(user));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public ResponseDto deleteUser(Long userId) {
        log.info("Inside user service delete user method");
        try {
            Optional<MUser> userOp = userDao.findById(userId);
            if (userOp.isEmpty()) {
                throw new BadRequestAlertException("User not found", "User", "User not found");
            } else {
                userDao.deleteById(userId);
                ResponseDto responseDto = new ResponseDto();
                responseDto.setId(userId);
                responseDto.setMessage("User deleted successfully");
                return responseDto;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public String updateVerifyStatus(String token) {
        log.info("Inside user service update verify status method");
        try {
            Optional<TVerificationToken> verificationToken = verificationTokenService.getToken(token);

            if (verificationToken.isEmpty() || verificationToken.get().getExpiryDate().isBefore(LocalDateTime.now())) {
                log.error("Token is invalid or expired!");
                return "Token is invalid or expired!";
            }

            MUser user = verificationToken.get().getUser();
            user.setIsVerified(true); // Set a flag in User entity to mark as verified
            userDao.save(user);
            log.info("Account verified successfully!");
            return "Account verified successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public ResponseEntity<ResponseDto> forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto) {
        log.info("Inside user service forgot password method");
        try {

            String yourEmail = forgotPasswordRequestDto.getYourEmail();
            String companyEmail = forgotPasswordRequestDto.getCompanyEmail();

            // validation
            if (!userDao.existsByEmailAndCompanyEmailAndIsActive(yourEmail, companyEmail, true)) {
                log.error("Email not registered");
                throw new BadRequestAlertException("Email not registered", "User", "User not found");
            }
            // generate OTP
            String otp = verificationTokenService.generateOTP();
            verificationTokenService.saveOtp(otp, userDao.findByEmailAndCompanyEmailAndIsActive(yourEmail, companyEmail, true).get().getUserId());
            String companyName = userDao.findByEmailAndCompanyEmailAndIsActive(yourEmail, companyEmail, true).get().getCompany().getName();
            // send verification & registration email
            String content = PASSWORD_RESET_EMAIL_CONTENT;
            content = content.replace("{{otp}}", otp);
            content = content.replace("{{companyName}}", companyName);

            String subject = PASSWORD_RESET_EMAIL_SUBJECT;
            subject = subject.replace("{{companyName}}", companyName);

            emailService.sendEmail(yourEmail, subject, content);

            ResponseDto response = new ResponseDto();
            response.setMessage("OTP sent to your email");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public ResponseEntity<ResponseDto> verifyOtp(OtpVerificationDto otpVerificationDto) {
        log.info("Inside user service verify otp method");
        try {
            // validate
            if (otpVerificationDto.getEmail() == null || otpVerificationDto.getEmail().isEmpty()) {
                log.error("Email is required");
                throw new BadRequestAlertException("Email is required", "User", "error");
            }
            if (otpVerificationDto.getOtp() == null || otpVerificationDto.getOtp().isEmpty()) {
                log.error("OTP is required");
                throw new BadRequestAlertException("OTP is required", "User", "error");
            }

            if (verificationTokenService.validateOtp(otpVerificationDto.getEmail(), otpVerificationDto.getOtp())) {
                ResponseDto response = new ResponseDto();
                response.setMessage("OTP verified. You can now reset your password.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                log.error("Invalid or expired OTP");
                throw new BadRequestAlertException("Invalid or expired OTP", "User", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public ResponseEntity<ResponseDto> resetPassword(ResetPasswordDto resetPasswordDto) {
        log.info("Inside user service reset password method");
        try {
            // validate
            if (resetPasswordDto.getEmail() == null || resetPasswordDto.getEmail().isEmpty()) {
                log.error("Email is required");
                throw new BadRequestAlertException("Email is required", "User", "error");
            }
            if (resetPasswordDto.getNewPassword() == null || resetPasswordDto.getNewPassword().isEmpty()) {
                log.error("New password is required");
                throw new BadRequestAlertException("New password is required", "User", "error");
            }
            if (resetPasswordDto.getOtp() == null || resetPasswordDto.getOtp().isEmpty()) {
                log.error("OTP is required");
                throw new BadRequestAlertException("OTP is required", "User", "error");
            }

            // verify otp
            if (!verificationTokenService.validateOtp(resetPasswordDto.getEmail(), resetPasswordDto.getOtp())) {
                log.error("Invalid or expired OTP");
                throw new BadRequestAlertException("Invalid or expired OTP", "User", "error");
            }

            Optional<MUser> userOpt = userDao.findByEmail(resetPasswordDto.getEmail());
            if (userOpt.isEmpty()) throw new BadRequestAlertException("User not found", "User", "error");

            MUser user = userOpt.get();
            user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword())); // Encrypt the password
            userDao.save(user);

            verificationTokenService.cleanUpOtp(user.getUserId()); // Clean up OTP

            // send password reset success email
            String content = PASSWORD_RESET_SUCCESS_EMAIL_CONTENT;
            content = content.replace("{{firstName}}", user.getFirstName());
            content = content.replace("{{companyName}}", user.getCompany().getName());

            String subject = PASSWORD_RESET_SUCCESS_EMAIL_SUBJECT;
            subject = subject.replace("{{companyName}}", user.getCompany().getName());

            emailService.sendEmail(user.getEmail(), subject, content);

            ResponseDto response = new ResponseDto();
            response.setMessage("Password reset successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public ResponseEntity<ResponseDto> changePassword(PasswordChangeDto passwordChangeDto) {
        log.info("Inside user service change password method");
        try {
            // validate
            if (passwordChangeDto.getUserId() == null) {
                log.error("User id is required");
                throw new BadRequestAlertException("User id is required", "User", "error");
            }
            if (passwordChangeDto.getOldPassword() == null || passwordChangeDto.getOldPassword().isEmpty()) {
                log.error("Old password is required");
                throw new BadRequestAlertException("Old password is required", "User", "error");
            }
            if (passwordChangeDto.getNewPassword() == null || passwordChangeDto.getNewPassword().isEmpty()) {
                log.error("New password is required");
                throw new BadRequestAlertException("New password is required", "User", "error");
            }

            // Fetch the user by userId
            Optional<MUser> userOpt = userDao.findById(passwordChangeDto.getUserId());
            if (userOpt.isEmpty()) {
                throw new BadRequestAlertException("User not found", "User", "error");
            }

            MUser user = userOpt.get();

            // Verify the old password
            if (!passwordEncoder.matches(passwordChangeDto.getOldPassword(), user.getPassword())) {
                throw new BadRequestAlertException("Old password is incorrect", "User", "error");
            }

            // Update the password
            user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword())); // Encrypt new password
            userDao.save(user);

            // send password change success email
            String content = CHANGE_PASSWORD_SUCCESS_EMAIL_CONTENT;
            content = content.replace("{{firstName}}", user.getFirstName());
            content = content.replace("{{companyName}}", user.getCompany().getName());

            String subject = CHANGE_PASSWORD_SUCCESS_EMAIL_SUBJECT;
            subject = subject.replace("{{companyName}}", user.getCompany().getName());

            emailService.sendEmail(user.getEmail(), subject, content);

            ResponseDto response = new ResponseDto();
            response.setMessage("Password changed successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public MUserDto getUserDetailsByToken(String token) {
        log.info("Inside user service get user details by token method");
        try {
            token = token.substring(7);
            String username = jwtUtils.extractUsername(token);
            Optional<MUser> userOp = userDao.findByEmail(username);
            if (userOp.isEmpty()) {
                throw new BadRequestAlertException("User not found", "User", "User not found");
            } else {
                return userMapper.toDto(userOp.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }
}
