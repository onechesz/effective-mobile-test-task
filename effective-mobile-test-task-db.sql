--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2023-08-28 19:00:35

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 222 (class 1259 OID 43235)
-- Name: follow; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.follow (
    user_id integer NOT NULL,
    followed_id integer NOT NULL
);


ALTER TABLE public.follow OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 43184)
-- Name: friend; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.friend (
    user_id integer NOT NULL,
    friend_id integer NOT NULL
);


ALTER TABLE public.friend OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 43199)
-- Name: friend_request; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.friend_request (
    user_id integer NOT NULL,
    target_id integer NOT NULL
);


ALTER TABLE public.friend_request OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 43265)
-- Name: message; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.message (
    id integer NOT NULL,
    user_id integer NOT NULL,
    target_id integer NOT NULL,
    text text NOT NULL,
    send_time timestamp without time zone NOT NULL
);


ALTER TABLE public.message OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 43264)
-- Name: message_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.message ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.message_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 219 (class 1259 OID 26640)
-- Name: picture; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.picture (
    id integer NOT NULL,
    name character varying(64) NOT NULL,
    path character varying(255) NOT NULL,
    type character varying(10) NOT NULL,
    size bigint NOT NULL,
    post_id integer NOT NULL
);


ALTER TABLE public.picture OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 26639)
-- Name: picture_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.picture ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.picture_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 217 (class 1259 OID 26627)
-- Name: post; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.post (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    text text,
    created timestamp without time zone NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.post OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 26626)
-- Name: post_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.post ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.post_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 215 (class 1259 OID 18407)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    id integer NOT NULL,
    role character varying(10) NOT NULL,
    name character varying(32) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(60) NOT NULL
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 18406)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."user" ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3382 (class 0 OID 43235)
-- Dependencies: 222
-- Data for Name: follow; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.follow (user_id, followed_id) VALUES (1, 2);
INSERT INTO public.follow (user_id, followed_id) VALUES (2, 1);
INSERT INTO public.follow (user_id, followed_id) VALUES (3, 2);
INSERT INTO public.follow (user_id, followed_id) VALUES (2, 3);


--
-- TOC entry 3380 (class 0 OID 43184)
-- Dependencies: 220
-- Data for Name: friend; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.friend (user_id, friend_id) VALUES (2, 1);
INSERT INTO public.friend (user_id, friend_id) VALUES (1, 2);
INSERT INTO public.friend (user_id, friend_id) VALUES (2, 3);
INSERT INTO public.friend (user_id, friend_id) VALUES (3, 2);


--
-- TOC entry 3381 (class 0 OID 43199)
-- Dependencies: 221
-- Data for Name: friend_request; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3384 (class 0 OID 43265)
-- Dependencies: 224
-- Data for Name: message; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.message (id, user_id, target_id, text, send_time) VALUES (1, 1, 2, 'Test message', '2023-08-23 20:02:11.730649');
INSERT INTO public.message (id, user_id, target_id, text, send_time) VALUES (2, 1, 2, 'Test message', '2023-08-23 20:03:24.832732');


--
-- TOC entry 3379 (class 0 OID 26640)
-- Dependencies: 219
-- Data for Name: picture; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.picture (id, name, path, type, size, post_id) VALUES (3, '32HIvcpHJLBiv3b6pxHlsUxhBWBJlZdNJkskLRjX2RqeYa5SDzqjDYPL6u3a.jpg', 'C:\Users\minya\Documents\Code\Java\effective-mobile-test-task\uploads\images\32HIvcpHJLBiv3b6pxHlsUxhBWBJlZdNJkskLRjX2RqeYa5SDzqjDYPL6u3a.jpg', 'image/jpeg', 156644, 1);
INSERT INTO public.picture (id, name, path, type, size, post_id) VALUES (4, 'GMujSsdPJrK9cCOC7S4jdFkrd3ayynGFKDtu5raBIO2SY5r8fDobBS8NDF7p.jpg', 'C:\Users\minya\Documents\Code\Java\effective-mobile-test-task\uploads\images\GMujSsdPJrK9cCOC7S4jdFkrd3ayynGFKDtu5raBIO2SY5r8fDobBS8NDF7p.jpg', 'image/jpeg', 35411, 1);


--
-- TOC entry 3377 (class 0 OID 26627)
-- Dependencies: 217
-- Data for Name: post; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.post (id, title, text, created, user_id) VALUES (2, 'First post from 3 user', 'First post''s text', '2023-08-24 17:14:35.761711', 3);
INSERT INTO public.post (id, title, text, created, user_id) VALUES (3, 'First post from 3 user', 'First post''s text', '2023-08-24 17:15:30.370637', 2);
INSERT INTO public.post (id, title, text, created, user_id) VALUES (4, 'First post from 3 user', 'First post''s text', '2023-08-24 19:56:59.606408', 1);
INSERT INTO public.post (id, title, text, created, user_id) VALUES (1, 'First post (updated)', 'First post''s updated text', '2023-08-24 17:10:02.398932', 1);


