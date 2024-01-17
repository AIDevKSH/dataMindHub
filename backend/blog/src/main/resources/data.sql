
# 기본 권한 설정
INSERT INTO authorities(name)
SELECT 'create'
WHERE NOT EXISTS (SELECT 1 FROM authorities WHERE name = 'create');

INSERT INTO authorities(name)
SELECT 'read'
WHERE NOT EXISTS (SELECT 1 FROM authorities WHERE name = 'read');

INSERT INTO authorities(name)
SELECT 'update'
WHERE NOT EXISTS (SELECT 1 FROM authorities WHERE name = 'update');

INSERT INTO authorities(name)
SELECT 'delete'
WHERE NOT EXISTS (SELECT 1 FROM authorities WHERE name = 'delete');

# 기본 역할 설정
INSERT INTO roles(name)
SELECT 'admin'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'admin');

INSERT INTO roles(name)
SELECT 'manager'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'manager');

INSERT INTO roles(name)
SELECT 'user'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'user');

# role_authorities 테이블 자료 추가
BEGIN;

SELECT @admin := id FROM roles WHERE name = 'admin';
SELECT @manager := id FROM roles WHERE name = 'manager';
SELECT @user := id FROM roles WHERE name = 'user';

SELECT @create := id FROM authorities WHERE name = 'create';
SELECT @read := id FROM authorities WHERE name = 'read';
SELECT @update := id FROM authorities WHERE name = 'update';
SELECT @delete := id FROM authorities WHERE name = 'delete';

INSERT INTO role_authorities (role_id, authority_id) VALUES (@admin, @create);
INSERT INTO role_authorities (role_id, authority_id) VALUES (@admin, @read);
INSERT INTO role_authorities (role_id, authority_id) VALUES (@admin, @update);
INSERT INTO role_authorities (role_id, authority_id) VALUES (@admin, @delete);

INSERT INTO role_authorities (role_id, authority_id) VALUES (@manager, @create);
INSERT INTO role_authorities (role_id, authority_id) VALUES (@manager, @read);
INSERT INTO role_authorities (role_id, authority_id) VALUES (@manager, @update);
INSERT INTO role_authorities (role_id, authority_id) VALUES (@manager, @delete);

#INSERT INTO role_authorities (role_id, authority_id) VALUES (@user, @create);
INSERT INTO role_authorities (role_id, authority_id) VALUES (@user, @read);
#INSERT INTO role_authorities (role_id, authority_id) VALUES (@user, @update);
#INSERT INTO role_authorities (role_id, authority_id) VALUES (@user, @delete);

COMMIT;