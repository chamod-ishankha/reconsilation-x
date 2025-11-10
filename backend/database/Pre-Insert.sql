-- This script inserts predefined statuses into the STATUS table. --
INSERT INTO R_STATUS (STATUS_NAME, STATUS_DESCRIPTION, IS_ACTIVE, CREATED_AT, CREATED_BY)
VALUES
    ('NEW', 'The record is newly created', TRUE, CURRENT_TIMESTAMP, 'SYSTEM'),
    ('DELETED', 'The record has been deleted', TRUE, CURRENT_TIMESTAMP, 'SYSTEM'),
    ('PENDING', 'The record is pending approval', TRUE, CURRENT_TIMESTAMP, 'SYSTEM'),
    ('IN_PROGRESS', 'The record is currently being processed', TRUE, CURRENT_TIMESTAMP, 'SYSTEM'),
    ('CANCELLED', 'The record has been cancelled', TRUE, CURRENT_TIMESTAMP, 'SYSTEM'),
    ('COMPLETED', 'The record has been completed', TRUE, CURRENT_TIMESTAMP, 'SYSTEM'),
    ('FAILED', 'The record has failed processing', TRUE, CURRENT_TIMESTAMP, 'SYSTEM'),
    ('APPROVED', 'The record has been approved', TRUE, CURRENT_TIMESTAMP, 'SYSTEM'),
    ('REJECTED', 'The record has been rejected', TRUE, CURRENT_TIMESTAMP, 'SYSTEM'),
    ('ARCHIVED', 'The record has been archived', TRUE, CURRENT_TIMESTAMP, 'SYSTEM');

-- This script inserts predefined roles into the ROLE table. --
INSERT INTO R_ROLE (ROLE_NAME, ROLE_DESCRIPTION, IS_ACTIVE, CREATED_AT, CREATED_BY)
VALUES
    ('SUPER_ADMIN', 'Super administrator role with all permissions', TRUE, CURRENT_TIMESTAMP, 'SYSTEM'),
    ('ADMIN', 'Administrator role with full access', TRUE, CURRENT_TIMESTAMP, 'SYSTEM'),
    ('STAFF', 'Staff role with access to manage content', TRUE, CURRENT_TIMESTAMP, 'SYSTEM'),
    ('EMPLOYEE', 'Employee role with access to internal resources', TRUE, CURRENT_TIMESTAMP, 'SYSTEM'),
    ('USER', 'Standard user role with limited access', TRUE, CURRENT_TIMESTAMP, 'SYSTEM'),
    ('GUEST', 'Guest role with minimal access', TRUE, CURRENT_TIMESTAMP, 'SYSTEM');