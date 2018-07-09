
/*==============================================================*/
/* Table: CCRTB048_MENSAGEM_SISTEMA                             */
/*==============================================================*/
create table CCR.CCRTB048_MENSAGEM_SISTEMA 
(
   CO_MENSAGEM          CHAR(10)             not null,
   DS_MENSAGEM          VARCHAR(150),
   constraint PK_CCRTB048_MENSAGEM_SISTEMA primary key (CO_MENSAGEM)
);


/*==============================================================*/
/* Table: CCRTB037_TIPO_SITUACAO                                */
/*==============================================================*/
create table CCR.CCRTB037_TIPO_SITUACAO 
(
   NU_TIPO_SITUACAO     NUMBER(5)            not null,
   NO_TIPO_SITUACAO     VARCHAR2(80)         not null,
   constraint PK_CCRTB037 primary key (NU_TIPO_SITUACAO)
);




/*==============================================================*/
/* Table: CCRTB014_SITUACAO                                     */
/*==============================================================*/
create table CCR.CCRTB014_SITUACAO 
(
   NU_SITUACAO          NUMBER(5)            not null,
   NU_TIPO_SITUACAO     NUMBER(5)            not null,
   DE_SITUACAO          VARCHAR2(80)         not null,
   constraint PK_CCRTB014 primary key (NU_SITUACAO, NU_TIPO_SITUACAO)
);


alter table CCR.CCRTB014_SITUACAO
   add constraint FK_CCRTB014_CCRTB037 foreign key (NU_TIPO_SITUACAO)
      references CCR.CCRTB037_TIPO_SITUACAO (NU_TIPO_SITUACAO);


      
/*==============================================================*/
/* Table: CCRTB002_GRUPO_TAXA                                   */
/*==============================================================*/
create table CCR.CCRTB002_GRUPO_TAXA 
(
   NU_GRUPO_TAXA        NUMBER(6)            not null,
   NO_GRUPO_TAXA        VARCHAR2(70)         not null,
   CO_GRUPO_TAXA        NUMBER(10)           not null,
   TS_ALTERACAO         TIMESTAMP,
   CO_USUARIO_ALTERACAO CHAR(8),
   constraint PK_CCRTB002 primary key (NU_GRUPO_TAXA)
);

create unique index CCR.CCR1I002 on CCR.CCRTB002_GRUPO_TAXA (
   CO_GRUPO_TAXA ASC
);


/*==============================================================*/
/* Table: CCRTB011_GRUPO_AVERBACAO                              */
/*==============================================================*/
create table CCR.CCRTB011_GRUPO_AVERBACAO 
(
   NU_GRUPO_AVERBACAO   NUMBER(5)            not null,
   NO_GRUPO_AVERBACAO   VARCHAR2(50)         not null,
   constraint PK_CCRTB011 primary key (NU_GRUPO_AVERBACAO)
);


/*==============================================================*/
/* Table: CCRTB031_ORIGEM_RECURSO                               */
/*==============================================================*/
create table CCR.CCRTB031_ORIGEM_RECURSO 
(
   NU_ORIGEM_RECURSO    NUMBER(5)            not null,
   NO_ORIGEM_RECURSO    VARCHAR2(50)         not null,
   constraint PK_CCRTB031 primary key (NU_ORIGEM_RECURSO)
);


