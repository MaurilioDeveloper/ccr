drop table CCR.CCRTB048_MENSAGEM_SISTEMA cascade constraints;

alter table CCR.CCRTB001_CONVENIO
   drop constraint FK_CCRTB001_CCRTB002;

alter table CCR.CCRTB005_TAXA_JURO_GRUPO
   drop constraint FK_CCRTB005_CCRTB002;

drop table CCR.CCRTB002_GRUPO_TAXA cascade constraints;

alter table CCR.CCRTB001_CONVENIO
   drop constraint FK_CCRTB001_CCRTB011;

drop table CCR.CCRTB011_GRUPO_AVERBACAO cascade constraints;

alter table CCR.CCRTB001_CONVENIO
   drop constraint FK_CCRTB001_CCRTB031;

drop table CCR.CCRTB031_ORIGEM_RECURSO cascade constraints;


alter table CCR.CCRTB003_CONVENIO_CANAL
   drop constraint FK_CCRTB003_CCRTB004;

drop table CCR.CCRTB004_CANAL_ATENDIMENTO cascade constraints;

alter table CCR.CCRTB001_CONVENIO
   drop constraint FK_CCRTB001_CCRTB001;

alter table CCR.CCRTB001_CONVENIO
   drop constraint FK_CCRTB001_CCRTB002;

alter table CCR.CCRTB001_CONVENIO
   drop constraint FK_CCRTB001_CCRTB011;

alter table CCR.CCRTB001_CONVENIO
   drop constraint FK_CCRTB001_CCRTB014_1;

alter table CCR.CCRTB001_CONVENIO
   drop constraint FK_CCRTB001_CCRTB014_2;

alter table CCR.CCRTB001_CONVENIO
   drop constraint FK_CCRTB001_CCRTB031;

alter table CCR.CCRTB001_CONVENIO
   drop constraint FK_CCRTB001_ICOTBU24_1;

alter table CCR.CCRTB001_CONVENIO
   drop constraint FK_CCRTB001_ICOTBU24_2;

alter table CCR.CCRTB001_CONVENIO
   drop constraint FK_CCRTB001_FK_CCRTB0_ICOTBU24;

alter table CCR.CCRTB001_CONVENIO
   drop constraint FK_CCRTB001_FK_CCRTB0_ICOTBU24;

alter table CCR.CCRTB003_CONVENIO_CANAL
   drop constraint FK_CCRTB003_CCRTB001;

alter table CCR.CCRTB006_TAXA_JURO_CONVENIO
   drop constraint FK_CCRTB006_CCRTB001;

alter table CCR.CCRTB012_SIMULACAO_CONTRATO
   drop constraint FK_CCRTB012_REFERENCE_CCRTB001;

alter table CCR.CCRTB013_CONTRATO
   drop constraint FK_CCRTB013_REFERENCE_CCRTB001;

alter table CCR.CCRTB045_CONVENIO_UF
   drop constraint FK_CCRTB045_REFERENCE_CCRTB001;

alter table CCR.CCRTB047_CONVENIO_SR
   drop constraint FK_CCRTB047_REFERENCE_CCRTB001;

alter table CCRTB049_CONVENIO_CNPJ_VNCDO
   drop constraint FK_CCRTB049_FK_CCRTB0_CCRTB001;

alter table CCR.CCRTBH01_CONVENIO
   drop constraint FK_CCRTBH01_CCRTB001;

drop index CCR.IX_CCRTB001_10;

drop index CCR.IX_CCRTB001_9;

drop index CCR.IX_CCRTB001_8;

drop index CCR.IX_CCRTB001_7;

drop index CCR.IX_CCRTB001_6;

drop index CCR.IX_CCRTB001_5;

drop index CCR.IX_CCRTB001_4;

drop index CCR.IX_CCRTB001_3;

drop index CCR.IX_CCRTB001_2;

drop index CCR.IX_CCRTB001_1;

drop table CCR.CCRTB001_CONVENIO cascade constraints;


alter table CCR.CCRTB005_TAXA_JURO_GRUPO
   drop constraint FK_CCRTB005_CCRTB002;

drop table CCR.CCRTB005_TAXA_JURO_GRUPO cascade constraints;

alter table CCR.CCRTB006_TAXA_JURO_CONVENIO
   drop constraint FK_CCRTB006_CCRTB001;

drop table CCR.CCRTB006_TAXA_JURO_CONVENIO cascade constraints;


