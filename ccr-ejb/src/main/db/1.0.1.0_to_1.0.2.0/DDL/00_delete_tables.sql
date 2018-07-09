alter table CCRTB017_ARQUIVO_INTEGRACAO
   drop constraint FK_CCRTB017_CCRTB014;

alter table CCRTB018_ARQUIVO_CONTRATO
   drop constraint FK_CCRTB018_CCRTB017;

drop table CCRTB017_ARQUIVO_INTEGRACAO cascade constraints;



alter table CCRTB018_ARQUIVO_CONTRATO
   drop constraint FK_CCRTB018_CCRTB017;

alter table CCRTB018_ARQUIVO_CONTRATO
   drop constraint FK_CCRTB018_CCRTB013;

alter table CCRTB019_ERRO_ARQUIVO_CONTRATO
   drop constraint FK_CCRTB019_REFERENCE_CCRTB018;

drop table CCRTB018_ARQUIVO_CONTRATO cascade constraints;




alter table CCRTB019_ERRO_ARQUIVO_CONTRATO
   drop constraint FK_CCRTB019_REFERENCE_CCRTB018;

alter table CCRTB019_ERRO_ARQUIVO_CONTRATO
   drop constraint FK_CCRTB019_CCRTB020;

drop table CCRTB019_ERRO_ARQUIVO_CONTRATO cascade constraints;


alter table CCRTB019_ERRO_ARQUIVO_CONTRATO
   drop constraint FK_CCRTB019_CCRTB020;

drop table CCRTB020_ERRO_INTEGRACAO cascade constraints;




drop sequence CCRSQ017_ARQUIVO_INTEGRACAO;

drop sequence CCRSQ018_ARQUIVO_CONTRATO;

drop sequence CCRSQ019_ERRO_ARQUIVO_CONTRATO;


alter table CCRTB100_AUDITORIA_TRANSACAO
   drop constraint FK_CCRTB100_CCRTB101;

drop table CCRTB101_TRANSACAO_AUDITADA cascade constraints;


alter table CCRTB100_AUDITORIA_TRANSACAO
   drop constraint FK_CCRTB100_CCRTB101;

drop table CCRTB100_AUDITORIA_TRANSACAO cascade constraints;




alter table CCRTBH03_CONVENIO_CANAL
   drop constraint FK_CCRTBH03_FK_CCRTBH_CCRTB003;

drop table CCRTBH03_CONVENIO_CANAL cascade constraints;
