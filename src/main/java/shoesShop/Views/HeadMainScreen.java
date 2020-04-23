/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoesShop.Views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Iryna Butenko
 */
public class HeadMainScreen extends javax.swing.JFrame  {

    /**
     * Creates new form HeadMainScreen
     */
 
    public HeadMainScreen() {
        initComponents();
         JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        Font font = new Font("Verdana", Font.PLAIN, 11);
         
        JMenuBar menuBar = new JMenuBar();
         
        JMenu fileMenu = new JMenu("Дії");
        fileMenu.setFont(font);
        JMenu totalGoodsMenu = new JMenu("Всі товари ");
         totalGoodsMenu.setFont(font);
         JMenu saleMenu = new JMenu("Продажі");
        saleMenu.setFont(font);
        JMenuItem chequeCreator=new JMenuItem("Створити чек");
        chequeCreator.setFont(font);
        chequeCreator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                ChequeForm cheq=new ChequeForm();
                cheq.setVisible(true);
            }
        });
        saleMenu.add(chequeCreator);
        JMenu newMenu = new JMenu("Додати новий запис про...");
        newMenu.setFont(font);
        fileMenu.add(newMenu);
         
        JMenuItem orderItem = new JMenuItem("Накладна");
        orderItem.setFont(font);
        newMenu.add(orderItem);
        orderItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  frame.setVisible(false);
             ConsignmentView conFrame = new ConsignmentView();
             conFrame.setVisible(true); //To change body of generated methods, choose Tools | Templates.
            }

            private void setVisible(boolean b) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
         
        JMenuItem shoeItem = new JMenuItem("Модель взуття");
        shoeItem.setFont(font);
        newMenu.add(shoeItem);
         shoeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                     frame.setVisible(false);
             NewShoesCreator newShoesFrame = new NewShoesCreator();
             newShoesFrame.setVisible(true);
            }
         });
//        JMenuItem producerItem = new JMenuItem("Виробник");
//        producerItem.setFont(font);
//        newMenu.add(producerItem);
          JMenuItem staffItem = new JMenuItem("Продавець");
        staffItem.setFont(font);
        staffItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                     frame.setVisible(false);
             NewWorkerFrame newWorkerFrame = new NewWorkerFrame();
             newWorkerFrame.setVisible(true);
            }
         });
        newMenu.add(staffItem);
        JMenuItem openItem = new JMenuItem("");
        openItem.setFont(font);
        fileMenu.add(openItem);
          JMenu refreshMenu = new JMenu("Редагувати запис про...");
        refreshMenu.setFont(font);
        fileMenu.add(refreshMenu);
         
        JMenuItem orderItemRefresh = new JMenuItem("Накладна");
        orderItemRefresh.setFont(font);
        refreshMenu.add(orderItemRefresh);
 
         
        JMenuItem shoeItemRefresh = new JMenuItem("Модель взуття");
        shoeItemRefresh.setFont(font);
        refreshMenu.add(shoeItemRefresh);
         
        JMenuItem producerItemRefresh = new JMenuItem("Виробник");
        producerItemRefresh.setFont(font);
        refreshMenu.add(producerItemRefresh);
          JMenuItem staffItemRefresh = new JMenuItem("Продавець");
        staffItemRefresh.setFont(font);
        refreshMenu.add(staffItemRefresh);
      JMenu infoMenu = new JMenu("Вивести дані про...");
        infoMenu.setFont(font);
        fileMenu.add(infoMenu);
        
         JMenuItem infoProdItem = new JMenuItem("Виробника");
        infoProdItem.setFont(font);
        infoMenu.add(infoProdItem);
        JMenuItem infoOrderItem = new JMenuItem("Накладну");
        infoOrderItem.setFont(font);
        infoMenu.add( infoOrderItem);
        JMenuItem infoStaffItem = new JMenuItem("Продавця");
        infoStaffItem.setFont(font);
        infoMenu.add(infoStaffItem);
        infoMenu.addSeparator();
             JMenuItem infoShoeItem = new JMenuItem("Моделі взуття");
        infoShoeItem.setFont(font);
        infoMenu.add(infoShoeItem);
        infoMenu.addSeparator();
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setFont(font);
        fileMenu.add(exitItem);
         
        exitItem.addActionListener(new ActionListener() {           
            public void actionPerformed(ActionEvent e) {
                System.exit(0);             
            }           
        });
        JMenuItem allItem = new JMenuItem("Робота з товарами");
        allItem.setFont(font);
        totalGoodsMenu.add(allItem);
        allItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  frame.setVisible(false);
             ShopGoodsFrame shopGoodsFrame = new ShopGoodsFrame();
             shopGoodsFrame.setVisible(true); //To change body of generated methods, choose Tools | Templates.
            }

            private void setVisible(boolean b) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        JMenuItem statItem= new JMenuItem("Подивитися статистику магазину");
        statItem.setFont(font);
        totalGoodsMenu.add(statItem);
        menuBar.add(fileMenu);
                 menuBar.add(totalGoodsMenu);
                 menuBar.add(saleMenu);
        frame.setJMenuBar(menuBar);
         
     
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(400,300);
        frame.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(HeadMainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HeadMainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HeadMainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HeadMainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        HeadMainScreen headMainScreen = new HeadMainScreen();
                
            
        
    }

   

 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
