CREATE SEQUENCE IF NOT EXISTS m_company_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS m_company_branch_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS m_user_role_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS m_user_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS r_role_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS r_status_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS t_login_history_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS t_password_reset_token_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS t_verification_token_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE m_company
(
    company_id                          BIGINT       NOT NULL,
    created_at                          TIMESTAMP WITHOUT TIME ZONE,
    updated_at                          TIMESTAMP WITHOUT TIME ZONE,
    created_by                          VARCHAR(255),
    updated_by                          VARCHAR(255),
    name                                VARCHAR(100) NOT NULL,
    email                               VARCHAR(100) NOT NULL,
    contact_no                          VARCHAR(15),
    address                             TEXT,
    website                             VARCHAR(255),
    company_desc                        VARCHAR(255),
    is_active                           BOOLEAN      NOT NULL,
    max_failed_attempts                 INTEGER,
    enable_email_verification           BOOLEAN,
    allow_leave_requests                BOOLEAN,
    company_default_leave_request_count INTEGER,
    status_id                           BIGINT       NOT NULL,
    CONSTRAINT pk_m_company PRIMARY KEY (company_id)
);

CREATE TABLE m_company_branch
(
    branch_id        BIGINT       NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    created_by       VARCHAR(255),
    updated_by       VARCHAR(255),
    branch_code      VARCHAR(6)   NOT NULL,
    branch_name      VARCHAR(100) NOT NULL,
    branch_address   VARCHAR(250),
    branch_contact_1 VARCHAR(15),
    branch_contact_2 VARCHAR(15),
    branch_email     VARCHAR(50),
    is_active        BOOLEAN      NOT NULL,
    company_id       BIGINT       NOT NULL,
    status_id        BIGINT       NOT NULL,
    CONSTRAINT pk_m_company_branch PRIMARY KEY (branch_id)
);

CREATE TABLE m_user
(
    user_id         BIGINT  NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    created_by      VARCHAR(255),
    updated_by      VARCHAR(255),
    first_name      VARCHAR(255),
    last_name       VARCHAR(255),
    email           VARCHAR(255),
    password        VARCHAR(255),
    phone           VARCHAR(255),
    uniq_key        VARCHAR(255),
    dob             date,
    is_active       BOOLEAN,
    is_verified     BOOLEAN,
    is_locked       BOOLEAN NOT NULL,
    failed_attempts INTEGER NOT NULL,
    status_id       BIGINT  NOT NULL,
    company_id      BIGINT  NOT NULL,
    branch_id       BIGINT  NOT NULL,
    CONSTRAINT pk_m_user PRIMARY KEY (user_id)
);

CREATE TABLE m_user_role
(
    user_role_id BIGINT NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    created_by   VARCHAR(255),
    updated_by   VARCHAR(255),
    is_active    BOOLEAN,
    user_id      BIGINT NOT NULL,
    role_id      BIGINT NOT NULL,
    status_id    BIGINT NOT NULL,
    CONSTRAINT pk_m_user_role PRIMARY KEY (user_role_id)
);

CREATE TABLE r_role
(
    role_id          BIGINT NOT NULL DEFAULT nextval('r_role_seq'),
    created_at       TIMESTAMP WITHOUT TIME ZONE,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    created_by       VARCHAR(255),
    updated_by       VARCHAR(255),
    role_name        VARCHAR(50) NOT NULL,
    role_description VARCHAR(255),
    is_active        BOOLEAN     NOT NULL,
    CONSTRAINT pk_r_role PRIMARY KEY (role_id)
);

CREATE TABLE r_status
(
    status_id          BIGINT NOT NULL DEFAULT nextval('r_status_seq'),
    created_at         TIMESTAMP WITHOUT TIME ZONE,
    updated_at         TIMESTAMP WITHOUT TIME ZONE,
    created_by         VARCHAR(255),
    updated_by         VARCHAR(255),
    status_name        VARCHAR(255),
    status_description VARCHAR(255),
    is_active          BOOLEAN,
    CONSTRAINT pk_r_status PRIMARY KEY (status_id)
);

CREATE TABLE t_login_history
(
    login_h_id BIGINT                      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    user_id    BIGINT                      NOT NULL,
    ip_address VARCHAR(50),
    device     VARCHAR(255),
    login_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_t_login_history PRIMARY KEY (login_h_id)
);

CREATE TABLE t_password_reset_token
(
    reset_token_id BIGINT NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    created_by     VARCHAR(255),
    updated_by     VARCHAR(255),
    otp            VARCHAR(255),
    expires_at     TIMESTAMP WITHOUT TIME ZONE,
    user_id        BIGINT NOT NULL,
    CONSTRAINT pk_t_password_reset_token PRIMARY KEY (reset_token_id)
);

