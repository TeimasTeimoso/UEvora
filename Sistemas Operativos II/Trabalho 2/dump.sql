--
-- PostgreSQL database dump
--

-- Dumped from database version 11.7 (Ubuntu 11.7-0ubuntu0.19.10.1)
-- Dumped by pg_dump version 11.7 (Ubuntu 11.7-0ubuntu0.19.10.1)

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

SET default_with_oids = false;

--
-- Name: authorities; Type: TABLE; Schema: public; Owner: so2user
--

CREATE TABLE public.authorities (
    username character varying(50) NOT NULL,
    authority character varying(50) NOT NULL
);


ALTER TABLE public.authorities OWNER TO so2user;

--
-- Name: corporation; Type: TABLE; Schema: public; Owner: so2user
--

CREATE TABLE public.corporation (
    id bigint NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.corporation OWNER TO so2user;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: so2user
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO so2user;

--
-- Name: occupation; Type: TABLE; Schema: public; Owner: so2user
--

CREATE TABLE public.occupation (
    entry_id bigint NOT NULL,
    day date NOT NULL,
    hours time without time zone NOT NULL,
    level character varying(255) NOT NULL,
    locat character varying(255) NOT NULL,
    user_id character varying(255) NOT NULL,
    id bigint
);


ALTER TABLE public.occupation OWNER TO so2user;

--
-- Name: store; Type: TABLE; Schema: public; Owner: so2user
--

CREATE TABLE public.store (
    address character varying(255) NOT NULL,
    latidude double precision,
    location character varying(255) NOT NULL,
    longitude double precision,
    id bigint
);


ALTER TABLE public.store OWNER TO so2user;

--
-- Name: users; Type: TABLE; Schema: public; Owner: so2user
--

CREATE TABLE public.users (
    username character varying(50) NOT NULL,
    password character varying(255) NOT NULL,
    enabled boolean NOT NULL
);


ALTER TABLE public.users OWNER TO so2user;

--
-- Data for Name: authorities; Type: TABLE DATA; Schema: public; Owner: so2user
--

COPY public.authorities (username, authority) FROM stdin;
ruben	user
luis	user
l39868	user
l35003	user
\.


--
-- Data for Name: corporation; Type: TABLE DATA; Schema: public; Owner: so2user
--

COPY public.corporation (id, name) FROM stdin;
1	Pingo Doce
2	Continente
3	Auchan
\.


--
-- Data for Name: occupation; Type: TABLE DATA; Schema: public; Owner: so2user
--

COPY public.occupation (entry_id, day, hours, level, locat, user_id, id) FROM stdin;
4	2020-06-12	03:17:46	1. vazio ou com mínima lotação	Horta das Figueiras	ruben	1
5	2020-06-12	03:37:33	4. muito cheio e com fila de espera	Évora	ruben	3
6	2020-06-12	03:38:39	1. vazio ou com mínima lotação	Horta das Figueiras	ruben	1
7	2020-06-12	03:38:55	4. muito cheio e com fila de espera	Bairro Senhora da Glória	ruben	1
8	2020-06-12	03:39:17	3. muito cheio	Horta das Figueiras	luis	1
9	2020-06-12	14:06:45	2. com pessoas, mas espaço suficiente	Évora	ruben	2
10	2020-06-12	16:31:28	2. com pessoas, mas espaço suficiente	Horta das Figueiras	ruben	1
11	2020-06-12	16:33:14	2. com pessoas, mas espaço suficiente	Horta das Figueiras	luis	1
12	2020-06-24	19:13:38	1. vazio ou com mínima lotação	Évora	ruben	3
13	2020-06-24	21:07:51	3. muito cheio	Bairro Senhora da Glória	luis	1
14	2020-06-24	21:17:38	2. com pessoas, mas espaço suficiente	Bairro Senhora da Glória	luis	1
15	2020-06-25	00:17:07	1. vazio ou com mínima lotação	Évora	ruben	2
16	2020-06-25	00:17:14	1. vazio ou com mínima lotação	Évora	ruben	2
17	2020-06-25	00:17:21	4. muito cheio e com fila de espera	Évora	ruben	2
18	2020-06-25	00:27:28	4. muito cheio e com fila de espera	Évora	luis	2
19	2020-06-25	00:27:49	3. muito cheio	Évora	luis	2
20	2020-06-25	00:42:49	1. vazio ou com mínima lotação	Évora	luis	3
21	2020-06-25	15:07:48	3. muito cheio	Horta das Figueiras	ruben	1
22	2020-06-25	19:36:18	3. muito cheio	Horta das Figueiras	ruben	1
23	2020-06-29	02:44:52	1. vazio ou com mínima lotação	Évora	l39868	2
25	2020-07-03	17:55:29	2. com pessoas, mas espaço suficiente	Bairro Senhora da Glória	l39868	1
26	2020-07-03	18:57:24	3. muito cheio	Évora	l39868	3
27	2020-07-03	21:34:17	4. muito cheio e com fila de espera	Horta das Figueiras	l39868	1
29	2020-07-04	13:48:56	1. vazio ou com mínima lotação	Évora	l35003	3
30	2020-07-04	13:49:14	3. muito cheio	Évora	l35003	3
31	2020-07-04	14:28:22	2. com pessoas, mas espaço suficiente	Évora	l35003	3
32	2020-07-04	14:31:07	3. muito cheio	Évora	l35003	3
39	2020-07-04	19:42:04	1. vazio ou com mínima lotação	Évora	l39868	2
40	2020-07-05	15:24:16	1. vazio ou com mínima lotação	Évora	l35003	2
41	2020-07-05	15:29:57	1. vazio ou com mínima lotação	Horta das Figueiras	l35003	1
42	2020-07-05	15:30:56	1. vazio ou com mínima lotação	Évora	l39868	3
43	2020-07-05	17:34:13	2. com pessoas, mas espaço suficiente	Horta das Figueiras	l39868	1
44	2020-07-05	17:36:02	3. muito cheio	Bairro Senhora da Glória	l39868	1
45	2020-07-05	17:48:15	1. vazio ou com mínima lotação	Évora	l39868	2
46	2020-07-05	17:59:03	1. vazio ou com mínima lotação	Bairro Senhora da Glória	l39868	1
47	2020-07-05	19:44:21	1. vazio ou com mínima lotação	Évora	l39868	2
48	2020-07-05	19:58:23	3. muito cheio	Évora	l39868	2
50	2020-07-05	19:59:35	1. vazio ou com mínima lotação	Évora	l39868	2
52	2020-07-05	21:12:18	1. vazio ou com mínima lotação	Horta das Figueiras	l39868	1
53	2020-07-06	14:54:19	1. vazio ou com mínima lotação	Bairro Senhora da Glória	l39868	1
54	2020-07-06	15:27:59	2. com pessoas, mas espaço suficiente	Évora	l35003	2
\.


--
-- Data for Name: store; Type: TABLE DATA; Schema: public; Owner: so2user
--

COPY public.store (address, latidude, location, longitude, id) FROM stdin;
Praça Angra do Heroísmo 1, 7000-121 Évora	38.5693600000000032	Bairro Senhora da Glória	-7.92147999999999985	1
Av. Bento Jesus Caraça 1 Lote 1, Bairro Dos Álamos, 7000-334 Évora	38.5741000000000014	Álamos	-7.89972999999999992	1
Rua Armando Antunes da Silva 16, 7005-145 Évora, Portugal	38.5577600000000018	Évora	-7.92103999999999964	2
R. da Horta das Figueiras 2, 7000-790 Évora	38.5602599999999995	Horta das Figueiras	-7.91392000000000007	1
R. Luís Adelino Fonseca nº 2 piso 0, 7005-345 Évora	38.549590000000002	Évora	-7.90533000000000019	3
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: so2user
--

COPY public.users (username, password, enabled) FROM stdin;
ruben	teimas	t
luis	ressonha	t
l39868	$2a$10$nGhijzKhRcJGj0Yq5G09WOBeSTVfYn8UztBb8eedNNjOaKZ3AfdJa	t
l35003	$2a$10$O6IyNvZdXC4NjffBvXJzSuAeFJGb8SX2pJ.inim5YI620uK12jBi.	t
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: so2user
--

SELECT pg_catalog.setval('public.hibernate_sequence', 54, true);


--
-- Name: corporation corporation_pkey; Type: CONSTRAINT; Schema: public; Owner: so2user
--

ALTER TABLE ONLY public.corporation
    ADD CONSTRAINT corporation_pkey PRIMARY KEY (id);


--
-- Name: occupation occupation_pkey; Type: CONSTRAINT; Schema: public; Owner: so2user
--

ALTER TABLE ONLY public.occupation
    ADD CONSTRAINT occupation_pkey PRIMARY KEY (entry_id);


--
-- Name: store store_pkey; Type: CONSTRAINT; Schema: public; Owner: so2user
--

ALTER TABLE ONLY public.store
    ADD CONSTRAINT store_pkey PRIMARY KEY (address);


--
-- Name: store uk_914tof0lwn5fxg11x7d1m0grm; Type: CONSTRAINT; Schema: public; Owner: so2user
--

ALTER TABLE ONLY public.store
    ADD CONSTRAINT uk_914tof0lwn5fxg11x7d1m0grm UNIQUE (latidude);


--
-- Name: corporation uk_rc64tm620vte9ly891nde8cau; Type: CONSTRAINT; Schema: public; Owner: so2user
--

ALTER TABLE ONLY public.corporation
    ADD CONSTRAINT uk_rc64tm620vte9ly891nde8cau UNIQUE (name);


--
-- Name: store uk_tjqcp3fqj8te1emtab0q9j37; Type: CONSTRAINT; Schema: public; Owner: so2user
--

ALTER TABLE ONLY public.store
    ADD CONSTRAINT uk_tjqcp3fqj8te1emtab0q9j37 UNIQUE (longitude);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: so2user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);


--
-- Name: ix_auth_username; Type: INDEX; Schema: public; Owner: so2user
--

CREATE UNIQUE INDEX ix_auth_username ON public.authorities USING btree (username, authority);


--
-- Name: authorities fk_authorities_users; Type: FK CONSTRAINT; Schema: public; Owner: so2user
--

ALTER TABLE ONLY public.authorities
    ADD CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES public.users(username);


--
-- Name: occupation fkkj2gkdjxkkfp7popoqxmav8p6; Type: FK CONSTRAINT; Schema: public; Owner: so2user
--

ALTER TABLE ONLY public.occupation
    ADD CONSTRAINT fkkj2gkdjxkkfp7popoqxmav8p6 FOREIGN KEY (id) REFERENCES public.corporation(id);


--
-- Name: store fkmu5oq3smmu6q0oxqfaqmlmvgl; Type: FK CONSTRAINT; Schema: public; Owner: so2user
--

ALTER TABLE ONLY public.store
    ADD CONSTRAINT fkmu5oq3smmu6q0oxqfaqmlmvgl FOREIGN KEY (id) REFERENCES public.corporation(id);


--
-- PostgreSQL database dump complete
--

