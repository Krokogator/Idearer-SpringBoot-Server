/*-- USERS
INSERT INTO public.userx (id, email, password, role, username) VALUES (1, 'admin@gmail.com', '$2a$10$6fD6.q5h4Vdeyt17IilBhOYNz7sAu1hGcqLrAGEfujYOeP6V3OL/q', 'ADMIN', 'admin');
INSERT INTO public.userx (id, email, password, role, username) VALUES (2, 'user@gmail.com', '$2a$10$LsJ8eTbZwYP6D0bVAHIAvONqmNwkpYF7dKJjMVrm9I82yqYPk2AGm', 'USER', 'user');

-- CATEGORIES
INSERT INTO public.category (id, name) VALUES (1, 'Food');
INSERT INTO public.category (id, name) VALUES (2, 'Games');
INSERT INTO public.category (id, name) VALUES (3, 'Ideas');
INSERT INTO public.category (id, name) VALUES (4, 'Technology');

-- ARTICLES
INSERT INTO public.article (id, content, created, status, title, category_id, user_id) VALUES (1, 'B8y3SSmz4sg', '2018-11-22 17:32:17.161000', 1, 'Ramen', 1, 1);
INSERT INTO public.article (id, content, created, status, title, category_id, user_id) VALUES (2, '2pNCQdGvaKU', '2018-11-23 12:18:32.179000', 1, 'Doomfist proplays', 2, 2);
INSERT INTO public.article (id, content, created, status, title, category_id, user_id) VALUES (3, 'K3EZwmoHVtc', '2018-11-24 15:15:51.161000', 1, 'Alahukebab', 1, 2);
INSERT INTO public.article (id, content, created, status, title, category_id, user_id) VALUES (4, 'VBlIvghQTlI', '2018-12-12 11:04:16.179000', 1, 'Epic landing', 4, 2);
INSERT INTO public.article (id, content, created, status, title, category_id, user_id) VALUES (5, 'lPsSL6_7NBg', '2018-12-22 16:00:02.161000', 1, 'Structural patterns', 4, 2);
INSERT INTO public.article (id, content, created, status, title, category_id, user_id) VALUES (6, '9qA5kw8dcSU', '2018-12-23 17:29:50.179000', 1, 'Command pattern!', 4, 2);

INSERT INTO public.article (id, content, created, status, title, category_id, user_id) VALUES (7, 'B8y3SSmz4sg', '2018-11-22 17:32:17.161000', 1, 'Patterns #1', 4, 1);
INSERT INTO public.article (id, content, created, status, title, category_id, user_id) VALUES (8, '2pNCQdGvaKU', '2018-11-23 12:18:32.179000', 1, 'Doomfist proplays', 4, 1);
INSERT INTO public.article (id, content, created, status, title, category_id, user_id) VALUES (9, 'K3EZwmoHVtc', '2018-11-24 15:15:51.161000', 1, 'Alahukebab', 4, 1);
INSERT INTO public.article (id, content, created, status, title, category_id, user_id) VALUES (10, 'VBlIvghQTlI', '2018-12-12 11:04:16.179000', 1, 'Epic landing', 4, 2);
INSERT INTO public.article (id, content, created, status, title, category_id, user_id) VALUES (11, 'lPsSL6_7NBg', '2018-12-22 16:00:02.161000', 1, 'Structural patterns', 4, 2);
INSERT INTO public.article (id, content, created, status, title, category_id, user_id) VALUES (12, '9qA5kw8dcSU', '2018-12-23 17:29:50.179000', 1, 'Command pattern!', 4, 2);



-- COMMENTS
INSERT INTO public.comment (id, content, created, article_id, parent_comment_id, user_id) VALUES (1, 'Looks amazing!', '2018-11-23 17:52:07.168000', 1, null, 1);
INSERT INTO public.comment (id, content, created, article_id, parent_comment_id, user_id) VALUES (2, 'I didn''t like the last part :|', '2018-11-24 18:59:12.177000', 1, 1, 2);
INSERT INTO public.comment (id, content, created, article_id, parent_comment_id, user_id) VALUES (3, 'Wowowowow', '2018-12-24 17:53:47.180000', 2, null, 2);
INSERT INTO public.comment (id, content, created, article_id, parent_comment_id, user_id) VALUES (4, 'What a player!!', '2018-12-25 14:12:01.182000', 2, null, 1);

-- ARTICLE REPORTS
INSERT INTO public.article_report (id, description, report_author_id, article_id) VALUES (1, 'This video contains violence', 2, 1);

