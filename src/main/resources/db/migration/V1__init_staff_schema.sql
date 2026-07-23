CREATE TABLE employee_code_sequence (
    id BIGINT NOT NULL AUTO_INCREMENT,
    next_val BIGINT NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO employee_code_sequence (next_val) VALUES (1);

CREATE TABLE staff (
    id BIGINT NOT NULL AUTO_INCREMENT,
    staff_uuid VARCHAR(36) NOT NULL,
    employee_code VARCHAR(50) NOT NULL,
    staff_type VARCHAR(50) NOT NULL,
    title VARCHAR(20) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    last_name VARCHAR(100) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    date_of_birth DATE NOT NULL,
    profile_image_url VARCHAR(255),
    
    phone_primary VARCHAR(20) NOT NULL,
    phone_secondary VARCHAR(20),
    email VARCHAR(100) NOT NULL,
    emergency_contact_name VARCHAR(100),
    emergency_contact_phone VARCHAR(20),
    
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    district VARCHAR(100),
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    
    department_id BIGINT NOT NULL,
    designation_id BIGINT NOT NULL,
    employment_type VARCHAR(50) NOT NULL,
    joining_date DATE NOT NULL,
    probation_end_date DATE,
    reporting_manager_id BIGINT,
    shift_id BIGINT,
    weekly_off_day VARCHAR(20),
    
    qualification VARCHAR(255),
    specialization VARCHAR(255),
    experience_years INT,
    registration_number VARCHAR(100),
    registration_council VARCHAR(100),
    registration_expiry_date DATE,
    
    status VARCHAR(50) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at DATETIME,
    deleted_by BIGINT,
    created_on DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by BIGINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    
    PRIMARY KEY (id),
    CONSTRAINT uk_staff_uuid UNIQUE (staff_uuid),
    CONSTRAINT uk_employee_code UNIQUE (employee_code),
    CONSTRAINT uk_email UNIQUE (email),
    CONSTRAINT uk_phone_primary UNIQUE (phone_primary),
    CONSTRAINT uk_registration_number UNIQUE (registration_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Indexes
CREATE INDEX idx_staff_employee_code ON staff(employee_code);
CREATE INDEX idx_staff_first_name ON staff(first_name);
CREATE INDEX idx_staff_last_name ON staff(last_name);
CREATE INDEX idx_staff_phone_primary ON staff(phone_primary);
CREATE INDEX idx_staff_email ON staff(email);
CREATE INDEX idx_staff_department_id ON staff(department_id);
CREATE INDEX idx_staff_designation_id ON staff(designation_id);
CREATE INDEX idx_staff_staff_type ON staff(staff_type);
CREATE INDEX idx_staff_status ON staff(status);
CREATE INDEX idx_staff_is_active ON staff(is_active);
CREATE INDEX idx_staff_deleted ON staff(deleted);
CREATE INDEX idx_staff_created_on ON staff(created_on);

-- Composite Indexes
CREATE INDEX idx_staff_dept_status_del ON staff(department_id, status, deleted);
CREATE INDEX idx_staff_type_status_del ON staff(staff_type, status, deleted);
CREATE INDEX idx_staff_active_del ON staff(is_active, deleted);
