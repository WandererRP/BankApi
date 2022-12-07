truncate table solva_test.public.account CASCADE ;
truncate table solva_test.public.currency_rates;
truncate table solva_test.public.month_limit CASCADE ;
truncate table solva_test.public.transaction CASCADE;

insert into solva_test.public.currency_rates(currency, rate, datetime) VALUES ('USD', 1, '2022-12-06 00:00:00.000000 +00:00');
insert into solva_test.public.currency_rates(currency, rate, datetime) VALUES ('KZT', 0.0021, '2022-12-06 00:00:00.000000 +00:00');
insert into solva_test.public.currency_rates(currency, rate, datetime) VALUES ('RUB', 0.016, '2020-12-06 00:00:00.000000 +00:00');