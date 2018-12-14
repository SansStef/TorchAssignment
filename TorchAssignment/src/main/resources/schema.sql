create schema torch;

create table subway_line_status_history (
	id serial,
    subway_line varchar(15) not null,
    line_status varchar(15) not null,
	updated_at timestamp not null default now()
);

#insert new status
insert into subway_line_status_history
(subway_line, line_status)
values
('RED', 'NOT_DELAYED');

#Get current status
select subway_line, line_status 
from subway_line_status_history
where subway_line = 'RED'
order by updated_at desc limit 1;

#Get history of a line
select subway_line, line_status, updated_at
from subway_line_status_history
where subway_line = 'RED'
order by updated_at asc;

#Get line total time
select subway_line, updated_at, now() - updated_at as 'total_time'
from subway_line_status_history
where subway_line = 'RED'
order by updated_at asc limit 1;