--
-- PostgreSQL database dump
--

\restrict 5Hu6gzHq79dP6QCWVQr2MraRabeUehWbzo5bVPRRWBXrh12FQBZ2fALKSgwjJau

-- Dumped from database version 18.1
-- Dumped by pg_dump version 18.1

-- Started on 2026-06-12 18:53:33

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
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
-- TOC entry 226 (class 1259 OID 16998)
-- Name: attendance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.attendance (
    id integer NOT NULL,
    user_id integer,
    type character varying(50),
    date date,
    "time" time without time zone,
    address text,
    latitude character varying(50),
    longitude character varying(50),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    status character varying(50),
    photo character varying(255)
);


ALTER TABLE public.attendance OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16997)
-- Name: attendance_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.attendance_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.attendance_id_seq OWNER TO postgres;

--
-- TOC entry 5063 (class 0 OID 0)
-- Dependencies: 225
-- Name: attendance_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.attendance_id_seq OWNED BY public.attendance.id;


--
-- TOC entry 222 (class 1259 OID 16957)
-- Name: leave_requests; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.leave_requests (
    id integer NOT NULL,
    user_id integer,
    type character varying(50),
    start_date date,
    end_date date,
    reason text,
    attachment text,
    status character varying(50),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.leave_requests OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16956)
-- Name: leave_requests_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.leave_requests_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.leave_requests_id_seq OWNER TO postgres;

--
-- TOC entry 5064 (class 0 OID 0)
-- Dependencies: 221
-- Name: leave_requests_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.leave_requests_id_seq OWNED BY public.leave_requests.id;


--
-- TOC entry 224 (class 1259 OID 16982)
-- Name: notifications; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notifications (
    id integer NOT NULL,
    user_id integer,
    title character varying(100),
    message text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    type character varying(50)
);


ALTER TABLE public.notifications OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16981)
-- Name: notifications_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.notifications_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.notifications_id_seq OWNER TO postgres;

--
-- TOC entry 5065 (class 0 OID 0)
-- Dependencies: 223
-- Name: notifications_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.notifications_id_seq OWNED BY public.notifications.id;


--
-- TOC entry 228 (class 1259 OID 17009)
-- Name: pengajuan_izin; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pengajuan_izin (
    id integer NOT NULL,
    user_id integer,
    jenis character varying(100),
    tanggal character varying(50),
    waktu character varying(50),
    keterangan text,
    status character varying(50) DEFAULT 'Menunggu'::character varying,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.pengajuan_izin OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 17008)
-- Name: pengajuan_izin_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pengajuan_izin_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pengajuan_izin_id_seq OWNER TO postgres;

--
-- TOC entry 5066 (class 0 OID 0)
-- Dependencies: 227
-- Name: pengajuan_izin_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pengajuan_izin_id_seq OWNED BY public.pengajuan_izin.id;


--
-- TOC entry 220 (class 1259 OID 16928)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    nama character varying(100),
    email character varying(100),
    password character varying(100),
    phone character varying(20),
    "position" character varying(100),
    photo text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16927)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 5067 (class 0 OID 0)
-- Dependencies: 219
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 4882 (class 2604 OID 17001)
-- Name: attendance id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attendance ALTER COLUMN id SET DEFAULT nextval('public.attendance_id_seq'::regclass);


--
-- TOC entry 4878 (class 2604 OID 16960)
-- Name: leave_requests id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.leave_requests ALTER COLUMN id SET DEFAULT nextval('public.leave_requests_id_seq'::regclass);


--
-- TOC entry 4880 (class 2604 OID 16985)
-- Name: notifications id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifications ALTER COLUMN id SET DEFAULT nextval('public.notifications_id_seq'::regclass);


--
-- TOC entry 4884 (class 2604 OID 17012)
-- Name: pengajuan_izin id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pengajuan_izin ALTER COLUMN id SET DEFAULT nextval('public.pengajuan_izin_id_seq'::regclass);


