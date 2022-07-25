package aniversario.davi.hamburguer.interfaces;

import java.text.Normalizer;
import java.util.Locale;

public interface Impressora {

    static String somenteNumeros(String texto) {

        texto = texto.replaceAll("\\p{Punct}", "");

        StringBuilder numeros = new StringBuilder();
        char[] caracteres = texto.toCharArray();
        for (char caracter : caracteres) {
            if(Character.isDigit(caracter)) {
                numeros.append(caracter);
            }
        }
        if (numeros.length() == 0)
            return null;
        return numeros.toString();
    }

    static String removerAcentos(String str) {
        String normalizado = Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        normalizado = normalizado.replaceAll("�","");
        return normalizado;
    }
}
