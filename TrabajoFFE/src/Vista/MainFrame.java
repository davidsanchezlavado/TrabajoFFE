package Vista;

import Controlador.PlataformaControlador;
import Controlador.SerieControlador;
import Modelo.Plataforma;
import Modelo.Serie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame {

    private PlataformaControlador plataformaControlador;
    private SerieControlador serieControlador;

    // Componentes para la gestión de Plataformas
    private JPanel panelPlataformas;
    private JTable tablaPlataformas;
    private DefaultTableModel modeloTablaPlataformas;
    private JTextField txtPlataformaId;
    private JTextField txtPlataformaNombre;
    private JTextField txtPlataformaPais;
    private JButton btnPlataformaAgregar;
    private JButton btnPlataformaActualizar;
    private JButton btnPlataformaEliminar;
    private JButton btnPlataformaLimpiar;

    // Componentes para la gestión de Series
    private JPanel panelSeries;
    private JTable tablaSeries;
    private DefaultTableModel modeloTablaSeries;
    private JTextField txtSerieId;
    private JTextField txtSerieTitulo;
    private JTextField txtSerieGenero;
    private JTextField txtSerieNumTemporadas;
    private JTextField txtSerieAnoLanzamiento;
    private JComboBox<Plataforma> cbSeriePlataforma; // ComboBox para seleccionar Plataforma
    private JButton btnSerieAgregar;
    private JButton btnSerieActualizar;
    private JButton btnSerieEliminar;
    private JButton btnSerieLimpiar;
    private JTextField txtSerieBuscarTitulo;
    private JTextField txtSerieBuscarGenero;
    private JComboBox<Plataforma> cbSerieBuscarPlataforma;
    private JButton btnSerieBuscar;
    private JButton btnSerieMostrarTodo;


    public MainFrame() {
        super("Gestor de Series y Plataformas de Streaming");
        this.plataformaControlador = new PlataformaControlador();
        this.serieControlador = new SerieControlador();

        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        initComponents();
        cargarDatosIniciales();
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // --- Panel de Plataformas ---
        panelPlataformas = new JPanel(new BorderLayout(10, 10));
        panelPlataformas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Formulario de Plataformas
        JPanel formPlataforma = new JPanel(new GridLayout(4, 2, 5, 5));
        formPlataforma.setBorder(BorderFactory.createTitledBorder("Datos de Plataforma"));

        txtPlataformaId = new JTextField();
        txtPlataformaId.setEditable(false);
        txtPlataformaNombre = new JTextField();
        txtPlataformaPais = new JTextField();

        formPlataforma.add(new JLabel("ID:"));
        formPlataforma.add(txtPlataformaId);
        formPlataforma.add(new JLabel("Nombre:"));
        formPlataforma.add(txtPlataformaNombre);
        formPlataforma.add(new JLabel("País de Origen:"));
        formPlataforma.add(txtPlataformaPais);

        panelPlataformas.add(formPlataforma, BorderLayout.NORTH);

        // Botones de Plataformas
        JPanel panelBotonesPlataforma = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPlataformaAgregar = new JButton("Agregar");
        btnPlataformaActualizar = new JButton("Actualizar");
        btnPlataformaEliminar = new JButton("Eliminar");
        btnPlataformaLimpiar = new JButton("Limpiar");

        panelBotonesPlataforma.add(btnPlataformaAgregar);
        panelBotonesPlataforma.add(btnPlataformaActualizar);
        panelBotonesPlataforma.add(btnPlataformaEliminar);
        panelBotonesPlataforma.add(btnPlataformaLimpiar);

        panelPlataformas.add(panelBotonesPlataforma, BorderLayout.SOUTH);

        // Tabla de Plataformas
        modeloTablaPlataformas = new DefaultTableModel(new Object[]{"ID", "Nombre", "País de Origen"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que las celdas de la tabla no sean editables directamente
            }
        };
        tablaPlataformas = new JTable(modeloTablaPlataformas);
        tablaPlataformas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Solo una fila seleccionada a la vez
        JScrollPane scrollPlataformas = new JScrollPane(tablaPlataformas);
        panelPlataformas.add(scrollPlataformas, BorderLayout.CENTER);


        // --- Panel de Series ---
        panelSeries = new JPanel(new BorderLayout(10, 10));
        panelSeries.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Formulario de Series
        JPanel formSerie = new JPanel(new GridLayout(6, 2, 5, 5));
        formSerie.setBorder(BorderFactory.createTitledBorder("Datos de Serie"));

        txtSerieId = new JTextField();
        txtSerieId.setEditable(false);
        txtSerieTitulo = new JTextField();
        txtSerieGenero = new JTextField();
        txtSerieNumTemporadas = new JTextField();
        txtSerieAnoLanzamiento = new JTextField();
        cbSeriePlataforma = new JComboBox<>();

        formSerie.add(new JLabel("ID:"));
        formSerie.add(txtSerieId);
        formSerie.add(new JLabel("Título:"));
        formSerie.add(txtSerieTitulo);
        formSerie.add(new JLabel("Género:"));
        formSerie.add(txtSerieGenero);
        formSerie.add(new JLabel("Nº Temporadas:"));
        formSerie.add(txtSerieNumTemporadas);
        formSerie.add(new JLabel("Año Lanzamiento:"));
        formSerie.add(txtSerieAnoLanzamiento);
        formSerie.add(new JLabel("Plataforma:"));
        formSerie.add(cbSeriePlataforma);

        // Panel de búsqueda de Series
        JPanel panelBusquedaSerie = new JPanel(new GridLayout(1, 7, 5, 5));
        panelBusquedaSerie.setBorder(BorderFactory.createTitledBorder("Buscar Series"));
        txtSerieBuscarTitulo = new JTextField();
        txtSerieBuscarGenero = new JTextField();
        cbSerieBuscarPlataforma = new JComboBox<>();
        btnSerieBuscar = new JButton("Buscar");
        btnSerieMostrarTodo = new JButton("Mostrar Todo");

        panelBusquedaSerie.add(new JLabel("Título:"));
        panelBusquedaSerie.add(txtSerieBuscarTitulo);
        panelBusquedaSerie.add(new JLabel("Género:"));
        panelBusquedaSerie.add(txtSerieBuscarGenero);
        panelBusquedaSerie.add(new JLabel("Plataforma:"));
        panelBusquedaSerie.add(cbSerieBuscarPlataforma);
        panelBusquedaSerie.add(btnSerieBuscar);
        panelBusquedaSerie.add(btnSerieMostrarTodo);


        JPanel panelNorteSeries = new JPanel(new BorderLayout());
        panelNorteSeries.add(formSerie, BorderLayout.NORTH);
        panelNorteSeries.add(panelBusquedaSerie, BorderLayout.SOUTH);

        panelSeries.add(panelNorteSeries, BorderLayout.NORTH);

        // Botones de Series
        JPanel panelBotonesSerie = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnSerieAgregar = new JButton("Agregar");
        btnSerieActualizar = new JButton("Actualizar");
        btnSerieEliminar = new JButton("Eliminar");
        btnSerieLimpiar = new JButton("Limpiar");

        panelBotonesSerie.add(btnSerieAgregar);
        panelBotonesSerie.add(btnSerieActualizar);
        panelBotonesSerie.add(btnSerieEliminar);
        panelBotonesSerie.add(btnSerieLimpiar);

        panelSeries.add(panelBotonesSerie, BorderLayout.SOUTH);

        // Tabla de Series
        modeloTablaSeries = new DefaultTableModel(new Object[]{"ID", "Título", "Género", "Temporadas", "Año", "Plataforma"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaSeries = new JTable(modeloTablaSeries);
        tablaSeries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollSeries = new JScrollPane(tablaSeries);
        panelSeries.add(scrollSeries, BorderLayout.CENTER);


        // Añadir paneles al JTabbedPane
        tabbedPane.addTab("Plataformas", panelPlataformas);
        tabbedPane.addTab("Series", panelSeries);

        add(tabbedPane, BorderLayout.CENTER);

        // --- Listeners ---
        addPlataformaListeners();
        addSerieListeners();
        addTableSelectionListeners();
    }

    private void addPlataformaListeners() {
        btnPlataformaAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPlataforma();
            }
        });

        btnPlataformaActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPlataforma();
            }
        });

        btnPlataformaEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPlataforma();
            }
        });

        btnPlataformaLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCamposPlataforma();
            }
        });
    }

    private void addSerieListeners() {
        btnSerieAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarSerie();
            }
        });

        btnSerieActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarSerie();
            }
        });

        btnSerieEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarSerie();
            }
        });

        btnSerieLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCamposSerie();
            }
        });

        btnSerieBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarSeries();
            }
        });

        btnSerieMostrarTodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarSeries();
            }
        });
    }

    private void addTableSelectionListeners() {
        tablaPlataformas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaPlataformas.getSelectedRow() != -1) {
                int selectedRow = tablaPlataformas.getSelectedRow();
                txtPlataformaId.setText(modeloTablaPlataformas.getValueAt(selectedRow, 0).toString());
                txtPlataformaNombre.setText(modeloTablaPlataformas.getValueAt(selectedRow, 1).toString());
                txtPlataformaPais.setText(modeloTablaPlataformas.getValueAt(selectedRow, 2) != null ? modeloTablaPlataformas.getValueAt(selectedRow, 2).toString() : "");
            }
        });

        tablaSeries.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaSeries.getSelectedRow() != -1) {
                int selectedRow = tablaSeries.getSelectedRow();
                txtSerieId.setText(modeloTablaSeries.getValueAt(selectedRow, 0).toString());
                txtSerieTitulo.setText(modeloTablaSeries.getValueAt(selectedRow, 1).toString());
                txtSerieGenero.setText(modeloTablaSeries.getValueAt(selectedRow, 2).toString());
                txtSerieNumTemporadas.setText(modeloTablaSeries.getValueAt(selectedRow, 3).toString());
                txtSerieAnoLanzamiento.setText(modeloTablaSeries.getValueAt(selectedRow, 4).toString());

                // Seleccionar la plataforma en el ComboBox
                String plataformaNombre = (String) modeloTablaSeries.getValueAt(selectedRow, 5);
                for (int i = 0; i < cbSeriePlataforma.getItemCount(); i++) {
                    Plataforma p = (Plataforma) cbSeriePlataforma.getItemAt(i);
                    if (p.getNombre().equals(plataformaNombre)) {
                        cbSeriePlataforma.setSelectedItem(p);
                        break;
                    }
                }
            }
        });
    }

    // --- Métodos de carga de datos ---
    private void cargarDatosIniciales() {
        cargarPlataformas(); // Este es el método que debería cargar Netflix
        cargarPlataformasEnComboBox(); // Y este el que debería cargarla en los ComboBoxes
        cargarSeries();
    }

    private void cargarPlataformas() {
        modeloTablaPlataformas.setRowCount(0); // Limpiar tabla
        List<Plataforma> plataformas = plataformaControlador.obtenerTodasLasPlataformas(); // <-- Aquí se obtienen
        System.out.println("Plataformas cargadas desde DB: " + plataformas.size()); // Añade esta línea para depuración
        for (Plataforma p : plataformas) {
            modeloTablaPlataformas.addRow(new Object[]{p.getId(), p.getNombre(), p.getPaisOrigen()}); // <-- Aquí se añaden
        }
        limpiarCamposPlataforma(); // Limpiar campos después de cargar
    }

    private void cargarPlataformasEnComboBox() {
        cbSeriePlataforma.removeAllItems();
        cbSerieBuscarPlataforma.removeAllItems();

        cbSeriePlataforma.addItem(new Plataforma(0, "Seleccionar Plataforma", ""));
        cbSerieBuscarPlataforma.addItem(new Plataforma(0, "Todas las Plataformas", ""));

        List<Plataforma> plataformas = plataformaControlador.obtenerTodasLasPlataformas(); // <-- También aquí
        System.out.println("Plataformas cargadas para ComboBox: " + plataformas.size()); // Añade esta línea
        for (Plataforma p : plataformas) {
            cbSeriePlataforma.addItem(p);
            cbSerieBuscarPlataforma.addItem(p);
        }
    }

    private void cargarSeries() {
        modeloTablaSeries.setRowCount(0); // Limpiar tabla
        List<Serie> series = serieControlador.obtenerTodasLasSeries();
        for (Serie s : series) {
            String plataformaNombre = (s.getPlataforma() != null) ? s.getPlataforma().getNombre() : "N/A";
            modeloTablaSeries.addRow(new Object[]{s.getId(), s.getTitulo(), s.getGenero(),
                                                 s.getNumTemporadas(), s.getAnoLanzamiento(), plataformaNombre});
        }
        limpiarCamposSerie(); // Limpiar campos después de cargar
    }

    // --- Métodos de gestión de Plataformas (CRUD) ---
    private void agregarPlataforma() {
        String nombre = txtPlataformaNombre.getText();
        String pais = txtPlataformaPais.getText();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de la plataforma es obligatorio.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (plataformaControlador.agregarPlataforma(nombre, pais)) {
            JOptionPane.showMessageDialog(this, "Plataforma agregada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarPlataformas();
            cargarPlataformasEnComboBox(); // Recargar ComboBoxes
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar plataforma. Revise los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarPlataforma() {
        String idStr = txtPlataformaId.getText();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una plataforma para actualizar.", "Error de Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(idStr);
        String nombre = txtPlataformaNombre.getText();
        String pais = txtPlataformaPais.getText();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de la plataforma es obligatorio.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (plataformaControlador.actualizarPlataforma(id, nombre, pais)) {
            JOptionPane.showMessageDialog(this, "Plataforma actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarPlataformas();
            cargarPlataformasEnComboBox(); // Recargar ComboBoxes
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar plataforma.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPlataforma() {
        String idStr = txtPlataformaId.getText();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una plataforma para eliminar.", "Error de Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(idStr);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar esta plataforma?\n(Las series asociadas perderán su plataforma)", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (plataformaControlador.eliminarPlataforma(id)) {
                JOptionPane.showMessageDialog(this, "Plataforma eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarPlataformas();
                cargarPlataformasEnComboBox(); // Recargar ComboBoxes
                cargarSeries(); // Recargar series ya que pueden haber cambiado por la eliminación de la plataforma
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar plataforma.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCamposPlataforma() {
        txtPlataformaId.setText("");
        txtPlataformaNombre.setText("");
        txtPlataformaPais.setText("");
        tablaPlataformas.clearSelection(); // Deseleccionar cualquier fila
    }

    // --- Métodos de gestión de Series (CRUD y Búsqueda) ---
    private void agregarSerie() {
        String titulo = txtSerieTitulo.getText();
        String genero = txtSerieGenero.getText();
        int numTemporadas;
        int anoLanzamiento;
        Plataforma selectedPlataforma = (Plataforma) cbSeriePlataforma.getSelectedItem();
        int idPlataforma = (selectedPlataforma != null) ? selectedPlataforma.getId() : 0;

        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título de la serie es obligatorio.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            numTemporadas = Integer.parseInt(txtSerieNumTemporadas.getText());
            if (numTemporadas <= 0) {
                JOptionPane.showMessageDialog(this, "Número de temporadas debe ser un valor positivo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número de temporadas no es válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            anoLanzamiento = Integer.parseInt(txtSerieAnoLanzamiento.getText());
            if (anoLanzamiento < 1900 || anoLanzamiento > 2100) { // Rango razonable
                JOptionPane.showMessageDialog(this, "Año de lanzamiento no es válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Año de lanzamiento no es válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (idPlataforma == 0) { // Si no se seleccionó ninguna plataforma válida
            JOptionPane.showMessageDialog(this, "Debe seleccionar una plataforma para la serie.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }


        if (serieControlador.agregarSerie(titulo, genero, numTemporadas, anoLanzamiento, idPlataforma)) {
            JOptionPane.showMessageDialog(this, "Serie agregada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarSeries();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar serie. Revise los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarSerie() {
        String idStr = txtSerieId.getText();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una serie para actualizar.", "Error de Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(idStr);
        String titulo = txtSerieTitulo.getText();
        String genero = txtSerieGenero.getText();
        int numTemporadas;
        int anoLanzamiento;
        Plataforma selectedPlataforma = (Plataforma) cbSeriePlataforma.getSelectedItem();
        int idPlataforma = (selectedPlataforma != null) ? selectedPlataforma.getId() : 0;

        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título de la serie es obligatorio.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            numTemporadas = Integer.parseInt(txtSerieNumTemporadas.getText());
            if (numTemporadas <= 0) {
                JOptionPane.showMessageDialog(this, "Número de temporadas debe ser un valor positivo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número de temporadas no es válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            anoLanzamiento = Integer.parseInt(txtSerieAnoLanzamiento.getText());
            if (anoLanzamiento < 1900 || anoLanzamiento > 2100) {
                JOptionPane.showMessageDialog(this, "Año de lanzamiento no es válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Año de lanzamiento no es válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (idPlataforma == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una plataforma para la serie.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (serieControlador.actualizarSerie(id, titulo, genero, numTemporadas, anoLanzamiento, idPlataforma)) {
            JOptionPane.showMessageDialog(this, "Serie actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarSeries();
        } else {            JOptionPane.showMessageDialog(this, "Error al actualizar serie. Revise los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarSerie() {
        String idStr = txtSerieId.getText();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una serie para eliminar.", "Error de Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(idStr);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar esta serie?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (serieControlador.eliminarSerie(id)) {
                JOptionPane.showMessageDialog(this, "Serie eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarSeries();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar serie.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCamposSerie() {
        txtSerieId.setText("");
        txtSerieTitulo.setText("");
        txtSerieGenero.setText("");
        txtSerieNumTemporadas.setText("");
        txtSerieAnoLanzamiento.setText("");
        cbSeriePlataforma.setSelectedIndex(0); // Seleccionar "Seleccionar Plataforma"
        tablaSeries.clearSelection();
    }

    private void buscarSeries() {
        String titulo = txtSerieBuscarTitulo.getText();
        String genero = txtSerieBuscarGenero.getText();
        Plataforma selectedPlataforma = (Plataforma) cbSerieBuscarPlataforma.getSelectedItem();
        int idPlataforma = (selectedPlataforma != null && selectedPlataforma.getId() != 0) ? selectedPlataforma.getId() : 0; // 0 para "Todas las Plataformas"

        List<Serie> seriesEncontradas = serieControlador.buscarSeries(titulo, genero, idPlataforma);

        modeloTablaSeries.setRowCount(0); // Limpiar tabla
        if (seriesEncontradas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron series con los filtros especificados.", "Búsqueda de Series", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Serie s : seriesEncontradas) {
                String plataformaNombre = (s.getPlataforma() != null) ? s.getPlataforma().getNombre() : "N/A";
                modeloTablaSeries.addRow(new Object[]{s.getId(), s.getTitulo(), s.getGenero(),
                                                     s.getNumTemporadas(), s.getAnoLanzamiento(), plataformaNombre});
            }
        }
    }
}
