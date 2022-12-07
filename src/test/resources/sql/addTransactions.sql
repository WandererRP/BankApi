INSERT INTO solva_test.public.account values (123);

INSERT INTO solva_test.public.month_limit (id, account_id, limit_sum, datetime, currency)

VALUES (2, 123, 600, '2022-12-06 19:42:55.160634 +00:00', 'USD');

INSERT INTO solva_test.public.transaction (id, account_from, account_to, currency, sum, sum_usd, expense_category, datetime,
                                    limit_exceeded)
VALUES (11, 123, 9999991555, 'KZT', 400.5, 0.8514754837034502, 'SERVICE', '2022-12-06 20:00:00.000000 +00:00', false);

INSERT INTO solva_test.public.transaction (id, account_from, account_to, currency, sum, sum_usd, expense_category, datetime,
                                limit_exceeded)
VALUES (12, 123, 9999991555, 'KZT', 14055.5, 29.882431114091997, 'SERVICE', '2022-12-06 20:00:00.000000 +00:00', false);

INSERT INTO solva_test.public.transaction (id, account_from, account_to, currency, sum, sum_usd, expense_category, datetime,
                                limit_exceeded)
VALUES (13, 123, 9999991555, 'KZT', 55000.5, 116.93277738185172, 'PRODUCT', '2022-12-06 20:00:00.000000 +00:00', false);

INSERT INTO solva_test.public.transaction (id, account_from, account_to, currency, sum, sum_usd, expense_category, datetime,
                                limit_exceeded)
VALUES (14, 123, 9999991555, 'RUB', 2500.5, 40.108019999999996, 'PRODUCT', '2022-12-06 20:00:00.000000 +00:00', false);

INSERT INTO solva_test.public.transaction (id, account_from, account_to, currency, sum, sum_usd, expense_category, datetime,
                                limit_exceeded)
VALUES (15, 123, 8899991555, 'RUB', 2500.5, 40.108019999999996, 'PRODUCT', '2022-12-06 20:20:00.000000 +00:00', false);

INSERT INTO solva_test.public.transaction (id, account_from, account_to, currency, sum, sum_usd, expense_category, datetime,
                                limit_exceeded)
VALUES (16, 123, 8899991555, 'RUB', 10000.55, 160.408822, 'PRODUCT', '2022-12-06 20:25:00.000000 +00:00', false);

INSERT INTO solva_test.public.transaction (id, account_from, account_to, currency, sum, sum_usd, expense_category, datetime,
                                limit_exceeded)
VALUES (17, 123, 8899991555, 'RUB', 23000.55, 368.92882199999997, 'SERVICE', '2022-12-06 20:30:00.000000 +00:00', true);

INSERT INTO solva_test.public.transaction (id, account_from, account_to, currency, sum, sum_usd, expense_category, datetime,
                                limit_exceeded)
VALUES (18, 123, 8897991555, 'KZT', 23000.55, 48.89988623394604, 'SERVICE', '2022-12-06 20:33:00.000000 +00:00', true);

