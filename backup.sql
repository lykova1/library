--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3


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
-- Name: books; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.books (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    title character varying(255) NOT NULL,
    article character varying(255) NOT NULL,
    description character varying(255),
    category character varying(255),
    release_year integer,
    quantity integer DEFAULT 0,
    last_modified timestamp without time zone,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.books OWNER TO postgres;

--
-- Data for Name: book; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.book (id, name) FROM stdin;
\.


--
-- Data for Name: books; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.books (id, title, article, description, category, release_year, quantity, last_modified, created_at) FROM stdin;
c4efeeb3-5aff-435b-9265-43ae8c0f16fc	Clean Code	C11223	A Handbook of Agile Software Craftsmanship	Programming	2008	15	2024-07-03 16:18:03.065549	2024-07-03 16:18:03.065549
01b699bf-5e43-4c63-852f-185d833111cb	Design Patterns	D44556	Elements of Reusable Object-Oriented Software	Programming	1994	-3	2024-07-03 21:43:57.268592	2024-07-03 16:18:03.065549
986c704c-4bd1-4a11-abc6-6ef96c488010	The Great Gatsby	KL7424975	A novel written by American author F. Scott Fitzgerald.	Classic	1925	3	2024-07-07 18:20:39.957658	2024-07-07 18:20:39.957658
7987cc78-ac9f-4867-b2ff-796ba46b1bc8	To Kill a Mockingbird	TKM-001	A novel by Harper Lee	Fiction	2013	8	2024-07-07 18:51:25.786178	2024-07-03 21:22:22.214884
cd5a5ed4-f1ef-4306-8bf9-6522952e179c	1984	GE-002	A dystopian novel by George Orwell	Science Fiction	1949	5	2024-07-07 18:59:16.442495	2024-07-07 18:59:16.441496
0e80b0da-4f4c-4cee-911f-8c0cda3f5e75	1984	G-002	A dystopian novel by George Orwell	Science Fiction	1949	5	2024-07-08 00:38:28.887727	2024-07-08 00:38:28.887727
\.


--
-- Name: book book_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);


--
-- Name: books books_article_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_article_key UNIQUE (article);


--
-- Name: books books_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