--
-- TOC entry 4876 (class 2604 OID 16931)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 5055 (class 0 OID 16998)
-- Dependencies: 226
-- Data for Name: attendance; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.attendance (id, user_id, type, date, "time", address, latitude, longitude, created_at, status, photo) FROM stdin;
1	1	Check In	2026-05-15	21:15:15	Jl. Sudirman No.123 Jakarta	-6.200000	106.816666	2026-05-15 21:15:15.938106	\N	\N
2	1	Check In	2026-05-15	21:26:36	Jl. Sudirman No.123 Jakarta	-6.200000	106.816666	2026-05-15 21:26:36.696469	\N	\N
3	1	Check In	2026-05-15	21:33:17	Jl. Sudirman No.123 Jakarta	-6.200000	106.816666	2026-05-15 21:33:16.908054	\N	\N
4	1	Check In	2026-05-15	21:34:37	Jl. Sudirman No.123 Jakarta	-6.200000	106.816666	2026-05-15 21:34:36.653314	\N	\N
5	1	Check In	2026-05-15	21:46:39	Jl. Sudirman No.123 Jakarta	-6.200000	106.816666	2026-05-15 21:46:38.605742	\N	\N
6	1	Check In	2026-05-15	21:50:16	Jl. Sudirman No.123 Jakarta	-6.200000	106.816666	2026-05-15 21:50:15.471572	\N	\N
7	1	Check In	2026-05-16	02:56:03	Jl. Sudirman No.123 Jakarta	-6.200000	106.816666	2026-05-16 02:56:03.006922	\N	\N
8	3	Check In	2026-05-16	03:29:14		0.0	0.0	2026-05-16 03:29:13.951189	\N	\N
9	3	Check In	2026-05-16	03:46:06	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336491	110.637918	2026-05-16 03:46:06.211188	\N	\N
10	3	Check Out	2026-05-16	03:49:40	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336491	110.637918	2026-05-16 03:49:40.156974	\N	\N
11	3	Check Out	2026-05-16	04:00:56	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336491	110.6379151	2026-05-16 04:00:56.007868	\N	\N
12	3	Check Out	2026-05-16	04:04:04	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336491	110.6379151	2026-05-16 04:04:04.183369	\N	\N
13	3	Check In	2026-05-16	04:04:17	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336491	110.6379151	2026-05-16 04:04:17.408754	\N	\N
14	3	Check In	2026-05-16	04:13:31	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336492	110.6379174	2026-05-16 04:13:30.840035	\N	\N
15	8	Check In	2026-05-16	14:13:13		0.0	0.0	2026-05-16 14:13:12.504296	\N	\N
16	8	Check Out	2026-05-16	14:13:36	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336485	110.6379136	2026-05-16 14:13:35.639203	\N	\N
17	9	Check In	2026-05-16	14:54:11	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336365	110.6379438	2026-05-16 14:54:10.276335	\N	\N
18	9	Check Out	2026-05-16	14:58:40	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336389	110.6379421	2026-05-16 14:58:40.10159	\N	\N
19	9	Check In	2026-05-16	19:44:21	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336371	110.6379423	2026-05-16 19:44:21.657502	\N	\N
20	9	Check Out	2026-05-16	20:03:43	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336491	110.6379151	2026-05-16 20:03:43.410443	\N	\N
21	9	Check Out	2026-05-16	20:03:48	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336491	110.6379151	2026-05-16 20:03:48.644627	\N	\N
22	9	Check Out	2026-05-16	20:03:53	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336491	110.6379151	2026-05-16 20:03:53.158527	\N	\N
23	9	Check Out	2026-05-16	20:03:58	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336491	110.6379151	2026-05-16 20:03:58.144126	\N	\N
24	9	Check In	2026-05-16	20:10:11	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336493	110.6379159	2026-05-16 20:10:11.370248	\N	\N
25	9	Check Out	2026-05-16	20:17:20	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336493	110.6379159	2026-05-16 20:17:20.232279	\N	\N
26	9	Check In	2026-05-16	20:35:39	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336491	110.6379151	2026-05-16 20:35:39.289402	\N	\N
27	9	Check In	2026-05-16	20:36:10	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336491	110.6379151	2026-05-16 20:36:10.641167	\N	\N
28	10	Check In	2026-05-19	19:51:33	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336488	110.6379144	2026-05-19 19:51:33.721208	\N	\N
29	10	Check Out	2026-05-19	19:55:21	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336488	110.6379144	2026-05-19 19:55:21.354668	\N	\N
30	9	Check Out	2026-05-19	20:43:03	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336481	110.6379128	2026-05-19 20:43:03.465821	\N	\N
31	9	Check Out	2026-05-20	10:30:18	Jl. Glagahsari No.35, Warungboto, Kec. Umbulharjo, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55164, Indonesia	-7.8060263	110.3887391	2026-05-20 10:30:18.394942	\N	\N
32	9	Check In	2026-05-23	14:05:10	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336481	110.6379128	2026-05-23 14:05:09.440495	\N	\N
33	9	Check Out	2026-05-23	14:06:07	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336477	110.6379113	2026-05-23 14:06:06.652659	\N	\N
34	3	Check In	2026-05-23	14:30:20	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.63364	110.6379419	2026-05-23 14:30:19.646993	\N	1779521419_IMG_1779521420301.jpg
35	9	Check In	2026-05-24	21:00:57	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336391	110.637942	2026-05-24 21:00:57.338179	\N	1779631257_IMG_1779631257518.jpg
36	9	Check In	2026-05-31	19:21:36	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336488	110.6379185	2026-05-31 19:21:38.48433	\N	1780230098_IMG_1780230096793.jpg
37	9	Check Out	2026-05-31	19:36:16	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336475	110.6379255	2026-05-31 19:36:18.063627	\N	1780230978_IMG_1780230976420.jpg
38	9	Check In	2026-05-31	20:21:26	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336479	110.637912	2026-05-31 20:21:27.928406	\N	1780233687_IMG_1780233686308.jpg
39	3	Check In	2026-06-03	01:51:07	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336475	110.6379266	2026-06-03 01:51:08.052757	\N	1780426268_IMG_1780426267815.jpg
40	3	Check Out	2026-06-03	01:51:46	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336475	110.6379266	2026-06-03 01:51:47.015803	\N	1780426307_IMG_1780426306806.jpg
41	9	Check In	2026-06-03	02:21:48	1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA	37.4219983	-122.084	2026-06-03 02:21:49.112337	\N	1780428109_IMG_1780428108256.jpg
42	1	Check Out	2026-06-03	03:16:31	9J8Q+CCV, Gamolan Timur, Gledeg, Kec. Karanganom, Kabupaten Klaten, Jawa Tengah 57475, Indonesia	-7.6336488	110.6379144	2026-06-03 03:16:31.601629	\N	1780431391_IMG_1780431391316.jpg
43	12	Check In	2026-06-03	03:34:53	1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA	37.4219983	-122.084	2026-06-03 03:34:54.249319	\N	1780432494_IMG_1780432493348.jpg
44	12	Check Out	2026-06-03	03:35:32	1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA	37.4219983	-122.084	2026-06-03 03:35:33.701449	\N	1780432533_IMG_1780432532788.jpg
45	13	Check In	2026-06-04	08:49:52	Jl. Glagahsari No.35, Warungboto, Kec. Umbulharjo, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55164, Indonesia	-7.806093	110.3887867	2026-06-04 08:49:53.754458	\N	1780537793_IMG_1780537792895.jpg
46	13	Check In	2026-06-04	08:52:01	Jl. Glagahsari No.35, Warungboto, Kec. Umbulharjo, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55164, Indonesia	-7.806093	110.3887867	2026-06-04 08:52:02.226734	\N	1780537922_IMG_1780537921387.jpg
47	14	Check In	2026-06-04	09:03:05	59VQ+8PW, Jl. Perwira, Warungboto, Kec. Umbulharjo, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55164, Indonesia	-7.8066061	110.3895288	2026-06-04 09:03:05.805227	\N	1780538585_IMG_1780538585292.jpg
48	14	Check Out	2026-06-04	09:05:18	59VQ+8PW, Jl. Perwira, Warungboto, Kec. Umbulharjo, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55164, Indonesia	-7.8066061	110.3895288	2026-06-04 09:05:18.701179	\N	1780538718_IMG_1780538718254.jpg
49	15	Check In	2026-06-05	08:43:00	Jl. Glagahsari No.35, Warungboto, Kec. Umbulharjo, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55164, Indonesia	-7.8061597	110.3889528	2026-06-05 08:43:01.065739	\N	1780623781_IMG_1780623780444.jpg
50	15	Check In	2026-06-05	08:44:50	Jl. Glagahsari No.35, Warungboto, Kec. Umbulharjo, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55164, Indonesia	-7.8061259	110.3889396	2026-06-05 08:44:51.410674	\N	1780623891_IMG_1780623890878.jpg
\.