/*==============================================================*/
/* Table: CCRTB001_CONVENIO                                     */
/*==============================================================*/
create table CCR.CCRTB001_CONVENIO 
(
   NU_CONVENIO          NUMBER(5)            not null,
   CO_DV_CONVENIO       NUMBER(1),
   NU_CNPJ_CONVENENTE   NUMBER(14)           not null,
   NU_GRUPO_TAXA        NUMBER(6),
   NU_TIPO_SITUACAO     NUMBER(5)            not null,
   NU_SITUACAO          NUMBER(5)            not null,
   NO_CONVENIO          VARCHAR2(50)         not null,
   NU_AGENCIA_CONTA_CREDITO NUMBER(5)            not null,
   NU_OPERACAO_CONTA_CREDITO NUMBER(5)            not null,
   NU_CONTA_CREDITO     NUMBER(8)            not null,
   NU_DV_CONTA_CREDITO  NUMBER(5)            not null,
   NU_NATURAL_PV_RESPONSAVEL NUMBER(10)           not null,
   NU_PV_RESPONSAVEL    NUMBER(5)            not null,
   NU_NATURAL_PV_COBRANCA NUMBER(10)           not null,
   NU_PV_COBRANCA       NUMBER(5)            not null,
   NU_GRUPO_AVERBACAO   NUMBER(5),
   DD_CREDITO_SALARIO   NUMBER(2)            not null,
   PZ_EMISSAO_EXTRATO   NUMBER(5),
   IC_ABRANGENCIA       NUMBER(5)            not null
      constraint CC_CCRTB001_1 check (IC_ABRANGENCIA in (1,2,3)),
   IC_ACEITA_CANAL      CHAR(1)              not null
      constraint CC_CCRTB001_6 check (IC_ACEITA_CANAL in ('S','N')),
   NU_SITUACAO_MIGRACAO NUMBER(5),
   NU_SPRNA_RESPONSAVEL NUMBER(5)            not null,
   NU_NATURAL_SPRNA_RESPONSAVEL NUMBER(10)           not null,
   NU_SPRNA_COBRANCA    NUMBER(5),
   NU_NATURAL_SPRNA_COBRANCA NUMBER(10),
   QT_EMPREGADO         NUMBER(15),
   CO_ORGAO             CHAR(8),
   QT_DIA_AGUARDAR_AUTORIZACAO NUMBER(15),
   constraint PK_CCRTB001 primary key (NU_CONVENIO)
);

create index CCR.IX_CCRTB001_1 on CCR.CCRTB001_CONVENIO (
   NU_GRUPO_TAXA ASC
);

create index CCR.IX_CCRTB001_2 on CCR.CCRTB001_CONVENIO (
   NU_PV_RESPONSAVEL ASC,
   NU_NATURAL_PV_RESPONSAVEL ASC
);

create index CCR.IX_CCRTB001_3 on CCR.CCRTB001_CONVENIO (
   NU_PV_COBRANCA ASC,
   NU_NATURAL_PV_COBRANCA ASC
);

create index CCR.IX_CCRTB001_4 on CCR.CCRTB001_CONVENIO (
   NU_SITUACAO ASC,
   NU_TIPO_SITUACAO ASC
);


create index CCR.IX_CCRTB001_6 on CCR.CCRTB001_CONVENIO (
   NU_SITUACAO_MIGRACAO ASC
);
/*
create index CCR.IX_CCRTB001_7 on CCR.CCRTB001_CONVENIO (
   
);*/
/*
create index CCR.IX_CCRTB001_8 on CCR.CCRTB001_CONVENIO (
   
);*/

create index CCR.IX_CCRTB001_9 on CCR.CCRTB001_CONVENIO (
   NU_GRUPO_AVERBACAO ASC
);


alter table CCR.CCRTB001_CONVENIO
   add constraint FK_CCRTB001_CCRTB002 foreign key (NU_GRUPO_TAXA)
      references CCR.CCRTB002_GRUPO_TAXA (NU_GRUPO_TAXA);

alter table CCR.CCRTB001_CONVENIO
   add constraint FK_CCRTB001_CCRTB011 foreign key (NU_GRUPO_AVERBACAO)
      references CCR.CCRTB011_GRUPO_AVERBACAO (NU_GRUPO_AVERBACAO);

alter table CCR.CCRTB001_CONVENIO
   add constraint FK_CCRTB001_CCRTB014_1 foreign key (NU_SITUACAO, NU_TIPO_SITUACAO)
      references CCR.CCRTB014_SITUACAO (NU_SITUACAO, NU_TIPO_SITUACAO);

/*alter table CCR.CCRTB001_CONVENIO
   add constraint FK_CCRTB001_CCRTB014_2 foreign key (NU_SITUACAO_MIGRACAO )
      references CCR.CCRTB014_SITUACAO (NU_SITUACAO, NU_TIPO_SITUACAO);

alter table CCR.CCRTB001_CONVENIO
   add constraint FK_CCRTB001_CCRTB031 foreign key ()
      references CCR.CCRTB031_ORIGEM_RECURSO (NU_ORIGEM_RECURSO);*/
      
      
      
