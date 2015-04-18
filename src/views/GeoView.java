/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.GeoController;
import controllers.IController;
import helps.ISubject;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import static models.GeoModel.Address;

/**
 *
 * @author Obaro
 */
public class GeoView extends JFrame implements ISubject {

    private GeoController controller;

    private JLabel lblSearchAddress;
    private JTextField txtSearchAddress;
    private JButton btnSearchAddress;
    private JTable table;
    private JScrollPane jscrpanel;
    private JPanel tblPanel;
    private TableModel listModel;
    private EventHandler eventHandler;
    
    
    //location informations
    
    private JLabel lblCurrrentLocation;
    private JTextField txtCurrentLocation;
    

    public GeoView(String title) {
        this.setTitle(title);
        listModel = new TableModel(this);
        eventHandler= new EventHandler(this);
        initGui();
    }

    @Override
    public void attach(IController controller) {

        this.controller = (GeoController) controller;
    }

    public void exec() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    public void Update(ArrayList<Address> addresses)
    {
        this.listModel.setData(addresses);
        this.listModel.fireTableDataChanged();
    }

    private void initGui() {
        GridBagLayout layout = new GridBagLayout();
        this.lblSearchAddress = new JLabel("Find Address:");
        this.txtSearchAddress = new JTextField(40);
        this.txtSearchAddress.addKeyListener(eventHandler);
        this.btnSearchAddress = new JButton("Search Now");
        
        this.lblCurrrentLocation = new JLabel("Current Address: ");
        this.txtCurrentLocation = new JTextField(50);
       this.lblCurrrentLocation.setPreferredSize(new Dimension(150, 50)); 
        this.txtCurrentLocation.setPreferredSize(new Dimension(800, 50));
        this.txtSearchAddress.setPreferredSize(new Dimension(800, 50));
        this.lblSearchAddress.setPreferredSize(new Dimension(100, 50));
        this.btnSearchAddress.setPreferredSize(new Dimension(150, 50));
        
        this.btnSearchAddress.addActionListener(this.eventHandler);
        this.txtCurrentLocation.setEditable(false);

        this.tblPanel = new JPanel(new GridBagLayout());
        this.tblPanel.setBorder(BorderFactory.createTitledBorder("Total search(0)"));
        this.table = new JTable();
        this.table.setSize(new Dimension(500, 600));
        this.table.setModel(listModel);
        //this.table.setPreferredSize(new Dimension(1000, 800));
        this.jscrpanel = new JScrollPane(this.table);
        this.tblPanel.add(this.jscrpanel);
        this.jscrpanel.setPreferredSize(this.tblPanel.getPreferredSize());
        this.jscrpanel.setPreferredSize(new Dimension(800, 300));
        this.jscrpanel.setAutoscrolls(true);
        

        this.setLayout(layout);
        GridBagConstraints g = new GridBagConstraints();

        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0;
        g.gridy = 0;
        g.anchor = GridBagConstraints.NORTHWEST;
        g.insets = new Insets(5, 5, 5, 5);
        this.add(this.lblSearchAddress, g);

        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 1;
        g.gridy = 0;
        g.gridwidth = 4;
        g.insets = new Insets(5, 5, 5, 5);
        this.add(this.txtSearchAddress, g);

        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 5;
        g.gridy = 0;
        g.gridwidth = 1;
        g.insets = new Insets(5, 5, 5, 5);
        this.add(this.btnSearchAddress, g);

        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 6;
        g.insets = new Insets(5, 5, 5, 5);
        this.add(this.tblPanel, g);
        
        
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0;
        g.gridy = 2;
        g.gridwidth = 1;
        g.insets = new Insets(5, 5, 5, 5);
        this.add(this.lblCurrrentLocation, g);
        
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 1;
        g.gridy = 2;
        g.gridwidth = 5;
        g.insets = new Insets(5, 5, 5, 5);
        this.add(this.txtCurrentLocation, g); 
        
        

        this.pack();
        this.setResizable(false);
    }

    public void connect(String currentAddress) {

        if (currentAddress != "" || currentAddress != null) {
            this.setTitle("Connected with " + currentAddress);
        } else {
            this.setTitle("Non Connection");
        }
    }

    private void makeSearch(int status) {
        
     this.controller.xhsAsyncSearch(this.txtSearchAddress.getText(),status);
    
    }

    public void updateGeoPosition(String address,String lat, String lng) {
        
       this.txtCurrentLocation.setText(address);
     
      }

    private class TableModel extends AbstractTableModel {

        private ArrayList<Address> addresses;
        private final GeoView parent;
        private final int CONST_ADDRESS_COLUMN_COUNT = 5;

        private final int TABLE_COLUMN_SN = 0;
        private final int TABLE_COLUMN_ADDR = 1;
        private final int TABLE_COLUMN_POSTCODE = 2;
        private final int TABLE_COLUMN_TOWN = 3;

        public TableModel(GeoView parent) {
            this.parent = parent;
            this.addresses = new ArrayList<>();
        }

        @Override
        public int getRowCount() {

            if (this.addresses != null) {
                return this.addresses.size();
            }

            return 0;
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Address address = this.addresses.get(rowIndex);

            if (address == null) {
                return null;
            }
            switch (columnIndex) {
                case TABLE_COLUMN_SN: {
                    return rowIndex;
                }
                case TABLE_COLUMN_ADDR: {
                    return address.addr;
                }
                case TABLE_COLUMN_POSTCODE: {
                    return address.postcode;
                }
                case TABLE_COLUMN_TOWN: {
                    return address.town;
                }
                default:
                    return null;
            }

        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            Address address = this.addresses.get(rowIndex);

            if (address != null) {
                switch (columnIndex) {
                    case TABLE_COLUMN_ADDR: {
                        address.addr = (String) value;
                    }
                    break;
                    case TABLE_COLUMN_POSTCODE: {
                        address.postcode = (String) value;
                    }
                    break;
                    case TABLE_COLUMN_TOWN: {
                        address.town = (String) value;
                    }
                    break;
                    default:
                        break;
                }
                this.addresses.set(rowIndex, address);
                this.fireTableCellUpdated(rowIndex, columnIndex);
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }

        @Override
        public Class<?> getColumnClass(int col) {
            switch (col) {
                case TABLE_COLUMN_SN:
                    return Integer.class;
                case TABLE_COLUMN_ADDR:
                    return String.class;
                case TABLE_COLUMN_POSTCODE:
                    return String.class;
                case TABLE_COLUMN_TOWN:
                    return String.class;
                default:
                    return null;
            }
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case TABLE_COLUMN_SN:
                    return "S/N";
                case TABLE_COLUMN_ADDR:
                    return "Address";
                case TABLE_COLUMN_POSTCODE:
                    return "Zip/Postcode";
                case TABLE_COLUMN_TOWN:
                    return "Location";

                default:
                    return null;
            }
        }

        private void setData(ArrayList<Address> addresses) {
         this.addresses=addresses;
         this.fireTableDataChanged();
        
        }

    }

    
    //the events
    
    
    private class  EventHandler implements ActionListener,KeyListener
            {
    private  GeoView view;
          public EventHandler(GeoView view)
          {
              this.view=view;
          }
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(e.getSource()==this.view.btnSearchAddress)
            {
                this.view.makeSearch(1);
            }
        
        }

        @Override
        public void keyTyped(KeyEvent e) {
             }

        @Override
        public void keyPressed(KeyEvent e) {
         }

        @Override
        public void keyReleased(KeyEvent e) {
            
            if(e.getSource()==this.view.txtSearchAddress)
            {
                this.view.makeSearch(0);
            }
             }
        
                
                
       }
}
