import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


        // Anderson Rodrigues Ramos CB3020533
	// Lucas Vieira dos Santos Martins CB3020223

public class FormularioPesquisa extends JFrame {
    private JTextField pesquisaTextField, nomeTextField, salarioTextField, cargoTextField;
    private JButton pesquisarButton, anteriorButton, proximoButton;
    private Connection connection;
    private ResultSet resultSet;

    public FormularioPesquisa() {
        setTitle("Formulário de Pesquisa");
        setSize(360, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pesquisaTextField = new JTextField(20);
        nomeTextField = new JTextField(20);
        salarioTextField = new JTextField(20);
        cargoTextField = new JTextField(20);
        pesquisarButton = new JButton("Pesquisar");
        anteriorButton = new JButton("Anterior");
        proximoButton = new JButton("Próximo");


        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Nome:"));
        panelSuperior.add(pesquisaTextField);
        panelSuperior.add(pesquisarButton);


        JPanel panelInformacoes = new JPanel();
        panelInformacoes.setLayout(new GridLayout(3, 2));
        panelInformacoes.add(new JLabel("Nome:"));
        panelInformacoes.add(nomeTextField);
        panelInformacoes.add(new JLabel("Salário:"));
        panelInformacoes.add(salarioTextField);
        panelInformacoes.add(new JLabel("Cargo:"));
        panelInformacoes.add(cargoTextField);


        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelInferior.add(anteriorButton);
        panelInferior.add(proximoButton);


        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(panelInformacoes, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        pesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pesquisarRegistro();
            }
        });

        anteriorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRegistroAnterior();
            }
        });

        proximoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarProximoRegistro();
            }
        });


        try {
	    String url = "jdbc:sqlserver://localhost:1433/aulajava;user:konsa;password:Haddouken";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pesquisarRegistro() {
        try {
            String pesquisa = pesquisaTextField.getText();
            String sql = "SELECT nome_func, sal_func, cargo FROM tbfunc WHERE nome_func LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + pesquisa + "%");
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nomeTextField.setText(resultSet.getString("nome_func"));
                salarioTextField.setText(resultSet.getString("sal_func"));
                cargoTextField.setText(resultSet.getString("cargo"));
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum registro encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarRegistroAnterior() {
        try {
            if (resultSet != null && resultSet.previous()) {
                nomeTextField.setText(resultSet.getString("nome_func"));
                salarioTextField.setText(resultSet.getString("sal_func"));
                cargoTextField.setText(resultSet.getString("cargo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarProximoRegistro() {
        try {
            if (resultSet != null && resultSet.next()) {
                nomeTextField.setText(resultSet.getString("nome_func"));
                salarioTextField.setText(resultSet.getString("sal_func"));
                cargoTextField.setText(resultSet.getString("cargo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FormularioPesquisa form = new FormularioPesquisa();
                form.setVisible(true);
            }
        });
    }
}