CREATE TABLE CCR.CCRTB003_CONVENIO_CANAL  ( 
	NU_CANAL_ATENDIMENTO          	NUMBER(19) NOT NULL,
	NU_CONVENIO                   	NUMBER(19) NOT NULL,
	IC_AUTORIZA_MARGEM_CONTRATO   	VARCHAR2(255) NULL,
	IC_AUTORIZA_MARGEM_RENOVACAO  	VARCHAR2(255) NULL,
	IC_EXIGE_ANUENCIA             	VARCHAR2(255) NULL,
	IC_FAIXA_JURO_CONTRATACAO     	VARCHAR2(255) NULL,
	IC_FAIXA_JURO_RENOVACAO       	VARCHAR2(255) NULL,
	IC_PERMITE_CONTRATACAO        	VARCHAR2(255) NULL,
	IC_PERMITE_RENOVACAO          	VARCHAR2(255) NULL,
	PZ_MAXIMO_CONTRATACAO         	NUMBER(19,2) NULL,
	PZ_MAXIMO_RENOVACAO           	NUMBER(10) NULL,
	PZ_MINIMO_CONTRATACAO         	VARCHAR2(255) NULL,
	PZ_MINIMO_RENOVACAO           	VARCHAR2(255) NULL,
	QT_DIA_GARANTIA_CONDICAO      	VARCHAR2(255) NULL,
	IC_EXIGE_AUTORIZACAO_CONTRATO 	VARCHAR2(255) NULL,
	VR_MINIMO_EXIGE_AUTORIZACAO   	FLOAT NULL,
	QT_DIA_MAXIMO_SIMULACAO_FUTURA	NUMBER(10) NULL,
	IC_TAXA_JURO_PADRAO           	VARCHAR2(255) NULL,
	PRIMARY KEY(NU_CONVENIO,NU_CANAL_ATENDIMENTO)
);
      
      

/*==============================================================*/
/* Table: CCRTB005_TAXA_JURO_GRUPO                              */
/*==============================================================*/
create table CCR.CCRTB005_TAXA_JURO_GRUPO 
(
   NU_GRUPO             NUMBER(6)            not null,
   IC_UTILIZACAO_TAXA   NUMBER(5)            not null
      constraint CC_CCRTB005_1 check (IC_UTILIZACAO_TAXA in (1,2)),
   PZ_TAXA_GRUPO        NUMBER(5)            not null,
   DT_INICIO_VIGENCIA   DATE                 not null,
   PC_TAXA_MINIMA       NUMBER(8,5)          not null,
   PC_TAXA_MEDIA        NUMBER(8,5)          not null,
   PC_TAXA_MAXIMA       NUMBER(8,5)          not null,
   DT_FIM_VIGENCIA      DATE,
   CO_USUARIO_INCLUSAO  CHAR(8)              not null,
   TS_INCLUSAO_TAXA     TIMESTAMP            not null,
   CO_USUARIO_FINALIZACAO CHAR(8),
   TS_INCLUSAO_FIM_VIGENCIA_TAXA TIMESTAMP,
   constraint PK_CCRTB005 primary key (NU_GRUPO, IC_UTILIZACAO_TAXA, PZ_TAXA_GRUPO, DT_INICIO_VIGENCIA)
);

alter table CCR.CCRTB005_TAXA_JURO_GRUPO
   add constraint FK_CCRTB005_CCRTB002 foreign key (NU_GRUPO)
      references CCR.CCRTB002_GRUPO_TAXA (NU_GRUPO_TAXA);

      

/*==============================================================*/
/* Table: CCRTB006_TAXA_JURO_CONVENIO                           */
/*==============================================================*/
create table CCR.CCRTB006_TAXA_JURO_CONVENIO 
(
   NU_CONVENIO          NUMBER(5)            not null,
   IC_UTILIZACAO_TAXA   NUMBER(5)            not null
      constraint CC_CCRTB006_1 check (IC_UTILIZACAO_TAXA in (1,2)),
   PZ_TAXA_CONVENIO     NUMBER(5)            not null,
   DT_INICIO_VIGENCIA   DATE                 not null,
   PC_TAXA_MINIMA       NUMBER(8,5)          not null,
   PC_TAXA_MEDIA        NUMBER(8,5)          not null,
   PC_TAXA_MAXIMA       NUMBER(8,5)          not null,
   DT_FIM_VIGENCIA      DATE,
   CO_USUARIO_INCLUSAO  CHAR(8)              not null,
   TS_INCLUSAO_TAXA     TIMESTAMP            not null,
   CO_USUARIO_FINALIZACAO CHAR(8),
   TS_INCLUSAO_FIM_VIGENCIA_TAXA TIMESTAMP,
   constraint PK_CCRTB006 primary key (NU_CONVENIO, PZ_TAXA_CONVENIO, DT_INICIO_VIGENCIA, IC_UTILIZACAO_TAXA)
);