--
-- TOC entry 5051 (class 0 OID 16957)
-- Dependencies: 222
-- Data for Name: leave_requests; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.leave_requests (id, user_id, type, start_date, end_date, reason, attachment, status, created_at) FROM stdin;
\.


--
-- TOC entry 5053 (class 0 OID 16982)
-- Dependencies: 224
-- Data for Name: notifications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notifications (id, user_id, title, message, created_at, type) FROM stdin;
1	1	Check In Berhasil	Anda berhasil check in	2026-05-16 20:27:14.776811	checkin
8	9	Check In Berhasil	Anda berhasil melakukan check in	2026-05-23 14:05:09.450841	checkin
9	9	Check Out Berhasil	Anda berhasil melakukan check out	2026-05-23 14:06:06.654371	checkout
10	3	Check In Berhasil	Anda berhasil melakukan check in	2026-05-23 14:30:19.648854	checkin
11	9	Check In Berhasil	Anda berhasil melakukan check in	2026-05-24 21:00:57.349754	checkin
12	9	Check In Berhasil	Anda berhasil melakukan check in	2026-05-31 19:21:38.488295	checkin
15	9	Pengajuan Cuti	Pengajuan cuti berhasil dikirim	2026-05-31 20:35:47.734799	izin
16	3	Pengajuan Izin	Pengajuan izin berhasil dikirim	2026-06-01 03:10:51.526819	izin
17	3	Check In Berhasil	Anda berhasil melakukan check in	2026-06-03 01:51:08.061104	checkin
18	3	Check Out Berhasil	Anda berhasil melakukan check out	2026-06-03 01:51:47.019275	checkout
19	3	Pengajuan Cuti	Pengajuan cuti berhasil dikirim	2026-06-03 02:11:04.160257	izin
20	9	Check In Berhasil	Anda berhasil melakukan check in	2026-06-03 02:21:49.114839	checkin
21	1	Check Out Berhasil	Anda berhasil melakukan check out	2026-06-03 03:16:31.605831	checkout
22	3	Pengajuan Izin	Pengajuan izin berhasil dikirim	2026-06-03 03:20:17.801709	izin
23	12	Pengajuan Keperluan Keluarga	Pengajuan keperluan keluarga berhasil dikirim	2026-06-03 03:31:42.321411	izin
24	12	Pengajuan Izin	Pengajuan izin berhasil dikirim	2026-06-03 03:32:44.766801	izin
25	12	Check In Berhasil	Anda berhasil melakukan check in	2026-06-03 03:34:54.254413	checkin
26	12	Check Out Berhasil	Anda berhasil melakukan check out	2026-06-03 03:35:33.706413	checkout
27	12	Pengajuan Izin	Pengajuan izin berhasil dikirim	2026-06-03 03:39:03.542609	izin
28	13	Check In Berhasil	Anda berhasil melakukan check in	2026-06-04 08:49:53.766088	checkin
29	13	Check In Berhasil	Anda berhasil melakukan check in	2026-06-04 08:52:02.230121	checkin
31	14	Check Out Berhasil	Anda berhasil melakukan check out	2026-06-04 09:05:18.703355	checkout
32	14	Pengajuan Sakit	Pengajuan sakit berhasil dikirim	2026-06-04 09:06:32.286936	izin
33	14	Pengajuan Keperluan Keluarga	Pengajuan keperluan keluarga berhasil dikirim	2026-06-04 09:06:55.26838	izin
34	14	Pengajuan Izin	Pengajuan izin berhasil dikirim	2026-06-04 09:18:26.566892	izin
35	9	Pengajuan Sakit	Pengajuan sakit berhasil dikirim	2026-06-04 19:16:43.735155	izin
36	15	Check In Berhasil	Anda berhasil melakukan check in	2026-06-05 08:43:01.074675	checkin
37	15	Check In Berhasil	Anda berhasil melakukan check in	2026-06-05 08:44:51.41319	checkin
\.


