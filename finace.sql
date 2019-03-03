SELECT SUM(payment),DATE_FORMAT(begin_time,'%Y-%m') monthdate FROM t_item
WHERE DATE_FORMAT(begin_time,'%Y-%m') IN('2019-01','2019-02')
GROUP BY monthdate
SELECT SUM(payment), depart_id  FROM t_item
WHERE DATE_FORMAT(begin_time,'%Y') IN('2019','2018')
GROUP BY depart_id
SELECT SUM(base_wage),DATE_FORMAT(work_time,'%Y-%m') stringValue FROM t_wage
WHERE DATE_FORMAT(work_time,'%Y-%m') IN('2015-01','2015-02')
GROUP BY stringValue
SELECT SUM(base_wage), depart_id intType FROM t_wage
WHERE DATE_FORMAT(work_time,'%Y-%m') IN('2015-01','2015-02')
GROUP BY intType

SELECT SUM(w.base_wage)+SUM(e.money) total,w.depart_id FROM t_wage w,t_expense e
WHERE DATE_FORMAT(w.work_time,'%Y') = DATE_FORMAT(e.create_time,'%Y')
AND DATE_FORMAT(w.work_time,'%Y') ='2019' AND e.status =1
GROUP BY depart_id

SELECT ROUND((SUM(w.base_wage)+SUM(e.money))/100) SUM(w.base_wage)+SUM(e.money) total,DATE_FORMAT(w.work_time,'%Y-%m') strig FROM t_wage w,t_expense e
WHERE DATE_FORMAT(w.work_time,'%Y-%m') = DATE_FORMAT(e.create_time,'%Y-%m')
AND DATE_FORMAT(w.work_time,'%Y-%m') IN('2019-01','2019-02') AND e.status =1
GROUP BY strig