alter table CCR.CCRTB006_TAXA_JURO_CONVENIO
   add constraint FK_CCRTB006_CCRTB001 foreign key (NU_CONVENIO)
      references CCR.CCRTB001_CONVENIO (NU_CONVENIO);
      
      

/*==============================================================*/
/* Table: CCRTB045_CONVENIO_UF                                  */
/*==============================================================*/
create table CCR.CCRTB045_CONVENIO_UF 
(
   NU_CONVENIO          NUMBER(5)            not null,
   SG_UF                CHAR(2)              not null,
   TS_INCLUSAO_UF       TIMESTAMP,
   TS_EXCLUSAO_UF       TIMESTAMP,
   CO_USUARIO_INCLUSAO  CHAR(8),
   CO_USUARIO_EXCLUSAO  CHAR(8),
   constraint PK_CCRTB045_CONVENIO_UF primary key (NU_CONVENIO, SG_UF)
);


alter table CCR.CCRTB045_CONVENIO_UF
   add constraint FK_CCRTB045_REFERENCE_CCRTB001 foreign key (NU_CONVENIO)
      references CCR.CCRTB001_CONVENIO (NU_CONVENIO);

      
      

/*==============================================================*/
/* Table: CCRTB047_CONVENIO_SR                                  */
/*==============================================================*/
create table CCR.CCRTB047_CONVENIO_SR 
(
   NU_CONVENIO          NUMBER(5)            not null,
   NU_UNIDADE           NUMBER(5)            not null,
   NU_NATURAL           NUMBER(10)           not null,
   TS_INCLUSAO_SR       TIMESTAMP,
   TS_EXCLUSAO_SR       TIMESTAMP,
   CO_USUARIO_INCLUSAO  CHAR(8),
   CO_USUARIO_EXCLUSAO  CHAR(8),
   constraint PK_CCRTB047_CONVENIO_SR primary key (NU_CONVENIO, NU_UNIDADE, NU_NATURAL)
);


alter table CCR.CCRTB047_CONVENIO_SR
   add constraint FK_CCRTB047_REFERENCE_CCRTB001 foreign key (NU_CONVENIO)
      references CCR.CCRTB001_CONVENIO (NU_CONVENIO);
/*==============================================================*/
/* Table: CCRTB004_CANAL_ATENDIMENTO                            */
/*==============================================================*/
create table CCR.CCRTB004_CANAL_ATENDIMENTO 
(
   NU_CANAL_ATENDIMENTO NUMBER(5)            not null,
   NO_CANAL_ATENDIMENTO VARCHAR2(50)         not null,
   constraint PK_CCRTB004 primary key (NU_CANAL_ATENDIMENTO)
);



CREATE TABLE CCR.CCRTB007_TAXA_SEGURO  ( 
	PZ_TAXA_SEGURO               	NUMBER(10) NOT NULL,
	DT_INICIO_VIGENCIA           	DATE NOT NULL,
	NU_IDADE                     	NUMBER(10) NOT NULL,
	TS_INCLUSAO_FIM_VIGENCIA_TAXA	DATE NULL,
	TS_INCLUSAO_TAXA             	DATE NULL,
	DT_FIM_VIGENCIA              	DATE NULL,
	PC_TAXA_SEGURO               	FLOAT NOT NULL,
	CO_USUARIO_FINALIZACAO       	VARCHAR2(255) NULL,
	CO_USUARIO_INCLUSAO          	VARCHAR2(255) NULL,
	PRIMARY KEY(PZ_TAXA_SEGURO,DT_INICIO_VIGENCIA,NU_IDADE)
);

CREATE TABLE CCR.CCRTB008_TAXA_IOF  ( 
	NU_TAXA_IOF                  	NUMBER(10) NOT NULL,
	PC_ALIQUOTA_BASICA           	FLOAT NULL,
	PC_ALIQUOTA_COMPLEMENTAR     	FLOAT NULL,
	TS_INCLUSAO_FIM_VIGENCIA_TAXA	DATE NULL,
	TS_INCLUSAO_TAXA             	DATE NULL,
	DT_FIM_VIGENCIA              	DATE NULL,
	DT_INICIO_VIGENCIA           	DATE NULL,
	CO_USUARIO_FINALIZACAO       	VARCHAR2(255) NULL,
	CO_USUARIO_INCLUSAO          	VARCHAR2(255) NULL,
	PRIMARY KEY(NU_TAXA_IOF)
);



