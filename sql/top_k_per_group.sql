-- Finding the top k items for each group can be annoying since Oracle and MySQL do not support
-- subqueries that have order by or limit, so the following doesn't work:

-- MySQL:

select vehicle_id, lat, lon, time
from vehicle_location v
where time in
(select time from vehicle_location v2
where v2.vehicle_id = v.vehicle_id
order by time desc
limit 3)
;

-- Oracle:

select vehicle_id, lat, lon, time
from vehicle_location v
where time in
(select time from vehicle_location v2
where v2.vehicle_id = v.vehicle_id
and rownum <=3
order by time desc)
;

-- This method is successful is SQL Server however:

-- SQL Server:

select vehicle_id, lat, lon, time
from vehicle_location v
where time in
(select top 3 time from vehicle_location v2
where v2.vehicle_id = v.vehicle_id
order by time desc)
;

-- *****But, there is a way that it can be done using a self join, and it works properly for all of the
-- DBs above:

SELECT v.vehicle_id, v.lat, v.lon, v.time
FROM vehicle_location v
LEFT JOIN vehicle_location v2
  ON (v.vehicle_id = v2.vehicle_id AND v.time < v2.time)
GROUP BY v.vehicle_id, v.lat, v.lon, v.time
HAVING COUNT(*) < 3
ORDER BY v.vehicle_id, v.time1 desc;

-- What this does is it finds the rows for each vehicle_id that have one of the 3 highest values for time
-- (so for this case, the 3 most recently recorded locations for each vehicle_id)

-- How this works is that it does a self join on vehicle_location on matching vehicle_id and only if the time
-- from the right table is greater than the one from the left. It then groups by the vehicle_id and only returns
-- groups in which there are less than 3 rows (meaning that vehicle_id was not beat out by 3 or more rows with 
-- the same vehicle_id, since if it was, it would disqualify the row from being in the top 3 for that group).
-- Note, that this needs to be a left join because the top row for each group will not actually pass both conditions
-- on the join, so it would not be included otherwise.

-- How to reuse this query:
-- if you were seeking the top k (ranked by field rank123) for each group of field grouping123, use the following:

SELECT t.grouping123, t.rank123, ...
FROM table t
LEFT JOIN table t2
  ON (t.grouping123 = t2.grouping123 AND t.rank123 < t2.rank123)
GROUP BY t.grouping123, t.rank123, ...
HAVING COUNT(*) < k
ORDER BY t.grouping123, t.rank123 desc;

-- Note, to find the bottom k instead, just flip the '<' to a '>'

