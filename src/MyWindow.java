import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Аlexander
 * on 10.01.2017.
 */

public class MyWindow extends JFrame {
    private JTable paintsTable;
    private JTextField widthSurfaceInAddTextField;
    private JTextField heightSurfaceInAddTextField;
    private DefaultTableModel paintsDefaultTableModel; // Модель таблицы
    private DefaultListModel<String> listPaintsModel; // Модель листа
    private JButton addSurfaceButton, saveButton, showResultButton, removeButton;
    private JComboBox<String> chooseTypePaintMaterialComboBox, chooseTypeSurfaceComboBox;
    private JList listPaintsName;
    private JFileChooser chooser;

    public MyWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("VGTCalc04");
        setLayout(new GridBagLayout());
        preapareModels();
        createGUI();
        addActions();
        pack();
        setVisible(true);
    }

    public void preapareModels() {
        paintsDefaultTableModel = new DefaultTableModel(new Object[]{"Поверхность", "Ширина, см", "Длина / Высота, см", "Материал", "Привязка"}, 0);
        listPaintsModel = new DefaultListModel<>();
    }

    public void createGUI() {
        widthSurfaceInAddTextField = new JTextField("500");
        heightSurfaceInAddTextField = new JTextField("500");
        chooser = new JFileChooser();
        // == Первая большая панель размещения: таблица, кнопки редактиования и удаления =================================
        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new GridBagLayout());
        paintsTable = new JTable(paintsDefaultTableModel) {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        JScrollPane scrollPanePaintsTable = new JScrollPane(paintsTable);
        JPanel editButtonsPanel = new JPanel(); // Панель с кнопками "Редактировать", "Удалить"
        removeButton = new JButton("Удалить строку");
        editButtonsPanel.add(removeButton);
        firstPanel.add(scrollPanePaintsTable, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 2, 2, 2), 0, 0));
        firstPanel.add(editButtonsPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));

        // == Вторая большая панель размещения: содержит панели "добавления" и "вычитания" поверхностей =================================
        JPanel secondPanel = new JPanel();
        JLabel squareSurface = new JLabel("Площадь обрабатываемой поверхности с учетом вычета проемов: ");
        JLabel paintMaterial = new JLabel("Материалы (наименование, фасовка, количество штук, стоимость за 1 штуку): ");
        JLabel costMaterial = new JLabel("Стоимость материала, итого: ");
        JLabel consumptionMaterial = new JLabel("Расход материала: ");
        secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.X_AXIS));
        // Панель с элементами "добавление поверхностей"
        JPanel addSurfacePanel = new JPanel();
        addSurfacePanel.setBorder(BorderFactory.createTitledBorder("Параметры добавляемых поверхностей"));
        addSurfacePanel.setLayout(new GridBagLayout());
        chooseTypeSurfaceComboBox = new JComboBox(new String[]{"Стена", "Потолок", "Пол", "Окно", "Дверь"});
        JLabel chosePaintMaterialJlabel = new JLabel("Выберете материал для нанесения");
        JLabel widthTitle = new JLabel("Ширина, см");
        JLabel heightTitle = new JLabel("Длина/Высота, см");
        String[] paintsName = DataBase.getPaintsTypes();
        chooseTypePaintMaterialComboBox = new JComboBox(paintsName);
        listPaintsName = new JList(listPaintsModel);
        saveButton = new JButton("Сохранить расчет в файл");
        JScrollPane listPaintsNameScrollPane = new JScrollPane(listPaintsName);
        JLabel numberOfCoatingsJLabel = new JLabel("Укажите количество слоев нанесения материала");
        JTextField numberOfCoatingsTextField = new JTextField("0", 4);
        addSurfaceButton = new JButton("Добавить поверхность");
        addSurfacePanel.add(chooseTypeSurfaceComboBox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 2, 2, 2), 0, 0));
        addSurfacePanel.add(widthTitle, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 2, 2, 2), 0, 0));
        addSurfacePanel.add(widthSurfaceInAddTextField, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 2, 2, 2), 0, 0));
        addSurfacePanel.add(heightTitle, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 2, 2, 2), 0, 0));
        addSurfacePanel.add(heightSurfaceInAddTextField, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 2, 2, 2), 0, 0));
        addSurfacePanel.add(chosePaintMaterialJlabel, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 2, 2, 2), 0, 0));
        addSurfacePanel.add(chooseTypePaintMaterialComboBox, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 2, 2, 2), 0, 0));
        addSurfacePanel.add(listPaintsNameScrollPane, new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 2, 2, 2), 0, 0));
        addSurfacePanel.add(numberOfCoatingsJLabel, new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 2, 2, 2), 0, 0));
        addSurfacePanel.add(numberOfCoatingsTextField, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 2, 2, 2), 0, 0));
        addSurfacePanel.add(addSurfaceButton, new GridBagConstraints(0, 5, 3, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 2, 2, 2), 0, 0));
        secondPanel.add(addSurfacePanel);

        // == Третья большая панель размещения: содержит результы вычислений и кнопку экспорта =================================
        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(new BoxLayout(thirdPanel, BoxLayout.Y_AXIS));
        showResultButton = new JButton("Показать результат");
        thirdPanel.add(squareSurface);
        thirdPanel.add(paintMaterial);
        thirdPanel.add(costMaterial);
        thirdPanel.add(consumptionMaterial);
        thirdPanel.add(showResultButton);
        thirdPanel.add(saveButton);

        // Менеджер размещения оснвных больших панелей
        add(firstPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 2, 2, 2), 0, 0));
        add(secondPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 2, 2, 2), 0, 0));
        add(thirdPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(2, 2, 2, 2), 0, 0));
    }

    public void addActions() {
        widthSurfaceInAddTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) { //  запрет на буквы и пробел, юзер сможет вводить только цифры:
                char keyChar = e.getKeyChar();
                if (keyChar < '0' || keyChar > '9') e.consume();  // игнорим введенные буквы и пробел
            }
        });

        heightSurfaceInAddTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (keyChar < '0' || keyChar > '9') e.consume();
            }
        });

        removeButton.addActionListener(e -> {
            try {
                int rowCount = paintsTable.getSelectedRow();
                paintsDefaultTableModel.removeRow(rowCount);
            } catch (Exception exs) {
                JOptionPane.showMessageDialog(null, "Не выбран жлемент для удаления");
            }
        });

        chooseTypeSurfaceComboBox.addActionListener(e -> {
            listPaintsName.setEnabled(true);
            chooseTypePaintMaterialComboBox.setEnabled(true);
            if (chooseTypeSurfaceComboBox.getSelectedItem().equals("Окно") || chooseTypeSurfaceComboBox.getSelectedItem().equals("Дверь")) {
                chooseTypePaintMaterialComboBox.setEnabled(false);
                listPaintsName.setEnabled(false);
                listPaintsName.clearSelection();
            }
        });

        chooseTypePaintMaterialComboBox.addActionListener(e -> {
            String[] strings = DataBase.getPaintsNamesByType((String) chooseTypePaintMaterialComboBox.getSelectedItem());
            listPaintsModel.clear();
            for (int i = 0; i < strings.length; i++) {
                listPaintsModel.addElement(strings[i]);
            }
        });

        saveButton.addActionListener(e -> {
            saveResultsToFile();
        });

        showResultButton.addActionListener(e -> {
            Calculator.resultCalc(paintsTable);
            // Calculator.calculteSecondVar(paintsDefaultTableModel);
        });

        addSurfaceButton.addActionListener(e -> {
            addElementToTable();
        });
    }

    public void addElementToTable() {
        try {
            String st1 = widthSurfaceInAddTextField.getText();
            String st2 = heightSurfaceInAddTextField.getText();
            String st3 = (String) chooseTypeSurfaceComboBox.getSelectedItem();
            String st4 = (String) listPaintsName.getSelectedValue();
            String st5 = "-";
            int n = 0;
            for (int i = 0; i < paintsDefaultTableModel.getRowCount(); i++) {// проходимся по каждой строке
                String s = (String) paintsDefaultTableModel.getValueAt(i, 0); // вытаскиваем из кажой строки таблицы тип элемента
                if (s.startsWith(st3)) { // если тип элемента таблицы начинается с типа выбранного элемента "типа поверхности"
                    n = Math.max(n, Integer.parseInt(s.split(" ")[1])); // взять из строки номер элемента
                }
            }
            n++;
            st3 += " " + n;
            if (st3.startsWith("Стена") || st3.startsWith("Потолок") || st3.startsWith("Пол")) {
                if (st4 == null) throw new RuntimeException("Не выбран материал");
            }
            if (st3.startsWith("Окно") || st3.startsWith("Дверь")) {
                int numberString = paintsTable.getSelectedRow();
                if (numberString == -1) throw new RuntimeException("Не выбран элемент привязки");
                String selectedCel = (String) paintsDefaultTableModel.getValueAt(numberString, 0);
                System.out.println(numberString);
                System.out.println(selectedCel);
                if (selectedCel.startsWith("Стена") || selectedCel.startsWith("Потолок") || selectedCel.startsWith("Пол")) {
                    st5 = selectedCel;
                }
                if (st5.equals("-")) throw new RuntimeException("Не верно выбран элемент привязки");
            }
            paintsDefaultTableModel.addRow(new Object[]{st3, st1, st2, st4, st5}); // добавление элементов (надо привязать к столцу в базе данных)
        } catch (RuntimeException exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage());
        }
    }

    public void saveResultsToFile() {
        int returnVal = chooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) { // запись в файл
            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(chooser.getSelectedFile()), "UTF-8"));
                for (int i = 0; i < paintsTable.getRowCount(); i++) {
                    for (int j = 0; j < paintsTable.getColumnCount(); j++) {
                        bw.write(paintsTable.getModel().getValueAt(i, j) + "\t");
                    }
                    bw.newLine();
                }
                bw.close();
                JOptionPane.showMessageDialog(null, "Данные сохранены");
            } catch (IOException iox) {
                iox.printStackTrace();
            }
        }
    }
}