CREATE TABLE CCR.CCRVW001_UNIDADE  ( 
	NU_UNIDADE        	NUMBER(19) NOT NULL,
	IC_ULTIMA_SITUACAO	VARCHAR2(255) NULL,
	NO_UNIDADE        	VARCHAR2(255) NULL,
	NU_CGC_UNIDADE    	NUMBER(19) NULL,
	NU_DV_CGC         	NUMBER(19) NULL,
	NU_DV_NU_UNIDADE  	NUMBER(19) NULL,
	NU_NATURAL        	NUMBER(19) NULL,
	NU_TP_UNIDADE_U21 	NUMBER(19) NULL,
	SG_UF_L22         	VARCHAR2(255) NULL,
	SG_UNIDADE        	VARCHAR2(255) NULL,
	PRIMARY KEY(NU_UNIDADE)
	NOT DEFERRABLE
	 VALIDATE
);

CREATE TABLE CCR.CCRVW002_UNIDADE_FEDERATIVA  ( 
	SG_UF	VARCHAR2(255) NOT NULL,
	NO_UF	VARCHAR2(255) NULL,
	PRIMARY KEY(SG_UF)
);

CREATE TABLE CCR.CCRVW003_VINCULO_UNIDADE  ( 
	NU_UNDE_VNCLRA_U24	NUMBER(19) NOT NULL,
	NU_UNDE_VNCLA_U24 	NUMBER(19) NOT NULL,
	NU_TP_VINCULO_U22 	NUMBER(19) NOT NULL,
	NU_NTRL_VNCLRA_U24	NUMBER(19) NOT NULL,
	NU_NTRL_VNCLA_U24 	NUMBER(19) NOT NULL,
	DT_FIM            	DATE NULL,
	DT_INICIO         	DATE NULL,
	PRIMARY KEY(NU_UNDE_VNCLRA_U24,NU_UNDE_VNCLA_U24,NU_TP_VINCULO_U22,NU_NTRL_VNCLRA_U24,NU_NTRL_VNCLA_U24)
);




 
CREATE TABLE CCR.CCRTBH01_CONVENIO  ( 
	NU_CONVENIO                 	NUMBER(5) NOT NULL,
	NU_HISTORICO                	NUMBER(5) NOT NULL,
	NU_GRUPO_TAXA               	NUMBER(5) NULL,
	NU_CNPJ_CONVENENTE          	NUMBER(14) NULL,
	NU_REMESSA_EXTRATO          	NUMBER(5) NULL,
	NU_TIPO_SITUACAO            	NUMBER(5) NULL,
	NU_SITUACAO                 	NUMBER(5) NULL,
	NO_CONVENIO                 	VARCHAR2(50) NULL,
	NU_AGENCIA_CONTA_CREDITO    	NUMBER(5) NULL,
	NU_OPERACAO_CONTA_CREDITO   	NUMBER(5) NULL,
	NU_CONTA_CREDITO            	NUMBER(8) NULL,
	NU_DV_CONTA_CREDITO         	NUMBER(5) NULL,
	NU_NATURAL_SPRNA_RESPONSAVEL	NUMBER(10) NULL,
	NU_SPRNA_RESPONSAVEL        	NUMBER(5) NULL,
	NU_NATURAL_PV_RESPONSAVEL   	NUMBER(10) NULL,
	NU_PV_RESPONSAVEL           	NUMBER(5) NULL,
	NU_NATURAL_SPRNA_COBRANCA   	NUMBER(10) NULL,
	NU_SPRNA_COBRANCA           	NUMBER(5) NULL,
	NU_NATURAL_PV_COBRANCA      	NUMBER(10) NULL,
	NU_PV_COBRANCA              	NUMBER(5) NULL,
	NU_GRUPO_AVERBACAO          	NUMBER(5) NULL,
	DD_CREDITO_SALARIO          	NUMBER(5) NULL,
	PZ_EMISSAO_EXTRATO          	NUMBER(5) NULL,
	IC_ABRANGENCIA              	NUMBER(5) NULL,
	IC_ACEITA_CANAL             	CHAR(1) NULL,
	NU_SITUACAO_MIGRACAO        	NUMBER(5) NULL,
	NU_FORMA_AVERBACAO          	NUMBER(5) NULL,
	TS_HISTORICO                	TIMESTAMP(6) NOT NULL,
	CO_USUARIO                  	CHAR(8) NOT NULL,
	CONSTRAINT PK_CCRTBH01 PRIMARY KEY(NU_HISTORICO,NU_CONVENIO)
);
ALTER TABLE CCR.CCRTBH01_CONVENIO
	ADD ( CONSTRAINT CC_CCRTBH01_7
	CHECK (IC_ACEITA_CANAL is null or (IC_ACEITA_CANAL in ('S','N')))
	 );
