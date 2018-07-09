STUB.adicionarStub(/emprest\/userprofile\/loadgroups/,'POST', 
["Renegociacao009","Renegociacao008","Renegociacao006","Gerencial7000EMP","Consulta7000EMP","SimulaAPI/APX","ConsultaAPI/APX"]
);

STUB.adicionarStub(/emprest\/userprofile\/load/,'POST',
{"empregado":{"numeroMatricula":891843,"numeroDvMatricula":0,"nomePessoa":"TESTE                                   ",
	"dataPrevisaoDesligamento":null,"numeroUnidade":664,"numeroNatural":645,"numeroFuncao":null,"codigoCargo":"TBN   ",
	"numeroCpfEmpregado":46930973068,"numeroUnidadeAlocacao":null,"numeroNaturalAlocacao":null,"numeroIP":"10.208.10.87",
	"numeroPerfilNav":10,"matriculaUsuario":"c891843","nis":null,"cnpj":null,"dataNascimento":null,"email":null,"nomeMae":null,
	"cpf":null,"matricula":"C891843"},
	"grupoUsuarioList":["FEC000000040","FEC000000039","FEC000000037","FEC000000004","FEC000000003","FEC000000002","FEC000000001"],
	"numeroIP":"10.208.10.87"}
);

STUB.adicionarStub(/emprest\/empregado\/consultarPorMatricula/,'POST',
{
	"numeroMatricula":891843,"numeroDvMatricula":0,"nomePessoa":"TESTE                                   ",
	"dataPrevisaoDesligamento":null,"numeroUnidade":664,"numeroNatural":645,"numeroFuncao":null,"codigoCargo":"TBN   ",
	"numeroCpfEmpregado":46930973068,"numeroUnidadeAlocacao":null,"numeroNaturalAlocacao":null,"numeroIP":"10.208.10.87",
	"numeroPerfilNav":10,"matriculaUsuario":"c891843","nis":null,"cnpj":null,"dataNascimento":null,"email":null,"nomeMae":null,
	"cpf":null,"matricula":"C891843"
}
);