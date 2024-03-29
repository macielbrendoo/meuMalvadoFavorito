package edu.view.lecomparativos;

import edu.controller.action_listeners.le_comparativos.Comparativo;
import edu.controller.dto.ComparativoDTO;
import edu.model.dao.EleitorDAO;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

public class TelaComparativo {
    String login;
    EleitorDAO eleitorDao = new EleitorDAO();
    List<ComparativoDTO> comparativoDTO = new ArrayList<>();


    public TelaComparativo(String login) {
        this.login = login;
        criarLayout();
    }


    private void criarLayout() {
        try {
            comparativoDTO = eleitorDao.getComparativo(eleitorDao.getEleitorId(login));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        JFrame janela = new JFrame("Comparativo");

        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbcPrincipal = new GridBagConstraints();

        JPanel painelCandidato = new JPanel(new GridBagLayout());
        GridBagConstraints gbcCandidato = new GridBagConstraints();
        gbcPrincipal.insets = new Insets(4, 4, 4, 4);
        gbcCandidato.insets = new Insets(4, 4, 4, 4);

        painelCandidato.setBorder(new CompoundBorder(new TitledBorder("Candidato"), null));

        JLabel lblMensagem = new JLabel("De acordo com as suas respostas o candidato mais parecido com voc� � :");
        JTextArea txtCandidato = new JTextArea();
        txtCandidato.setEditable(false);
//        txtCandidato.setText("Candidato");
        txtCandidato.setText(comparativoDTO.get(0).getNomeDeputado());
        JButton btnRank = new JButton("Ver Rank");
        JButton btnVoltar = new JButton("Voltar");

        Comparativo comparativoOuvinte = new Comparativo(janela, login,comparativoDTO);
        btnVoltar.addActionListener(comparativoOuvinte);
        btnRank.addActionListener(comparativoOuvinte);

        gbcCandidato.gridx = 0;
        gbcCandidato.gridy = 0;
        gbcCandidato.fill = GridBagConstraints.HORIZONTAL;
        painelCandidato.add(lblMensagem, gbcCandidato);

        gbcCandidato.fill = GridBagConstraints.RELATIVE;
        gbcCandidato.gridx = 0;
        gbcCandidato.gridy = 1;
        gbcCandidato.anchor = GridBagConstraints.WEST;
        painelCandidato.add(txtCandidato, gbcCandidato);

        gbcCandidato.gridx = 2;
        gbcCandidato.gridy++;
        gbcCandidato.anchor = GridBagConstraints.EAST;

        gbcPrincipal.fill = GridBagConstraints.HORIZONTAL;
        gbcPrincipal.gridx = 0;
        gbcPrincipal.gridy = 0;
        gbcPrincipal.gridheight = 2;
        gbcPrincipal.gridwidth = 3;
        painelPrincipal.add(painelCandidato, gbcPrincipal);

        gbcPrincipal.fill = GridBagConstraints.NONE;
        gbcPrincipal.anchor = GridBagConstraints.WEST;
        gbcPrincipal.gridheight = 1;
        gbcPrincipal.gridwidth = 1;
        gbcPrincipal.gridx = 1;
        gbcPrincipal.gridy = 2;
        painelPrincipal.add(btnRank, gbcPrincipal);
        gbcPrincipal.gridx = 2;
        painelPrincipal.add(btnVoltar, gbcPrincipal);

        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
        janela.setLocation((ds.width - 650) / 2, (ds.height - 650) / 2);

        janela.setContentPane(painelPrincipal);

        janela.setSize(650, 650);
        janela.setVisible(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
