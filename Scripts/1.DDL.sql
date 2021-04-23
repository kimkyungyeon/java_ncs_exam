-- java_ncs_exam
DROP SCHEMA IF EXISTS java_ncs_exam;

-- java_ncs_exam
CREATE SCHEMA java_ncs_exam;

-- 직책
CREATE TABLE java_ncs_exam.Title (
	tno   INT         NOT NULL COMMENT '직책코드', -- 직책코드
	tname VARCHAR(20) NOT NULL COMMENT '직책명' -- 직책명
)
COMMENT '직책';

-- 직책
ALTER TABLE java_ncs_exam.Title
	ADD CONSTRAINT PK_Title -- 직책 기본키
		PRIMARY KEY (
			tno -- 직책코드
		);
		
