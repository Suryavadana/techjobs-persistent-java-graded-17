--Part 1
--  columns         data types
--  id              int             primarykey
--  employer        varchar(255)
--  name            varchar(255)
--  skills          varchar(255)
--
--Part 2
SELECT name FROM employer WHERE location = "St. Louis City";
--Part 3
DROP TABLE job;
--Part 4
SELECT *
FROM Skill
INNER JOIN job_skills ON Skill.id = job_skills.skills_id
WHERE job_skills.jobs_id IS NOT NULL
ORDER BY name ASC;

