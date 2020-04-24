/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoesShop.Views;

import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.HTMLDocument.Iterator;

import shoesShop.DB.Category;
import shoesShop.DB.Main;
import shoesShop.DB.Matherial;
import shoesShop.DB.ProductModel;
import shoesShop.DB.Produser;
import shoesShop.DB.Season;
import shoesShop.Exceptions.ArgumentException;

/**
 *
 * @author Iryna Butenko
 */
public class ShopGoodsFrame extends javax.swing.JFrame {

	private LinkedList<ProductModel> products;
	private String [] n_models;
	private String [] colors;
	private Integer [] sizes;
	private String [] materials;
	private String [] categories;
	private String [] seasons;
	private Integer [] produsers;
	private boolean noFilter = false;
    /**
     * Creates new form ShopGoodsFrame
     */
    public ShopGoodsFrame() {
    	products = ProductModel.getAllModels();
    	
    	
    	LinkedList<String> c = Main.getAllColors();
    	colors = new String [c.size()];
        c.toArray(colors);
        
        sizes = new Integer[21];
    	for(int i=26, j=0; i<47; i++, j++) {
    		sizes[j] =i;
    	}
    	
		LinkedList<Produser> pr = Produser.getAll();
    	produsers = new Integer[pr.size()];
    	
    	for(int i=0; i<pr.size(); i++) {
    		produsers[i] = pr.get(i).getN_company();
    	}
    	
    	Season [] s = Season.values();
    	seasons = new String[s.length];
    	for(int i=0; i<s.length; i++) {
    		seasons[i] = s[i].getName();
    	}
    	
    	Category [] cat = Category.values();
    	categories = new String[cat.length];
    	for(int i=0; i<cat.length; i++) {
    		categories[i] = cat[i].getName();
    	}
    	
    	LinkedList<Matherial> m = Matherial.getAllMatherials();
    	materials = new String[m.size()];
    	for(int i=0; i<m.size(); i++) {
    		materials[i] = m.get(i).getName(); 
    	}
    	
    	n_models = new String[products.size()];
    	for(int i=0; i<products.size(); i++) {
    		n_models[i] = products.get(i).getN_model(); 
    	}
    	
        initComponents();
        n_company.setSelectedIndex(-1);
        resetFilter();
        fillTable(products);
    }

