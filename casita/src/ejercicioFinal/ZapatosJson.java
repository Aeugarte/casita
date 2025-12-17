package ejercicioFinal;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

// Clase contenedora para mapear la raíz del JSON:
public class ZapatosJson {

    // Indica a Jackson que este campo corresponde a la clave "zapatos" del JSON
    @JsonProperty("zapatos")
    private List<Zapato> zapatos;

    // Getter y setter para que Jackson pueda rellenar la lista
    public List<Zapato> getZapatos() { return zapatos; }
    public void setZapatos(List<Zapato> zapatos) { this.zapatos = zapatos; }
}