alter table CCR.CCRTB045_CONVENIO_UF
   drop constraint FK_CCRTB045_FK_ICOTBL_ICOTBL22;

alter table CCR.CCRTB045_CONVENIO_UF
   drop constraint FK_CCRTB045_REFERENCE_CCRTB001;

drop table CCR.CCRTB045_CONVENIO_UF cascade constraints;


alter table CCR.CCRTB047_CONVENIO_SR
   drop constraint FK_CCRTB047_FK_ICOTBU_ICOTBU24;

alter table CCR.CCRTB047_CONVENIO_SR
   drop constraint FK_CCRTB047_REFERENCE_CCRTB001;

drop table CCR.CCRTB047_CONVENIO_SR cascade constraints;


alter table CCR.CCRTB014_SITUACAO
   drop constraint FK_CCRTB014_CCRTB037;

drop table CCR.CCRTB037_TIPO_SITUACAO cascade constraints;




alter table CCR.CCRTB001_CONVENIO
   drop constraint FK_CCRTB001_CCRTB014_1;

alter table CCR.CCRTB001_CONVENIO
   drop constraint FK_CCRTB001_CCRTB014_2;

alter table CCR.CCRTB013_CONTRATO
   drop constraint FK_CCRTB013_CCRTB014;

alter table CCR.CCRTB014_SITUACAO
   drop constraint FK_CCRTB014_CCRTB037;

alter table CCR.CCRTB014_SITUACAO
   drop constraint FK_CCRTB014_CCRTB050;

alter table CCR.CCRTB038_HISTORICO_SITUACAO
   drop constraint FK_CCRTB038_FK_CCRTB0_CCRTB014;

drop table CCR.CCRTB014_SITUACAO cascade constraints;

drop TABLE CCR.CCRTB003_CONVENIO_CANAL  cascade constraints;

DROP TABLE CCR.CCRTB008_TAXA_IOF  cascade constraints;

DROP TABLE CCR.CCRTB007_TAXA_SEGURO cascade constraints;

DROP TABLE CCR.CCRVW001_UNIDADE  cascade constraints;

DROP TABLE CCR.CCRVW002_UNIDADE_FEDERATIVA cascade constraints;

DROP TABLE CCR.CCRVW003_VINCULO_UNIDADE cascade constraints;





ALTER TABLE CCR.CCRTBH01_CONVENIO
	drop CONSTRAINT CC_CCRTBH01_7;
ALTER TABLE CCR.CCRTBH01_CONVENIO
	drop CONSTRAINT CC_CCRTBH01_2;
drop TABLE CCR.CCRTBH01_CONVENIO cascade constraints;



drop sequence CCR.CCRSQ001_CONVENIO;


drop sequence CCR.CCRSQ002_GRUPO_TAXA;



drop sequence CCR.CCRSQ003_CONVENIO_CANAL;


drop sequence CCR.CCRSQ005_TAXA_JURO_GRUPO;


drop sequence CCR.CCRSQ006_TAXA_JURO_CONVENIO;


drop sequence CCR.CCRSQ007_TAXA_SEGURO;


drop sequence CCR.CCRSQ008_TAXA_IOF;


drop sequence CCR.CCRSQ011_GRUPO_AVERBACAO;


drop sequence CCR.CCRSQ012_SIMULACAO_CONTRATO;



drop sequence CCR.CCRSQ013_CONTRATO;



drop sequence CCR.CCRSQ017_ARQUIVO_INTEGRACAO;


drop sequence CCR.CCRSQ018_ARQUIVO_CONTRATO;



drop sequence CCR.CCRSQ019_ERRO_ARQUIVO_CONTRATO;



drop sequence CCR.CCRSQ039_AVALIACAO_RISCO;


drop sequence CCR.CCRSQ049_CONVENIO_CNPJ_VNCDO;



drop sequence CCR.CCRSQ050_AUTORIZACAO_CONTRATO;


drop sequence CCR.CCRSQ071_MODELO_CONTRATO;



drop sequence CCR.CCRSQ073_CAMPO_USADO;


/*
drop sequence CCR.CCRSQ074_SISTEMA;
*/


drop sequence CCR.CCRSQ100_AUDITORIA_TRANSACAO;

drop sequence CCR.CCRSQH03_CONVENIO_CANAL;


drop sequence CCR.CCRSQH13_CONTRATO;





