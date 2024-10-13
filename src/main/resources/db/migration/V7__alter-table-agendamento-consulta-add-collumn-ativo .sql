alter table agendamento_consulta add ativo tinyint;
update agendamento_consulta set ativo =1;