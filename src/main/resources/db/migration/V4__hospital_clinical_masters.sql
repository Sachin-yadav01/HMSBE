-- Phase 1: Hospital and Clinical Master Data

CREATE TABLE hospitals (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    phone_primary VARCHAR(20),
    phone_secondary VARCHAR(20),
    email VARCHAR(100),
    registration_no VARCHAR(100),
    gstin VARCHAR(50),
    logo_url VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at DATETIME,
    deleted_by BIGINT,
    created_on DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by BIGINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO hospitals (
    id, name, address_line1, city, state, postal_code, country,
    phone_primary, email, active, deleted, created_on, created_by, updated_on, updated_by, version
) VALUES (
    1, 'CarePulse Hospital', '123 Healthcare Way', 'Noida', 'Uttar Pradesh', '201301', 'India',
    '+91 1200000000', 'admin@carepulse.hospital', TRUE, FALSE, NOW(), 1, NOW(), 1, 0
);

CREATE TABLE departments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at DATETIME,
    deleted_by BIGINT,
    created_on DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by BIGINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT uk_departments_code UNIQUE (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_departments_active ON departments(active);
CREATE INDEX idx_departments_deleted ON departments(deleted);
CREATE INDEX idx_departments_name ON departments(name);

CREATE TABLE specializations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    department_id BIGINT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at DATETIME,
    deleted_by BIGINT,
    created_on DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by BIGINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT uk_specializations_code UNIQUE (code),
    CONSTRAINT fk_specializations_department FOREIGN KEY (department_id) REFERENCES departments(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_specializations_department_id ON specializations(department_id);
CREATE INDEX idx_specializations_active ON specializations(active);
CREATE INDEX idx_specializations_deleted ON specializations(deleted);

CREATE TABLE consultation_types (
    id BIGINT NOT NULL AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    default_duration_min INT,
    default_fee DECIMAL(12, 2),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at DATETIME,
    deleted_by BIGINT,
    created_on DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by BIGINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT uk_consultation_types_code UNIQUE (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_consultation_types_active ON consultation_types(active);
CREATE INDEX idx_consultation_types_deleted ON consultation_types(deleted);

CREATE TABLE service_categories (
    id BIGINT NOT NULL AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at DATETIME,
    deleted_by BIGINT,
    created_on DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by BIGINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT uk_service_categories_code UNIQUE (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_service_categories_active ON service_categories(active);
CREATE INDEX idx_service_categories_deleted ON service_categories(deleted);

CREATE TABLE hospital_services (
    id BIGINT NOT NULL AUTO_INCREMENT,
    service_code VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    category_id BIGINT NOT NULL,
    rate DECIMAL(12, 2) NOT NULL,
    tax_pct DECIMAL(5, 2) NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at DATETIME,
    deleted_by BIGINT,
    created_on DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by BIGINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT uk_hospital_services_service_code UNIQUE (service_code),
    CONSTRAINT fk_hospital_services_category FOREIGN KEY (category_id) REFERENCES service_categories(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_hospital_services_category_id ON hospital_services(category_id);
CREATE INDEX idx_hospital_services_active ON hospital_services(active);
CREATE INDEX idx_hospital_services_deleted ON hospital_services(deleted);
CREATE INDEX idx_hospital_services_name ON hospital_services(name);

CREATE TABLE payment_modes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    requires_ref_no BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at DATETIME,
    deleted_by BIGINT,
    created_on DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by BIGINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT uk_payment_modes_code UNIQUE (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_payment_modes_active ON payment_modes(active);
CREATE INDEX idx_payment_modes_deleted ON payment_modes(deleted);