--
-- TOC entry 3375 (class 0 OID 18407)
-- Dependencies: 215
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."user" (id, role, name, email, password) VALUES (1, 'ROLE_USER', 'test', 'test@test.test', '$2a$10$IRvH/7PV45a4Oi691gq/3O34hSoKM/q6GuSScK2CqOvcrGOnC96Ai');
INSERT INTO public."user" (id, role, name, email, password) VALUES (2, 'ROLE_USER', 'test2', 'test2@test.test', '$2a$10$.WE7mmpFzLzaU2lX84eXvehczKDcXk058ymbM9xLwsOgCjCZa.e62');
INSERT INTO public."user" (id, role, name, email, password) VALUES (3, 'ROLE_USER', 'test3', 'test3@test.test', '$2a$10$i0z4Dmlk3WbDxGCDQFXvFuH9iVmLhy.oGFobmMWIbi541WLB7ZwQy');


--
-- TOC entry 3390 (class 0 OID 0)
-- Dependencies: 223
-- Name: message_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.message_id_seq', 2, true);


--
-- TOC entry 3391 (class 0 OID 0)
-- Dependencies: 218
-- Name: picture_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.picture_id_seq', 4, true);


--
-- TOC entry 3392 (class 0 OID 0)
-- Dependencies: 216
-- Name: post_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.post_id_seq', 4, true);


--
-- TOC entry 3393 (class 0 OID 0)
-- Dependencies: 214
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq', 3, true);


--
-- TOC entry 3219 (class 2606 OID 43239)
-- Name: follow follow_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.follow
    ADD CONSTRAINT follow_pkey PRIMARY KEY (user_id, followed_id);


--
-- TOC entry 3215 (class 2606 OID 43188)
-- Name: friend friend_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.friend
    ADD CONSTRAINT friend_pkey PRIMARY KEY (user_id, friend_id);


--
-- TOC entry 3217 (class 2606 OID 43203)
-- Name: friend_request friend_request_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.friend_request
    ADD CONSTRAINT friend_request_pkey PRIMARY KEY (user_id, target_id);


--
-- TOC entry 3221 (class 2606 OID 43271)
-- Name: message message_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.message
    ADD CONSTRAINT message_pkey PRIMARY KEY (id);


--
-- TOC entry 3209 (class 2606 OID 26646)
-- Name: picture picture_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.picture
    ADD CONSTRAINT picture_name_key UNIQUE (name);


--
-- TOC entry 3211 (class 2606 OID 26648)
-- Name: picture picture_path_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.picture
    ADD CONSTRAINT picture_path_key UNIQUE (path);


--
-- TOC entry 3213 (class 2606 OID 26644)
-- Name: picture picture_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.picture
    ADD CONSTRAINT picture_pkey PRIMARY KEY (id);


--
-- TOC entry 3207 (class 2606 OID 26633)
-- Name: post post_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT post_pkey PRIMARY KEY (id);


--
-- TOC entry 3201 (class 2606 OID 18415)
-- Name: user user_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_email_key UNIQUE (email);


--
-- TOC entry 3203 (class 2606 OID 18413)
-- Name: user user_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_name_key UNIQUE (name);


--
-- TOC entry 3205 (class 2606 OID 18411)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 3228 (class 2606 OID 43245)
-- Name: follow follow_followed_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.follow
    ADD CONSTRAINT follow_followed_id_fkey FOREIGN KEY (followed_id) REFERENCES public."user"(id);


--
-- TOC entry 3229 (class 2606 OID 43240)
-- Name: follow follow_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.follow
    ADD CONSTRAINT follow_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- TOC entry 3224 (class 2606 OID 43194)
-- Name: friend friend_friend_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.friend
    ADD CONSTRAINT friend_friend_id_fkey FOREIGN KEY (friend_id) REFERENCES public."user"(id);


--
-- TOC entry 3226 (class 2606 OID 43209)
-- Name: friend_request friend_request_target_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.friend_request
    ADD CONSTRAINT friend_request_target_id_fkey FOREIGN KEY (target_id) REFERENCES public."user"(id);


--
-- TOC entry 3227 (class 2606 OID 43204)
-- Name: friend_request friend_request_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.friend_request
    ADD CONSTRAINT friend_request_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- TOC entry 3225 (class 2606 OID 43189)
-- Name: friend friend_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.friend
    ADD CONSTRAINT friend_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- TOC entry 3230 (class 2606 OID 43277)
-- Name: message message_target_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.message
    ADD CONSTRAINT message_target_id_fkey FOREIGN KEY (target_id) REFERENCES public."user"(id);


--
-- TOC entry 3231 (class 2606 OID 43272)
-- Name: message message_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.message
    ADD CONSTRAINT message_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- TOC entry 3223 (class 2606 OID 26649)
-- Name: picture picture_post_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.picture
    ADD CONSTRAINT picture_post_id_fkey FOREIGN KEY (post_id) REFERENCES public.post(id);


--
-- TOC entry 3222 (class 2606 OID 26634)
-- Name: post post_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT post_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


-- Completed on 2023-08-28 19:00:35

--
-- PostgreSQL database dump complete
--