ALTER TABLE CCR.CCRTBH01_CONVENIO
	ADD ( CONSTRAINT CC_CCRTBH01_2
	CHECK (IC_ABRANGENCIA is null or (IC_ABRANGENCIA in (1,2)))
	 );
ALTER TABLE CCR.CCRTBH01_CONVENIO
	ADD ( CONSTRAINT CC_CCRTBH01_1
	CHECK (NU_TIPO_SITUACAO is null or (NU_TIPO_SITUACAO in (1)))
	 );
ALTER TABLE CCR.CCRTBH01_CONVENIO
	ADD ( CONSTRAINT FK_CCRTBH01_CCRTB001
	FOREIGN KEY(NU_CONVENIO)
	REFERENCES CCR.CCRTB001_CONVENIO(NU_CONVENIO)
	 );




create sequence CCRSQ001_CONVENIO
start with 1
 nominvalue
 nomaxvalue;

create sequence CCRSQ002_GRUPO_TAXA
start with 1
 nominvalue
 nomaxvalue;



create sequence CCRSQ003_CONVENIO_CANAL
start with 1
 nominvalue
 nomaxvalue;



create sequence CCRSQ005_TAXA_JURO_GRUPO
start with 1
 nominvalue
 nomaxvalue;



create sequence CCRSQ006_TAXA_JURO_CONVENIO
start with 1
 nominvalue
 nomaxvalue;



create sequence CCRSQ007_TAXA_SEGURO
start with 1
 nominvalue
 nomaxvalue;



create sequence CCRSQ008_TAXA_IOF
start with 1
 nominvalue
 nomaxvalue;



create sequence CCRSQ011_GRUPO_AVERBACAO
start with 1
 nominvalue
 nomaxvalue;



create sequence CCRSQ012_SIMULACAO_CONTRATO
start with 1
 nominvalue
 nomaxvalue;



create sequence CCRSQ013_CONTRATO
start with 1
 nominvalue
 nomaxvalue;



create sequence CCRSQ017_ARQUIVO_INTEGRACAO
increment by 1
start with 1
 nominvalue
 nomaxvalue;



create sequence CCRSQ018_ARQUIVO_CONTRATO
increment by 1
start with 1
 nominvalue
 nomaxvalue;



create sequence CCRSQ019_ERRO_ARQUIVO_CONTRATO
increment by 1
start with 1
 nominvalue
 nomaxvalue;



create sequence CCRSQ039_AVALIACAO_RISCO
increment by 1
start with 1
 minvalue 1
 nomaxvalue;



create sequence CRSQ049_CONVENIO_CNPJ_VNCDO
increment by 1
start with 1
 nomaxvalue
 nominvalue;



create sequence CCRSQ050_AUTORIZACAO_CONTRATO
increment by 1
start with 1
 nomaxvalue
 nominvalue;




create sequence CCRSQ071_MODELO_CONTRATO
start with 1
 nominvalue
 nomaxvalue;



create sequence CCRSQ073_CAMPO_USADO
start with 1
 nominvalue
 nomaxvalue;


/*
create sequence CCRSQ074_SISTEMA
start with 1
 nominvalue
 nomaxvalue;
 */


 
 create sequence 
 CCRSQH03_CONVENIO_CANAL
increment by 1
start with 1
 nominvalue
 nomaxvalue;
 
 
  create sequence CCRSQ100_AUDITORIA_TRANSACAO
start with 1
increment by 1
 nomaxvalue
 nominvalue;
 
 
   create sequence CCRSQH13_CONTRATO
start with 1
increment by 1
 nomaxvalue
 nominvalue;
 
 