--
-- TOC entry 5057 (class 0 OID 17009)
-- Dependencies: 228
-- Data for Name: pengajuan_izin; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pengajuan_izin (id, user_id, jenis, tanggal, waktu, keterangan, status, created_at) FROM stdin;
1	8	Izin	2026-05-16	04:07	terlambat	Menunggu	2026-05-16 14:37:18.279161
2	9	Izin	2026-05-18	07:59	keperluan pekerjaan luar lapangan	Menunggu	2026-05-16 14:59:52.289405
3	10	Izin	2026-05-22	03:51	acara sunatan	Menunggu	2026-05-19 19:52:11.310232
4	9	Cuti	2026-06-01	19:25	acara keluarga	Menunggu	2026-05-31 20:25:39.733249
5	9	Cuti	2026-05-02	01:00	acara keluarga	Menunggu	2026-05-31 20:35:47.730692
6	3	Izin	2026-06-02	01:10	acara keluarga	Menunggu	2026-06-01 03:10:51.523228
7	3	Cuti	2026-06-03	02:10	izin	Menunggu	2026-06-03 02:11:04.153183
8	3	Izin	2026-06-03	03:20	cuti	Menunggu	2026-06-03 03:20:17.798539
9	12	Keperluan Keluarga	2026-06-03	02:31	Reoni	Menunggu	2026-06-03 03:31:42.318682
10	12	Izin	2026-06-03	03:32	reouni	Menunggu	2026-06-03 03:32:44.765156
11	12	Izin	2026-06-03	03:38	arisan	Menunggu	2026-06-03 03:39:03.539958
12	14	Sakit	2026-06-04	09:06	atit gigi	Menunggu	2026-06-04 09:06:32.281569
13	14	Keperluan Keluarga	2026-06-04	09:06	Ahh ahh	Menunggu	2026-06-04 09:06:55.264169
14	14	Izin	2026-06-04	09:18	ah ah	Menunggu	2026-06-04 09:18:26.563895
15	9	Sakit	2026-06-04	19:16	Demam	Menunggu	2026-06-04 19:16:43.722095
\.


