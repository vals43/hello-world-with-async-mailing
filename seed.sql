DELETE FROM user_course;
DELETE FROM app_user;
DELETE FROM course;

INSERT INTO app_user (id, first_name, last_name, user_name, email)
VALUES (
  '00000000-0000-0000-0000-000000000001',
  'Val',
  's',
  'vals43',
  'vals433311@gmail.com'
);

INSERT INTO app_user (id, first_name, last_name, user_name, email)
VALUES (
  '00000000-0000-0000-0000-000000000003',
  'Amboara',
  'Niaina',
  'amboara_n',
  'amboaraniainarkt@gmail.com'
);

INSERT INTO course (id, title, start_instant, end_instant)
VALUES (
  '00000000-0000-0000-0000-000000000002',
  'Spring Boot 101',
  NOW(),
  NOW() + INTERVAL '1 hour'
);

-- =================================================================
-- Liens de test pour les mails
-- Base : https://ols6jma4qwiyxxjpwpmvshqmre0pfbcu.lambda-url.eu-west-3.on.aws
-- =================================================================
--
-- Users :
--   00000000-0000-0000-0000-000000000001  (Val   <vals433311@gmail.com>)
--   00000000-0000-0000-0000-000000000003  (Amboara  <amboaraniainarkt@gmail.com>)
-- Course :
--   00000000-0000-0000-0000-000000000002  (Spring Boot 101)
--
-- 1) SYNC subscribe (envoi immédiat)
--    curl -X POST "https://ols6jma4qwiyxxjpwpmvshqmre0pfbcu.lambda-url.eu-west-3.on.aws/mailing/sync/subscribe/00000000-0000-0000-0000-000000000001/00000000-0000-0000-0000-000000000002"
--    curl -X POST "https://ols6jma4qwiyxxjpwpmvshqmre0pfbcu.lambda-url.eu-west-3.on.aws/mailing/sync/subscribe/00000000-0000-0000-0000-000000000003/00000000-0000-0000-0000-000000000002"
--
-- 2) ASYNC subscribe (via EventBridge + SQS + Lambda)
--    curl -X POST "https://ols6jma4qwiyxxjpwpmvshqmre0pfbcu.lambda-url.eu-west-3.on.aws/mailing/async/subscribe/00000000-0000-0000-0000-000000000001/00000000-0000-0000-0000-000000000002"
--    curl -X POST "https://ols6jma4qwiyxxjpwpmvshqmre0pfbcu.lambda-url.eu-west-3.on.aws/mailing/async/subscribe/00000000-0000-0000-0000-000000000003/00000000-0000-0000-0000-000000000002"
--
-- 3) Health email (5 tests : subject only, with CC, with BCC, with body, with attachment)
--    curl "https://ols6jma4qwiyxxjpwpmvshqmre0pfbcu.lambda-url.eu-west-3.on.aws/health/email?to=vals433311@gmail.com"
--    curl "https://ols6jma4qwiyxxjpwpmvshqmre0pfbcu.lambda-url.eu-west-3.on.aws/health/email?to=amboaraniainarkt@gmail.com"
--
-- 4) Hello world (simple email async)
--    curl "https://ols6jma4qwiyxxjpwpmvshqmre0pfbcu.lambda-url.eu-west-3.on.aws/hello?to=vals433311@gmail.com"
--    curl "https://ols6jma4qwiyxxjpwpmvshqmre0pfbcu.lambda-url.eu-west-3.on.aws/hello?to=amboaraniainarkt@gmail.com"
--
-- 5) Email with PDF (PDF depuis S3 via BucketComponent)
--    POST {base}/email-with-pdf?to={email}&bucketKey={key}
--    bucketKey optionnel (défaut: pdfs/cours.pdf), à uploader sur S3 d'abord
-- Exemples :
--    curl -X POST "https://ols6jma4qwiyxxjpwpmvshqmre0pfbcu.lambda-url.eu-west-3.on.aws/email-with-pdf?to=vals433311@gmail.com"
--    curl -X POST "https://ols6jma4qwiyxxjpwpmvshqmre0pfbcu.lambda-url.eu-west-3.on.aws/email-with-pdf?to=amboaraniainarkt@gmail.com"
