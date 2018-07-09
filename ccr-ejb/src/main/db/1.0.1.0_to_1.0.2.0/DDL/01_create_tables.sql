

/*==============================================================*/
/* Table: CCRTB017_ARQUIVO_INTEGRACAO                           */
/*==============================================================*/
create table CCRTB017_ARQUIVO_INTEGRACAO 
(
   NU_ARQUIVO_INTEGRACAO NUMBER(19)           not null,
   NU_SITUACAO          NUMBER(5),
   NU_TIPO_SITUACAO     NUMBER(5),
   DH_ENVIO_ARQUIVO     TIMESTAMP            not null,
   QT_CONTRATO_ENVIO   NUMBER(7)            not null,
   DH_RETORNO_ARQUIVO   TIMESTAMP,
   QT_CONTRATO_RETORNO NUMBER(7),
   constraint PK_CCRTB017_ARQUIVO_INTEGRACAO primary key (NU_ARQUIVO_INTEGRACAO)
);

alter table CCRTB017_ARQUIVO_INTEGRACAO
   add constraint FK_CCRTB017_CCRTB014 foreign key (NU_SITUACAO, NU_TIPO_SITUACAO)
      references CCR.CCRTB014_SITUACAO (NU_SITUACAO, NU_TIPO_SITUACAO);



/*==============================================================*/
/* Table: CCRTB018_ARQUIVO_CONTRATO                             */
/*==============================================================*/
create table CCRTB018_ARQUIVO_CONTRATO 
(
   NU_ARQUIVO_CONTRATO  NUMBER(19)           not null,
   NU_ARQUIVO_INTEGRACAO NUMBER(19),
   NU_CONTRATO          NUMBER(19),
   constraint PK_CCRTB018_ARQUIVO_CONTRATO primary key (NU_ARQUIVO_CONTRATO)
);
      
alter table CCRTB018_ARQUIVO_CONTRATO
   add constraint FK_CCRTB018_CCRTB017 foreign key (NU_ARQUIVO_INTEGRACAO)
      references CCRTB017_ARQUIVO_INTEGRACAO (NU_ARQUIVO_INTEGRACAO);

alter table CCRTB018_ARQUIVO_CONTRATO
   add constraint FK_CCRTB018_CCRTB013 foreign key (NU_CONTRATO)
      references CCR.CCRTB013_CONTRATO (NU_CONTRATO);
      

 
 
 
 /*==============================================================*/
/* Table: CCRTB020_ERRO_INTEGRACAO                              */
/*==============================================================*/
create table CCRTB020_ERRO_INTEGRACAO 
(
   CO_ERRO_INTEGRACAO   NUMBER(5)            not null,
   DE_ERRO_INTEGRACAO   VARCHAR2(255)        not null,
   constraint PK_CCRTB020_ERRO_INTEGRACAO primary key (CO_ERRO_INTEGRACAO)
);


/*==============================================================*/
/* Table: CCRTB019_ERRO_ARQUIVO_CONTRATO                        */
/*==============================================================*/
create table CCRTB019_ERRO_ARQUIVO_CONTRATO 
(
   NU_ERRO_ARQUIVO_CONTRATO NUMBER(19)           not null,
   NU_ARQUIVO_CONTRATO  NUMBER(19),
   CO_ERRO_INTEGRACAO   NUMBER(5),
   constraint PK_CCRTB019_ERRO_ARQUIVO_CONTR primary key (NU_ERRO_ARQUIVO_CONTRATO)
);



alter table CCRTB019_ERRO_ARQUIVO_CONTRATO
   add constraint FK_CCRTB019_REFERENCE_CCRTB018 foreign key (NU_ARQUIVO_CONTRATO)
      references CCRTB018_ARQUIVO_CONTRATO (NU_ARQUIVO_CONTRATO);

alter table CCRTB019_ERRO_ARQUIVO_CONTRATO
   add constraint FK_CCRTB019_CCRTB020 foreign key (CO_ERRO_INTEGRACAO)
      references CCRTB020_ERRO_INTEGRACAO (CO_ERRO_INTEGRACAO);
      
      
      
      
   
 
 
 
