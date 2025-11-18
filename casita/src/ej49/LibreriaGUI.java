package ej49;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibreriaGUI extends JFrame {
    private Libreria libreria;
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    private JTextField txtTitulo, txtAutor, txtEditorial, txtISBN;
    private JButton btnAgregar, btnModificar, btnEliminar, btnGuardar, btnLimpiar;

    public LibreriaGUI() {
        libreria = new Libreria();
        inicializarComponentes();
        configurarVentana();
        cargarDatosEnTabla();
    }

    private void inicializarComponentes() {
        // Configuración de la ventana principal
        setTitle("Librería Paqui - Gestión de Libros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel de entrada de datos
        JPanel panelEntrada = crearPanelEntrada();
        add(panelEntrada, BorderLayout.NORTH);

        // Panel de la tabla
        JPanel panelTabla = crearPanelTabla();
        add(panelTabla, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelEntrada() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Libro"));

        // Crear componentes
        JLabel lblTitulo = new JLabel("Título:");
        txtTitulo = new JTextField();
        
        JLabel lblAutor = new JLabel("Autor:");
        txtAutor = new JTextField();
        
        JLabel lblEditorial = new JLabel("Editorial:");
        txtEditorial = new JTextField();
        
        JLabel lblISBN = new JLabel("ISBN:");
        txtISBN = new JTextField();

        // Agregar componentes al panel
        panel.add(lblTitulo);
        panel.add(txtTitulo);
        panel.add(lblAutor);
        panel.add(txtAutor);
        panel.add(lblEditorial);
        panel.add(txtEditorial);
        panel.add(lblISBN);
        panel.add(txtISBN);
        
        // Espacio vacío para alineación
        panel.add(new JLabel());
        panel.add(new JLabel());

        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Libros"));

        // Crear modelo de tabla
        String[] columnas = {"Título", "Autor", "Editorial", "ISBN"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaLibros = new JTable(modeloTabla);
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Agregar listener para cargar datos en los campos cuando se selecciona una fila
        tablaLibros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaLibros.getSelectedRow() != -1) {
                cargarDatosDesdeTabla();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        // Crear botones
        btnAgregar = new JButton("Agregar Libro");
        btnModificar = new JButton("Modificar Libro");
        btnEliminar = new JButton("Eliminar Libro");
        btnGuardar = new JButton("Guardar Cambios");
        btnLimpiar = new JButton("Limpiar Campos");

        // Agregar listeners
        btnAgregar.addActionListener(e -> agregarLibro());
        btnModificar.addActionListener(e -> modificarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnGuardar.addActionListener(e -> guardarCambios());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        // Agregar botones al panel
        panel.add(btnAgregar);
        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(btnGuardar);
        panel.add(btnLimpiar);

        return panel;
    }

    private void configurarVentana() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cargarDatosEnTabla() {
        modeloTabla.setRowCount(0);
        
        for (Libro libro : libreria.getLibros()) {
            Object[] fila = {
                libro.getTitulo(),
                libro.getAutor(),
                libro.getEditorial(),
                libro.getIsbn()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void cargarDatosDesdeTabla() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada != -1) {
            txtTitulo.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtAutor.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtEditorial.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtISBN.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
        }
    }

    private void agregarLibro() {
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String editorial = txtEditorial.getText().trim();
        String isbn = txtISBN.getText().trim();

        if (titulo.isEmpty() || autor.isEmpty() || editorial.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Todos los campos son obligatorios.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        Libro nuevoLibro = new Libro(titulo, autor, editorial, isbn);
        if (libreria.agregarLibro(nuevoLibro)) {
            cargarDatosEnTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, 
                "Libro agregado correctamente.", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error: Ya existe un libro con ese ISBN.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarLibro() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un libro de la tabla para modificar.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String isbnOriginal = modeloTabla.getValueAt(filaSeleccionada, 3).toString();
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String editorial = txtEditorial.getText().trim();
        String isbn = txtISBN.getText().trim();

        if (titulo.isEmpty() || autor.isEmpty() || editorial.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Todos los campos son obligatorios.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (libreria.modificarLibro(isbnOriginal, titulo, autor, editorial)) {
            cargarDatosEnTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, 
                "Libro modificado correctamente.", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error al modificar el libro.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarLibro() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un libro de la tabla para eliminar.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String isbn = modeloTabla.getValueAt(filaSeleccionada, 3).toString();
        String titulo = modeloTabla.getValueAt(filaSeleccionada, 0).toString();

        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de que desea eliminar el libro:\n" + titulo + "?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (libreria.borrarLibro(isbn)) {
                cargarDatosEnTabla();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, 
                    "Libro eliminado correctamente.", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar el libro.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarCambios() {
        libreria.guardar();
        JOptionPane.showMessageDialog(this, 
            "Datos guardados correctamente en " + Libreria.ARCHIVO, // Ahora funciona
            "Guardado Exitoso", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarCampos() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtEditorial.setText("");
        txtISBN.setText("");
        tablaLibros.clearSelection();
    }

    public static void main(String[] args) {
        // Establecer el look and feel del sistema - VERSIÓN CORREGIDA
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | 
                 InstantiationException | IllegalAccessException e) {
            // Si falla, usar el look and feel por defecto
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Ejecutar la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new LibreriaGUI();
        });
    }
}