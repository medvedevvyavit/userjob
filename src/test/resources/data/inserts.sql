INSERT INTO public.users (id, created, updated, family_name, middle_name, first_name, birthday, gender, age, description, is_blocked) VALUES (2, '2023-06-26 15:31:18.449585', '2023-06-26 15:31:18.449585', 'Петров', 'Алексеевич', 'Алексей', '1985-06-25', 'MALE', 36, 'Любит спорт, особенно футбол', false);
INSERT INTO public.users (id, created, updated, family_name, middle_name, first_name, birthday, gender, age, description, is_blocked) VALUES (3, '2023-06-26 15:31:35.108333', '2023-06-26 15:31:35.108333', 'Кузнецов', 'Сергеевич', 'Сергей', '1974-08-05', 'MALE', 47, 'Любит путешествовать и фотографировать', false);

INSERT INTO public.companies (id, created, updated, company_name, description, is_activity) VALUES (2, '2023-06-26 15:31:18.451585', '2023-06-26 15:31:18.451585', 'DEF Corporation', 'Производитель мобильных устройств', true);
INSERT INTO public.companies (id, created, updated, company_name, description, is_activity) VALUES (3, '2023-06-26 15:31:35.108333', '2023-06-26 15:31:35.108333', 'GHI Corp', 'Занимается продажей электроники', true);

INSERT INTO public.user_job_info (id, created, updated, description, is_activity, user_id, company_id) VALUES (2, '2023-06-26 15:31:18.453584', '2023-06-26 15:31:18.453584', 'Менеджер по продажам', true, 2, 2);
INSERT INTO public.user_job_info (id, created, updated, description, is_activity, user_id, company_id) VALUES (3, '2023-06-26 15:31:35.108333', '2023-06-26 15:31:35.108333', 'Директор', true, 3, 3);
