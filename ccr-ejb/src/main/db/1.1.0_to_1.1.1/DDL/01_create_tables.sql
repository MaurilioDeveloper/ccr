


 
create table CCRTB102_PRCSO_ENVIO_CONTRATO 
(
   NU_PROCESSO_ENVIO_CONTRATO NUMBER(22)	not null,
   NO_PROCESSO_ENVIO_CONTRATO VARCHAR(30)           not null,
   DH_INICIO_PROCESSO TIMESTAMP(6)           not null,
   DH_FIM_PROCESSO TIMESTAMP(6)           not null,
   NU_INTERVALO NUMBER(22) not null,
   constraint PK_CCRTB102 primary key (NU_PROCESSO_ENVIO_CONTRATO)
);

create sequence CCRSQ102_PRCSO_ENVIO_CONTRATO
increment by 1
start with 1
 minvalue 0
nocycle
 nocache
order;
