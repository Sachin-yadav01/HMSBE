-- Add employee_code_sequence table if it doesn't already exist
CREATE TABLE IF NOT EXISTS employee_code_sequence (
    id BIGINT NOT NULL AUTO_INCREMENT,
    next_val BIGINT NOT NULL,
    PRIMARY KEY (id)
);

-- Ensure initial seed row exists
INSERT INTO employee_code_sequence (next_val)
SELECT 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM employee_code_sequence LIMIT 1);
