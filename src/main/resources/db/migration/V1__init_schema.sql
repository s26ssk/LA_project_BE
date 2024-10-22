CREATE TABLE IF NOT EXISTS `employees` (
    employee_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    department_id BIGINT(20) NOT NULL,
    employee_name VARCHAR(255) NOT NULL,
    employee_name_kana VARCHAR(255) DEFAULT NULL,
    employee_birth_date DATE DEFAULT NULL,
    employee_email VARCHAR(255) NOT NULL,
    employee_telephone VARCHAR(50) DEFAULT NULL,
    employee_login_id VARCHAR(50) NOT NULL,
    employee_login_password VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (`employee_id`) USING BTREE,
    FOREIGN KEY (`department_id`) REFERENCES `departments`(`department_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `departments` (
    department_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    department_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (`department_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `certifications` (
    certification_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    certification_name VARCHAR(50) NOT NULL,
    certification_level INT NOT NULL,
    PRIMARY KEY (`certification_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `employees_certifications` (
    employee_certification_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    employee_id BIGINT(20) NOT NULL,
    certification_id BIGINT(20) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    score DECIMAL NOT NULL,
    PRIMARY KEY (`employee_certification_id`) USING BTREE,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
    FOREIGN KEY (certification_id) REFERENCES certifications(certification_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO employees (department_id, employee_name, employee_name_kana, employee_birth_date, employee_email, employee_telephone, employee_login_id, employee_login_password)
VALUES (1, 'Administrator', 'アドミニストレーター', '2004-07-01', 'la@luvina.net', '02437931103', 'admin', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy');