--
-- TOC entry 5049 (class 0 OID 16928)
-- Dependencies: 220
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, nama, email, password, phone, "position", photo, created_at) FROM stdin;
1	Andi Setiawan	admin@gmail.com	123456	08123456789	Staf Marketing	\N	2026-05-15 20:16:49.782163
2					\N	\N	2026-05-15 20:27:34.089046
8	budi	budi@gmail.com	budi123	085625625212	karyawan	\N	2026-05-16 02:38:53.857542
3	Yudha Kurniawan	yudha@gmail.com	12345	085675454321	karyawan	uploads_profile/profile_1780424394.jpg	2026-05-15 20:35:34.320345
12	Rijal Saputra	rijal@gmail.com	rijal123	085636987451	staf marketing	\N	2026-06-03 03:23:15.03832
9	Bima bayu	bima@gmail.com	bima12345	085463625121	karyawan	uploads_profile/profile_1780536876.jpg	2026-05-16 14:52:57.144489
13	aldi	aldi@gmail.com	12345678	0858539627	dosen	\N	2026-06-04 08:48:33.837426
14	Novan Nugraha	novan@gmail.com	novan123	08953633999	HRD	uploads_profile/profile_1780539663.jpg	2026-06-04 08:56:54.500156
15	nabil	nabil@gmail.com	nabil123	933872434855	boss besar	uploads_profile/profile_1780624002.jpg	2026-06-05 08:40:47.582649
\.


--
-- TOC entry 5068 (class 0 OID 0)
-- Dependencies: 225
-- Name: attendance_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.attendance_id_seq', 50, true);


--
-- TOC entry 5069 (class 0 OID 0)
-- Dependencies: 221
-- Name: leave_requests_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.leave_requests_id_seq', 1, false);


--
-- TOC entry 5070 (class 0 OID 0)
-- Dependencies: 223
-- Name: notifications_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notifications_id_seq', 37, true);


--
-- TOC entry 5071 (class 0 OID 0)
-- Dependencies: 227
-- Name: pengajuan_izin_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pengajuan_izin_id_seq', 15, true);


--
-- TOC entry 5072 (class 0 OID 0)
-- Dependencies: 219
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 15, true);


--
-- TOC entry 4896 (class 2606 OID 17007)
-- Name: attendance attendance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attendance
    ADD CONSTRAINT attendance_pkey PRIMARY KEY (id);


--
-- TOC entry 4892 (class 2606 OID 16966)
-- Name: leave_requests leave_requests_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.leave_requests
    ADD CONSTRAINT leave_requests_pkey PRIMARY KEY (id);


--
-- TOC entry 4894 (class 2606 OID 16991)
-- Name: notifications notifications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifications
    ADD CONSTRAINT notifications_pkey PRIMARY KEY (id);


--
-- TOC entry 4898 (class 2606 OID 17019)
-- Name: pengajuan_izin pengajuan_izin_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pengajuan_izin
    ADD CONSTRAINT pengajuan_izin_pkey PRIMARY KEY (id);


--
-- TOC entry 4888 (class 2606 OID 16939)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 4890 (class 2606 OID 16937)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4899 (class 2606 OID 16967)
-- Name: leave_requests leave_requests_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.leave_requests
    ADD CONSTRAINT leave_requests_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4900 (class 2606 OID 16992)
-- Name: notifications notifications_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifications
    ADD CONSTRAINT notifications_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


-- Completed on 2026-06-12 18:53:34

--
-- PostgreSQL database dump complete
--

\unrestrict 5Hu6gzHq79dP6QCWVQr2MraRabeUehWbzo5bVPRRWBXrh12FQBZ2fALKSgwjJau