    private void fillTable(LinkedList<ProductModel> l) {
    	
    	jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "Номер", "Назва", "Ціна закупівлі", "Націнка", "Ціна для продажу", "Кількість"
                }
            ) {
                Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class
                };
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
    	
    	ProductModel cur;
    	for(int i =0; i<l.size(); i++) {
    		cur = l.get(i);
    		((DefaultTableModel) jTable1.getModel()).addRow(new Object[]{cur.getN_model(), cur.getName(), 
    				cur.getPrice_purchase(), cur.getExtra_charge(), cur.getShop_price(), cur.getNumber()});
    	}
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Avail = new javax.swing.ButtonGroup();
        Seasons = new javax.swing.ButtonGroup();
        notOnStock = new javax.swing.JRadioButton();
        onStock = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        isMale = new javax.swing.JCheckBox();
        isFemale = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        material = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        color = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        size = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        n_company = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        category = new javax.swing.JComboBox<>();
        isChild = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        season = new javax.swing.JComboBox<>();
        all = new javax.swing.JRadioButton();
        n_model = new javax.swing.JComboBox<>();
        details = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Avail.add(notOnStock);
        notOnStock.setText("Немає");
        notOnStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notOnStockActionPerformed(evt);
            }
        });

        Avail.add(onStock);
        onStock.setText("Наявні");
        onStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onStockActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel1.setText("Наявність:");

        jScrollPane2.setBackground(new java.awt.Color(204, 204, 255));
        jScrollPane2.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Номер", "Назва", "Ціна закупівлі", "Націнка", "Ціна для продажу", "Кількість"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        isMale.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        isMale.setText("Чоловіче");
        isMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isMaleActionPerformed(evt);
            }
        });

        isFemale.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        isFemale.setText("Жіноче");
        isFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isFemaleActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel2.setText("Тип:");

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel3.setText("Сезон:");

        material.setModel(new DefaultComboBoxModel(materials));
        material.setToolTipText("");
        material.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                materialActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel4.setText("Матеріал:");

        color.setModel(new DefaultComboBoxModel(colors));
        color.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel5.setText("Колір");

        size.setModel(new DefaultComboBoxModel(sizes));
        size.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sizeActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel6.setText("Розмір:");

        n_company.setModel(new DefaultComboBoxModel(produsers));
        n_company.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                n_companyActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel7.setText("Виробник:");

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel8.setText("Категорія:");

        category.setModel(new DefaultComboBoxModel(categories));
        category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryActionPerformed(evt);
            }
        });

        isChild.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        isChild.setText("Дитяче");
        isChild.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isChildActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Пошук за номером:");
        jLabel9.setToolTipText("");
        jLabel9.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jButton1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton1.setText("Скинути налаштування");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jButton2.setText("Назад");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        season.setModel(new DefaultComboBoxModel(seasons));
        season.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seasonActionPerformed(evt);
            }
        });

        Avail.add(all);
        all.setText("Усі");
        all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allActionPerformed(evt);
            }
        });

        n_model.setEditable(true);
        n_model.setModel(new DefaultComboBoxModel(n_models));
        n_model.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                n_modelActionPerformed(evt);
            }
        });

        details.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        details.setText("детальніше");
        details.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(color, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(material, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(category, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(n_company, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(n_model, javax.swing.GroupLayout.Alignment.LEADING, 0, 149, Short.MAX_VALUE))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(notOnStock, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(isFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(isChild, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(size, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(season, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                                .addComponent(isMale, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(onStock, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(all, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(details, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(19, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(details)
                .addGap(9, 9, 9)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(n_model, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(n_company, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(material, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(season, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(isMale))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(isFemale)
                    .addComponent(isChild))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onStock))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, Short.MAX_VALUE)
                .addComponent(notOnStock)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(all, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void notOnStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notOnStockActionPerformed
    	if(!noFilter) setFilters();
    }//GEN-LAST:event_notOnStockActionPerformed

    private void onStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onStockActionPerformed
    	if(!noFilter) setFilters();
    }//GEN-LAST:event_onStockActionPerformed

    private void isMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isMaleActionPerformed
    	if(!noFilter) setFilters();
    }//GEN-LAST:event_isMaleActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allActionPerformed
    	if(!noFilter) setFilters();
    }//GEN-LAST:event_allActionPerformed

    private void n_modelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_n_modelActionPerformed
        ProductModel p = ProductModel.getModel((String)n_model.getSelectedItem());
        if(p!=null) {
        	resetFilter();
        	LinkedList<ProductModel> l = new LinkedList<ProductModel>();
        	l.add(p);
        	fillTable(l);
        }
        
    }//GEN-LAST:event_n_modelActionPerformed

    private void n_companyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_n_companyActionPerformed
    	if(!noFilter) setFilters();
    }//GEN-LAST:event_n_companyActionPerformed

    private void categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryActionPerformed
    	if(!noFilter) setFilters();
    }//GEN-LAST:event_categoryActionPerformed

    private void materialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_materialActionPerformed
    	if(!noFilter) setFilters();
    }//GEN-LAST:event_materialActionPerformed

    private void colorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorActionPerformed
    	if(!noFilter) setFilters();
    }//GEN-LAST:event_colorActionPerformed

    private void sizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sizeActionPerformed
    	if(!noFilter) setFilters();
    }//GEN-LAST:event_sizeActionPerformed

    private void seasonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seasonActionPerformed
    	if(!noFilter) setFilters();
    }//GEN-LAST:event_seasonActionPerformed

    private void isFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isFemaleActionPerformed
    	if(!noFilter) setFilters();
    }//GEN-LAST:event_isFemaleActionPerformed

    private void isChildActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isChildActionPerformed
    	if(!noFilter) setFilters();
    }//GEN-LAST:event_isChildActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        resetFilter();
        n_model.setSelectedIndex(-1);
        fillTable(products);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void detailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsActionPerformed
        //if(jTable1.selec)
    	try {
    	int r = jTable1.getSelectedRow();
        ModelDetails m = new ModelDetails();
        ProductModel p = ProductModel.getModel((String)jTable1.getValueAt(r, 0));
        m.set(this, p);
        m.setVisible(true);
    	} catch (ArrayIndexOutOfBoundsException e) {
    		
    	}
    }//GEN-LAST:event_detailsActionPerformed

    protected void resetFilter() {
    	n_company.setSelectedIndex(-1);
    	category.setSelectedIndex(-1);
    	material.setSelectedIndex(-1);
    	color.setSelectedIndex(-1);
    	size.setSelectedIndex(-1);
    	season.setSelectedIndex(-1);
    }
    
    private void setFilters() {
    	LinkedList<ProductModel> p = ProductModel.getAllModels();
    	ListIterator<ProductModel> i = p.listIterator();
    	try {
	    	if(n_company.getSelectedIndex()>-1) 
	    		p = Produser.getAllModel((int)n_company.getSelectedItem());
	    	i = p.listIterator();
	    	while(i.hasNext())  {
	    		ProductModel pr = i.next();
	    		
	    	if(category.getSelectedIndex()>-1) 
	    				if(pr.getCategory()!=Category.getCategorynByName((String) category.getSelectedItem()))
	    					{i.remove(); pr = null;}
	    	
	    	if(material.getSelectedIndex()>-1 && pr!=null) {
	    		boolean consist = false;
	    		for(Matherial m: pr.getMaterials()) {
    				if(m.getName().equals((String)material.getSelectedItem())) consist = true;
	    		}
				if(!consist){i.remove(); pr = null;}
	    	}
	    	
	    	if(color.getSelectedIndex()>-1 && pr!=null) 
    				if(!pr.getColors().contains((String)color.getSelectedItem()))
    				{i.remove(); pr = null;}
	    	
	    	if(size.getSelectedIndex()>-1 && pr!=null) 
    				if(!pr.getSizes().contains((Integer)size.getSelectedItem()))
    				{i.remove(); pr = null;}
	    	
	    	if(season.getSelectedIndex()>-1 && pr!=null) 
    				if(pr.getSeason()!=Season.getSeasonByName((String) season.getSelectedItem()))
    				{i.remove(); pr = null;}
	    	
	    	if(isMale.isSelected() && isFemale.isSelected() && isChild.isSelected() && pr!=null) {
	    		if(!pr.isIs_child() || !pr.isIs_female() || !pr.isIs_male())
	    		{i.remove(); pr = null;}
	    	} else if(isMale.isSelected() && isFemale.isSelected() && pr!=null) {
	    		
	    		if( !pr.isIs_female() || !pr.isIs_male() && pr!=null) 
	    		{i.remove(); pr = null;}
	    	} else if(isMale.isSelected() && isChild.isSelected() && pr!=null) {
	    		if( !pr.isIs_child() || !pr.isIs_male()) 
	    		{i.remove(); pr = null;}
	    		
	    	}  else if(isFemale.isSelected() && isChild.isSelected() && pr!=null) {
	    		if( !pr.isIs_child() || !pr.isIs_female()) 
	    		{i.remove(); pr = null;}
	    		
	    	} else if(isChild.isSelected() && pr!=null) {
	    		if( !pr.isIs_child()) 
	    		{i.remove(); pr = null;}
	    	} else if(isMale.isSelected() && pr!=null) {
	    		if( !pr.isIs_male())
	    		{i.remove(); pr = null;}
	    	}else if(isFemale.isSelected() && pr!=null) {
	    		if( !pr.isIs_female())
	    		{i.remove(); pr = null;}
	    	}
	    	
	    	if(notOnStock.isSelected() && pr!=null) {
	    		if( pr.getNumber()>0) {i.remove(); pr = null;}
	    	} else if(onStock.isSelected() && pr!=null) {
	    		if(pr.getNumber()<1) {i.remove(); pr = null;}
	    	}
	    	}
	    	
	    	
	    	fillTable(p);
    	} catch(ArgumentException e) {
    		e.printStackTrace();
    	}
    }
    

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ShopGoodsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShopGoodsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShopGoodsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShopGoodsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShopGoodsFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Avail;
    private javax.swing.ButtonGroup Seasons;
    private javax.swing.JRadioButton all;
    private javax.swing.JComboBox<String> category;
    private javax.swing.JComboBox<String> color;
    private javax.swing.JButton details;
    private javax.swing.JCheckBox isChild;
    private javax.swing.JCheckBox isFemale;
    private javax.swing.JCheckBox isMale;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JComboBox<String> material;
    protected javax.swing.JComboBox<String> n_company;
    private javax.swing.JComboBox<String> n_model;
    private javax.swing.JRadioButton notOnStock;
    private javax.swing.JRadioButton onStock;
    private javax.swing.JComboBox<String> season;
    private javax.swing.JComboBox<String> size;
    // End of variables declaration//GEN-END:variables
}
