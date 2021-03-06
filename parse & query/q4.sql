--Q4a:
SELECT AREA, COUNT(*) FROM INPROCEEDINGS
GROUP BY AREA;

--Q4b:
SELECT AUTHORSHIP.AUTHOR, COUNT(INPROCEEDINGS.PUBKEY) AS NUMBEROFPAPERS FROM INPROCEEDINGS
JOIN AUTHORSHIP
ON AUTHORSHIP.PUBKEY = INPROCEEDINGS.PUBKEY AND INPROCEEDINGS.AREA = 'Database'
GROUP BY AUTHORSHIP.AUTHOR
ORDER BY NUMBEROFPAPERS DESC
LIMIT 20;

--Q4c:
SELECT COUNT(AUTH) FROM (
SELECT AUTHORSHIP.AUTHOR AS AUTH, COUNT(DISTINCT INPROCEEDINGS.AREA) AS CNT FROM INPROCEEDINGS
JOIN AUTHORSHIP
ON AUTHORSHIP.PUBKEY = INPROCEEDINGS.PUBKEY AND INPROCEEDINGS.AREA != 'UNKNOWN'
GROUP BY AUTHORSHIP.AUTHOR
) AS X
WHERE CNT = 2;

--Q4d:
SELECT COUNT(*) FROM
(SELECT AUTHORSHIP.AUTHOR AS AUTH1, COUNT(*) AS CNTARTICLE FROM ARTICLE
JOIN AUTHORSHIP
ON AUTHORSHIP.PUBKEY = ARTICLE.PUBKEY
GROUP BY AUTHORSHIP.AUTHOR) AS TAB1
JOIN
(SELECT AUTHORSHIP.AUTHOR AS AUTH2, COUNT(*) AS CNTINPROCEEDINGS FROM INPROCEEDINGS
JOIN AUTHORSHIP
ON AUTHORSHIP.PUBKEY = INPROCEEDINGS.PUBKEY
GROUP BY AUTHORSHIP.AUTHOR) AS TAB2
ON TAB1.AUTH1 = TAB2.AUTH2 AND TAB1.CNTARTICLE > TAB2.CNTINPROCEEDINGS;

--Q4e:
SELECT TAB1.AUTH_A, (TAB1.CNT_A+TAB2.CNT_I) AS CNT FROM 
(SELECT AUTHORSHIP.AUTHOR AS AUTH_A, COUNT(*) AS CNT_A FROM ARTICLE
JOIN AUTHORSHIP
ON AUTHORSHIP.PUBKEY = ARTICLE.PUBKEY AND CAST(ARTICLE.YEAR AS INTEGER) >= 2000
GROUP BY AUTHORSHIP.AUTHOR) AS TAB1
JOIN
(SELECT AUTHORSHIP.AUTHOR AS AUTH_I, COUNT(*) AS CNT_I FROM INPROCEEDINGS
JOIN AUTHORSHIP
ON AUTHORSHIP.PUBKEY = INPROCEEDINGS.PUBKEY AND CAST(INPROCEEDINGS.YEAR AS INTEGER) >= 2000
GROUP BY AUTHORSHIP.AUTHOR) AS TAB2
ON TAB1.AUTH_A = TAB2.AUTH_I AND TAB1.AUTH_A
IN (SELECT AUTHORSHIP.AUTHOR AS AUTH FROM AUTHORSHIP
JOIN INPROCEEDINGS
ON AUTHORSHIP.PUBKEY = INPROCEEDINGS.PUBKEY AND INPROCEEDINGS.AREA = 'Database'
GROUP BY AUTHORSHIP.AUTHOR)
ORDER BY CNT DESC
LIMIT 5;