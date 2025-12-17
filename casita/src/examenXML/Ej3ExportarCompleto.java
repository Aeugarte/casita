// ===== EJ3EXPORTARCOMPLETO.JAVA ===== (TXT + JSON + CSV + XML)
package examenXML;

import java.sql.*;
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Ej3ExportarCompleto {
    public static void main(String[] args) {
        Connection conn = CnxCursoBD.conectar();
        
        try {
            conn.setCatalog("BDCursos");
            System.out.println("🔄 Exportando datos de tabla 'curso'...");
            
            // 1. OBTENER DATOS DE LA BD
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM curso");
            List<Curso> cursos = new ArrayList<>();
            
            while(rs.next()) {
                Curso c = new Curso();
                c.setNombre(rs.getString("nombre"));
                c.setProfesor(rs.getString("profesor"));
                c.setHoras(rs.getInt("horas"));
                c.setPrecio(rs.getDouble("precio"));
                if(rs.getString("modalidad") != null) c.setModalidad(rs.getString("modalidad"));
                cursos.add(c);
            }
            System.out.println("📊 " + cursos.size() + " cursos leídos de BD");
            
            // ========================================
            // 2. EXPORTAR TXT (formato ; separado)
            // ========================================
            System.out.println("📝 Exportando TXT...");
            PrintWriter pwTxt = new PrintWriter("cursos.txt");
            for(Curso c : cursos) {
                pwTxt.print(c.getNombre() + ";" + 
                           c.getProfesor() + ";" + 
                           c.getHoras() + ";" + 
                           c.getPrecio());
                if(c.getModalidad() != null) pwTxt.print(";" + c.getModalidad());
                pwTxt.println(); // Nueva línea
            }
            pwTxt.close();
            System.out.println("✅ cursos.txt CREADO ✓");
            
            // ========================================
            // 3. EXPORTAR JSON (Jackson)
            // ========================================
            System.out.println("📄 Exportando JSON...");
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File("cursos.json"), cursos);
            System.out.println("✅ cursos.json CREADO ✓");
            
            // ========================================
            // 4. EXPORTAR CSV (formato comas)
            // ========================================
            System.out.println("📊 Exportando CSV...");
            PrintWriter pwCsv = new PrintWriter("cursos.csv");
            pwCsv.println("nombre,profesor,horas,precio,modalidad"); // CABECERA
            for(Curso c : cursos) {
                pwCsv.print("\"" + c.getNombre() + "\"," +
                           "\"" + c.getProfesor() + "\"," +
                           c.getHoras() + "," +
                           c.getPrecio());
                if(c.getModalidad() != null) pwCsv.print("," + "\"" + c.getModalidad() + "\"");
                pwCsv.println();
            }
            pwCsv.close();
            System.out.println("✅ cursos.csv CREADO ✓");
            
            // ========================================
            // 5. EXPORTAR XML (DOM manual)
            // ========================================
            System.out.println("📜 Exportando XML...");
            
            // Crear DOM vacío
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document doc = db.newDocument();
            doc.setXmlVersion("1.0");
            
            // Elemento raíz <cursos>
            Element raiz = doc.createElement("cursos");
            doc.appendChild(raiz);
            
            // Por cada curso → <curso><nombre>...</nombre></curso>
            for(Curso c : cursos) {
                Element cursoElem = doc.createElement("curso");
                raiz.appendChild(cursoElem);
                
                Element nombreElem = doc.createElement("nombre");
                nombreElem.setTextContent(c.getNombre());
                cursoElem.appendChild(nombreElem);
                
                Element profElem = doc.createElement("profesor");
                profElem.setTextContent(c.getProfesor());
                cursoElem.appendChild(profElem);
                
                Element horasElem = doc.createElement("horas");
                horasElem.setTextContent(String.valueOf(c.getHoras()));
                cursoElem.appendChild(horasElem);
                
                Element precioElem = doc.createElement("precio");
                precioElem.setTextContent(String.valueOf(c.getPrecio()));
                cursoElem.appendChild(precioElem);
                
                if(c.getModalidad() != null) {
                    Element modElem = doc.createElement("modalidad");
                    modElem.setTextContent(c.getModalidad());
                    cursoElem.appendChild(modElem);
                }
            }
            
            // Guardar XML
            DOMSource fuente = new DOMSource(doc);
            StreamResult resultado = new StreamResult(new File("cursos.xml"));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(fuente, resultado);
            System.out.println("✅ cursos.xml CREADO ✓");
            
            System.out.println("\n🎉 ¡TODOS LOS ARCHIVOS EXPORTADOS!");
            System.out.println("📋 Archivos creados:");
            System.out.println("   → cursos.txt (separador ;) ");
            System.out.println("   → cursos.csv (separador , con cabecera)");
            System.out.println("   → cursos.json (Jackson indentado)");
            System.out.println("   → cursos.xml (DOM estructurado)");
            System.out.println("\n💾 Recordar adjuntar BD BDCursos al proyecto");
            
        } catch(Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if(conn != null) conn.close(); } catch(Exception e) {}
        }
    }
}
