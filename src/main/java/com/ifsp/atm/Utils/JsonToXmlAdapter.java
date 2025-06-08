package com.ifsp.atm.Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class JsonToXmlAdapter {
    public static String convert(String jsonString) throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        XmlMapper xmlMapper = new XmlMapper();

        // Melhorando a visualização do JSON
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Converte JSON para árvore JsonNode
        JsonNode jsonNode = jsonMapper.readTree(jsonString);

        // Converte JsonNode para XML
        return xmlMapper.writeValueAsString(jsonNode);
    }

    public static void main(String[] args) {
        String json = """
            {
                "pessoa": {
                    "nome": "João",
                    "idade": 30,
                    "cidade": "São Paulo",
                    "Genero": "Masculino",
                    "Estado civil" : "solteiro"
  
                }
            }
        """;

        try {
            String xml = convert(json);
            System.out.println("XML gerado:\n" + xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

