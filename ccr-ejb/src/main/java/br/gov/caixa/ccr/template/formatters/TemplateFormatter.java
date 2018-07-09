package br.gov.caixa.ccr.template.formatters;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;
import javax.swing.text.MaskFormatter;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;

@Stateless
public class TemplateFormatter {

	public static final String DATA = "dd/MM/yyyy";
	public static final String DATA_HORA = "dd/MM/yyyy HH:mm:ss";

	public String formatarValor(Object o, Field field) throws IllegalAccessException {
		String valor = field.get(o).toString();
		if (field.isAnnotationPresent(TemplateFormat.class)) {
			// se for o caso, formatar de acordo com a annotation
			TemplateFormat annotation = field.getAnnotation(TemplateFormat.class);
			if (annotation.format() != null) {
				if (annotation.format().equals(FormatEnum.CPF)) {
					valor = formatarCpf(valor);
				} else if (annotation.format().equals(FormatEnum.CNPJ)) {
					valor = formatarCnpj(valor);
				} else if (annotation.format().equals(FormatEnum.CEP)) {
					valor = formatarCep(valor);
				} else if (annotation.format().equals(FormatEnum.DATA)) {
					valor = formatarData(field.get(o), DATA);
				} else if (annotation.format().equals(FormatEnum.DATA_HORA)) {
					valor = formatarData(field.get(o), DATA_HORA);
				} else if(annotation.format().equals(FormatEnum.VALOR_MONETARIO)){
					valor = formatarValorMonetario(valor);
				}else if(annotation.format().equals(FormatEnum.VALOR_PORCENTAGEM)){
					valor = formatarPorcentagem(valor);
				}
			}
		}
		return valor;
	}

	private String formatarData(Object o, String pattern) {
		String dataFormatada = "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (o instanceof Date) {
			dataFormatada = sdf.format((Date) o);

		} else if (o instanceof Calendar) {
			Calendar cal = (Calendar) o;
			dataFormatada = sdf.format(cal.getTime());
		} else if (o instanceof XMLGregorianCalendar) {
			XMLGregorianCalendar cal = (XMLGregorianCalendar) o;
			dataFormatada = sdf.format(cal.toGregorianCalendar().getTime());
		}
		return dataFormatada;
	}

	private String formatarCep(String cep) {
		cep = cep.replaceAll("\\.", "");
		cep = cep.replaceAll("-", "");
		cep = StringUtils.leftPad(cep, 8, "0");
		cep = format("#####-###", cep);
		return cep;
	}

	private String formatarCpf(String cpf) {
		cpf = cpf.replaceAll("\\.", "");
		cpf = cpf.replaceAll("-", "");
		cpf = StringUtils.leftPad(cpf, 11, "0");
		cpf = format("###.###.###-##", cpf);
		return cpf;
	}

	private String formatarCnpj(String cnpj) {
		cnpj = cnpj.replaceAll("\\.", "");
		cnpj = cnpj.replaceAll("-", "");
		cnpj = StringUtils.leftPad(cnpj, 14, "0");
		cnpj = format("##.###.###/####-##", cnpj);
		return cnpj;
	}
	
	private String formatarPorcentagem(String valorPorcentagem){
		BigDecimal bd = new BigDecimal(valorPorcentagem);
		return NumberFormat.getInstance().format(bd) +"%";
	}
	
	private String formatarValorMonetario(String valorMonetario){
		BigDecimal bd = new BigDecimal(valorMonetario);
		return NumberFormat.getCurrencyInstance().format(bd);
	}

	private String format(String pattern, Object value) {
		MaskFormatter mask;
		try {
			mask = new MaskFormatter(pattern);
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
