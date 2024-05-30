import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaPaqueteria {
    private JPanel ventana;
    private JTabbedPane tabbedPane1;
    private JSpinner spinner1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField textField1;
    private JTextField textField2;
    private JButton agregarButton;
    private JButton totalPaquetesButton;
    private JComboBox comboBox3;
    private JButton totalPesoPorCiudadButton;
    private JButton totalPesosButton;
    private JList list1;
    private JButton modificarButton;
    private JTextField textField3;
    private JButton modificarEstadoDelPaqueteButton;
    private JButton ordenarPorBurbujaButton;
    private JButton ordenarPorInserciónButton;
    private JList list2;
    private JList list3;
    private JComboBox comboBox4;
    private JButton calcularButton;
    private JTextArea textArea1;

    private Lista paquetes = new Lista();

    public VentanaPaqueteria() {
        quemarDatos();
        llenarJlist();
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    paquetes.adicionarElementos(new Paqueteria(Integer.parseInt(spinner1.getValue().toString()),
                            Double.parseDouble(textField1.getText()), comboBox1.getSelectedItem().toString(),
                            comboBox2.getSelectedItem().toString(), textField2.getText()));
                    JOptionPane.showMessageDialog(null, "Paquete agregado");
                    llenarJlist();
                    limpiarDatos();
                    System.out.println(paquetes.listarPaquetes());
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list1.getSelectedIndex() != -1) {
                    Paqueteria paqueteSeleccionado = (Paqueteria) list1.getSelectedValue();
                    int tracking = paqueteSeleccionado.getTracking();

                    double nuevoPeso = Double.parseDouble(textField1.getText());
                    String nuevaCiudadRecepcion = comboBox1.getSelectedItem().toString();
                    String nuevaCiudadEntrega = comboBox2.getSelectedItem().toString();
                    String nuevaCedula = textField2.getText();

                    try {
                        paquetes.editar(tracking, nuevoPeso, nuevaCiudadRecepcion, nuevaCiudadEntrega, nuevaCedula);
                        Paqueteria lista = paquetes.buscarPaquete(tracking);
                        llenarJlist();
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });



        totalPaquetesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "El total de paquetes es: "+ paquetes.sumarTotalPaquetes());
            }
        });

        totalPesosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"El peso total de los paquetes es:  "+paquetes.sumarTotalPeso());
            }
        });

        totalPesoPorCiudadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "El total de peso de la ciudad seleccionada es: "+paquetes.sumarTotalPesoCiudad(comboBox3.getSelectedItem().toString()));
            }
        });



        calcularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String estadoSeleccionado = comboBox4.getSelectedItem().toString();
                    int totalPaquetesEstado = paquetes.sumarTotalPaquetesEstado(estadoSeleccionado);
                    JOptionPane.showMessageDialog(null, "Total de paquetes en estado '" + estadoSeleccionado + "': " + totalPaquetesEstado);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al calcular el total de paquetes: " + ex.getMessage());
                }
            }
        });

        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(list1.getSelectedIndex()!=-1) {
                    int indice = list1.getSelectedIndex();
                    Paqueteria pa = paquetes.getServiEntrega().get(indice);
                    spinner1.setValue(pa.getTracking());
                    textField1.setText(String.valueOf(pa.getPeso()));
                    comboBox1.setSelectedItem(pa.getCiudadEntrega());
                    comboBox2.setSelectedItem(pa.getCiudadRecepcion());
                    textField2.setText(pa.getCedulaReceptor());
                }
            }
        });

        modificarEstadoDelPaqueteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int tracking = Integer.parseInt(textField3.getText());
                    String nuevoEstado = paquetes.estadoPaquete(tracking);
                    if (nuevoEstado != null) {
                        switch (nuevoEstado) {
                            case "Enviado":
                                JOptionPane.showMessageDialog(null, "El paquete con tracking " + tracking + " ha sido enviado.");
                                break;
                            case "Recibido":
                                JOptionPane.showMessageDialog(null, "El paquete con tracking " + tracking + " ha sido recibido.");
                                break;
                            case "Finalizado":
                                JOptionPane.showMessageDialog(null, "El paquete con tracking " + tracking + " ha finalizado su proceso de envío.");
                                break;
                        }
                        llenarJlist();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número de tracking válido.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (Exception ex) {
                }
            }
        });



        ordenarPorBurbujaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                llenarJlistOriginal();
                llenarJlistTracking();
            }
        });

        ordenarPorInserciónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                llenarJlistOriginal();
                llenasJlistPeso();
            }
        });

    }

    public void limpiarDatos(){
        spinner1.setValue(0);
        textField1.setText("");
        comboBox1.setSelectedIndex(0);
        comboBox2.setSelectedIndex(0);
        textField2.setText("");
    }

    public void quemarDatos() {
        try {
            paquetes.adicionarElementos(new Paqueteria(5, 25, "Quito", "Guayaquil", "11111111"));
            paquetes.adicionarElementos(new Paqueteria(6, 15, "Cuenca", "Riobamba", "22222222"));
            paquetes.adicionarElementos(new Paqueteria(7, 55, "Guayaquil", "Ibarra", "33333333"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void llenarJlist(){
        DefaultListModel dlm = new DefaultListModel<>();
        dlm.removeAllElements();
        for (Paqueteria p: paquetes.getServiEntrega())
            dlm.addElement(p);
        list1.setModel(dlm);
    }

    public void llenarJlistOriginal(){
        DefaultListModel dlm = new DefaultListModel<>();
        dlm.removeAllElements();
        for (Paqueteria p: paquetes.getServiEntrega())
            dlm.addElement(p);
        list2.setModel(dlm);
    }

    public void llenarJlistTracking() {
        DefaultListModel dlm2 = new DefaultListModel();
        List<Paqueteria> lista = paquetes.ordenarPaquetesPorTracking();
        for (Paqueteria p : lista) {
            dlm2.addElement(p);
        }
        list3.setModel(dlm2);
    }

    public void llenasJlistPeso() {
        DefaultListModel dlmOrdenada = new DefaultListModel();
        List<Paqueteria> sortedList = paquetes.ordenarPaquetesPorPeso();
        for (Paqueteria p : sortedList) {
            dlmOrdenada.addElement(p);
        }
        list3.setModel(dlmOrdenada);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaPaqueteria");
        frame.setContentPane(new VentanaPaqueteria().ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,600);
        frame.pack();
        frame.setVisible(true);
    }

}
