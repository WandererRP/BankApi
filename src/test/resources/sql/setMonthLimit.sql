alter sequence transaction_id_seq restart 1;
INSERT INTO solva_test.public.account values (123123);
INSERT INTO solva_test.public.month_limit (account_id, limit_sum, datetime, currency)
VALUES (123123, 600, '2022-12-06 19:42:55.160000 +00:00', 'USD');