/*==============================================================*/
/* Table: CCRTB101_TRANSACAO_AUDITADA                           */
/*==============================================================*/
create table CCRTB101_TRANSACAO_AUDITADA 
(
   NU_TRANSACAO_AUDITADA NUMBER(5)            not null,
   NO_TRANSACAO_AUDITADA VARCHAR2(100),
   constraint PK_CCRTB101_TRANSACAO_AUDITADA primary key (NU_TRANSACAO_AUDITADA)
);

 
 
 
/*==============================================================*/
/* Table: CCRTB100_AUDITORIA_TRANSACAO                          */
/*==============================================================*/
create table CCRTB100_AUDITORIA_TRANSACAO 
(
   NU_AUDITORIA_TRANSACAO NUMBER(19)           not null,
   NU_TRANSACAO_AUDITADA NUMBER(5),
   IC_TIPO_ACAO         CHAR(1)              not null
      constraint CKC_IC_TIPO_ACAO_CCRTB100 check (IC_TIPO_ACAO in ('I','A','E')),
   NO_CAMPO_AUDITADO    VARCHAR2(50)         not null,
   DE_VALOR_ANTERIOR    VARCHAR2(255),
   DE_VALOR_ATUAL       VARCHAR2(255)        not null,
   DH_TRANSACAO_AUDITADA TIMESTAMP            not null,
   CO_USUARIO_AUDITADO  CHAR(8),
   constraint PK_CCRTB100_AUDITORIA_TRANSACA primary key (NU_AUDITORIA_TRANSACAO)
);



alter table CCRTB100_AUDITORIA_TRANSACAO
   add constraint FK_CCRTB100_CCRTB101 foreign key (NU_TRANSACAO_AUDITADA)
      references CCRTB101_TRANSACAO_AUDITADA (NU_TRANSACAO_AUDITADA);

      
      
      

/*==============================================================*/
/* Table: CCRTBH03_CONVENIO_CANAL                               */
/*==============================================================*/
create table CCRTBH03_CONVENIO_CANAL 
(
   NU_HISTORICO         NUMBER(19)           not null,
   NU_CONVENIO          NUMBER(5)            not null,
   NU_CANAL_ATENDIMENTO NUMBER(5)            not null,
   IC_PERMITE_CONTRATACAO CHAR(1)              not null
      constraint CC_CCRTB003_1 check (IC_PERMITE_CONTRATACAO in ('S','N')),
   IC_PERMITE_RENOVACAO CHAR(1)              not null
      constraint CC_CCRTB003_2 check (IC_PERMITE_RENOVACAO in ('S','N')),
   IC_FAIXA_JURO_CONTRATACAO CHAR(1)              not null
      constraint CC_CCRTB003_3 check (IC_FAIXA_JURO_CONTRATACAO in ('A','B','C')),
   IC_EXIGE_ANUENCIA    CHAR(1)              not null
      constraint CC_CCRTB003_4 check (IC_EXIGE_ANUENCIA in ('S','N')),
   QT_DIA_GARANTIA_CONDICAO NUMBER(5),
   IC_AUTORIZA_MARGEM_CONTRATO CHAR(1)             
      constraint CC_CCRTB003_5 check (IC_AUTORIZA_MARGEM_CONTRATO is null or (IC_AUTORIZA_MARGEM_CONTRATO in ('S','N'))),
   IC_AUTORIZA_MARGEM_RENOVACAO CHAR(1)             
      constraint CC_CCRTB003_6 check (IC_AUTORIZA_MARGEM_RENOVACAO is null or (IC_AUTORIZA_MARGEM_RENOVACAO in ('S','N'))),
   PZ_MINIMO_CONTRATACAO NUMBER(5),
   PZ_MINIMO_RENOVACAO  NUMBER(5),
   PZ_MAXIMO_RENOVACAO  NUMBER(5),
   PZ_MAXIMO_CONTRATACAO NUMBER(5),
   IC_FAIXA_JURO_RENOVACAO CHAR(1),
   TS_HISTORICO         TIMESTAMP            not null,
   CO_USUARIO           CHAR(8)              not null,
   IC_ACAO              CHAR(1)              not null,
   constraint PK_CCRTBH03_CONVENIO_CANAL primary key (NU_HISTORICO)
);




alter table CCRTBH03_CONVENIO_CANAL
   add constraint FK_CCRTBH03_FK_CCRTBH_CCRTB003 foreign key (NU_CONVENIO, NU_CANAL_ATENDIMENTO)
      references CCR.CCRTB003_CONVENIO_CANAL (NU_CONVENIO, NU_CANAL_ATENDIMENTO);


 