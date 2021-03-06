
CREATE TABLE CCR.CCRVW002_UNIDADE_FEDERATIVA  ( 
	SG_UF	VARCHAR2(255) NOT NULL,
	NO_UF	VARCHAR2(255) NULL,
	PRIMARY KEY(SG_UF)
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