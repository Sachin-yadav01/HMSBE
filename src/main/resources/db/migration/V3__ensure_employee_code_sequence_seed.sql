-- Ensure sequence seed row exists (id = 1) for employee code generation
INSERT INTO employee_code_sequence (id, next_val)
SELECT 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM employee_code_sequence WHERE id = 1);