CREATE TABLE t_verification_token
(
    verification_id    BIGINT NOT NULL,
    created_at         TIMESTAMP WITHOUT TIME ZONE,
    updated_at         TIMESTAMP WITHOUT TIME ZONE,
    created_by         VARCHAR(255),
    updated_by         VARCHAR(255),
    verification_token VARCHAR(255),
    expiry_date        TIMESTAMP WITHOUT TIME ZONE,
    user_id            BIGINT NOT NULL,
    CONSTRAINT pk_t_verification_token PRIMARY KEY (verification_id)
);

ALTER TABLE m_company
    ADD CONSTRAINT uc_m_company_email UNIQUE (email);

ALTER TABLE m_company_branch
    ADD CONSTRAINT uc_9e6f501084a06d02f8bfa6acd UNIQUE (company_id, branch_code);

ALTER TABLE r_role
    ADD CONSTRAINT uc_r_role_role_name UNIQUE (role_name);

ALTER TABLE t_password_reset_token
    ADD CONSTRAINT uc_t_password_reset_token_user UNIQUE (user_id);

ALTER TABLE t_verification_token
    ADD CONSTRAINT uc_t_verification_token_user UNIQUE (user_id);

CREATE UNIQUE INDEX UNIQUE_M_USERS_UNIQ_KEY1_idx ON m_user (uniq_key);

ALTER TABLE m_company
    ADD CONSTRAINT FK_M_COMPANY_ON_STATUS FOREIGN KEY (status_id) REFERENCES r_status (status_id);

ALTER TABLE m_company_branch
    ADD CONSTRAINT FK_M_COMPANY_BRANCH_ON_COMPANY FOREIGN KEY (company_id) REFERENCES m_company (company_id);

ALTER TABLE m_company_branch
    ADD CONSTRAINT FK_M_COMPANY_BRANCH_ON_STATUS FOREIGN KEY (status_id) REFERENCES r_status (status_id);

CREATE INDEX fk_M_COMPANY_R_STATUS1_idx ON m_company (status_id);

ALTER TABLE m_user
    ADD CONSTRAINT FK_M_USER_ON_COMPANY FOREIGN KEY (company_id) REFERENCES m_company (company_id);

ALTER TABLE m_user
    ADD CONSTRAINT FK_M_USER_ON_STATUS FOREIGN KEY (status_id) REFERENCES r_status (status_id);

ALTER TABLE m_user
    ADD CONSTRAINT FK_M_USER_ON_BRANCH FOREIGN KEY (branch_id) REFERENCES m_company_branch (branch_id);

CREATE INDEX fk_M_USERS_R_STATUS1_idx ON m_user (status_id);

ALTER TABLE m_user_role
    ADD CONSTRAINT FK_M_USER_ROLE_ON_ROLE FOREIGN KEY (role_id) REFERENCES r_role (role_id);

CREATE INDEX fk_M_USER_ROLE_R_ROLE1_idx ON m_user_role (role_id);

ALTER TABLE m_user_role
    ADD CONSTRAINT FK_M_USER_ROLE_ON_STATUS FOREIGN KEY (status_id) REFERENCES r_status (status_id);

CREATE INDEX fk_M_USER_ROLE_R_STATUS1_idx ON m_user_role (status_id);

ALTER TABLE m_user_role
    ADD CONSTRAINT FK_M_USER_ROLE_ON_USER FOREIGN KEY (user_id) REFERENCES m_user (user_id);

CREATE INDEX fk_M_USER_ROLE_M_USER1_idx ON m_user_role (user_id);

ALTER TABLE t_login_history
    ADD CONSTRAINT FK_T_LOGIN_HISTORY_ON_USER FOREIGN KEY (user_id) REFERENCES m_user (user_id);

CREATE INDEX fk_T_LOGIN_HISTORY_M_USERS_idx ON t_login_history (user_id);

ALTER TABLE t_password_reset_token
    ADD CONSTRAINT FK_T_PASSWORD_RESET_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES m_user (user_id);

CREATE INDEX fk_T_PASSWORD_RESET_TOKEN_M_USERS1_idx ON t_password_reset_token (user_id);

ALTER TABLE t_verification_token
    ADD CONSTRAINT FK_T_VERIFICATION_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES m_user (user_id);

CREATE INDEX fk_T_VERIFICATION_TOKEN_M_USERS1_idx ON t_verification_token (user_id);